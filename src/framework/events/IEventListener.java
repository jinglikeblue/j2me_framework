package framework.events;

/**
 * 事件监听者接口
 * 
 * @author Jing
 */
public interface IEventListener
{
	/**
	 * 接收事件
	 * 
	 * @param type 事件类型
	 * @param dispatcher 广播者
	 * @param data 携带的数据
	 */
	void onReciveEvent(byte type, EventDispatcher dispatcher, Object data);	
}
