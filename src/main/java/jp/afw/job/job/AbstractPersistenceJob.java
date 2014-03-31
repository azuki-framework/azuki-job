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
package jp.afw.job.job;

import jp.afw.persistence.context.Context;
import jp.afw.persistence.context.ContextSupport;
import jp.afw.persistence.proterty.Property;
import jp.afw.persistence.proterty.PropertySupport;
import jp.afw.persistence.session.SessionSupport;
import jp.afw.persistence.store.Store;

/**
 * このクラスは、永続化層の機能を実装したジョブクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2013/02/21
 * @author Kawakicchi
 */
public abstract class AbstractPersistenceJob extends AbstractJob implements ContextSupport, PropertySupport, SessionSupport {

	/**
	 * コンテキスト情報
	 */
	private Context context;

	/**
	 * プロパティ情報
	 */
	private Property property;

	/**
	 * セッション情報
	 */
	private Store<String, Object> session;

	/**
	 * コンストラクタ
	 */
	public AbstractPersistenceJob() {
		super();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aName 名前
	 */
	public AbstractPersistenceJob(final String aName) {
		super(aName);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aClass クラス
	 */
	public AbstractPersistenceJob(final Class<?> aClass) {
		super(aClass);
	}

	@Override
	public final void setContext(final Context aContext) {
		context = aContext;
	}

	/**
	 * コンテキストを取得する。
	 * 
	 * @return コンテキスト
	 */
	protected final Context getContext() {
		return context;
	}

	@Override
	public final void setProperty(final Property aProperty) {
		property = aProperty;
	}

	/**
	 * プロパティ情報を取得する。
	 * 
	 * @return プロパティ情報
	 */
	protected final Property getProperty() {
		return property;
	}

	@Override
	public final void setSession(final Store<String, Object> aSession) {
		session = aSession;
	}

	/**
	 * セッション情報を取得する。
	 * 
	 * @return セッション情報
	 */
	protected final Store<String, Object> getSession() {
		return session;
	}

	@Override
	protected void doBeforeExecute() {
		super.doBeforeExecute();
		// TODO Write doBeforeExecute code.

	}

	@Override
	protected void doAfterExecute() {
		// TODO Write doAfterExecute code.

		super.doAfterExecute();
	}
}
