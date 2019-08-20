package cc.netty.server;

import cc.netty.handle.DiscardServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class DiscardServer implements Server
{

	private int port;

	public DiscardServer(int port)
	{
		this.port = port;
	}

	public void run() throws Exception
	{
		/**
		 * NioEventLoopGroup 使用多线程轮询处理IO操作，int nThreads，ThreadFactory
		 */
		/**
		 * boss event loop 接收客户端的连接请求
		 */
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		/**
		 * worker event loop ,boss event loop 接受客户端连接处理完毕后，就把连接注册给worker，开始处理里面的信息流。
		 */
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try
		{
			//ServerBootstrap 工具类，帮助使用者快速构建服务器
			ServerBootstrap boostrap = new ServerBootstrap();
			boostrap.group(bossGroup,workerGroup)
			  //使用 NioServerSocketChannel,对于接收到的连接，使用新的channel
			 .channel(NioServerSocketChannel.class)
			 //ChannelInitializer 是一个特殊的channel,用于帮助用户配置一个新的channel,
			 .childHandler(new ChannelInitializer<SocketChannel>()
			{
				@Override
				protected void initChannel(SocketChannel ch) throws Exception
				{
					ch.pipeline()
					 .addLast("discardServerHandler",new DiscardServerHandler());
				}
			})
			 //设置tcp参数，tcp_backlog
			 //option 设置 NioServerSocketChannel,它处理正在准备接受的连接
			 .option(ChannelOption.SO_BACKLOG, 128)
			 //设置ServerChannel 生成的channel参数，在这个列子中ServerChannel是NioServerSocketChannel.
			 .childOption(ChannelOption.SO_KEEPALIVE, true);
			//Bind and start to accept incoming connections.
			ChannelFuture f = boostrap.bind(port).sync();
			// Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully shut down your server.
			f.channel().closeFuture().sync();
		}
		finally
		{
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}

	}

}
