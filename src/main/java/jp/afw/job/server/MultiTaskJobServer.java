package jp.afw.job.server;

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
