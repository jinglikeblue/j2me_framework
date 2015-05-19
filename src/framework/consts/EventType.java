
package framework.consts;

/**
 * 时间类型
 * 
 * @author Jing
 */
public class EventType
{

	/**
	 * 舞台进入新的一帧
	 */
	static public final byte EVENT_STAGE_ENTER_FRAME = 1;

	/**
	 * 键盘按下事件
	 */
	static public final byte EVENT_KEY_PRESSED = 2;

	/**
	 * 键盘弹起事件
	 */
	static public final byte EVENT_KEY_RELEASEED = 3;

	/**
	 * Tween结束事件
	 */
	static public final byte EVENT_TWEEN_COMPLETE = 4;

	/**
	 * MC又从头开始播放了
	 */
	static public final byte EVENT_MOVIE_CLIP_RESTART = 5;

	/**
	 * 时间，Loader加载完成
	 */
	static public final byte EVENT_LOAD_COMPLETE = 6;

}
