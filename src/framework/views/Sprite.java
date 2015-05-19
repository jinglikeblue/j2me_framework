
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
	 * �����ʾ�����б�
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
	 * ����ʾ������ָ��λ�ò���
	 * 
	 * @param object
	 * @param index
	 * @return
	 */
	public DisplayObject addChildAt(DisplayObject object, int index)
	{
		// �п���֮ǰ���б��У��������Ƴ�һ��
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
	 * ���б��Ƴ���ʾ����
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
	 * �Ƴ�ָ������λ�õ���ʾ����
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
	 * ��ȡ��ʾ���������ֵ
	 * 
	 * @param object
	 */
	public int getChildIndex(DisplayObject object)
	{
		return _childs.indexOf(object);
	}

	/**
	 * ��ȡָ������λ�õĶ���
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
	 * ͨ�����ƻ�ȡ����
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
	 * ��ȡ�Ӷ�������
	 * 
	 * @return
	 */
	public int numChildren()
	{
		return _childs.size();
	}

	/**
	 * �Ƴ����е��Ӷ���
	 */
	public void removeAllChildren()
	{
		_childs.setSize(0);
	}

	/**
	 * ��Ϸ������һ֡
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
