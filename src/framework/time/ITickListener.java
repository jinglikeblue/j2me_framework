package framework.time;


/**
 * 时间触发捕获器
 * @author Jing
 *
 */
public interface ITickListener
{
	/**
	 * 
	 * @param tickItem 心跳对象
	 */
	void onTick(TickItem tickItem);
}
