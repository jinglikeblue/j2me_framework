
package framework.util;

import java.util.Vector;

/**
 * 字符串工具
 * 
 * @author Administrator
 */
public class StringUtil
{

	/**
	 * 拆分字符串("拆分字符为cahr类型")
	 * 
	 * @param str
	 * @param flag 用来分割的符号
	 * @return
	 */
	static public String[] split(String str, char flag)
	{
		Vector indexs = new Vector();
		char[] chars = str.toCharArray();
		for(int i = 0; i < chars.length; i++)
		{
			char c = chars[i];
			if(c == flag)
			{
				indexs.addElement(new Integer(i));
			}
		}

		int indexAmount = indexs.size();
		String[] strs = new String[indexAmount + 1];
		int startIndex = 0;
		for(int i = 0; i < indexAmount; i++)
		{
			int endIndex = ((Integer)indexs.elementAt(i)).intValue();
			String temp = str.substring(startIndex, endIndex);
			strs[i] = temp;
			startIndex = endIndex + 1;
		}
		strs[strs.length - 1] = str.substring(startIndex);
		return strs;
	}

	/**
	 * 替换字符串中的字符
	 * 
	 * @param str 字符串源
	 * @param p 要替换的字符串
	 * @param repl 替换成的字符串
	 * @param global 替换所有匹配的，或者替换第一个匹配的
	 * @return
	 */
	static public String replace(String str, String p, String repl, boolean global)
	{
		int index = 0;
		do
		{
			int si = str.indexOf(p, index);
			if(-1 == si)
			{
				break;
			}
			int ei = si + p.length();
			str = str.substring(0, si) + repl + str.substring(ei);
			index = si + repl.length();
			
		} while(global);
		return str;
	}
	
	/**
	 * 格式化字符串，替换符用%s表示
	 * @param str
	 * @param repls
	 * @return
	 */
	static public String format(String str, String[] repls)
	{			
		for(int i = 0; i < repls.length; i++)
		{
			str = replace(str, "%s", repls[i], false);
		}
		return str;		
	}
}
