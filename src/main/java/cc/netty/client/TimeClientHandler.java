package cc.netty.client;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * netty 从不会并发调用handler，除非使用@Sharable
 * 
 * @author caicai
 *
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter
{
	public ByteBuf buf;

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception
	{
		buf = ctx.alloc().buffer(4);
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception
	{
		buf.release();
		buf = null;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
	{
		ByteBuf m = (ByteBuf) msg;
		buf.writeBytes(m);
		try
		{
			if (buf.readableBytes() >= 4)
			{
				long current = (buf.readUnsignedInt() - 2208988800L) * 1000L;
				System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
						.format(LocalDateTime.ofInstant(new Date(current).toInstant(), ZoneId.systemDefault())));
				ctx.close();
			}
		}
		finally
		{
			m.release();

		}

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
	{
		cause.printStackTrace();
		ctx.close();
	}

}
