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
package org.azkfw.job.store;

import java.util.HashMap;
import java.util.Map;

import org.azkfw.persistence.store.AbstractStore;

/**
 * このクラスは、HTTPセッション用のストアクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2013/01/05
 * @author Kawakicchi
 */
public final class JobSessionStore extends AbstractStore<String, Object> {

	private Map<String, Object> map;

	/**
	 * コンストラクタ
	 * 
	 */
	public JobSessionStore() {
		map = new HashMap<String, Object>();
	}

	@Override
	public void put(final String aKey, final Object aValue) {
		map.put(aKey, aValue);
	}

	@Override
	public void putAll(final Map<String, Object> aMap) {
		for (String key : aMap.keySet()) {
			map.put(key, aMap.get(key));
		}
	}

	@Override
	public Object get(String aKey) {
		Object result = null;
		result = map.get(aKey);
		return result;
	}

	@Override
	public Object get(final String aKey, final Object aDefault) {
		Object result = aDefault;
		if (null != map.get(aKey)) {
			result = map.get(aKey);
		}
		return result;
	}

	@Override
	public boolean has(final String aKey) {
		Object obj = map.get(aKey);
		return (null != obj);
	}

	@Override
	public void remove(final String aKey) {
		map.remove(aKey);
	}

}
