
package framework.util;

import javax.microedition.lcdui.Image;

/**
 * 图像处理工具
 * @author Administrator
 *
 */
public class ImageUtil
{

	/**
	 * 将img中指定的颜色修改为透明色
	 * 
	 * @param img
	 * @param oColor
	 * @param alpha
	 * @return
	 */
	static public Image alpha(Image img, int color)
	{
		int w = img.getWidth();
		int h = img.getHeight();

		int[] colors = new int[w * h];
		img.getRGB(colors, 0, w, 0, 0, w, h);
		for(int i = 0; i < colors.length; i++)
		{
			int v = colors[i];
			if(v == color)
			{
				colors[i] = 0;
			}
		}
		img = Image.createRGBImage(colors, w, h, true);
		return img;
	}
}
