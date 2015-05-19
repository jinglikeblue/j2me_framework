
package framework.events;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * 事件广播者
 * 
 * @author Jing
 */
public class EventDispatcher
{

	private Hashtable _eventTbl = new Hashtable();

	public EventDispatcher()
	{

	}

	/**
	 * 广播一个事件
	 * 
	 * @param type 事件的类型
	 */
	public void dispatchEvent(byte type)
	{
		dispatchEvent(type, null);
	}

	/**
	 * 广播一个事件
	 * 
	 * @param type 事件的类型
	 * @param data 事件携带的参数
	 */
	public void dispatchEvent(byte type, Object data)
	{
		Vector listeners = (Vector)_eventTbl.get(new Byte(type));
		if(null == listeners)
		{
			return;
		}

		for(Enumeration enumobj = listeners.elements(); enumobj.hasMoreElements();)
		{
			IEventListener listener = (IEventListener)enumobj.nextElement();
			listener.onReciveEvent(type, this, data);
		}
	}

	/**
	 * 监听指定的事件
	 * 
	 * @param type 事件的类型
	 * @param listener
	 */
	public void addEventListener(byte type, IEventListener listener)
	{
		Vector listeners = (Vector)_eventTbl.get(new Byte(type));
		if(null == listeners)
		{
			listeners = new Vector();
			_eventTbl.put(new Byte(type), listeners);
		}

		if(false == listeners.contains(listener))
		{
			listeners.addElement(listener);
		}
	}

	/**
	 * 移除指定的事件监听
	 */
	public void removeEventListener(byte type, IEventListener listener)
	{
		Vector listeners = (Vector)_eventTbl.get(new Byte(type));
		if(null != listeners)
		{
			listeners.removeElement(listener);			
		}
	}
}
