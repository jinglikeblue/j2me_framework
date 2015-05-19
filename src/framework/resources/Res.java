
package framework.resources;

import framework.events.EventDispatcher;

/**
 * ��Դ������
 * 
 * @author Jing
 */
public class Res extends EventDispatcher
{
	/**
	 * ���� ͼƬ
	 */
	static public final String TYPE_IMAGE = "image";
	
	/**
	 * ���� JSON
	 */
	static public final String TYPE_JSON = "json";
	
	/**
	 * ���� ����
	 */
	static public final String TYPE_SHEET = "sheet";
	
	/**
	 * ���� ����
	 */
	static public final String TYPE_SOUND = "sound";
	
	/**
	 * ���� ����
	 */
	static public final String TYPE_FONT = "font";
	
	/**
	 * ���� ����
	 */
	static public final String TYPE_TEXTURE = "texture";

	/**
	 * �������õļ������������Ǳ��ؼ�������Ҳ���������������������л�ʹ��
	 */
	static public AResGetter actively = null;
	
	
	/**
	 * ������Դ��ȡ��,ʹ��ǰ����init��ʼ��
	 */
	static public final LocalResGetter localRes = new LocalResGetter();
	
	/**
	 * ������Դ��ȡ����ʹ��ǰ����init��ʼ��
	 */
	static public final HttpResGetter httpRes = new HttpResGetter();
}
