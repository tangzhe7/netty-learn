package cc.netty.server;

import cc.netty.handle.EchoServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServer implements Server
{

	private int port;

	public EchoServer(int port)
	{
		this.port = port;
	}

	public void run() throws InterruptedException
	{
		EventLoopGroup bossEventLoop = new NioEventLoopGroup();
		EventLoopGroup workerEventLoop = new NioEventLoopGroup();
		try
		{
			ServerBootstrap helper = new ServerBootstrap();
			helper.group(bossEventLoop, workerEventLoop)
					// 创建channel的类型
					.channel(NioServerSocketChannel.class).childHandler(new EchoServerHandler())
					.option(ChannelOption.SO_BACKLOG, 512).childOption(ChannelOption.SO_KEEPALIVE, true);
			ChannelFuture future = helper.bind(port).sync();
			future.channel().closeFuture().sync();
			System.out.println("echo Server is done " + future.isDone());
		}
		finally
		{
			bossEventLoop.shutdownGracefully();
			workerEventLoop.shutdownGracefully();
		}

	}

}
