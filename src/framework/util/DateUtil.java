package framework.util;

import java.util.Date;


public class DateUtil
{
	/**
	 * 将时间按照指定格式打印出来
	 * @param time
	 * @param format
	 * @return
	 */
	static public String getDateFormat(long time, String format)
	{
		Date d = new Date(time);
		String ds = d.toString();
		String[] arr = StringUtil.split(ds, ' ');
		
		
		String mon = "00";
		if(arr[1].startsWith("Jan"))
		{
			mon = "01";
		}
		else if(arr[1].startsWith("Feb"))
		{
			mon = "02";
		}
		else if(arr[1].startsWith("Mar"))
		{
			mon = "03";
		}
		else if(arr[1].startsWith("Apr"))
		{
			mon = "04";
		}
		else if(arr[1].startsWith("May"))
		{
			mon = "05";
		}
		else if(arr[1].startsWith("Jun"))
		{
			mon = "06";
		}
		else if(arr[1].startsWith("Jul"))
		{
			mon = "07";
		}
		else if(arr[1].startsWith("Aug"))
		{
			mon = "08";
		}
		else if(arr[1].startsWith("Sep"))
		{
			mon = "09";
		}
		else if(arr[1].startsWith("Oct"))
		{
			mon = "10";
		}
		else if(arr[1].startsWith("Nov"))
		{
			mon = "11";
		}
		else if(arr[1].startsWith("Dec"))
		{
			mon = "12";
		}
		
		String dd = arr[2];
		String yyyy = arr[5];
		String hhmmss = arr[3];
		String[] hms = StringUtil.split(hhmmss, ':');
		
		
		
		
		format = StringUtil.replace(format, "YYYY", yyyy, false);
		format = StringUtil.replace(format, "MM", mon, false);
		format = StringUtil.replace(format, "DD", dd, false);
		format = StringUtil.replace(format, "hh", hms[0], false);
		format = StringUtil.replace(format, "mm", hms[1], false);
		format = StringUtil.replace(format, "ss", hms[2], false);
		
		
		return format;
	}
}
