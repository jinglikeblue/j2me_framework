
package debug.units;

import java.util.Random;

import framework.resources.Res;
import framework.views.Bitmap;
import framework.views.DisplayObject;
import framework.views.MovieClip;
import framework.views.Quad;
import framework.views.Sprite;
import framework.views.Stage;

public class AddSprTest extends Sprite
{

	public AddSprTest()
	{

	}

	protected void addedToStage()
	{
		int w = Stage.current.getWidth();
		int h = Stage.current.getHeight();

		Random rm = new Random();

		int imgCount = 50;
		while(--imgCount > -1)
		{
			String imgName = "wan_" + (rm.nextInt(3) + 1) + "_" + (rm.nextInt(8) + 1) + "_png";
			// String imgName = "picture_png";
			Bitmap bmd = new Bitmap(Res.localRes.getTexture(imgName), DisplayObject.ANCHOR_CENTER);
			// bmd.setPosition(640, 0);
			bmd.setPosition(rm.nextInt(w), rm.nextInt(h));
			this.addChild(bmd);
		}

		int mcCount = 0;
		while(--mcCount > -1)
		{
			String mcName = "wind_json";
			MovieClip mc = new MovieClip(Res.localRes.getSheet(mcName), 20, DisplayObject.ANCHOR_CENTER);
			// mc.setPosition(640, 0);
			mc.setPosition(rm.nextInt(w), rm.nextInt(h));
			this.addChildAt(mc, rm.nextInt(this.numChildren()));
		}

		this.addChild(new Quad(0xFF0000, 100, 100, 0));
	}

	protected void removedFromStage()
	{
		System.out.println("removedFromStage");
	}
}
