
package framework.views;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import framework.consts.EventType;
import framework.events.EventDispatcher;
import framework.geom.Rectangle;
import framework.time.ITickListener;
import framework.time.TickItem;
import framework.time.Ticker;
import framework.util.Keyboard;

/**
 * ������Ļ���
 * 
 * @author Jing
 */
public class Stage extends Canvas implements Runnable
{

	public static Stage current = null;	
	
	/**
	 * ��Ϸÿ֡��Ԥ�ƺ�ʱ
	 */
	private int _frameMS = 16;

	private long _now = 0;

	/**
	 * ��ǰ��ʱ��(����)
	 * 
	 * @return
	 */
	public long now()
	{
		return _now;
	}

	/**
	 * ʱ��������
	 */
	public final Ticker ticker = new Ticker();

	private int _realFPS = 0;

	/**
	 * ʵ����Ϸ����ʵ��֡��
	 */
	public int getFPS()
	{
		return _realFPS;
	}

	/**
	 * �����Ϸ֡����ʱ���
	 */
	private long _checkFPSTime = 0;

	/**
	 * ��һ����ִ�е�֡��
	 */
	private int _frameInSecond = 0;

	/**
	 * ����ɫ
	 */
	private int _bgColor = 0;

	/**
	 * ��̨���
	 */
	private int _stageWidth = 640;

	public int stageWidth()
	{
		return _stageWidth;
	}

	/**
	 * ��̨�߶�
	 */
	private int _stageHeight = 530;

	public int stageHeight()
	{
		return _stageHeight;
	}
	

	private int _viewWidth = 0;	
	
	/**
	 * ��̨���Ӿ����
	 */
	public int viewWidth()
	{
		return _viewWidth;
	}	

	private int _viewHeight = 0;
	
	/**
	 * ��̨���Ӿ��߶�
	 */
	public int viewHeight()
	{
		return _viewHeight;
	}
	
	/**
	 * ������̨���Ӿ����
	 * @param w
	 * @param h
	 */
	public void setViewSize(int w, int h)
	{
		_viewWidth = w;
		_viewHeight = h;
	}
	
	/**
	 * ��Ϸ�Ļ���
	 */
	private Image _canvas = null;
	
	private Rectangle _viewPort = null;
	
	/**
	 * �ӿ�
	 * @return
	 */
	public Rectangle viewPort()
	{
		return _viewPort;
	}

	private Sprite _root = null;

	/**
	 * ����ʾ����(�ĵ���)
	 */
	public Sprite getRoot()
	{
		return _root;
	}

	private Stats _stats = null;
	
	/**
	 * ״̬��
	 * @return
	 */
	public void setStatsOutput(String content)
	{
		if(null != _stats)
		{
			_stats.output(content);
		}
	}

	/**
	 * ��ʾ����״̬
	 * 
	 * @param b
	 */
	public void showStats(boolean b)
	{
		if(true == b)
		{
			if(null == _stats)
			{
				_stats = new Stats();
				_root.addChild(_stats);
			}
		}
		else
		{
			if(null != _stats)
			{
				_root.removeChild(_stats);
				_stats = null;
			}
		}
	}

	/**
	 * �Ƿ��ѳ�ʼ����
	 */
	private boolean _inited = false;

	/**
	 * ����
	 */
	public final Keyboard keyboard = new Keyboard();

	/**
	 * ֪ͨ��
	 */
	public final EventDispatcher notice = new EventDispatcher();

	public Stage()
	{

	}

	public Stage(Class _rootCls)
	{
		init(_rootCls);
	}

	public Stage(Class _rootCls, int fps, int bgColor, int stageW, int stageH)
	{
		setFPS(fps);
		_bgColor = bgColor;
		_viewWidth = _stageWidth = stageW;
		_viewHeight = _stageHeight = stageH;
		_viewPort = new Rectangle(0, 0, stageW, stageH);
		_canvas = Image.createImage(_stageWidth, _stageHeight);
		init(_rootCls);
	}

	public void init(Class _rootCls)
	{
		if(_inited)
		{
			return;
		}
		_inited = true;
		setFullScreenMode(true);
		current = this;
		_now = System.currentTimeMillis();
		// ��ʼ��ʱ������ʱ��
		ticker.tick(_now);
		setRoot(_rootCls);
		new Thread(this).start();
	}

