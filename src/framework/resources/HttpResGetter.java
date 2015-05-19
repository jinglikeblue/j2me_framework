
package framework.resources;

import java.io.IOException;
import java.util.Hashtable;

import javax.microedition.lcdui.Image;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;

import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

import framework.consts.EventType;
import framework.events.EventDispatcher;
import framework.events.IEventListener;
import framework.io.FileIO;
import framework.io.HttpData;
import framework.resources.loader.Loader;
import framework.util.JSONUtil;
import framework.util.StringUtil;

/**
 * 网络资源获取器
 * 
 * @author Jing
 */
public class HttpResGetter extends AResGetter implements IEventListener
{

	/**
	 * 资源表
	 */
	private Hashtable _resTable = new Hashtable();

	/**
	 * 资源对应的数据表
	 */
	private Hashtable _dataTable = new Hashtable();

	/**
	 * HTTP服务地址
	 */
	private String _httpServer = null;

	/**
	 * 加载器
	 */
	private Loader _loader = new Loader();

	protected HttpResGetter(String path, String httpServer)
	{
		init(path, httpServer);
		_loader.addEventListener(EventType.EVENT_LOAD_COMPLETE, this);
	}

	protected HttpResGetter()
	{
		_loader.addEventListener(EventType.EVENT_LOAD_COMPLETE, this);
	}

	public void init(String path, String httpServer)
	{
		if(httpServer.charAt(httpServer.length() - 1) == '/')
		{
			httpServer = httpServer.substring(0, httpServer.length() - 1);
		}
		_httpServer = httpServer;

		try
		{
			HttpData hd = FileIO.getHttpData(_httpServer + path);
			System.out.println(hd.data().length);
			JSONObject jsonObj = JSONUtil.bytes2Json(hd.data());
			JSONArray resList = jsonObj.getJSONArray("resources");
			for(int i = 0; i < resList.length(); i++)
			{
				JSONObject resItem = resList.getJSONObject(i);
				String itemName = resItem.getString("name");
				String itemType = resItem.getString("type");
				String itemUrl = _httpServer + "/" + resItem.getString("url");
				ResItem item = new ResItem(itemName, itemType, itemUrl);

				if(itemType.equals("sheet"))
				{
					String subkeys = resItem.getString("subkeys");
					item.subkeys = subkeys;
				}

				_resTable.put(item.name(), item);
			}
		}
		catch(JSONException e)
		{

		}

		System.out.println("Total web resource:" + _resTable.size());
	}

	/**
	 * 异步加载图片
	 * 
	 * @param name
	 */
	public void getImageAsyn(String name)
	{
		ResItem item = getResItem(name, Res.TYPE_IMAGE);
		_loader.load(item);
	}

	/**
	 * 异步获取JSON
	 * 
	 * @param name
	 */
	public void getJsonAsyn(String name)
	{
		ResItem item = getResItem(name, Res.TYPE_JSON);
		_loader.load(item);
	}

	/**
	 * 异步获取纹理集
	 * 
	 * @param name
	 */
	public void getSheetAsyn(String name)
	{
		ResItem item = getResItem(name, Res.TYPE_SHEET);
		_loader.load(item);
	}

	/**
	 * 异步获取字体
	 * 
	 * @param name
	 */
	public void getFontSheetAsyn(String name)
	{
		ResItem item = getResItem(name, Res.TYPE_FONT);
		_loader.load(item);
	}

	public void onReciveEvent(byte type, EventDispatcher dispatcher, Object data)
	{
		this.dispatchEvent(type, data);
	}

	/**
	 * 获取JSON对象
	 * 
	 * @param name
	 * @return
	 */
	public JSONObject getJson(String name)
	{
		JSONObject obj = null;
		ResItem item = getResItem(name, Res.TYPE_JSON);
		if(false == _dataTable.containsKey(name))
		{
			HttpData hd = FileIO.getHttpData(item.url());
			obj = JSONUtil.bytes2Json(hd.data());
			_dataTable.put(name, obj);
		}
		else
		{
			obj = (JSONObject)_dataTable.get(name);
		}
		return obj;
	}

	/**
	 * 获取图片资源(以后不再提供，建议使用getTexture)
	 * 
	 * @param name 图片资源的名称
	 * @return
	 */
	public Image getImage(String name)
	{
		Image image = null;
		ResItem item = getResItem(name, Res.TYPE_IMAGE);
		if(false == _dataTable.containsKey(name))
		{
			String url = item.url();
			HttpData hd = FileIO.getHttpData(url);
			byte[] b = hd.data();
			int len = b.length;
			image = Image.createImage(b, 0, len);
			_dataTable.put(name, image);
		}
		else
		{
			image = (Image)_dataTable.get(name);
		}

		return image;
	}
	
