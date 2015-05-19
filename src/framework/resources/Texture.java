
package framework.resources;

import javax.microedition.lcdui.Image;

/**
 * 纹理
 * 
 * @author Jing
 */
public class Texture
{

	private Image _img = null;

	/**
	 * 图片资源
	 * @return
	 */
	public Image img()
	{
		return _img;
	}

	private TextureData _td = null;

	/**
	 * 纹理的显示数据
	 * @return
	 */
	public TextureData data()
	{
		return _td;
	}

	public Texture(Image img)
	{
		_img = img;
		_td = new TextureData();
		_td.w = _img.getWidth();
		_td.h = _img.getHeight();
		_td.sourceW = _td.w;
		_td.sourceH = _td.h;
	}
	
	public Texture(Image img, TextureData td)
	{
		_img = img;
		_td = td;
	}
}
