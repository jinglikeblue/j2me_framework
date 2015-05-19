package debug.units;

import framework.geom.Rectangle;
import framework.views.Sprite;

/*
 * æÿ–Œ≤‚ ‘
 * @author Jing
 *
 */
public class RectTest extends Sprite
{
	public RectTest()
	{		
		Rectangle rect = new Rectangle(0, 0, 100, 100);
		Rectangle rect2 = new Rectangle(50, 50, 100, 100);
		System.out.println(Rectangle.getIntersectRect(rect2, rect).toString());
	}
}
