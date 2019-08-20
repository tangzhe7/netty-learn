package cc.netty.handle;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * handler 负责释放任何传递给handler的资源
 * 
 * @author caicai
 *
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter
{
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
	{
		ByteBuf in = (ByteBuf) msg;
		try
		{
			// read message,there is ByteBuf
			while (in.isReadable())
			{
				System.out.print((char) in.readByte());
				System.out.flush();
			}
		}
		finally
		{
			ReferenceCountUtil.release(in);
		}
	}

	/**
	 * io/error or process event error,通常是关闭channel，打印日志
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
	{
		cause.printStackTrace();
		ctx.close();
	}

}
