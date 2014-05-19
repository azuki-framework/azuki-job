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
package org.azkfw.job.commandline;

/**
 * このクラスは、標準のコマンドライン引数解析クラスです。
 * <p>
 * Job [-option...] [-optionValue...] [argument...]
 * <p>
 * TODO JOB [-option...] [argument...]の場合、最後のoptionがoptionValueとして認識してしまう。
 * </p>
 * </p>
 * 
 * @since 1.0.0
 * @version 1.0.0 2013/02/16
 * @author Kawakicchi
 */
public final class StandardCommandLineArgumentPurser implements CommandLineArgumentPurser {

	/**
	 * コンストラクタ
	 */
	public StandardCommandLineArgumentPurser() {

	}

	@Override
	public CommandLineArgument purse(final String[] aArguments) {
		CommandLineArgument arg = new CommandLineArgument();
		for (int i = 0; i < aArguments.length; i++) {
			String value = aArguments[i];
			if (value.startsWith("-")) {
				String key = value.substring(1);
				if (aArguments.length > i + 1) {
					String nextValue = aArguments[i + 1];
					if (nextValue.startsWith("-")) {
						arg.setOption(key);
					} else {
						arg.setOptionValue(key, nextValue);
					}
				} else {
					arg.setOption(key);
				}
			} else {
				arg.addArgument(value);
			}
		}
		return arg;
	}

}
