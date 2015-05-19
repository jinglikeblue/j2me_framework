package debug.units;

import javax.microedition.lcdui.Image;

import framework.consts.EventType;
import framework.events.EventDispatcher;
import framework.events.IEventListener;
import framework.resources.Res;
import framework.resources.loader.LoaderItem;
import framework.views.Bitmap;
import framework.views.Sprite;

/**
 * º”‘ÿ∆˜≤‚ ‘
 * @author Jing
 *
 */
public class LoaderTest extends Sprite implements IEventListener
{
	
	public LoaderTest()
	{
		Res.httpRes.addEventListener(EventType.EVENT_LOAD_COMPLETE, this);
		Res.httpRes.getImageAsyn("charge_png");

	}
	public void onReciveEvent(byte type, EventDispatcher dispatcher, Object data)
	{
		LoaderItem item = (LoaderItem)data;
		if(item.item().name().equals("charge_png"))
		{
			Image image = (Image)item.obj();
//			Bitmap bmd = new Bitmap(image);
//			this.addChild(bmd);
		}
	}
}
