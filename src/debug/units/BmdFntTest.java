package debug.units;

import framework.events.EventDispatcher;
import framework.events.IEventListener;
import framework.views.BitmapFont;
import framework.views.Sprite;


public class BmdFntTest extends Sprite implements IEventListener
{
	
	int i = 0;
	BitmapFont fnt = null;
	public BmdFntTest()
	{		
//		Res.init("/res.json", Global.server);
//		this.setPosition(100, 100);
//		Quad quad = new Quad(0xFF0000,100,100,0.7);
//		this.addChild(quad);
//		
//		Sprite spr = new Sprite();
//		fnt = new BitmapFont(Res.getFontSheet("num_2_fnt"));
//		
//		fnt.setPosition(100, 100);
//		fnt.setAnchor(DisplayObject.ANCHOR_CENTER);
//		spr.addChild(fnt);
//		this.addChild(spr);
//		
//		fnt.setText("1234567890");
//		
//		//fnt.setTrans(TransType.ROT90);
//		Stage.current.notice.addEventListener(EventType.EVENT_STAGE_ENTER_FRAME, this);
	}
	public void onReciveEvent(byte type, EventDispatcher dispatcher, Object data)
	{
		fnt.setText(String.valueOf(i++));
	}
}
