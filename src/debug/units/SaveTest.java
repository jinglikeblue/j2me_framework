
package debug.units;

import framework.io.FileIO;
import framework.views.Sprite;

/**
 * ´æ´¢ÎÄ¼þ²âÊÔ
 * 
 * @author Jing
 */
public class SaveTest extends Sprite
{

	public SaveTest()
	{
		//String string = "hello pig";
		//FileIO.rmsSave(string.getBytes(), "xxxx");

		byte[] dat = FileIO.rmsLoad("xxxx");
		String content = new String(dat);
		System.out.println(content);
	}
}


