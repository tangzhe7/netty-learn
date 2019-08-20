package cc.netty.client;

import cc.netty.time.UnixTime;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeClientHandler3 extends ChannelInboundHandlerAdapter
{

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
	{
		UnixTime m = (UnixTime) msg;
		System.out.println(m);
		ctx.close();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
	{
		cause.printStackTrace();
		ctx.close();
	}

}
