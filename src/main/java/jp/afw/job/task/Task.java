package jp.afw.job.task;

/**
 * このインターフェースは、タスク機能を定義したインターフェースです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/01/06
 * @author Kawakicchi
 */
public interface Task {

	/**
	 * 初期化処理を行う。
	 */
	public void initialize();

	/**
	 * 解放処理を行う。
	 */
	public void destroy();

	/**
	 * タスクを実行する。
	 */
	public void execute();

}
