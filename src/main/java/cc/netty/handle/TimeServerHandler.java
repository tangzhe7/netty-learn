package cc.netty.handle;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * TIME 协议，发送32位数字给客户端， 然后关闭连接。
 * 我们准备忽略任何收到的信息，在连接建立后尽快发送信息出去，所以，我们不用channelRead，换用ChannelActive
 * 
 * @author caicai
 *
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter
{
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception
	{
		final ByteBuf time = ctx.alloc().buffer(4);
		time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
		final ChannelFuture f = ctx.writeAndFlush(time);
		f.addListener((future) -> {
			assert f == future;
			ctx.close();
		});
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
	{
		cause.printStackTrace();
		ctx.close();
	}

}
