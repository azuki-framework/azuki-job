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
package jp.afw.job.result;

/**
 * このクラスは、ジョブ再実行を指示するジョブ結果クラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2013/02/15
 * @author Kawakicchi
 */
public final class ContinueJobResult implements JobResult {

	/**
	 * 待ち時間(ms)
	 */
	private long wait;

	/**
	 * コンストラクタ
	 * <p>
	 * 待ち時間なしで再実行する。
	 * </p>
	 */
	public ContinueJobResult() {
		wait = 0;
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aWait 待ち時間(ms)
	 */
	public ContinueJobResult(long aWait) {
		wait = aWait;
	}

	@Override
	public boolean isResult() {
		return true;
	}

	@Override
	public boolean isContinue() {
		return true;
	}

	@Override
	public long getWait() {
		return wait;
	}

}
