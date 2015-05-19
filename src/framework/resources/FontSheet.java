package framework.resources;

import java.io.IOException;

import javax.microedition.lcdui.Image;

import org.json.me.JSONObject;

import framework.io.FileIO;


/**
 * ����
 * @author 
 *
 */
public class FontSheet
{
	private TextureAtlas _ta = null;
	
	/**
	 * ����
	 * @return
	 */
	public Image sheet()
	{
		return _ta.img();
	}
	
	/**
	 * ��ȡ�ַ�����
	 * @param ch �ַ�
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
