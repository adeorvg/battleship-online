package centralServer;

class AppStart {
	public static boolean serverStart() {
		CentralServer server = new CentralServer();
		server.start(50000);
		return true;
	}
}