package debug.units;

import java.util.Date;

import framework.util.DateUtil;
import framework.views.Sprite;


public class DateTest extends Sprite
{
	public DateTest()
	{
		Date d = new Date();
		long time = d.getTime();
		System.out.println(DateUtil.getDateFormat(time, "YYYY-MM-DD hh:mm:ss"));
	}
}
