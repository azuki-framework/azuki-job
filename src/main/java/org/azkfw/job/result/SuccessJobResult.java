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
package org.azkfw.job.result;

/**
 * このクラスは、ジョブ実行成功情報を保持するクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2013/02/20
 * @author Kawakicchi
 */
public final class SuccessJobResult implements JobResult {

	private boolean continueFlg;
	private long wait;

	/**
	 * コンストラクタ
	 */
	public SuccessJobResult() {
		continueFlg = false;
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aWait 実行待ち時間（ミリ秒）
	 */
	public SuccessJobResult(long aWait) {
		continueFlg = true;
		wait = aWait;
	}

	@Override
	public boolean isResult() {
		return true;
	}

	@Override
	public boolean isContinue() {
		return continueFlg;
	}

	@Override
	public long getWait() {
		return wait;
	}

}
