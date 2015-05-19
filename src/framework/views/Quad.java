
package framework.views;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import framework.geom.Point;

/**
 * 方形纯色图像
 * 
 * @author Jing
 */
public class Quad extends DisplayObject
{

	private int _color = 0;

	/**
	 * 颜色值
	 * 
	 * @return
	 */
	public int color()
	{
		return _color;
	}

	/**
	 * X轴缩放
	 */
	private double _scaleX = 1;

	/**
	 * Y轴缩放
	 */
	private double _scaleY = 1;

	/**
	 * 设置颜色
	 * 
	 * @param color
	 */
	public void setColor(int color)
	{
		_color = color;
		refactorQuad();
	}
	
	/**
	 * 透明度
	 */
	private double _alpha = 1;
	
	public double alpha()
	{
		return _alpha;
	}
	
	public void setAlpha(double alpha)
	{
		_alpha = alpha;
		refactorQuad();
	}
	
	private Image _img = null;
	
	public Quad(int color, int w, int h, double alpha)
	{
		_width = w;
		_height = h;
		_alpha = alpha;
		setColor(color);
		refactorQuad();
	}
	
	private void refactorQuad()
	{
		int w = (int)(_width * _scaleX);
		int h = (int)(_height * _scaleY);		
		
		int[] argb = new int[w*h];
		for(int i = 0; i < argb.length; i++)
		{
			argb[i] = ((int)(_alpha * 255) << 24) | _color;
		}
		_img = Image.createRGBImage(argb, w, h, true);
	}

	public void paint(Graphics g)
	{		
		Point gp = local2Global(_x, _y);		

		g.drawImage(_img, gp.x(), gp.y(), _anchor);
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

	protected void onScaleChange(double scaleX, double scaleY)
	{
		_scaleX = scaleX;
		_scaleY = scaleY;
		refactorQuad();
	}

}
