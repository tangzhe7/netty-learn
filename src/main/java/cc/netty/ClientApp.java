package cc.netty;

import cc.netty.client.Client;
import cc.netty.client.TimeClient;
import cc.netty.client.TimeClient2;
import cc.netty.client.TimeClient3;

public class ClientApp
{

	public static void main(String[] args) throws InterruptedException
	{
		String host = "127.0.0.1";
		int port = 8082;
		run(new TimeClient(), host, port);
		run(new TimeClient2(), host, port);
		int port3 = 8083;
		run(new TimeClient3(), host, port3);
	}

	public static void run(Client client, final String host, final int port)
	{
		new Thread(() -> {
			int max = 20;
			int cur = 0;
			while (true && cur < max)
			{
				try
				{
					client.run(host, port);
					cur = 0;
					Thread.sleep(1000 * 2);
				}
				catch (Exception e)
				{
					++cur;
				}
			}
		}).start();
	}

}