	/**
	 * 获取图片纹理
	 * @param name
	 * @return
	 */
	public Texture getTexture(String name)
	{
		Texture t = null;
		ResItem item = getResItem(name,Res.TYPE_TEXTURE);
		if(false == _dataTable.containsKey(name))
		{
			String url = item.url();
			HttpData hd = FileIO.getHttpData(url);
			byte[] b = hd.data();
			int len = b.length;
			Image image = Image.createImage(b, 0, len);
			t = new Texture(image);
			_dataTable.put(name, t);
		}
		else
		{
			t = (Texture)_dataTable.get(name);
		}
		
		return t;		
	}

	/**
	 * 获取纹理集
	 * 
	 * @param name
	 * @return
	 */
	public SpriteSheet getSheet(String name)
	{
		SpriteSheet ss = null;
		ResItem item = getResItem(name, Res.TYPE_SHEET);
		if(false == _dataTable.containsKey(name))
		{
			String url = item.url();
			HttpData hd = FileIO.getHttpData(url);
			JSONObject jsonObj = JSONUtil.bytes2Json(hd.data());
			String imagePath = url.substring(0, url.length() - 5) + ".png";
			HttpData imgHD = FileIO.getHttpData(imagePath);
			Image sheet = Image.createImage(imgHD.data(), 0, imgHD.data().length);

			ss = new SpriteSheet(jsonObj, StringUtil.split(item.subkeys, ','), sheet);
			_dataTable.put(name, ss);
		}
		else
		{
			ss = (SpriteSheet)_dataTable.get(name);
		}

		return ss;
	}

	/**
	 * 获取字符集
	 * 
	 * @param name
	 * @return
	 */
	public FontSheet getFontSheet(String name)
	{
		FontSheet fs = null;
		ResItem item = getResItem(name, Res.TYPE_FONT);
		if(false == _dataTable.containsKey(name))
		{
			String url = item.url();
			HttpData hd = FileIO.getHttpData(url);
			JSONObject jsonObj = JSONUtil.bytes2Json(hd.data());
			String imagePath = url.substring(0, url.length() - 4) + ".png";
			HttpData imgHD = FileIO.getHttpData(imagePath);
			Image sheet = Image.createImage(imgHD.data(), 0, imgHD.data().length);

			fs = new FontSheet(jsonObj, sheet);
			_dataTable.put(name, fs);
		}
		else
		{
			fs = (FontSheet)_dataTable.get(name);
		}

		return fs;
	}
	
	/**
	 * 获取音频文件
	 */
	public Player getAudio(String name)
	{
		Player p = null;
		ResItem item = getResItem(name, Res.TYPE_SOUND);
		if(false == _dataTable.contains(name))
		{
			try
			{
				System.out.println("构建声音资源：" + item.url());
				p = Manager.createPlayer(item.url());
				_dataTable.put(name, p);
			}
			catch(IOException e)
			{
				System.out.println("资源加载出错：" + item.url());
				e.printStackTrace();
			}
			catch(MediaException e)
			{
				System.out.println("媒体出错：" + item.url());
				e.printStackTrace();
			}
		}
		else
		{
			p = (Player)_dataTable.get(name);
		}
		return p;
	}


	/**
	 * 获取资源的URL地址
	 * 
	 * @param name
	 * @return
	 */
	public String getUrl(String name)
	{
		String url;
		if(_resTable.containsKey(name))
		{
			ResItem item = (ResItem)_resTable.get(name);
			url = item.url();
		}
		else
		{
			url = name;
			if(url.charAt(0) != '/')
			{
				url = '/' + url;
			}
			url = _httpServer + url;
		}
		return url;
	}


	/**
	 * 释放资源
	 */
	public void release(String name)
	{
		if(_dataTable.containsKey(name))
		{
			_dataTable.remove(name);
		}
	}

	private ResItem getResItem(String name, String type)
	{
		ResItem item = null;
		if(_resTable.containsKey(name))
		{
			item = (ResItem)_resTable.get(name);
		}
		else
		{
			String url = name;
			if(url.charAt(0) != '/')
			{
				url = '/' + url;
			}
			url = _httpServer + url;
			item = new ResItem(name, type, url);
			_resTable.put(name, item);
		}
		return item;
	}

}
