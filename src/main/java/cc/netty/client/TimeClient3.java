package cc.netty.client;

import cc.netty.decoder.UnixTimeDecode3;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TimeClient3 implements Client
{

	public void run(String host, int port) throws InterruptedException
	{
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try
		{
			Bootstrap helper = new Bootstrap();
			helper.group(workerGroup).channel(NioSocketChannel.class).option(ChannelOption.SO_KEEPALIVE, true)
					.handler(new ChannelInitializer<SocketChannel>()
					{
						@Override
						protected void initChannel(SocketChannel ch) throws Exception
						{
							ch.pipeline().addLast(new UnixTimeDecode3(),new TimeClientHandler3());
						}
					});
			ChannelFuture f = helper.connect(host, port).sync();
			f.channel().closeFuture().sync();
		}
		finally
		{
			workerGroup.shutdownGracefully();
		}
	}

}
