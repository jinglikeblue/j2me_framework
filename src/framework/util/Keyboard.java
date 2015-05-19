package framework.util;

import java.util.Hashtable;

import framework.consts.EventType;
import framework.events.EventDispatcher;

/**
 * ���̶���
 * @author Jing
 *
 */
public class Keyboard extends EventDispatcher
{	
	/**
	 * ����ӳ��
	 */
	private Hashtable _map = new Hashtable();
	
	public Keyboard()
	{
		
	}
	
	/**
	 * ���ð��µļ�
	 * @param keyCode
	 */
	public void pressed(int keyCode)
	{
		_map.put(new Integer(keyCode), new Boolean(true));
		this.dispatchEvent(EventType.EVENT_KEY_PRESSED, new Integer(keyCode));
	}
	
	/**
	 * �����ͷŵļ�
	 * @param keyCode
	 */
	public void released(int keyCode)
	{
		_map.put(new Integer(keyCode), new Boolean(false));
		this.dispatchEvent(EventType.EVENT_KEY_RELEASEED, new Integer(keyCode));
	}
	
	/**
	 * ���ָ�������Ƿ���
	 * @param keyCode
	 * @return
	 */
	public boolean isPressed(int keyCode)
	{
		if(false == _map.containsKey(new Integer(keyCode)))
		{
			return false;
		}
		Boolean bool = (Boolean)_map.get(new Integer(keyCode));
		
		return bool.booleanValue();		
	}
}
