package framework.io;

import javax.microedition.rms.RecordStore;

public class LocalRecordIO {
	
	public LocalRecordIO()
	{
		
	}
	
	public static void writeRecord(String strName, String[] strData)
	{  
		if(strData != null)
		{
			for (int i = 0; i < strData.length; i++)
	        {
				writeRecord(strName, strData[i], i+1);
	        }
		}
	}
	
	public static boolean writeRecord(String strName, String strData, int intID)
	{  
	    //----------------------------  
	    //--- RecordStore的存储  
	    //----------------------------  
	    RecordStore rsWrite = null;
	    try
	    {
	        rsWrite = RecordStore.openRecordStore(strName, true);
	        byte[] bytWrite = strData.getBytes();
	        int index = rsWrite.getNumRecords();
	        if (index >= intID)
	        {
	            //--- RecordStore不存在，则生成之  
	        	rsWrite.setRecord(intID, bytWrite, 0, bytWrite.length);
	        }else
	        {
	            //--- RecordStore更新 
	        	rsWrite.addRecord(bytWrite, 0, bytWrite.length);
	        }
	        rsWrite.closeRecordStore();
	        return true;
	    }
	    catch(Exception e)
	    {
	        if (rsWrite != null)
	        {
	            try
	            {
	                rsWrite.closeRecordStore();
	            }
	            catch(Exception e2){}
	        }
	        return false;
	    }
	}
	
	public static String[] readRecord(String strName)
	{
		RecordStore rsRead = null;
		String[] str = null;
	    try
	    {
	    	rsRead = RecordStore.openRecordStore(strName, true);
	        int num = rsRead.getNumRecords();
	        str = new String[num];
			for(int i = 0; i < num; i++)
			{
				str[i] = readRecord(strName, i+1);
			}
			return str;
	    }
	    catch(Exception e)
	    {
	        if (rsRead != null)
	        {
	            try
	            {
	            	rsRead.closeRecordStore();
	            }
	            catch(Exception e2){}
	        }
	        return str;
	    }
	}
	
	public static String readRecord(String strName, int intID)
	{
	    //----------------------------
	    //--- RecordStore的读取 
	    //----------------------------
	    RecordStore rsRead = null;
	    try
	    {
	        rsRead = RecordStore.openRecordStore(strName, false);
	        byte[] bytRead = rsRead.getRecord(intID);
	        rsRead.closeRecordStore();
	        return new String(bytRead);
	    }
	    catch(Exception e)
	    {
	        if (rsRead != null)
	        {
	            try
	            {
	                rsRead.closeRecordStore();
	            }
	            catch(Exception e2){}
	        }
	        return null;
	    }
	}
}
