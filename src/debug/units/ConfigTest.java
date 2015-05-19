package debug.units;

import framework.resources.Res;
import framework.views.Sprite;
import framework.views.Stage;


public class ConfigTest extends Sprite
{
	public ConfigTest()
	{

	}
	
	protected void addedToStage()
	{
		Stage.current.showStats(true);
		
		Res.localRes.init("/res.json");		
//		Global.config = new Config();
//		System.out.println(Global.config.gateAddr());
	}
}
