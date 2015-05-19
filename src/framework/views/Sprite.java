
package framework.views;

import java.util.Enumeration;
import java.util.Vector;

import javax.microedition.lcdui.Graphics;

public class Sprite extends DisplayObject
{

	private Vector _childs = new Vector();

	public Sprite()
	{

	}

	public void paint(Graphics g)
	{
		for(Enumeration enumobj = _childs.elements(); enumobj.hasMoreElements();)
		{
			DisplayObject object = (DisplayObject)enumobj.nextElement();
			if(true == object.getVisible())
			{
				object.paint(g);
			}
		}
	}

	protected void addedToStage()
	{
		for(Enumeration enumobj = _childs.elements(); enumobj.hasMoreElements();)
		{
			DisplayObject object = (DisplayObject)enumobj.nextElement();
			object.addedToStage();
		}
	}

	protected void removedFromStage()
	{
		for(Enumeration enumobj = _childs.elements(); enumobj.hasMoreElements();)
		{
			DisplayObject object = (DisplayObject)enumobj.nextElement();
			object.removedFromStage();
		}
	}

	/**
	 * 添加显示对象到列表
	 * 
	 * @param object
	 * @return
	 */
	public DisplayObject addChild(DisplayObject object)
	{
		if(object.getParent() != null)
		{
			Sprite parent = (Sprite)object.getParent();
			parent.removeChild(object);
		}
		_childs.addElement(object);
		object.setParent(this);
		return object;
	}

	/**
	 * 将显示对象在指定位置插入
	 * 
	 * @param object
	 * @param index
	 * @return
	 */
	public DisplayObject addChildAt(DisplayObject object, int index)
	{
		// 有可能之前在列表中，所以先移除一次
		if(object.getParent() != null)
		{
			Sprite parent = (Sprite)object.getParent();
			parent.removeChild(object);
		}

		_childs.insertElementAt(object, index);
		object.setParent(this);
		return object;
	}

	/**
	 * 从列表移除显示对象
	 * 
	 * @param object
	 * @return
	 */
	public DisplayObject removeChild(DisplayObject object)
	{
		if(true == _childs.removeElement(object))
		{
			object.setParent(null);
		}
		return object;
	}

	/**
	 * 移除指定索引位置的显示对象
	 * 
	 * @param index
	 * @return
	 */
	public DisplayObject removeChildAt(int index)
	{
		if(index >= _childs.size())
		{
			return null;
		}

		DisplayObject object = (DisplayObject)_childs.elementAt(index);
		_childs.removeElementAt(index);
		object.setParent(null);
		return object;
	}

	/**
	 * 获取显示对象的索引值
	 * 
	 * @param object
	 */
	public int getChildIndex(DisplayObject object)
	{
		return _childs.indexOf(object);
	}

	/**
	 * 获取指定索引位置的对象
	 * 
	 * @param index
	 * @return
	 */
	public DisplayObject getChildAt(int index)
	{
		if(index < 0 || index >= _childs.size())
		{
			return null;
		}

		return (DisplayObject)_childs.elementAt(index);
	}

	/**
	 * 通过名称获取对象
	 * 
	 * @param name
	 * @return
	 */
	public DisplayObject getChildByName(String name)
	{
		if(null != name)
		{
			for(Enumeration enumobj = _childs.elements(); enumobj.hasMoreElements();)
			{
				DisplayObject object = (DisplayObject)enumobj.nextElement();
				if(null != object.getName() && object.getName().equals(name))
				{
					return object;
				}
			}
		}
		return null;
	}

	/**
	 * 获取子对象数量
	 * 
	 * @return
	 */
	public int numChildren()
	{
		return _childs.size();
	}

	/**
	 * 移除所有的子对象
	 */
	public void removeAllChildren()
	{
		_childs.setSize(0);
	}

	/**
	 * 游戏进入新一帧
	 */
	protected void enterFrame(long time)
	{
		for(Enumeration enumobj = _childs.elements(); enumobj.hasMoreElements();)
		{
			DisplayObject object = (DisplayObject)enumobj.nextElement();
			object.enterFrame(time);
		}
	}

	protected void onScaleChange(double scaleX, double scaleY)
	{

	}
}
