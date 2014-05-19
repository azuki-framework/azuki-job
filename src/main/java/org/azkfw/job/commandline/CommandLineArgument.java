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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * このクラスは、コマンドライン引数情報を保持するクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2013/02/16
 * @author Kawakicchi
 */
public final class CommandLineArgument {

	/**
	 * オプション情報
	 */
	private Set<String> options;

	/**
	 * オプション値情報
	 */
	private Map<String, String> optionValues;

	/**
	 * 引数情報
	 */
	private List<String> arguments;

	/**
	 * コンストラクタ
	 */
	public CommandLineArgument() {
		options = new HashSet<String>();
		optionValues = new HashMap<String, String>();
		arguments = new ArrayList<String>();
	}

	/**
	 * オプションを設定する。
	 * 
	 * @param aOption オプション
	 */
	public void setOption(final String aOption) {
		options.add(aOption);
	}

	/**
	 * オプション値を設定する。
	 * 
	 * @param aOption オプション
	 * @param aValue 値
	 */
	public void setOptionValue(final String aOption, final String aValue) {
		optionValues.put(aOption, aValue);
	}

	/**
	 * 引数を追加する。
	 * 
	 * @param aValue 引数値
	 */
	public void addArgument(final String aValue) {
		arguments.add(aValue);
	}

	/**
	 * オプションが存在するか判断する。
	 * 
	 * @param aOption オプション
	 * @return 判断
	 */
	public boolean hasOption(final String aOption) {
		return options.contains(aOption);
	}

	/**
	 * オプション値が存在するか判断する。
	 * 
	 * @param aOption オプション
	 * @return 判断
	 */
	public boolean hasOptionValue(final String aOption) {
		return optionValues.containsKey(aOption);
	}

	/**
	 * オプション値を取得する。
	 * 
	 * @param aOption キー
	 * @return 値
	 */
	public String getOptionValue(final String aOption) {
		return optionValues.get(aOption);
	}

	/**
	 * 引数値を取得する。
	 * 
	 * @param aIndex インデックス
	 * @return 値
	 */
	public String getArgument(final int aIndex) {
		return arguments.get(aIndex);
	}

	/**
	 * 引数値の数を取得する。
	 * 
	 * @return 数
	 */
	public int getArgumentCount() {
		return arguments.size();
	}
}
