package cc.netty;

import cc.netty.server.DiscardServer;
import cc.netty.server.EchoServer;
import cc.netty.server.Server;
import cc.netty.server.TimeServer;

/**
 * start discard server
 *
 */
public class ServerApp
{
	public static void main(String[] args) throws Exception
	{
		int discardPort = 8080;
		run(new DiscardServer(discardPort));
		int echoPort = 8081;
		run(new EchoServer(echoPort));
		int timePort = 8082;
		run(new TimeServer(timePort));
		int timePort2 = 8083;
		run(new TimeServer(timePort2));
	}

	public static void run(Server server)
	{
		new Thread(() -> {
			try
			{
				server.run();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}).start();
	}
}
