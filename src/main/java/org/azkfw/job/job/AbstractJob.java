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
package org.azkfw.job.job;

import org.azkfw.job.JobServiceException;
import org.azkfw.job.parameter.Parameter;
import org.azkfw.job.result.JobResult;
import org.azkfw.log.LoggingObject;

/**
 * このクラスは、ジョブ機能を実装する基底クラスです。
 * <p>
 * このクラスは、プロパティ機能をサポートします。
 * </p>
 * 
 * @since 1.0.0
 * @version 1.0.0 2013/01/15
 * @author Kawakicchi
 */
public abstract class AbstractJob extends LoggingObject implements Job {

	/**
	 * コンストラクタ
	 */
	public AbstractJob() {
		super(Job.class);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aName Name
	 */
	public AbstractJob(final String aName) {
		super(aName);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aClass Class
	 */
	public AbstractJob(final Class<?> aClass) {
		super(aClass);
	}

	@Override
	public final void initialize() {
		doInitialize();
	}

	@Override
	public final void destroy() {
		doDestroy();
	}

	@Override
	public final JobResult execute(final Parameter aParameter) throws JobServiceException {
		JobResult result = null;

		try {
			doBeforeExecute();
			result = doExecute(aParameter);
			doAfterExecute();
		} catch (JobServiceException ex) {
			fatal(ex);
		} finally {

		}

		return result;
	}

	/**
	 * 初期化処理を行う。
	 */
	protected abstract void doInitialize();

	/**
	 * 解放処理を行う。
	 */
	protected abstract void doDestroy();

	/**
	 * ジョブ実行直前の処理を行う。
	 * <p>
	 * ジョブ実行直前に処理を行いたい場合、このメソッドをオーバーライドしスーパークラスの同メソッドを呼び出した後で処理を記述すること。
	 * </p>
	 */
	protected void doBeforeExecute() {

	}

	/**
	 * ジョブ実行直後の処理を行う。
	 * <p>
	 * ジョブ実行直後に処理を行いたい場合、このメソッドをオーバーライドし処理を記述した後でスーバークラスの同メソッドを呼び出すこと。
	 * </p>
	 */
	protected void doAfterExecute() {

	}

	/**
	 * ジョブを実行する。
	 * 
	 * @param aParameter パラメータ情報
	 * @return 結果
	 * @throws JobServiceException ジョブ機能に起因する問題が発生した場合
	 */
	protected abstract JobResult doExecute(final Parameter aParameter) throws JobServiceException;

}
