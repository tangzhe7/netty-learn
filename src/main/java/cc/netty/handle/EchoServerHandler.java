package cc.netty.handle;

import java.nio.ByteBuffer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;

/**
 * echo 客户端发送什么,回什么
 * 
 * @author caicai
 *
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter
{

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
	{
		ByteBuf buf = (ByteBuf) msg;
		final ByteBuf buf2 = buf.retainedDuplicate();
		ByteBuffer buffer = ByteBuffer.allocate(buf.capacity());
		int start = 0;
		int end = buf2.capacity();
		//回车键不要
//		end--;
		while (start < end)
		{
			buffer.put(buf2.getByte(start));
			++start;
		}
		ReferenceCountUtil.release(buf2);
		ChannelFuture future = ctx.writeAndFlush(msg);
		future.addListener((Future<Void> futures) -> {
			System.out.print(new String(buffer.array()));
			System.out.println("echo ans " + futures.isSuccess());
			System.out.flush();
		});
		
	}

}
