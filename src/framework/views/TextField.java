
package framework.views;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import framework.geom.Point;
import framework.util.ImageUtil;
import framework.util.StringUtil;

/**
 * 文本框
 * 
 * @author Jing
 */
public class TextField extends DisplayObject
{

	public TextField()
	{
		updateFont();
	}

	private Font _font = null;

	private String _text = "";

	private String[] _lines = {""};

	private int _maxLength = -1;

	/**
	 * 最大字符串长度,-1表示不限制
	 */
	public int maxLength()
	{
		return _maxLength;
	}

	/**
	 * 设置最大字符数
	 * 
	 * @param length
	 */
	public void setMaxLength(int length)
	{
		_maxLength = length;
		if(_maxLength != -1 && _text.length() > _maxLength)
		{
			_text = _text.substring(0, _maxLength);
		}
	}

	public String getText()
	{
		return _text;
	}

	/**
	 * 设置文本内容
	 * 
	 * @param text
	 */
	public void setText(String text)
	{
		if(_text.equals(text))
		{
			return;
		}
		
		_text = text;
		if(_maxLength != -1 && _text.length() > _maxLength)
		{
			_text = _text.substring(0, _maxLength);
		}

		_lines = StringUtil.split(text, '\n');
		computeSize();
	}

	private Image _imgCache = null;
	
	public void setTrans(int value)
	{
		if(_trans != value)
		{
			_trans = value;
			updateImageCache();
		}
	}
	
	public void setAnchor(int anchor)
	{
		if(_anchor != anchor)
		{
			_anchor = anchor;
			updateImageCache();
		}
	}

	private int _color = 0;

	/**
	 * 设置文本颜色
	 * 
	 * @param color
	 */
	public void setColor(int color)
	{
		_color = color;
	}

	private boolean _bold = false;

	private boolean _italic = false;

	private boolean _underlined = false;

	private int _size = Font.SIZE_MEDIUM;

	private int _linegap = 0;

	/**
	 * 行间距
	 */
	public int linegap()
	{
		return _linegap;
	}

	/**
	 * 设置行间距
	 */
	public void setLinegap(int linegap)
	{
		_linegap = linegap;
		computeSize();
	}

	public boolean isBold()
	{
		return _bold;
	}

	public void setBold(boolean v)
	{
		_bold = v;
		updateFont();
	}

	public boolean isItalic()
	{
		return _italic;
	}

	public void setItalic(boolean v)
	{
		_italic = v;
		updateFont();
	}

	public boolean isUnderlined()
	{
		return _underlined;
	}

	public void setUnderlined(boolean v)
	{
		_underlined = v;
		updateFont();
	}

	public int size()
	{
		return _size;
	}

	public void setSize(int size)
	{
		_size = size;
		updateFont();
	}

	private int _lineHeight = 0;

	/**
	 * 行高
	 * 
	 * @return
	 */
	public int lineHeight()
	{
		return _lineHeight;
	}

	/**
	 * 设置行高
	 * 
	 * @param lineHeight
	 */
	public void setLineHeight(int lineHeight)
	{
		_lineHeight = lineHeight;
	}

	protected void updateFont()
	{
		int style = 0;

		if(_bold)
		{
			style |= Font.STYLE_BOLD;
		}

		if(_italic)
		{
			style |= Font.STYLE_ITALIC;
		}

		if(_underlined)
		{
			style |= Font.STYLE_UNDERLINED;
		}

		if(0 == style)
		{
			style = Font.STYLE_PLAIN;
		}

		_font = Font.getFont(Font.FACE_MONOSPACE, style, _size);
		_lineHeight = _font.getHeight();
		computeSize();
	}

	public void paint(Graphics g)
	{
		if(_text == "")
		{
			return;
		}
		
		if(null != _imgCache)
		{
			Point gp = local2Global(_x, _y);		
			int destX = (int)(gp.x() * DisplayObject.morphX);
			int destY = (int)(gp.y() * DisplayObject.morphY);
			g.drawRegion(_imgCache, 0, 0, _imgCache.getWidth(), _imgCache.getHeight(), _trans, destX, destY, _anchor);
			return;
		}
		else
		{
			Point gp = local2Global(_x, _y);
			if(_font != null)
			{
				g.setFont(_font);
			}
			g.setColor(_color);

			int pos = gp.y();
			int gap = _lineHeight + _linegap;
			for(int i = 0; i < _lines.length; i++)
			{
				int destX = (int)(gp.x() * DisplayObject.morphX);
				int destY = (int)(pos * DisplayObject.morphY);
				g.drawString(_lines[i], destX, destY, 0);
				pos += gap;
			}
		}
	}

	private void computeSize()
	{
		_width = 0;
		_height = _lineHeight + (_lineHeight + _linegap) * (_lines.length - 1);
		for(int i = 0; i < _lines.length; i++)
		{
			String line = null;
			line = _lines[i];
			int w = _font.stringWidth(line);
			if(w > _width)
			{
				_width = w;
			}
		}
		
		updateImageCache();
	}

	protected void updateImageCache()
	{
		if("" == _text || (0 == _trans && DisplayObject.ANCHOR_TOP_LEFT == _anchor))
		{
			_imgCache = null;
			return;
		}
		int flagColor = 0xFF000000 | (~_color);
		
		_imgCache = Image.createImage(_width, _height);
		Graphics g = _imgCache.getGraphics();
		g.setColor(flagColor);
		g.fillRect(0, 0, _width, _height);
		
		if(_font != null)
		{
			g.setFont(_font);
		}
		g.setColor(_color);

		int pos = 0;
		int gap = _lineHeight + _linegap;
		for(int i = 0; i < _lines.length; i++)
		{
			g.drawString(_lines[i], 0, pos, 0);
			pos += gap;
		}
		
		_imgCache = ImageUtil.alpha(_imgCache, flagColor);
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
