
package debug.units;

import framework.resources.Res;
import framework.util.StringUtil;
import framework.views.Bitmap;
import framework.views.DisplayObject;
import framework.views.Sprite;

public class SpriteTest extends Sprite
{

	public SpriteTest()
	{
		String str = "hello world";
		str = StringUtil.replace(str, "l", " l ", true);
		System.out.println(str);
		
		String str1 = "x=%s,y=%s";
		String[] repls = {String.valueOf(1),"2"};
		str1 = StringUtil.format(str1, repls);
		System.out.println(str1);
		
		Sprite sprite = new Sprite();		
		Bitmap bmd = new Bitmap(Res.actively.getTexture("backgroud_png"), DisplayObject.ANCHOR_TOP_LEFT);
		//bmd.setPosition(300, 100);
		System.out.println(bmd.getBounds().toString());
		//bmd.setScale(2, 0.5);
		System.out.println(bmd.getBounds().toString());
		sprite.addChild(bmd);
		//bmd.setTrans(TransType.ROT90);
		//sprite.addChild(bmd2);

		//setCenter(bmd2, bmd);
		sprite.setPosition(0, 0);

		this.addChild(sprite);

	}

	/**
	 * 将显示对象a移动后和b以中心点对齐
	 * 
	 * @param a
	 * @param b
	 */
	public void setCenter(DisplayObject a, DisplayObject b)
	{
		int x = b.getX() + ((b.getWidth() - a.getWidth()) >> 1);
		int y = b.getY() + ((b.getHeight() - a.getHeight()) >> 1);
		a.setPosition(x, y);
	}
}
