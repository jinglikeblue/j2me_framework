
package framework.views;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import framework.geom.Point;
import framework.resources.FontSheet;
import framework.resources.TextureData;
import framework.util.ImageUtil;

/**
 * 位图字体
 * 
 * @author Jing
 */
public class BitmapFont extends DisplayObject
{

	private FontSheet _sheet = null;

	private Image _textImageCache = null;

	private char[] _textChars = null;

	private String _text = "";

	public String text()
	{
		return _text;
	}

	public void setText(String text)
	{
		if(text != null && _text != text)
		{
			_text = text;
		}

		if(_text.length() > 0)
		{
			_textChars = new char[_text.length()];
			_text.getChars(0, _text.length(), _textChars, 0);

			if(_trans != 0)
			{
				updateTextImageCache();
			}
		}
		else
		{
			_textChars = null;
		}
	}

	public BitmapFont(FontSheet sheet)
	{
		init(sheet);
	}

	protected void init(FontSheet sheet)
	{
		_sheet = sheet;
	}
	
	
	public void setTrans(int value)
	{
		if(_trans != value)
		{
			_trans = value;
			if(_trans != 0)
			{
				updateTextImageCache();
			}
			else
			{
				_textImageCache = null;
			}
		}
	}

	/**
	 * 更新文本图片缓存(图片需要旋转时使用)
	 */
	private void updateTextImageCache()
	{
		int h = 0;
		int w = 0;
		for(int i = 0; i < _textChars.length; i++)
		{
			TextureData td = _sheet.getCharData(_textChars[i]);
			if(null != td)
			{
				h = td.sourceH() > h ? td.sourceH() : h;
				w += td.sourceW();
			}
		}
		int flagColor = 0xFF778899;
		_textImageCache = Image.createImage(w, h);
		Graphics g = _textImageCache.getGraphics();
		g.setColor(flagColor);
		g.fillRect(0, 0, _textImageCache.getWidth(), _textImageCache.getHeight());

		int pos = 0;
		for(int i = 0; i < _textChars.length; i++)
		{
			TextureData td = _sheet.getCharData(_textChars[i]);
			if(null != td)
			{
				int x = td.offX() + pos;
				int y = td.offY();	
				
				int destX = (int)(x * DisplayObject.morphX);
				int destY = (int)(y * DisplayObject.morphY);
				g.drawRegion(_sheet.sheet(), td.x(), td.y(), td.w(), td.h(), 0, destX, destY, 0);
				pos = x + td.w();
			}
		}
		
		_textImageCache = ImageUtil.alpha(_textImageCache, flagColor);

	}

	public void paint(Graphics g)
	{
		if(_textImageCache != null)
		{
			Point gp = local2Global(_x, _y);
			int destX = (int)(gp.x() * DisplayObject.morphX);
			int destY = (int)(gp.y() * DisplayObject.morphY);
			g.drawRegion(_textImageCache, 0, 0, _textImageCache.getWidth(), _textImageCache.getHeight(), _trans, destX, destY, _anchor);
			return;
		}
		else if(null != _textChars)
		{
			int pos = _x;
			int totalW = 0;

			if(_anchor == DisplayObject.ANCHOR_CENTER)
			{
				for(int i = 0; i < _textChars.length; i++)
				{
					TextureData td = _sheet.getCharData(_textChars[i]);
					totalW += td.w();
				}
				pos -= totalW >> 1;
			}

			for(int i = 0; i < _textChars.length; i++)
			{
				TextureData td = _sheet.getCharData(_textChars[i]);

				if(null != td)
				{
					int x = td.offX() + pos;
					int y = td.offY() + _y;

					if(_anchor == DisplayObject.ANCHOR_CENTER)
					{
						y -= td.h() >> 1;
					}

					Point gp = local2Global(x, y);
					int destX = (int)(gp.x() * DisplayObject.morphX);
					int destY = (int)(gp.y() * DisplayObject.morphY);
					g.drawRegion(_sheet.sheet(), td.x(), td.y(), td.w(), td.h(), 0, destX, destY, 0);
					pos = x + td.w();
				}
			}
		}
	}

	protected void onScaleChange(double scaleX, double scaleY)
	{

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
