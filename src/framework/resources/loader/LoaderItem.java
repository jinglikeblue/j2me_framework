package framework.resources.loader;

import framework.resources.ResItem;

/**
 * º”‘ÿœÓ
 * @author Jing
 *
 */
public class LoaderItem
{
	private ResItem _item = null;
	public ResItem item()
	{
		return _item;
	}
	
	private Object _obj = null;
	public Object obj()
	{
		return _obj;
	}
	
	protected LoaderItem(ResItem item, Object obj)
	{
		_item = item;
		_obj = obj;
	}
}
