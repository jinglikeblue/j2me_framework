
package framework.io;

import java.io.InputStream;

/**
 * Http�������������
 * 
 * @author Jing
 */
public class HttpData
{

	private int _rc = 0;

	/**
	 * ���緵����
	 */
	public int rc()
	{
		return _rc;
	}

	private InputStream _is = null;

	/**
	 * ������
	 * @return
	 */
	public InputStream inputStream()
	{
		return _is;
	}

	private byte[] _data = null;

	/**
	 * �õ�������
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
