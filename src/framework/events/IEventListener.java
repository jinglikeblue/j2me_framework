package framework.events;

/**
 * �¼������߽ӿ�
 * 
 * @author Jing
 */
public interface IEventListener
{
	/**
	 * �����¼�
	 * 
	 * @param type �¼�����
	 * @param dispatcher �㲥��
	 * @param data Я��������
	 */
	void onReciveEvent(byte type, EventDispatcher dispatcher, Object data);	
}
