package framework.util;

import java.io.UnsupportedEncodingException;

import org.json.me.JSONException;
import org.json.me.JSONObject;

/**
 * JSON���� 
 * @author Jing
 *
 */
public class JSONUtil
{
	/**
	 * �ֽ�����ת����JSON
	 * @param bytes
	 * @return
	 */
	static public JSONObject bytes2Json(byte[] bytes)
	{
		JSONObject jsonObj = null;
		String json;
		try
		{
			json = new String(bytes, 0, bytes.length, "UTF-8");
			jsonObj = new JSONObject(json);
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		catch(JSONException e)
		{
			e.printStackTrace();
		}
		
		return jsonObj;
	}
}
