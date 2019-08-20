package cc.netty.client;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeClientHandler2 extends ChannelInboundHandlerAdapter
{

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
	{
		ByteBuf m = (ByteBuf) msg; 
		try
		{
			long currentTimeMillis = (m.readUnsignedInt() - 2208988800L) * 1000L;
			System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
					.format(LocalDateTime.ofInstant(new Date(currentTimeMillis).toInstant(), ZoneId.systemDefault())));
			ctx.close();
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
