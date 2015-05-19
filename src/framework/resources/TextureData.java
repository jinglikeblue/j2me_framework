package framework.resources;

/**
 * 纹理数据
 * @author Jing
 *
 */
public class TextureData
{
	protected String name = null;
	/**
	 * 纹理名称
	 */
	public String name()
	{
		return name;
	}
	
	protected int x = 0;
	/**
	 * 纹理有效像素开始的X位置
	 * @return
	 */
	public int x()
	{
		return x;
	}
	
	protected int y = 0;
	/**
	 * 纹理有效像素开始的Y位置
	 * @return
	 */
	public int y()
	{
		return y;
	}
	
	protected int w = 0;
	/**
	 * 纹理有效像素宽度
	 * @return
	 */
	public int w()
	{
		return w;
	}
	
	protected int h = 0;
	/**
	 * 纹理有效像素高度
	 * @return
	 */
	public int h()
	{
		return h;
	}
	
	protected int offX = 0;
	/**
	 * 纹理有效像素距离原图的X轴偏移
	 * @return
	 */
	public int offX()
	{
		return offX;
	}
	
	protected int offY = 0;
	/**
	 * 纹理有效像素距离原图的Y轴偏移
	 * @return
	 */
	public int offY()
	{
		return offY;
	}
	
	protected int sourceW = 0;
	/**
	 * 纹理原始图片宽度
	 * @return
	 */
	public int sourceW()
	{
		return sourceW;
	}
	
	protected int sourceH = 0;
	/**
	 * 纹理原始图片高度
	 * @return
	 */
	public int sourceH()
	{
		return sourceH;
	}
	
	
	protected TextureData()
	{
		
	}
}
