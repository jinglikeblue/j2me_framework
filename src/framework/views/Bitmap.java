
package framework.views;

import javax.microedition.lcdui.Graphics;

import framework.geom.Point;
import framework.geom.Rectangle;
import framework.resources.Texture;
import framework.resources.TextureData;

/**
 * 位图显示对象
 * 
 * @author Jing
 */
public class Bitmap extends DisplayObject
{

	/**
	 * 用来展现的img，可能是缩放后的样子
	 */
	protected Texture _t = null;

	/**
	 * 原始img
	 */
	protected Texture _source = null;

	public Bitmap(Texture t)
	{
		setTexture(t);
	}

	public Bitmap(Texture t, int anchor)
	{
		setTexture(t);
		setAnchor(anchor);
	}

	public void setTexture(Texture t)
	{
		_source = _t = t;
		_width = t.img().getWidth();
		_height = t.img().getHeight();
	}

	public void paint(Graphics g)
	{
		TextureData td = _t.data();

		int x = _x + td.offX();
		int y = _y + td.offY();

		if(_anchor == DisplayObject.ANCHOR_CENTER)
		{
			x -= _width >> 1;
			y -= _height >> 1;
		}
		Point gp = local2Global(x, y);

		Rectangle drawRect = Rectangle.getIntersectRect(new Rectangle(gp.x(), gp.y(), td.w(), td.h()), Stage.current.viewPort());
		if(null != drawRect)
		{
			int clipX = td.x() + drawRect.x() - gp.x();
			int clipY = td.y() + drawRect.y() - gp.y();
			int clipW = drawRect.w();
			int clipH = drawRect.h();

			if(null != _scrollRect)
			{
				clipX += _scrollRect.x();
				clipY += _scrollRect.y();
				if(clipW > _scrollRect.w())
				{
					clipW = _scrollRect.w();
				}
				if(clipH > _scrollRect.h())
				{
					clipH = _scrollRect.h();
				}
			}

			int destX = (int)(drawRect.x() * DisplayObject.morphX);
			int destY = (int)(drawRect.y() * DisplayObject.morphY);
			g.drawRegion(_t.img(), clipX, clipY, clipW, clipH, _trans, destX, destY, 0);
		}
	}

	protected void enterFrame(long time)
	{

	}

	protected void addedToStage()
	{

	}

	protected void removedFromStage()
	{

	}
}
