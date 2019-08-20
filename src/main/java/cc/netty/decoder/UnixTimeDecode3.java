package cc.netty.decoder;

import java.util.List;

import cc.netty.time.UnixTime;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class UnixTimeDecode3 extends ByteToMessageDecoder
{

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception
	{
		if(in.readableBytes()<4)
			return;
		out.add(new UnixTime(in.readUnsignedInt()));
	}

}
