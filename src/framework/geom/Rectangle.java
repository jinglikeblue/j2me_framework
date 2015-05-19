package framework.geom;

import framework.util.StringUtil;

/**
 * ʹ�õľ��ζ���
 * @author Jing
 *
 */
public class Rectangle
{
	
	private int _x = 0;
	
	public int x()
	{
		return _x;
	}
	
	public void setX(int x)
	{
		_x = x;
	}
	
	private int _y = 0;
	
	public int y()
	{
		return _y;
	}
	
	public void setY(int y)
	{
		_y = y;
	}
	
	private int _w = 0;
	
	public int w()
	{
		return _w;
	}
	
	public void setW(int w)
	{
		_w = w;
	}
	
	private int _h = 0;
	
	public int h()
	{
		return _h;
	}
	
	public void setH(int h)
	{
		_h = h;
	}
	
	public int right()
	{
		return _x + _w;
	}
	
	/**
	 * ���þ��ε��ұ߿��λ�ã�Ϊ�����޸�h���ᵼ��wС��0��������Ч
	 * @param right
	 */
	public void setRight(int right)
	{
		if(right >= _x)
		{
			_w = right - _x;
		}
	}
	
	public int bottom()
	{
		return _y + _h;
	}
	
	/**
	 * ���þ��ε��ұ߿��λ�ã�Ϊ�����޸�h���ᵼ��hС��0��������Ч
	 * @param bottom
	 */
	public void setBottom(int bottom)
	{
		if(bottom >= _y)
		{
			_h = bottom - _y;
		}
	}
	
	public String toString()
	{
		String  format = "[x=%s,y=%s,w=%s,h=%s]";
		String[] repls = {String.valueOf(_x)
				,String.valueOf(_y)
				,String.valueOf(_w)
				,String.valueOf(_h)};		
		return StringUtil.format(format, repls);
		
	}
	
	/**
	 * 
	 * @param x Xλ��
	 * @param y Yλ��
	 * @param w ���
	 * @param h �߶�
	 */
	public Rectangle(int x, int y, int w, int h)
	{
		_x = x;
		_y = y;
		_w = w;
		_h = h;
	}
	
    /**
     * �õ����ε��ཻ����û���ཻ�򷵻�null
     * @param rectA
     * @param rectB
     * @return
     *
     */
    static public Rectangle getIntersectRect(Rectangle rectA, Rectangle rectB)
    {
        int intersectX;
        int intersectY;

        if (rectA.x() + rectA.w() < rectB.x())
        {
            return null;
        }

        if (rectA.x() > rectB.x() + rectB.w())
        {
            return null;
        }

        if (rectA.y() + rectA.h() < rectB.y())
        {
            return null;
        }

        if (rectA.y() > rectB.y() + rectB.h())
        {
            return null;
        }

        intersectX = rectA.x() > rectB.x() ? rectA.x() : rectB.x();
        intersectY = rectA.y() > rectB.y() ? rectA.y() : rectB.y();
        
        int intersectRight = rectA.right() < rectB.right() ? rectA.right() : rectB.right();
        int intersectBottom = rectA.bottom() < rectB.bottom() ? rectA.bottom() : rectB.bottom();
        int intersectW = intersectRight - intersectX;
        int intersectH = intersectBottom - intersectY;
        Rectangle intersectRect = new Rectangle(intersectX, intersectY, intersectW, intersectH);
        return intersectRect;
    }
}