	public void setRoot(Class _rootCls)
	{
		try
		{
			_root = (Sprite)_rootCls.newInstance();
		}
		catch(InstantiationException e)
		{
			e.printStackTrace();
		}
		catch(IllegalAccessException e)
		{
			e.printStackTrace();
		}
		// �ĵ���ĸ��������Լ�,�Դ���Ϊ���
		_root.setParent(_root);
		// �����ĵ����addedToStage
		_root.addedToStage();
	}

	public void setFPS(int fps)
	{
		_frameMS = 1000 / fps;
	}

	
	
	public void run()
	{
		_checkFPSTime = System.currentTimeMillis() + 1000;
		while(true)
		{
			try
			{
				_now = System.currentTimeMillis();
				long startTime = _now;
				if(startTime >= _checkFPSTime)
				{
					_realFPS = _frameInSecond;
					_frameInSecond = 0;
					_checkFPSTime += 1000;
				}

				_frameInSecond++;
				if(_root != null)
				{
					notice.dispatchEvent(EventType.EVENT_STAGE_ENTER_FRAME);
					ticker.tick(_now);
					// ����֡
					_root.enterFrame(startTime);
					// �ػ�
					repaint(); // ��ʵ����������������Ȼ������ػ棬�Ժ������Ҫ�Ż�
					serviceRepaints();
				}

				long usedTime = System.currentTimeMillis() - startTime;

				if(usedTime < _frameMS)
				{
					System.gc();
					usedTime = System.currentTimeMillis() - startTime;
					if(usedTime < _frameMS)
					{
						Thread.sleep(_frameMS - usedTime);
					}
				}
			}
			catch(InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	protected void paint(Graphics g)
	{
		if(_viewWidth == _stageWidth && _viewHeight == _stageHeight)
		{
			//ʹ��ֱ�淽ʽ
			
			// �������
			g.setClip(0, 0, _stageWidth, _stageHeight);
			g.setColor(_bgColor);
			g.fillRect(0, 0, _stageWidth, _stageHeight);
			
			if(null != _root)
			{
				_root.paint(g);
			}
		}
		else
		{
			//ʹ�û��滭��			
			Graphics cg = _canvas.getGraphics();
			// �������
			cg.setClip(0, 0, _stageWidth, _stageHeight);
			cg.setColor(_bgColor);
			cg.fillRect(0, 0, _stageWidth, _stageHeight);
	
			if(null != _root)
			{
				_root.paint(cg);
			}
			
			//long s = System.currentTimeMillis();			
			Image cache = DisplayObject.scale(_canvas, _viewWidth, _viewHeight);
			//long e = System.currentTimeMillis();
			//System.out.println("��ʱ��" + (e - s));
			g.drawImage(cache, 0, 0, 0);
		}
	}

	/**
	 * ���������¼�
	 */
	protected void keyPressed(int keyCode)
	{
		keyboard.pressed(keyCode);
	}

	/**
	 * �����ظ������¼�
	 */
	protected void keyRepeated(int keyCode)
	{

	}

	/**
	 * �����ͷ��¼�
	 */
	protected void keyReleased(int keyCode)
	{
		keyboard.released(keyCode);
	}
}

/**
 * ����״̬
 * 
 * @author Jing
 */
class Stats extends Sprite implements ITickListener
{

	/**
	 * FPS��ʾ�ı�
	 */
	private TextField _tf = null;
	private Quad _quad = null;
	private String _output = "";
	public Stats()
	{
		_tf = createTF();
		Stage.current.ticker.setTimeInterval(1000, this, null);
	}

	private TextField createTF()
	{
		TextField tf = new TextField();
		tf.setBold(true);
		tf.setSize(Font.SIZE_LARGE);
		tf.setColor(0xFFFFFF);
		//tf.setLinegap(-10);
		this.addChild(tf);
		return tf;
	}

	public void onTick(TickItem tickItem)
	{
		((Sprite)this.getParent()).addChild(this);

		Runtime.getRuntime().gc();

		long free = Runtime.getRuntime().freeMemory() >> 10;
		long total = Runtime.getRuntime().totalMemory() >> 10;
		
		String content = "Time:" + (int)(Stage.current.now() / 1000) + "\n"; 
		content += "FPS:" + Stage.current.getFPS() + "\n";
		content += "MEM_USED:" + (total - free) + "kb";	
		content += _output;
		_tf.setText(content);
		
		if(null == _quad)
		{
			_quad = new Quad(0, _tf.getWidth(), _tf.getHeight(), 0.7);
			this.addChildAt(_quad,0);
		}
	}
	
	public void output(String content)
	{
		if (content == null) {
			_output = "null";
		} else {
			_output = "\n" + content;
		}
	}
}
