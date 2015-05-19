
package framework.views;

import javax.microedition.lcdui.Graphics;

import framework.consts.EventType;
import framework.geom.Point;
import framework.geom.Rectangle;
import framework.resources.SpriteSheet;
import framework.resources.TextureData;

/**
 * 动画帧类
 * 
 * @author Jing
 */
public class MovieClip extends DisplayObject
{

	private TextureData _ssd = null;

	private SpriteSheet _sheet = null;

	private int _interval = 0;

	private int _playIndex = 0;

	private long _changeTime = 0;

	private boolean _isStop = false;

	private boolean _isLoop = false;
	public boolean isLoop()
	{
		return _isLoop;
	}
	
	/**
	 * 是否循环播放
	 * @param v
	 */
	public void setIsLoop(boolean v)
	{
		if(_isLoop != v)
		{
			_isLoop = v;
		}
	}
	
	private boolean _autoDispose = false;

	public boolean autoDispsoe()
	{
		return _autoDispose;
	}

	/**
	 * 是否在播放完一次后自动销毁，如果设置为true，请确保mc没有被其它对象引用，以免内存泄露
	 * 
	 * @param v
	 */
	public void setAutoDispose(boolean v)
	{
		if(_autoDispose != v)
		{
			_autoDispose = v;
		}
	}

	public MovieClip(SpriteSheet sheet, int interval)
	{
		init(sheet, interval, DisplayObject.ANCHOR_TOP_LEFT);
	}

	public MovieClip(SpriteSheet sheet, int interval, int anchor)
	{
		init(sheet, interval, anchor);
	}

	private void init(SpriteSheet sheet, int interval, int anchor)
	{
		_sheet = sheet;
		_interval = interval;
		setAnchor(anchor);
	}

	public void paint(Graphics g)
	{
		if(null == _ssd)
		{
			return;
		}

		int x = _x + _ssd.offX();
		int y = _y + _ssd.offY();
		if(_anchor == DisplayObject.ANCHOR_CENTER)
		{			
			x -= _ssd.sourceW() >> 1;
			y -= _ssd.sourceH() >> 1;
		}
		Point gp = local2Global(x, y);
		Rectangle drawRect = Rectangle.getIntersectRect(new Rectangle(gp.x(), gp.y(), _ssd.w(), _ssd.h()), Stage.current.viewPort());
		if(null != drawRect)
		{
			int clipX = _ssd.x() + drawRect.x() - gp.x();
			int clipY = _ssd.y() + drawRect.y() - gp.y();
			int destX = (int)(drawRect.x() * DisplayObject.morphX);
			int destY = (int)(drawRect.y() * DisplayObject.morphY);
			g.drawRegion(_sheet.sheet(), clipX, clipY, drawRect.w(), drawRect.h(), _trans, destX, destY, 0);
		}
	}

	public void nextFrame()
	{
		_playIndex++;
		if(_playIndex == _sheet.datas().length)
		{
			this.dispatchEvent(EventType.EVENT_MOVIE_CLIP_RESTART);
			
			if(_autoDispose)
			{
				if(null != this.getParent())
				{
					((Sprite)this.getParent()).removeChild(this);
				}
				return;
			}
			
			if(false == _isLoop)
			{
				_isStop = true;
				return;
			}

			_playIndex = 0;
			
		}

		TextureData ssd = _sheet.datas()[_playIndex];
		setSSD(ssd);
	}

	private void setSSD(TextureData ssd)
	{
		_ssd = ssd;
		_width = ssd.sourceW();
		_height = ssd.sourceH();
	}

	protected void enterFrame(long time)
	{
		if(time >= _changeTime && false == _isStop)
		{
			_changeTime = time + _interval;
			nextFrame();
		}
	}

	/**
	 * 当前播放的帧
	 * 
	 * @return
	 */
	public int currentFrame()
	{
		return _playIndex;
	}

	/**
	 * 当前播放的标签
	 * 
	 * @return
	 */
	public String currentLabel()
	{
		if(_ssd != null)
		{
			return _ssd.name();
		}
		return null;
	}

	/**
	 * 跳转到指定标签并停止播放
	 * 
	 * @param label
	 */
	public void gotoAndStop(String label)
	{
		if(null != _sheet)
		{
			TextureData ssd = _sheet.getSSD(label);
			if(ssd != null)
			{
				setSSD(ssd);
				_isStop = true;
			}
		}
	}

	/**
	 * 跳转到指定帧并停止播放
	 * 
	 * @param frame
	 */
	public void gotoAndStop(int frame)
	{
		if(null != _sheet && frame >= 0 && frame < _sheet.datas().length)
		{
			TextureData ssd = _sheet.datas()[frame];
			setSSD(ssd);
			_isStop = true;
		}
	}

	/**
	 * 跳转到指定标签并停止播放
	 * 
	 * @param label
	 */
	public void gotoAndPlay(String label)
	{
		if(null != _sheet)
		{
			TextureData ssd = _sheet.getSSD(label);
			if(ssd != null)
			{
				setSSD(ssd);
				_isStop = false;
			}
		}
	}

	/**
	 * 跳转到指定帧并停止播放
	 * 
	 * @param frame
	 */
	public void gotoAndPlay(int frame)
	{
		if(null != _sheet && frame >= 0 && frame < _sheet.datas().length)
		{
			TextureData ssd = _sheet.datas()[frame];
			setSSD(ssd);
			_isStop = false;
		}
	}

	public void play()
	{
		_isStop = true;
	}

	public void stop()
	{
		_isStop = false;
	}

	protected void onScaleChange(double scaleX, double scaleY)
	{

	}

	protected void addedToStage()
	{

	}

	protected void removedFromStage()
	{

	}

}
