
package framework.util;

import java.util.Vector;

/**
 * �ַ�������
 * 
 * @author Administrator
 */
public class StringUtil
{

	/**
	 * ����ַ���("����ַ�Ϊcahr����")
	 * 
	 * @param str
	 * @param flag �����ָ�ķ���
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
	 * �滻�ַ����е��ַ�
	 * 
	 * @param str �ַ���Դ
	 * @param p Ҫ�滻���ַ���
	 * @param repl �滻�ɵ��ַ���
	 * @param global �滻����ƥ��ģ������滻��һ��ƥ���
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
	 * ��ʽ���ַ������滻����%s��ʾ
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
