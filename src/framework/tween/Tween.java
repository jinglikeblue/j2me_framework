package framework.tween;

import framework.consts.EventType;
import framework.events.EventDispatcher;
import framework.events.IEventListener;
import framework.views.DisplayObject;
import framework.views.Stage;

/**
 * 缓动
 * @author Jing
 *
 */
public class Tween extends EventDispatcher implements IEventListener
{
	private DisplayObject _binded = null;
	public DisplayObject binded()
	{
		return _binded;
	}
	private int _startX = 0;
	private int _startY = 0;
	private int _endX = 0;
	private int _endY = 0;
	private long _startTime = 0;
	private long _endTime = 0;
	private long _duration = 0;
	private int _distanceX = 0;
	private int _distanceY = 0;
	
	/**
	 * 缓动动画类
	 * @param binded
	 */
	protected Tween(DisplayObject binded, long delay, long duration, int targetX, int targetY)
	{
		_binded = binded;
		_startX = _binded.getX();
		_startY = _binded.getY();
		_endX = targetX;
		_endY = targetY;
		_distanceX = _endX - _startX;
		_distanceY = _endY - _startY;
		_startTime = Stage.current.now() + delay;
		_duration = duration;
		_endTime = _startTime + duration;
		
		Stage.current.notice.addEventListener(EventType.EVENT_STAGE_ENTER_FRAME, this);
	}
	
	/**
	 * 缓动到目标位置
	 * @param obj
	 * @param targetX
	 * @param targetY
	 */
	static public Tween toPosition(DisplayObject obj, long delay, long duration, int targetX, int targetY)
	{
		return new Tween(obj, delay, duration, targetX, targetY);
	}
	
//	public Tween toPosition(long delay, long duration, int targetX, int targetY)
//	{
//		delay = delay + (_endTime - Stage.current.now());
//		return new Tween(_binded, delay, duration, targetY, targetY);
//	}
	
	public void destroy()
	{
		destroy(false);
	}
	
	public void destroy(boolean complete)
	{
		if(true == complete)
		{
			_binded.setPosition(_endX, _endY);
		}
		Stage.current.notice.removeEventListener(EventType.EVENT_STAGE_ENTER_FRAME, this);
	}

	public void onReciveEvent(byte type, EventDispatcher dispatcher, Object data)
	{		
		long now = Stage.current.now();
		if(now <= _startTime)
		{
			return;
		}		

		if(now >= _endTime)
		{
			//移动结束			
			destroy(true);	
			this.dispatchEvent(EventType.EVENT_TWEEN_COMPLETE);
			return;
		}
		
		double k = (double)(now - _startTime) / _duration;
		int tempX = (int)(k * _distanceX);
		int tempY = (int)(k * _distanceY);
		_binded.setPosition(_startX + tempX, _startY + tempY);
	}
}
