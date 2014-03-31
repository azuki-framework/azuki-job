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
 * このインターフェースは、ジョブの結果情報を表現するインターフェースです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2013/02/01
 * @author Kawakicchi
 */
public interface JobResult {

	/**
	 * 結果を取得する。
	 * 
	 * @return 結果
	 */
	public boolean isResult();

	/**
	 * ジョブを再実行するか判断する。
	 * 
	 * @return 判断結果
	 */
	public boolean isContinue();

	/**
	 * ジョブ再実行までの待ち時間(ms)を取得する。
	 * 
	 * @return 待ち時間(ms)
	 */
	public long getWait();
}
