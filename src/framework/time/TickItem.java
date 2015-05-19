
package framework.time;

/**
 * ʱ�䴥����
 * 
 * @author Jing
 */
public class TickItem
{

	/**
	 * ���� 0:����һ�� 1:�������
	 */
	protected int type = 0;

	private int _id;

	/**
	 * �������ID
	 */
	public int id()
	{
		return _id;
	}
	
	protected void setId(int id)
	{
		_id = id;
	}

	private Object _data = null;

	/**
	 * Я��������
	 */
	public Object data()
	{
		return _data;
	}

	private long _interval = 0;

	/**
	 * ÿ�α������ļ��
	 */
	public long interval()
	{
		return _interval;
	}
	
	private long _tickTime = 0;

	/**
	 * �´δ�����ʱ��
	 * 
	 * @return
	 */
	public long tickTime()
	{
		return _tickTime;
	}

	/**
	 * rebase tick timer with new `now' param
	 * @param now tick base time
	 */
	public void reset( long now)
	{
		_tickTime = now + _interval;
	}

	/**
	 * delay next tick by parameter <code>delay</code>
	 * @param delay ms to delay
	 */
	public void delay( long delay )
	{
		_tickTime += delay;
	}

	private int _count = 1;
	public int get_count()
	{
		return _count;
	}
	public void inc_count()
	{
		_count++;
	}
	public void reset_count()
	{
		_count = 1;
	}

	//---------------------------�ǹ�������
	
	private ITickListener _listener = null;
	/**
	 * ������
	 * @return
	 */
	protected ITickListener listener()
	{
		return _listener;
	}

	/**
	 * �����´δ�����ʱ��
	 * 
	 * @param tickTime
	 */
	protected void setTickTime(long tickTime)
	{
		_tickTime = tickTime;
	}

	protected TickItem(int type, long interval, ITickListener listener, Object data)
	{		
		this.type = type;
		this._interval = interval;
		this._listener = listener;
		this._data = data;
		this._count = 1;
	}
}
