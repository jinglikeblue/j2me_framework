
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
 * ������Դ��ȡ��
 * 
 * @author Jing
 */
public class HttpResGetter extends AResGetter implements IEventListener
{

	/**
	 * ��Դ��
	 */
	private Hashtable _resTable = new Hashtable();

	/**
	 * ��Դ��Ӧ�����ݱ�
	 */
	private Hashtable _dataTable = new Hashtable();

	/**
	 * HTTP�����ַ
	 */
	private String _httpServer = null;

	/**
	 * ������
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
	 * �첽����ͼƬ
	 * 
	 * @param name
	 */
	public void getImageAsyn(String name)
	{
		ResItem item = getResItem(name, Res.TYPE_IMAGE);
		_loader.load(item);
	}

	/**
	 * �첽��ȡJSON
	 * 
	 * @param name
	 */
	public void getJsonAsyn(String name)
	{
		ResItem item = getResItem(name, Res.TYPE_JSON);
		_loader.load(item);
	}

	/**
	 * �첽��ȡ����
	 * 
	 * @param name
	 */
	public void getSheetAsyn(String name)
	{
		ResItem item = getResItem(name, Res.TYPE_SHEET);
		_loader.load(item);
	}

	/**
	 * �첽��ȡ����
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
	 * ��ȡJSON����
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
	 * ��ȡͼƬ��Դ(�Ժ����ṩ������ʹ��getTexture)
	 * 
	 * @param name ͼƬ��Դ������
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
	 * ��ȡͼƬ����
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
	 * ��ȡ����
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
	 * ��ȡ�ַ���
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
	 * ��ȡ��Ƶ�ļ�
	 */
	public Player getAudio(String name)
	{
		Player p = null;
		ResItem item = getResItem(name, Res.TYPE_SOUND);
		if(false == _dataTable.contains(name))
		{
			try
			{
				System.out.println("����������Դ��" + item.url());
				p = Manager.createPlayer(item.url());
				_dataTable.put(name, p);
			}
			catch(IOException e)
			{
				System.out.println("��Դ���س���" + item.url());
				e.printStackTrace();
			}
			catch(MediaException e)
			{
				System.out.println("ý�����" + item.url());
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
	 * ��ȡ��Դ��URL��ַ
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
	 * �ͷ���Դ
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
