package centralServer;

public class AppStart {
	public static boolean serverStart() {
		CentralServer server = new CentralServer();
		server.start(8080);
		return true;
	}
}
