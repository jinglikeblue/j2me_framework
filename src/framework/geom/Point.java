package framework.geom;

import framework.util.StringUtil;

/**
 * 
 * @author Jing
 *
 */
public class Point
{
	/**
	 * 获取一个点绕任意点顺时针旋转任意角度后的坐标
	 * @param x
	 * @param y
	 * @param cx
	 * @param cy
	 * @param angle
	 * @return
	 */
	static public Point rotatePoint(int x, int y, int cx, int cy, int angle)
	{
		//首先换算出正确的角度
		angle = angle % 360;
		if(angle < 0)
		{
			angle = 360 + angle;
		}
		angle = 360 - angle;
		
		int newX = 0;
		int newY = 0;
		
		//角度转换为弧度
        double radian = angle * Math.PI / 180;
        double tx = (x- cx) * Math.cos(radian) + (y - cy ) * Math.sin(radian) + cy;  
        double ty = -(x - cx) * Math.sin(radian) + (y - cy) * Math.cos(radian) + cy;  
        newX = (int)tx;
        newY = (int)ty;
		
		return new Point(newX,newY);
	}
	
	/**
	 * X坐标位置
	 */
	private int _x = 0;
	
	public int x()
	{
		return _x;
	}
	
	public void setX(int x)
	{
		_x = x;
	}
	
	/**
	 * Y坐标位置
	 */
	private int _y = 0;
	
	public int y()
	{
		return _y;
	}
	
	public void setY(int y)
	{
		_y = y;
	}
	
	
	/**
	 * 设置X,Y的值
	 * @param x
	 * @param y
	 */
	public void setPos(int x, int y)
	{
		_x = x;
		_y = y;
	}
	
	public Point(int x, int y)
	{
		_x = x;
		_y = y;
	}
	
	public String toString()
	{
		String[] args = new String[2];
		args[0] = String.valueOf(_x);
		args[1] = String.valueOf(_y);
		return StringUtil.format("[x = %s, y = %s]",args);
	}
}
