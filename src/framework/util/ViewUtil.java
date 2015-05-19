package framework.util;

import framework.views.DisplayObject;

/**
 * 显示对象处理工具
 * @author Jing
 *
 */
public class ViewUtil
{
	/**
	 * 将显示对象a移动后和b以中心点对齐
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
