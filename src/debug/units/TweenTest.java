
package debug.units;

import java.util.Random;

import framework.consts.EventType;
import framework.consts.KeyType;
import framework.events.EventDispatcher;
import framework.events.IEventListener;
import framework.resources.Res;
import framework.time.ITickListener;
import framework.time.TickItem;
import framework.tween.Tween;
import framework.views.DisplayObject;
import framework.views.MovieClip;
import framework.views.Sprite;
import framework.views.Stage;

public class TweenTest extends Sprite implements IEventListener, ITickListener
{

	private int _amount = 1;
	private boolean _visible = false;
	private Random r = new Random();

	public TweenTest()
	{

		Stage.current.ticker.setTimeInterval(1000, this, null);
		//onTick(null);
		Stage.current.keyboard.addEventListener(EventType.EVENT_KEY_PRESSED, this);
	}

	public void onReciveEvent(byte type, EventDispatcher dispatcher, Object data)
	{
		if(type == EventType.EVENT_TWEEN_COMPLETE)
		{
			Tween t = (Tween)dispatcher;
			this.removeChild(t.binded());
		}
		else if(type == EventType.EVENT_KEY_PRESSED)
		{
			Integer keyCode = (Integer)data;
			switch(keyCode.byteValue())
			{
				case KeyType.UP:

					_amount++;
					
					break;
				case KeyType.DOWN:

					_amount = _amount > 0 ? _amount - 1 : 0;

					break;
				case KeyType.LEFT:
					_visible = false;
					break;
				case KeyType.RIGHT:
					_visible = true;
					break;
			}
		}

	}

	public void onTick(TickItem tickItem)
	{		
		for(int i = 0; i < _amount; i++)
		{
			MovieClip mc = new MovieClip(Res.localRes.getSheet("wind_json"), 16, DisplayObject.ANCHOR_CENTER);
			
			this.addChild(mc);
			mc.setVisible(_visible);
			
			
			int toX = r.nextInt(Stage.current.stageWidth());
			int toY = r.nextInt(Stage.current.stageHeight());
			//System.out.println(toX + ":" + toY);
			mc.setPosition(toX, toY);
	

			toX = r.nextInt(Stage.current.stageWidth());
			toY = r.nextInt(Stage.current.stageHeight());
			Tween t = Tween.toPosition(mc, 0, 1000, toX, toY);// .toPosition(3000,
																// 1000, mc.getX(),
																// mc.getY());
			t.addEventListener(EventType.EVENT_TWEEN_COMPLETE, this);
		}
	}
}
