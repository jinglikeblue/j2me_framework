
package framework.resources;

/**
 * ��Դ��
 * 
 * @author Jing
 */
public class ResItem
{

	private String _name = null;

	/**
	 * ��Դ��
	 * @return
	 */
	public String name()
	{
		return _name;
	}

	private String _type = null;

	/**
	 * ��Դ����
	 * @return
	 */
	public String type()
	{
		return _type;
	}

	private String _url = null;
	
	/**
	 * ��Դ��ַ
	 * @return
	 */
	public String url()
	{
		return _url;
	}
	
	/**
	 * ֻ��sheet���Ͳ��е�����
	 */
	public String subkeys = null;

	public ResItem(String name, String type, String url)
	{
		_name = name;
		_type = type;
		_url = url;
	}


}
