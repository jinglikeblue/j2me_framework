package debug.units;

import framework.consts.EventType;
import framework.events.EventDispatcher;
import framework.events.IEventListener;
import framework.views.Sprite;
import framework.views.Stage;

/**
 * …˘“Ù≤‚ ‘
 * @author Jing
 *
 */
public class AudioTest extends Sprite implements IEventListener
{
	public AudioTest()
	{
		//new Sound("bg_wav");
		//AudioDevice.playBGM("bg_wav");
		Stage.current.keyboard.addEventListener(EventType.EVENT_KEY_PRESSED, this);
	}

	public void onReciveEvent(byte type, EventDispatcher dispatcher, Object data) {
		
		

		

	}
}
