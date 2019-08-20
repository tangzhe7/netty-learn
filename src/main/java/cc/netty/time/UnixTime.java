package cc.netty.time;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class UnixTime
{
	private final long value;

	public UnixTime()
	{
		this(System.currentTimeMillis() / 1000L + 2208988800L);
	}

	public UnixTime(long value)
	{
		this.value = value;
	}
	
	
	public long value() 
	{
		return value;
	}
	
	public String toString() 
	{
		return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
				.format(LocalDateTime.ofInstant(new Date((value() - 2208988800L) * 1000L).toInstant(), ZoneId.systemDefault()));
	}

}
