
package framework.resources;

/**
 * 资源项
 * 
 * @author Jing
 */
public class ResItem
{

	private String _name = null;

	/**
	 * 资源名
	 * @return
	 */
	public String name()
	{
		return _name;
	}

	private String _type = null;

	/**
	 * 资源类型
	 * @return
	 */
	public String type()
	{
		return _type;
	}

	private String _url = null;
	
	/**
	 * 资源地址
	 * @return
	 */
	public String url()
	{
		return _url;
	}
	
	/**
	 * 只有sheet类型才有的属性
	 */
	public String subkeys = null;

	public ResItem(String name, String type, String url)
	{
		_name = name;
		_type = type;
		_url = url;
	}


}
