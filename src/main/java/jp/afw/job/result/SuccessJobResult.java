package jp.afw.job.result;

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
