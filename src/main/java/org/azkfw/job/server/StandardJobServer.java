/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.azkfw.job.server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.azkfw.business.property.Property;
import org.azkfw.business.property.PropertyFile;
import org.azkfw.business.property.PropertyManager;
import org.azkfw.business.property.PropertySupport;
import org.azkfw.configuration.ConfigurationFormatException;
import org.azkfw.context.Context;
import org.azkfw.context.ContextSupport;
import org.azkfw.job.commandline.CommandLineArgument;
import org.azkfw.job.commandline.CommandLineArgumentPurser;
import org.azkfw.job.commandline.StandardCommandLineArgumentPurser;
import org.azkfw.job.context.JobContext;
import org.azkfw.job.job.Job;
import org.azkfw.job.parameter.Parameter;
import org.azkfw.job.store.JobSessionStore;
import org.azkfw.job.worker.JobWorker;
import org.azkfw.log.LoggerFactory;
import org.azkfw.persistence.session.SessionSupport;
import org.azkfw.plugin.PluginManager;
import org.azkfw.plugin.PluginServiceException;
import org.azkfw.util.StringUtility;

/**
 * このクラスは、標準のジョブサーバクラスです。
 * 
 * 実行引数 -baseDir ベースパス -config 設定ファイル -logClass ロガーファクトリークラス -logConfig
 * ロガーフ設定ファイル
 * 
 * @since 1.0.0
 * @version 1.0.0 2013/02/15
 * @author Kawakicchi
 */
public final class StandardJobServer extends AbstractJobServer {

	/**
	 * メイン処理
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {
		CommandLineArgumentPurser purser = new StandardCommandLineArgumentPurser();
		CommandLineArgument arg = purser.purse(args);

		String baseDir = arg.getOptionValue("baseDir");
		String config = arg.getOptionValue("config");

		Context context = null;
		if (StringUtility.isNotEmpty(baseDir)) {
			context = new JobContext(baseDir);
		} else {
			context = new JobContext();
		}

		// log
		String logClass = arg.getOptionValue("logClass");
		String logConfig = arg.getOptionValue("logConfig");
		LoggerFactory.load(logClass, logConfig, context);

		StandardJobServer server = new StandardJobServer(context);
		if (StringUtility.isNotEmpty(config)) {
			server.setConfig(config);
		}
		// TODO パラメータ
		server.setParameter(new HashMap<String, Object>());

		server.run();
	}

	/**
	 * コンテキスト
	 */
	private Context context;

	/**
	 * コンフィグ
	 */
	private String config;

	/**
	 * ワーカリスト
	 */
	private List<JobWorker> workers;

	private String stopFile;

	private boolean stopRequest = false;

	private Map<String, Object> params;

	/**
	 * コンストラクタ
	 */
	public StandardJobServer() {
		super(StandardJobServer.class);
		context = new JobContext();
		workers = new ArrayList<JobWorker>();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aContext コンテキスト
	 */
	public StandardJobServer(final Context aContext) {
		super(StandardJobServer.class);
		context = aContext;
		workers = new ArrayList<JobWorker>();
	}

	/**
	 * 設定ファイルを設定する
	 * 
	 * @param aConfig 設定
	 */
	public void setConfig(final String aConfig) {
		config = aConfig;
	}

	/**
	 * パラメータを設定する。
	 * 
	 * @param aParams パラメータ
	 */
	public void setParameter(final Map<String, Object> aParams) {
		params = new HashMap<String, Object>(aParams);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected boolean doRun() {
		boolean result = false;

		Class<? extends Job> jobClass = null;
		int workerCount = 1;
		Properties p = new Properties();
		try {
			if (StringUtility.isNotEmpty(config)) {
				InputStream stream = context.getResourceAsStream(config);
				if (null != stream) {
					p.load(stream);
				} else {
					System.out.println("Not found config file.[" + config + "]");
					return false;
				}
			}
			String job = p.getProperty("server.worker.job");
			if (StringUtility.isEmpty(job)) {
				System.out.println("Not setting job class.[server.worker.job]");
				return false;
			}
			jobClass = (Class<? extends Job>) Class.forName(job);

			String plugin = p.getProperty("server.plugin");
			if (StringUtility.isNotEmpty(plugin)) {
				InputStream stream = context.getResourceAsStream(plugin);
				if (null != stream) {
					PluginManager.initialize();
					PluginManager.load(stream, context);
				}
			}

			String strCount = p.getProperty("server.worker.thread");
			if (StringUtility.isNotEmpty(strCount)) {
				workerCount = Integer.parseInt(strCount);
			}
			stopFile = p.getProperty("server.control.stop.file");

		} catch (IOException ex) {
			fatal(ex);
			return false;
		} catch (ClassNotFoundException ex) {
			fatal(ex);
			return false;
		} catch (ConfigurationFormatException ex) {
			fatal(ex);
			return false;
		} catch (PluginServiceException ex) {
			fatal(ex);
			return false;
		}

		try {
			createWorker(jobClass, params, workerCount);
			startWorker();
			Thread.sleep(1 * 1000);
			while (!isStopWorker()) {
				checkStop();

				Thread.sleep(10 * 1000);
			}
			result = true;
		} catch (IllegalAccessException ex) {
			fatal(ex);
		} catch (InstantiationException ex) {
			fatal(ex);
		} catch (InterruptedException ex) {
			fatal(ex);
		}

		info("Server stop.");
		return result;
	}

	private void checkStop() {
		if (!stopRequest) {
			if (StringUtility.isNotEmpty(stopFile)) {
				String path = context.getAbstractPath(stopFile);
				File file = new File(path);
				if (file.isFile()) {
					info("Found server stop request file.[" + stopFile + "]");
					for (JobWorker worker : workers) {
						worker.stop();
					}
					stopRequest = true;
				}
			}
		}
	}

	private void createWorker(final Class<? extends Job> aJob, final Map<String, Object> aParams, final int aCount) throws IllegalAccessException,
			InstantiationException {
		info("Create worker.[count: " + aCount + "]");
		Property property = null;
		PropertyFile propertyFile = aJob.getAnnotation(PropertyFile.class);
		if (null != propertyFile) {
			String value = propertyFile.value();
			if (StringUtility.isNotEmpty(value)) {
				property = PropertyManager.get(aJob);
				if (null == property) {
					property = PropertyManager.load(aJob, context);
				}
			}
		}

		Parameter parameter = new Parameter(aParams);

		for (int i = 0; i < aCount; i++) {
			Job job = aJob.newInstance();

			if (job instanceof SessionSupport) {
				((SessionSupport) job).setSession(new JobSessionStore());
			}
			if (job instanceof ContextSupport) {
				((ContextSupport) job).setContext(context);
			}
			if (null != property) {
				if (job instanceof PropertySupport) {
					((PropertySupport) job).setProperty(property);
				} else {
					warn("This job is not property support.[" + job.getClass().getName() + "]");
				}
			}

			JobWorker worker = new JobWorker(job, parameter);
			workers.add(worker);
		}
	}

	private void startWorker() {
		info("Start worker.");
		for (JobWorker worker : workers) {
			Thread t = new Thread(worker);
			t.start();
		}
	}

	private boolean isStopWorker() {
		int runWorker = 0;
		for (JobWorker worker : workers) {
			if (worker.isWorking()) {
				runWorker++;
			}
		}

		if (0 == runWorker) {
			info("All worker stop.");
			return true;
		} else {
			if (stopRequest) {
				info("Stop waiting of worker.(" + (workers.size() - runWorker) + "/" + workers.size() + ")");
			}
			return false;
		}
	}
}
