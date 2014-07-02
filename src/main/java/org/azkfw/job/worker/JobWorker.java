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
package org.azkfw.job.worker;

import org.azkfw.job.JobServiceException;
import org.azkfw.job.job.Job;
import org.azkfw.job.parameter.Parameter;
import org.azkfw.job.result.JobResult;
import org.azkfw.lang.LoggingObject;

/**
 * このクラスは、ジョブ用のワーカークラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2013/02/15
 * @author Kawakicchi
 */
public class JobWorker extends LoggingObject implements Runnable {

	/**
	 * ジョブ
	 */
	private Job job;

	/**
	 * パラメータ情報
	 */
	private Parameter parameter;

	/**
	 * 稼働フラグ
	 */
	private boolean working;

	/**
	 * 停止依頼
	 */
	private boolean stop;

	/**
	 * コンストラクタ
	 * 
	 * @param aJob ジョブ
	 */
	public JobWorker(final Job aJob) {
		super(JobWorker.class);
		job = aJob;
		working = false;
		stop = false;
		parameter = new Parameter();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aJob ジョブ
	 */
	public JobWorker(final Job aJob, final Parameter aParameter) {
		super(JobWorker.class);
		job = aJob;
		working = false;
		stop = false;
		parameter = aParameter;
	}

	public void stop() {
		info("Request stop.");
		stop = true;
	}

	@Override
	public void run() {
		if (working) {
			warn("Worker has already run.");
			return;
		}
		stop = false;
		working = true;
		info("Worker start.[job: \"" + job.getClass().getName() + "\"]");

		try {

			job.initialize();

			while (!stop) {

				JobResult rslt = job.execute(parameter);

				if (null == rslt) {
					error("JobResult is null.");
					break;
				}
				if (!rslt.isResult()) {
					info("Job result.[error]");
					break;
				}
				if (!rslt.isContinue()) {
					info("Job result.[success]");
					break;
				}
				if (stop) {
					break;
				}
				Thread.sleep(rslt.getWait());
			}

		} catch (JobServiceException ex) {
			fatal(ex);
		} catch (InterruptedException ex) {
			fatal(ex);
		} catch (Exception ex) {
			fatal(ex);
		} finally {

			job.destroy();
		}

		info("Worker stop.");
		working = false;
	}

	/**
	 * ワーカーが稼働中か判断する。
	 * 
	 * @return 稼働中の場合、<code>true</code>を返す。
	 */
	public boolean isWorking() {
		return working;
	}
}
