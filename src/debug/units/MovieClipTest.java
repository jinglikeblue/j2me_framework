package debug.units;

import framework.events.EventDispatcher;
import framework.events.IEventListener;
import framework.resources.Res;
import framework.resources.SpriteSheet;
import framework.resources.Texture;
import framework.views.Bitmap;
import framework.views.DisplayObject;
import framework.views.MovieClip;
import framework.views.Sprite;
import framework.views.Stage;


public class MovieClipTest extends Sprite implements IEventListener
{
	MovieClip mc = null;
	public MovieClipTest()
	{		
		SpriteSheet ss = Res.actively.getSheet("pai_json").getSpriteSheet("north_hand_");
		Texture t = ss.getTexture("north_hand_2_7");
		
		Bitmap bmd = new Bitmap(t);
		this.addChild(bmd);
		bmd.setPosition((Stage.current.getWidth() >> 1) - 100, Stage.current.getHeight() >> 1);
		
		mc = new MovieClip(ss, 84, DisplayObject.ANCHOR_CENTER);
		mc.setIsLoop(true);
		//mc.setAutoDispose(true);		
		this.addChild(mc);
		mc.setPosition(Stage.current.getWidth() >> 1, Stage.current.getHeight() >> 1);
		
		//mc.gotoAndStop("south_corner_2_7");
	}

	public void onReciveEvent(byte type, EventDispatcher dispatcher, Object data)
	{		
		//this.removeChild(mc);
	}
}
