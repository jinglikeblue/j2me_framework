package debug.units;

import framework.consts.EventType;
import framework.events.EventDispatcher;
import framework.events.IEventListener;
import framework.views.Sprite;
import framework.views.Stage;


public class KeyTest extends Sprite implements IEventListener
{
	public KeyTest()
	{
		Stage.current.keyboard.addEventListener(EventType.EVENT_KEY_PRESSED, this);
	}

	public void onReciveEvent(byte type, EventDispatcher dispatcher, Object data)
	{
		Stage.current.setStatsOutput(data.toString());
		//System.out.println(data);
		
	}
}
