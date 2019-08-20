package cc.netty.handle;

import cc.netty.time.UnixTime;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * TIME 协议，发送32位数字给客户端， 然后关闭连接。
 * 我们准备忽略任何收到的信息，在连接建立后尽快发送信息出去，所以，我们不用channelRead，换用ChannelActive
 * 
 * @author caicai
 *
 */
public class TimeServerHandler3 extends ChannelInboundHandlerAdapter
{
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception
	{
		final ChannelFuture f = ctx.writeAndFlush(new UnixTime());
		f.addListener(ChannelFutureListener.CLOSE);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
	{
		cause.printStackTrace();
		ctx.close();
	}

}
