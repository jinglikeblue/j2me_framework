
package framework.resources.loader;

import java.util.Vector;

import framework.consts.EventType;
import framework.events.EventDispatcher;
import framework.resources.Res;
import framework.resources.ResItem;

public class Loader extends EventDispatcher implements Runnable
{

	/**
	 * 加载用的线程
	 */
	private Thread _t = null;

	private Vector _list = new Vector();

	public Loader()
	{

	}

	public void load(ResItem data)
	{
		_list.addElement(data);

		if(null == _t)
		{
			new Thread(this).start();
		}
	}

	public void run()
	{
		while(_list.size() > 0)
		{
			ResItem item = (ResItem)_list.elementAt(0);
			_list.removeElementAt(0);

			String type = item.type();
			Object obj = null;
			if(type.equals(Res.TYPE_FONT))
			{
				obj = Res.httpRes.getFontSheet(item.name());
			}
			else if(type.equals(Res.TYPE_IMAGE))
			{
				obj = Res.httpRes.getImage(item.name());
			}
			else if(type.equals(Res.TYPE_JSON))
			{
				obj = Res.httpRes.getJson(item.name());
			}
			else if(type.equals(Res.TYPE_SHEET))
			{
				obj = Res.httpRes.getSheet(item.name());
			}
			else if(type.equals(Res.TYPE_SOUND))
			{
				obj = Res.httpRes.getAudio(item.name());
			}
			else if(type.equals(Res.TYPE_TEXTURE))
			{
				obj = Res.httpRes.getAudio(item.name());
			}

			this.dispatchEvent(EventType.EVENT_LOAD_COMPLETE, new LoaderItem(item, obj));
		}

		_t = null;
	}
}
