package framework.audio;

import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.control.VolumeControl;

import framework.views.Stage;

/**
 * �����豸
 * @author Jing
 *
 */
public class AudioDevice
{
	private static Player _bgm = null;
	private static int _bgmVolume = 50;
	public int bgmVolume()
	{
		return _bgmVolume;
	}
	
	private static Player _effect = null;
	private static int _effectVolume = 50;
	public int effectVolume()
	{
		return _effectVolume;
	}
	
	/**
	 * �������в��ŵ�����
	 */
	static public void clearAllAudio()
	{
		pauseBGM();
		pauseEffect();
		//_effect.
		_bgm = null;
		_effect = null;
	}
	
	/**
	 * ���ű�������
	 * @param name
	 */
	static public void playBGM(Player p)
	{		
		pauseBGM();
		
		_bgm = p;
		
		if (_bgm == null) return;

		_bgm.setLoopCount(-1);		
		try
		{
			_bgm.realize();
			_bgm.prefetch();
			setPlayerVolume(_bgm, _bgmVolume);
			_bgm.start();			
		}
		catch(MediaException e)
		{
			e.printStackTrace();
			Stage.current.setStatsOutput(e.getMessage());
		}
	}
	
	
	/**
	 * ��ͣ��������
	 */
	static public void pauseBGM()
	{
		if(null != _bgm)
		{
			try
			{
				_bgm.stop();
				_bgm.deallocate();
				_bgm.close();
			}
			catch(MediaException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * ���ñ�������
	 * @param value
	 */
	static public void bgmVolume(int value)
	{
		//System.out.println("���ñ�����ֵ: " + value);
		_bgmVolume = value;
		setPlayerVolume(_bgm, value);
	}
	
	/**
	 * ������Ч
	 * @param name
	 */
	static public void playEffect(Player p)
	{		
		_effect = p;
		if (_effect == null) return;

		_effect.setLoopCount(1);
		try
		{
			_effect.realize();
			setPlayerVolume(_effect, _effectVolume);
			_effect.start();
		}
		catch(MediaException e)
		{
			e.printStackTrace();
			Stage.current.setStatsOutput(e.getMessage());
		}
	}
	
	/**
	 * ��ͣ��Ч
	 */
	static public void pauseEffect()
	{
		if(null != _effect)
		{
			try
			{
				_effect.stop();
			}
			catch(MediaException e)
			{
				e.printStackTrace();
			}
		}		
	}
	
	/**
	 * ������Ч����
	 * @param value
	 */
	static public void effectVolume(int value)
	{		
		//System.out.println("������Чֵ: " + value);
		_effectVolume = value;
		setPlayerVolume(_effect, value);
	}
	
	
	/**
	 * ����player����
	 * @param p
	 * @param v
	 */
	static private void setPlayerVolume(Player p, int v)
	{
		if(null == p)
		{
			return;
		}
		 
		if(v < 0)
		{
			v = 0;
		}
		else if(v > 100)
		{
			v = 100;
		}
		
		VolumeControl vc = (VolumeControl)p.getControl("VolumeControl");
		vc.setLevel(v);
	}
}
