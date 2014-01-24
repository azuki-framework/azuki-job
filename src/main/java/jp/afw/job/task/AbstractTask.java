package jp.afw.job.task;

import jp.afw.core.lang.LoggingObject;

/**
 * このクラスは、タスク機能を実装するための基底クラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/01/06
 * @author Kawakicchi
 */
public abstract class AbstractTask extends LoggingObject implements Task {

	/**
	 * コンストラクタ
	 */
	public AbstractTask() {
		super(Task.class);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aName Name
	 */
	public AbstractTask(final String aName) {
		super(aName);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aClass Class
	 */
	public AbstractTask(final Class<?> aClass) {
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
	public final void execute() {
		doExecute();
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
	 * タスクを実行する。
	 */
	protected abstract void doExecute();

}
