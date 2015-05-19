
package framework.io;

import java.io.InputStream;

/**
 * Http网络请求的数据
 * 
 * @author Jing
 */
public class HttpData
{

	private int _rc = 0;

	/**
	 * 网络返回码
	 */
	public int rc()
	{
		return _rc;
	}

	private InputStream _is = null;

	/**
	 * 网络流
	 * @return
	 */
	public InputStream inputStream()
	{
		return _is;
	}

	private byte[] _data = null;

	/**
	 * 得到的数据
	 * @return
	 */
	public byte[] data()
	{
		return _data;
	}

	protected HttpData(int rc, InputStream is, byte[] data)
	{
		_rc = rc;
		_is = is;
		_data = data;
	}
}
