package cc.netty.server;

import cc.netty.handle.TimeServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TimeServer implements Server
{
	
	private int port;
	public TimeServer(int port) 
	{
		this.port=port;
	}

	@Override
	public void run() throws Exception
	{
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try
		{
			ServerBootstrap helper = new ServerBootstrap();
			helper.group(bossGroup, workerGroup)
				  .channel(NioServerSocketChannel.class)
				  .childHandler(new ChannelInitializer<SocketChannel>()
				{
					@Override
					protected void initChannel(SocketChannel ch) throws Exception
					{
						ch.pipeline()
						 .addLast("timeServerHandler",new TimeServerHandler());
					}
				})
				  .option(ChannelOption.SO_BACKLOG, 16)
				  .childOption(ChannelOption.SO_KEEPALIVE, true);
			ChannelFuture future = helper.bind(port).sync();
			future.channel().closeFuture().sync();
		}
		finally
		{
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}

	}

}
