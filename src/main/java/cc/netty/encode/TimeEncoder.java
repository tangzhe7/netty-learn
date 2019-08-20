package cc.netty.encode;

import cc.netty.time.UnixTime;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class TimeEncoder extends ChannelOutboundHandlerAdapter
{
	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception
	{
		UnixTime m = (UnixTime) msg;
		ByteBuf encoded = ctx.alloc().buffer(4);
		encoded.writeInt((int) m.value());
		ctx.write(encoded, promise);
		//not need call flush,if need, override flush(ChannelHandlerContext ctx)
	}

}
