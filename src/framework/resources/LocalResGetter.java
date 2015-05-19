
package framework.resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

import javax.microedition.lcdui.Image;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;

import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

import framework.events.EventDispatcher;
import framework.events.IEventListener;
import framework.io.FileIO;
import framework.util.StringUtil;

/**
 * ������Դ��ȡ��
 * 
 * @author Jing
 */
public class LocalResGetter extends AResGetter implements IEventListener
{

	/**
	 * ��Դ��
	 */
	private Hashtable _resTable = new Hashtable();

	/**
	 * ��Դ��Ӧ�����ݱ�
	 */
	private Hashtable _dataTable = new Hashtable();

	protected LocalResGetter(String path)
	{
		init(path);
	}

	protected LocalResGetter()
	{

	}

	public void init(String path)
	{
		try
		{
			JSONObject jsonObj = FileIO.getJson(path);
			JSONArray resList = jsonObj.getJSONArray("resources");
			for(int i = 0; i < resList.length(); i++)
			{
				JSONObject resItem = resList.getJSONObject(i);
				String itemName = resItem.getString("name");
				String itemType = resItem.getString("type");
				String itemUrl = "/" + resItem.getString("url");
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

		System.out.println("Total local resource:" + _resTable.size());
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
			String url = item.url();
			obj = FileIO.getJson(url);
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
			try
			{
				image = Image.createImage(url);
				_dataTable.put(name, image);
			}
			catch(IOException e)
			{
				System.out.println("��Դ���س���" + url);
			}
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
			try
			{
				Image image = Image.createImage(url);
				t = new Texture(image);
				_dataTable.put(name, t);
			}
			catch(IOException e)
			{
				System.out.println("��Դ���س���" + url);
			}
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
			JSONObject jsonObj = FileIO.getJson(url);
			String imagePath = url.substring(0, url.length() - 5) + ".png";			
			Image sheet = null;
			try
			{
				sheet = Image.createImage(imagePath);
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
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
			fs = new FontSheet(item.url());
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
	 * 
	 * @param name ��Ƶ�ļ���Դ��
	 * @param type ��Ƶ�ļ�����
	 *            <ul>
	 *            <li>Wave audio files: audio/x-wav</li>
	 *            <li>AU audio files: audio/basic</li>
	 *            <li>MP3 audio files: audio/mpeg</li>
	 *            <li>MIDI files: audio/midi</li>
	 *            <li>Tone sequences: audio/x-tone-seq</li>
	 *            </ul>
	 */
	public Player getAudio(String name, String type)
	{
		Player p = null;
		ResItem item = getResItem(name, Res.TYPE_SOUND);
		if(false == _dataTable.contains(name))
		{
			InputStream is = FileIO.getInputStream(item.url());

			if(null == is)
			{
				System.out.println("��Դ�����ڣ�" + item.url());
			}
			else
			{
				try
				{
					p = Manager.createPlayer(is, type);
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
		}
		return url;
	}

	/**
	 * �ͷ���Դ
	 * 
	 * @param name ͼƬ��Դ������
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
			item = new ResItem(name, type, url);
			_resTable.put(name, item);
		}
		return item;
	}

	public void onReciveEvent(byte type, EventDispatcher dispatcher, Object data)
	{
		this.dispatchEvent(type, data);
	}

}
