package framework.resources;

import org.json.me.JSONObject;

import framework.events.EventDispatcher;

/**
 * 资源获取器基类
 * @author Jing
 *
 */
public abstract class AResGetter extends EventDispatcher
{	
	abstract public JSONObject getJson(String name);	
	abstract public Texture getTexture(String name);
	abstract public SpriteSheet getSheet(String name);
	abstract public FontSheet getFontSheet(String name);
	abstract public String getUrl(String name);	
	abstract public void release(String name);	
}
