package cc.netty.client;

public interface Client
{
	public void run(String host, int port) throws Exception;
}
