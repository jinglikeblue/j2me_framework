
package framework.resources;

import java.util.Hashtable;
import java.util.Vector;

import javax.microedition.lcdui.Image;

import org.json.me.JSONObject;

/**
 * @author Jing
 */
public class SpriteSheet
{

	private TextureAtlas _ta = null;

	public TextureAtlas textureAtlas()
	{
		return _ta;
	}

	public Image sheet()
	{
		return _ta.img();
	}

	protected TextureData[] _datas = null;

	public TextureData[] datas()
	{
		return _datas;
	}

	/**
	 * 通过名称获取纹理集数据
	 * 
	 * @param name
	 * @return
	 */
	public TextureData getSSD(String name)
	{
		return _ta.getFrame(name);
	}

	protected SpriteSheet(TextureAtlas ta, String[] frameNames)
	{
		init(ta, frameNames);
	}

	protected SpriteSheet(JSONObject jsonObj, String[] frameNames, Image sheet)
	{
		TextureAtlas ta = new TextureAtlas(sheet, jsonObj);
		init(ta, frameNames);
	}

	protected void init(TextureAtlas ta, String[] frameNames)
	{
		_ta = ta;
		_datas = new TextureData[frameNames.length];
		for(int i = 0; i < frameNames.length; i++)
		{
			_datas[i] = _ta.getFrame(frameNames[i]);
		}
	}

	/**
	 * 获取纹理
	 * 
	 * @param name
	 * @return
	 */
	public Texture getTexture(String name)
	{
		return _ta.getTexture(name);
	}

	/**
	 * 返回由一个指定的字符串开始按字母排序的所有纹理(这对"MovieClip"来说非常有用)
	 * 
	 * @param prefix
	 * @return
	 */
	public SpriteSheet getSpriteSheet(String prefix)
	{
		Vector list = new Vector();
		for(int i = 0; i < _datas.length; i++)
		{
			TextureData td = _datas[i];
			if(td.name.startsWith(prefix))
			{
				list.addElement(td);
			}
		}

		Hashtable frames = new Hashtable(list.size());
		String[] frameNames = new String[list.size()];
		for(int i = 0; i < frameNames.length; i++)
		{
			TextureData td = (TextureData)list.elementAt(i);
			frameNames[i] = td.name;
			frames.put(td.name, td);
		}

		TextureAtlas ta = new TextureAtlas(_ta.img(), frames);
		SpriteSheet ss = new SpriteSheet(ta, frameNames);
		return ss;
	}

}
