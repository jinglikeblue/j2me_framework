
package debug;

import debug.units.MovieClipTest;
import debug.units.SpriteTest;
import framework.resources.Res;
import framework.views.Sprite;
import framework.views.Stage;

public class DebugEntry extends Sprite
{

	public static final String server = "http://192.168.2.173/itv/res";

	public DebugEntry()
	{
		super();
	}

	protected void addedToStage()
	{		
		Res.localRes.init("/res.json");
		Res.httpRes.init("/res.json", server);
		Res.actively = Res.httpRes;
		
		Stage.current.showStats(true);
		Stage.current.setFullScreenMode(true);
		this.addChild(new MovieClipTest());
	}
}
