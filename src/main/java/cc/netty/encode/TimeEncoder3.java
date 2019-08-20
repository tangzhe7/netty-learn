package cc.netty.encode;

import cc.netty.time.UnixTime;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class TimeEncoder3 extends MessageToByteEncoder<UnixTime>
{

	@Override
	protected void encode(ChannelHandlerContext ctx, UnixTime msg, ByteBuf out) throws Exception
	{
		out.writeInt((int) msg.value());
	}

}
