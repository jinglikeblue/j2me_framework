
package framework.views;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import framework.consts.TransType;
import framework.events.EventDispatcher;
import framework.geom.Point;
import framework.geom.Rectangle;

/**
 * 显示对象
 * 
 * @author Jing
 */
public abstract class DisplayObject extends EventDispatcher
{

	/**
	 * X轴上图片的变形比例
	 */
	static public double morphX = 1;

	/**
	 * Y轴上图片的变形比例
	 */
	static public double morphY = 1;

	public static final int ANCHOR_TOP_LEFT = Graphics.TOP | Graphics.LEFT;

	public static final int ANCHOR_CENTER = Graphics.HCENTER | Graphics.VCENTER;

	protected int _anchor = DisplayObject.ANCHOR_TOP_LEFT;

	protected String _name = null;

	/**
	 * 设置名称
	 * 
	 * @param name
	 */
	public void setName(String name)
	{
		_name = name;
	}

	/**
	 * 获取名称
	 * 
	 * @return
	 */
	public String getName()
	{
		return _name;
	}

	protected int _x = 0;

	/**
	 * 获取对象X坐标值
	 * 
	 * @return
	 */
	public int getX()
	{
		return _x;
	}

	protected int _y = 0;

	/**
	 * 获取对象Y坐标值
	 * 
	 * @return
	 */
	public int getY()
	{
		return _y;
	}

	protected int _width = 0;

	/**
	 * 获取对象宽度
	 * 
	 * @return
	 */
	public int getWidth()
	{
		return _width;
	}

	protected int _height = 0;

	/**
	 * 获取对象高度
	 * 
	 * @return
	 */
	public int getHeight()
	{
		return _height;
	}

	protected int _trans = TransType.NONE;

	public int getTrans()
	{
		return _trans;
	}

	/**
	 * 设置旋转角度,使用TransType的值
	 * 
	 * @param vlaue
	 * @return
	 */
	public void setTrans(int value)
	{
		_trans = value;
	}

	protected boolean _visible = true;

	public boolean getVisible()
	{
		return _visible;
	}

	/**
	 * 设置对象是否可见
	 * 
	 * @param v
	 */
	public void setVisible(boolean v)
	{
		_visible = v;
	}

	protected Rectangle _scrollRect = null;

	public Rectangle getScrollRect()
	{
		return _scrollRect;
	}

	/**
	 * 设置滚动显示区域
	 * 
	 * @param rect
	 */
	public void setScrollRect(Rectangle rect)
	{
		_scrollRect = rect;
	}

	protected DisplayObject _parent = null;

	/**
	 * 获取对象的父对象，不存在则为null
	 * 
	 * @return
	 */
	public DisplayObject getParent()
	{
		return _parent;
	}

	protected boolean _inStage = false;

	/**
	 * 对象是否在舞台上
	 * 
	 * @return
	 */
	public boolean inStage()
	{
		return _inStage;
	}

	protected void setParent(DisplayObject parent)
	{
		if(_parent == parent)
		{
			return;
		}

		// 检查当前的父对象是否在舞台上
		boolean isParentInStage = false;
		DisplayObject pp = parent != null ? parent : _parent;
		while(null != pp)
		{
			if(pp == pp.getParent())
			{
				isParentInStage = true;
				break;
			}
			else
			{
				pp = pp.getParent();
			}
		}

		if(true == isParentInStage)
		{
			if(null == parent)
			{
				_inStage = false;
				removedFromStage();
			}
			else
			{
				_inStage = true;
				addedToStage();
			}
		}

		_parent = parent;
	}

	public DisplayObject()
	{

	}

	public void setAnchor(int anchor)
	{
		_anchor = anchor;
	}

	/**
	 * 渲染函数
	 * 
	 * @param g
	 */
	abstract public void paint(Graphics g);

	/**
	 * 设置坐标位置
	 * 
	 * @param x
	 * @param y
	 */
	public void setPosition(int x, int y)
	{
		_x = x;
		_y = y;
	}

	/**
	 * 返回一个矩形，该矩形定义相对于父对象坐标系的显示对象区域
	 * 
	 * @return
	 */
	public Rectangle getBounds()
	{
		int x = _x;
		int y = _y;
		switch(_anchor)
		{
			case ANCHOR_CENTER:
				x -= (_width >> 1);
				y -= (_height >> 1);
				break;
		}
		Rectangle rect = new Rectangle(x, y, _width, _height);
		return rect;
	}

	/**
	 * 当前的时间
	 * 
	 * @param time
	 */
	abstract protected void enterFrame(long time);

	/**
	 * 被添加到舞台上
	 */
	abstract protected void addedToStage();

	/**
	 * 从舞台上被移除
	 */
	abstract protected void removedFromStage();

	/**
	 * 将显示对象中的点转换成舞台上的坐标
	 * 
	 * @return
	 */
	public Point local2Global(int x, int y)
	{
		int gX = x;
		int gY = y;
		DisplayObject parent = this.getParent();
		while(parent != null)
		{
			gX += parent.getX();
			gY += parent.getY();
			if(parent != parent.getParent())
			{
				parent = parent.getParent();
			}
			else
			{
				parent = null;
			}
		}
		return new Point(gX, gY);
	}

	/**
	 * 对image进行缩放
	 * 
	 * @param srcImage
	 * @param newW
	 * @param newH
	 * @return
	 */
	public static final Image scale(Image srcImage, int newW, int newH)
	{
		int srcW = srcImage.getWidth();
		int srcH = srcImage.getHeight();

		int[] colors = new int[srcW * srcH];
		srcImage.getRGB(colors, 0, srcW, 0, 0, srcW, srcH);

		int[] targetColors = new int[newW * newH];

		double kw = (double)srcW / newW;
		double kh = (double)srcH / newH;

		for(int y = 0; y < newH; y++)
		{
			for(int x = 0; x < newW; x++)
			{
				int newIndex = y * newW + x;
				int srcIndex = (int)(y * kh) * srcW + (int)(x * kw);
				targetColors[newIndex] = colors[srcIndex];
			}
		}

		return Image.createRGBImage(targetColors, newW, newH, false);
	}

	/**
	 * 对image进行缩放
	 * 
	 * @param srcImage
	 * @param newW
	 * @param newH
	 * @return
	 */
	public static final void scale(Image srcImage, Image targetImage)
	{
		int srcW = srcImage.getWidth();
		int srcH = srcImage.getHeight();
		int newW = targetImage.getWidth();
		int newH = targetImage.getHeight();

		Graphics g = targetImage.getGraphics();

		// 先做水平方向上的伸缩变换
		for(int x = 0; x < newW; x++)
		{
			g.setClip(x, 0, 1, srcH); // 按比例放缩
			g.drawImage(srcImage, x - x * srcW / newW, 0, Graphics.LEFT | Graphics.TOP);
		}

		// 再做垂直方向上的伸缩变换
		for(int y = 0; y < newH; y++)
		{
			g.setClip(0, y, newW, 1); // 按比例放缩
			g.drawImage(srcImage, 0, y - y * srcH / newH, Graphics.LEFT | Graphics.TOP);
		}
	}

}
