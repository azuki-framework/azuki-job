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

/**
 * このクラスは、標準のジョブサーバクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/01/20
 * @author Kawakicchi
 */
public class MultiTaskJobServer extends AbstractJobServer {

	public static void main(final String[] args) {
		JobServer server = new MultiTaskJobServer();
		server.run();
	}

	public MultiTaskJobServer() {
		super(MultiTaskJobServer.class);
	}

	@Override
	protected boolean doRun() {

		try {
			// メインタスク
			while (true) {

				if (isStopFlag()) {
					stopRequest();
					if (isStop()) {
						break;
					}
				}
				Thread.sleep(1000);
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}

		return true;
	}

	private boolean isStop() {
		return false;
	}

	private boolean isStopFlag() {
		return false;
	}

	private void stopRequest() {

	}
}
