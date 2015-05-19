
package framework.resources;

import javax.microedition.lcdui.Image;

/**
 * ����
 * 
 * @author Jing
 */
public class Texture
{

	private Image _img = null;

	/**
	 * ͼƬ��Դ
	 * @return
	 */
	public Image img()
	{
		return _img;
	}

	private TextureData _td = null;

	/**
	 * �������ʾ����
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
