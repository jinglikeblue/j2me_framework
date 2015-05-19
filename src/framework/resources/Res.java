
package framework.resources;

import framework.events.EventDispatcher;

/**
 * 资源管理器
 * 
 * @author Jing
 */
public class Res extends EventDispatcher
{
	/**
	 * 类型 图片
	 */
	static public final String TYPE_IMAGE = "image";
	
	/**
	 * 类型 JSON
	 */
	static public final String TYPE_JSON = "json";
	
	/**
	 * 类型 纹理集
	 */
	static public final String TYPE_SHEET = "sheet";
	
	/**
	 * 类型 声音
	 */
	static public final String TYPE_SOUND = "sound";
	
	/**
	 * 类型 字体
	 */
	static public final String TYPE_FONT = "font";
	
	/**
	 * 类型 纹理
	 */
	static public final String TYPE_TEXTURE = "texture";

	/**
	 * 可以设置的加载器，可以是本地加载器，也可以是网络加载器，灵活切换使用
	 */
	static public AResGetter actively = null;
	
	
	/**
	 * 本地资源读取器,使用前请用init初始化
	 */
	static public final LocalResGetter localRes = new LocalResGetter();
	
	/**
	 * 网络资源读取器，使用前请用init初始化
	 */
	static public final HttpResGetter httpRes = new HttpResGetter();
}
