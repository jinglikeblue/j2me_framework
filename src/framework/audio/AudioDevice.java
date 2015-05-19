package framework.audio;

import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.control.VolumeControl;

import framework.views.Stage;

/**
 * 声音设备
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
	 * 清理所有播放的声音
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
	 * 播放背景音乐
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
	 * 暂停背景音乐
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
	 * 设置背景音量
	 * @param value
	 */
	static public void bgmVolume(int value)
	{
		//System.out.println("设置背景音值: " + value);
		_bgmVolume = value;
		setPlayerVolume(_bgm, value);
	}
	
	/**
	 * 播放音效
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
	 * 暂停音效
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
	 * 设置音效音量
	 * @param value
	 */
	static public void effectVolume(int value)
	{		
		//System.out.println("设置音效值: " + value);
		_effectVolume = value;
		setPlayerVolume(_effect, value);
	}
	
	
	/**
	 * 设置player音量
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
