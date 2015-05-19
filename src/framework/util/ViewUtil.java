package framework.util;

import framework.views.DisplayObject;

/**
 * ��ʾ��������
 * @author Jing
 *
 */
public class ViewUtil
{
	/**
	 * ����ʾ����a�ƶ����b�����ĵ����
	 * 
	 * @param a
	 * @param b
	 */
	static public void setCenter(DisplayObject a, DisplayObject b)
	{
		int x = b.getX() + ((b.getWidth() - a.getWidth()) >> 1);
		int y = b.getY() + ((b.getHeight() - a.getHeight()) >> 1);
		a.setPosition(x, y);
	}
}
