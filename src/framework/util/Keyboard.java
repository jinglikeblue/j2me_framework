package framework.util;

import java.util.Hashtable;

import framework.consts.EventType;
import framework.events.EventDispatcher;

/**
 * 键盘对象
 * @author Jing
 *
 */
public class Keyboard extends EventDispatcher
{	
	/**
	 * 按键映射
	 */
	private Hashtable _map = new Hashtable();
	
	public Keyboard()
	{
		
	}
	
	/**
	 * 设置按下的键
	 * @param keyCode
	 */
	public void pressed(int keyCode)
	{
		_map.put(new Integer(keyCode), new Boolean(true));
		this.dispatchEvent(EventType.EVENT_KEY_PRESSED, new Integer(keyCode));
	}
	
	/**
	 * 设置释放的键
	 * @param keyCode
	 */
	public void released(int keyCode)
	{
		_map.put(new Integer(keyCode), new Boolean(false));
		this.dispatchEvent(EventType.EVENT_KEY_RELEASEED, new Integer(keyCode));
	}
	
	/**
	 * 检测指定按键是否按下
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
