package debug.units;

import java.util.Enumeration;
import java.util.Vector;

import javax.microedition.lcdui.Image;

import framework.consts.EventType;
import framework.consts.KeyType;
import framework.events.EventDispatcher;
import framework.events.IEventListener;
import framework.resources.Res;
import framework.views.Bitmap;
import framework.views.Sprite;
import framework.views.Stage;


public class ReleaseTest extends Sprite implements IEventListener
{
	private Vector _names = new Vector();
	public ReleaseTest()
	{
		Stage.current.keyboard.addEventListener(EventType.EVENT_KEY_PRESSED, this);
	}

	public void onReciveEvent(byte type, EventDispatcher dispatcher, Object data)
	{

		
		
		byte keyCode = ((Integer)data).byteValue();
		
		switch(keyCode)
		{
			case KeyType.NUM_0:
				for(Enumeration enumobj = _names.elements(); enumobj.hasMoreElements();)
				{
					String name = (String)enumobj.nextElement();
					Res.httpRes.release(name);
				}
				_names = new Vector();
				this.removeAllChildren();
				break;
			case KeyType.NUM_1:
				testJSON("datingscene_json");
				break;
			case KeyType.NUM_2:
				testJSON("res_json");
				break;
			case KeyType.NUM_3:
				testJSON("board_json");
				break;
			case KeyType.NUM_4:
				addRes("title_panel_png");
				break;
			case KeyType.NUM_5:
				addRes("shop_png");
				break;
			case KeyType.NUM_6:
				addRes("setting_png");
				break;
			case KeyType.NUM_7:
				addRes("picture_png");
				break;
			case KeyType.NUM_8:
				addRes("personal_png");
				break;
			case KeyType.NUM_9:
				addRes("majiang_png");
				break;
		}
		
	}
	
	public void addRes(String name)
	{
		_names.addElement(name);
		Sprite spr = new Pic(name);
		this.addChild(spr);
	}
	
	public void testJSON(String name)
	{
		Res.httpRes.getJson(name);
		Res.httpRes.release(name);
	}
	
}


class Pic extends Sprite implements IEventListener
{
	public Pic(String name)
	{
		this.addEventListener(EventType.EVENT_STAGE_ENTER_FRAME, this);
		this.removeEventListener(EventType.EVENT_STAGE_ENTER_FRAME, this);
		//Image img = Res.httpRes.getImage(name);
//		Bitmap bmd = new Bitmap(img);
//		this.addChild(bmd);
//		bmd.setPosition(100, 100);
	}

	public void onReciveEvent(byte type, EventDispatcher dispatcher, Object data)
	{
		// TODO Auto-generated method stub
		
	}
}
