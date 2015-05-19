package debug.units;

import javax.microedition.lcdui.Font;

import framework.consts.EventType;
import framework.consts.TransType;
import framework.events.EventDispatcher;
import framework.events.IEventListener;
import framework.views.DisplayObject;
import framework.views.Quad;
import framework.views.Sprite;
import framework.views.Stage;
import framework.views.TextField;

/**
 * Œƒ±æ≤‚ ‘
 * @author Jing
 *
 */
public class TxtTest extends Sprite implements IEventListener
{
	int i = 0;
	TextField tf;
	public TxtTest()
	{
		
		//this.setPosition(100, 100);
		Quad quad = new Quad(0xFF0000,100,100,0.7);
		this.addChild(quad);
		
		tf = new TextField();
		tf.setTrans(TransType.ROT90);
		tf.setAnchor(DisplayObject.ANCHOR_CENTER);
		this.addChild(tf);
		tf.setBold(true);;
		tf.setColor(0xFFFFFF);
		tf.setLinegap(-10);
		//tf.setPosition(100, 100);
		tf.setText("hello world \nfuck you \nkiss my ass");
		tf.setSize(Font.SIZE_LARGE);
		Sprite spr = new Sprite();
		spr.addChild(tf);
		this.addChild(spr);
		//tf.setTrans(TransType.NONE);
		spr.setPosition(100, 100);
		
		Stage.current.notice.addEventListener(EventType.EVENT_STAGE_ENTER_FRAME, this);
	}
	
	public void onReciveEvent(byte type, EventDispatcher dispatcher, Object data)
	{
		tf.setText(String.valueOf(i++));
	}
}
