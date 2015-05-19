
package framework.events;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * �¼��㲥��
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
	 * �㲥һ���¼�
	 * 
	 * @param type �¼�������
	 */
	public void dispatchEvent(byte type)
	{
		dispatchEvent(type, null);
	}

	/**
	 * �㲥һ���¼�
	 * 
	 * @param type �¼�������
	 * @param data �¼�Я���Ĳ���
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
	 * ����ָ�����¼�
	 * 
	 * @param type �¼�������
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
	 * �Ƴ�ָ�����¼�����
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
