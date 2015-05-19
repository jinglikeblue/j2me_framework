import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import debug.DebugEntry;
import framework.views.Stage;

public class Main extends MIDlet
{

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException
	{

		this.destroyApp(true);

		this.notifyDestroyed();
	}

	protected void pauseApp()
	{

	}

	protected void startApp()
	{				
		Display display = Display.getDisplay(this);		
		Stage stage = new Stage(DebugEntry.class, 25, 0x666666, 640, 360);
		display.setCurrent(stage);
	}
}
