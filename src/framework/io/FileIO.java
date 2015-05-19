
package framework.io;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.midlet.MIDlet;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreFullException;
import javax.microedition.rms.RecordStoreNotFoundException;

import org.json.me.JSONException;
import org.json.me.JSONObject;

/**
 * 文件操作
 * 
 * @author Jing
 */
public class FileIO
{

	/**
	 * 以字符串形式读取文件内容
	 * 
	 * @return
	 */
	static public JSONObject getJson(String path)
	{
		JSONObject jsonObj = null;
		InputStream is = MIDlet.class.getResourceAsStream(path);
		if(null == is)
		{
			System.out.println("JSON文件不存在: " + path);
			return null;
		}
		int length = 0;
		try
		{
			length = is.available();
			byte[] bs = new byte[length];
			is.read(bs, 0, length);
			String json = new String(bs, 0, length, "UTF-8");
			jsonObj = new JSONObject(json);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		catch(JSONException e)
		{
			e.printStackTrace();
		}

		return jsonObj;
	}	
	
	/**
	 * 获取网络输入流
	 * @param url
	 * @return
	 */
	static public InputStream getNetInputStream(String url)
	{		
		try
		{
			HttpConnection c = null;
			InputStream is = null;
			c = (HttpConnection)Connector.open(url, Connector.READ);
			int rc = c.getResponseCode();
			if(rc != HttpConnection.HTTP_OK)
			{
				System.out.println(rc + " http request wrong: " + url);				
			}
			else
			{
				is = c.openInputStream();
				return is;
			}
		}
		catch(IOException e)
		{
			System.out.println(" http request wrong: " + url);
			e.printStackTrace();
		}
		
		return null;
	}	
	
	/**
	 * 获取网络数据
	 * @param url
	 * @return
	 */
	static public HttpData getHttpData(String url)
	{
		HttpData hd = null;
		try
		{
			HttpConnection c = null;
			DataInputStream dis = null;
			c = (HttpConnection)Connector.open(url, Connector.READ);
			int rc = c.getResponseCode();
			if(rc != HttpConnection.HTTP_OK)
			{
				System.out.println(rc + " http request wrong: " + url);				
			}
			else
			{
				System.out.println("requested: " + url);
				dis = c.openDataInputStream();
				int len = (int)c.getLength();
				byte[] b = new byte[len];
				dis.readFully(b);
				hd = new HttpData(rc, dis, b);
				return hd;
			}
		}
		catch(IOException e)
		{
			System.out.println(" http request wrong: " + url);
			e.printStackTrace();
		}
		
		return hd;		
	}
	
	
	/**
	 * 获取输入流
	 * @param path
	 * @return
	 */
	static public InputStream getInputStream(String path)
	{
		InputStream is = MIDlet.class.getResourceAsStream(path);
		return is;
	}
	
	/**
	 * 保存文件内容到RMS
	 * @param b
	 * @return
	 */
	static public boolean rmsSave(byte[] b, String storeName)
	{		
		try
		{
			RecordStore rs = RecordStore.openRecordStore(storeName, true);			
			if(rs.getNumRecords() == 0)
			{
				rs.addRecord(b, 0, b.length);				
			}
			else
			{
				rs.setRecord(1, b, 0, b.length);
			}
			rs.closeRecordStore();
			return true;
		}
		catch(RecordStoreFullException e)
		{			
			e.printStackTrace();
		}
		catch(RecordStoreNotFoundException e)
		{			
			e.printStackTrace();
		}
		catch(RecordStoreException e)
		{			
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 从RMS读取内容
	 * @param name
	 * @return
	 */
	static public byte[] rmsLoad(String storeName)
	{		
		try
		{
			RecordStore rs = RecordStore.openRecordStore(storeName, false);
			if(rs.getNumRecords() > 0)
			{		 
				 byte[] b = rs.getRecord(1);
				 return b;
			}
			rs.closeRecordStore();
			return null;
		}
		catch(RecordStoreFullException e)
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		catch(RecordStoreNotFoundException e)
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		catch(RecordStoreException e)
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return null;
	}
}
