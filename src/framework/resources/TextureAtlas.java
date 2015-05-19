
package framework.resources;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.microedition.lcdui.Image;

import org.json.me.JSONException;
import org.json.me.JSONObject;

import framework.views.DisplayObject;

/**
 * 纹理集
 * 
 * @author Jing
 */
public class TextureAtlas
{

	protected Image _img = null;
	
	public Image img()
	{
		return _img;
	}

	protected Hashtable _frames = null;
	
	public TextureData getFrame(String name)
	{
		return (TextureData)_frames.get(name);
	}

	public TextureAtlas(Image img, JSONObject jsonObj)
	{
		init(img, jsonObj);
	}

	public TextureAtlas(Image img, Hashtable frames)
	{
		_img = img;
		_frames = frames;
	}

	public void init(Image img, JSONObject jsonObj)
	{
		_img = img;
		_frames = getDatas(jsonObj);
	}

	/**
	 * 从纹理集中获取纹理帧
	 * 
	 * @param name
	 * @return
	 */
	public Texture getTexture(String name)
	{
		TextureData td = (TextureData)_frames.get(name);
		if(null == td)
		{
			return null;
		}
	
		return new Texture(_img, td);
	}
	

	/**
	 * 
	 * @param prefix
	 * @return
	 */
	public TextureAtlas getTextureAtlas(String prefix)
	{
		Hashtable frames = new Hashtable();
		for(Enumeration enumobj = _frames.elements(); enumobj.hasMoreElements();)
		{
			TextureData td = (TextureData)enumobj.nextElement();
			if(null != td && td.name.startsWith(prefix))
			{
				frames.put(td.name, td);
			}
		}
		return new TextureAtlas(_img, frames);
	}
	


	static public Hashtable getDatas(JSONObject jsonObj)
	{
		Hashtable frames = null;
		try
		{

			JSONObject jsonFrames = jsonObj.getJSONObject("frames");
			int charAmount = jsonFrames.length();
			frames = new Hashtable(charAmount);

			for(Enumeration enumobj = jsonFrames.keys(); enumobj.hasMoreElements();)
			{
				String key = (String)enumobj.nextElement();
				JSONObject jsonChar = jsonFrames.getJSONObject(key);
				TextureData data = new TextureData();
				data.name = key;
				data.x = jsonChar.getInt("x");
				data.y = jsonChar.getInt("y");
				data.w = jsonChar.getInt("w");
				data.h = jsonChar.getInt("h");
				data.offX = jsonChar.getInt("offX");
				data.offY = jsonChar.getInt("offY");
				if(jsonChar.has("sourceW"))
				{
					data.sourceW = jsonChar.getInt("sourceW");	
				}
				else
				{
					data.sourceW = data.w + data.offX;
				}
				
				if(jsonChar.has("sourceH"))
				{
					data.sourceH = jsonChar.getInt("sourceH");	
				}
				else
				{
					data.sourceH = data.h + data.offY;
				}				

				data.x *= DisplayObject.morphX;
				data.w *= DisplayObject.morphX;
				data.offX *= DisplayObject.morphX;
				data.sourceW *= DisplayObject.morphX;

				data.y *= DisplayObject.morphY;
				data.h *= DisplayObject.morphY;
				data.offY *= DisplayObject.morphY;
				data.sourceH *= DisplayObject.morphY;
				frames.put(key, data);
			}
		}
		catch(JSONException e)
		{
			e.printStackTrace();
		}
		return frames;
	}
}
