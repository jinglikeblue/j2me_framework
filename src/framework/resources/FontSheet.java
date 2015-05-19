package framework.resources;

import java.io.IOException;

import javax.microedition.lcdui.Image;

import org.json.me.JSONObject;

import framework.io.FileIO;


/**
 * 字体
 * @author 
 *
 */
public class FontSheet
{
	private TextureAtlas _ta = null;
	
	/**
	 * 纹理集
	 * @return
	 */
	public Image sheet()
	{
		return _ta.img();
	}
	
	/**
	 * 获取字符数据
	 * @param ch 字符
	 * @return
	 */
	public TextureData getCharData(char ch)
	{
		String str = String.valueOf(ch);
		return _ta.getFrame(str);		
	}
	
	protected FontSheet(String url)
	{
		try
		{
			JSONObject jsonObj = FileIO.getJson(url);
			String imagePath = url.substring(0, url.length() - 4) + ".png";
			Image sheet = Image.createImage(imagePath);
			init(jsonObj, sheet);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	protected FontSheet(JSONObject jsonObj, Image sheet)
	{
		init(jsonObj, sheet);
	}
	
	protected void init(JSONObject jsonObj, Image sheet)
	{	
		_ta = new TextureAtlas(sheet, jsonObj);
	}
}
