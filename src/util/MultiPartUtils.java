package util;

import java.util.HashMap;
import java.util.Map;

public class MultiPartUtils 
{

	public static Map<String, Object> multiPart(String object)
	{		
		try
		{
			if(object == null) return null;
			if(object.length() < 1) return null;		
			String [] objects = object.replace("\r", "").replace("\"", "").split("\n");

			Map<String, Object> map = new HashMap<String, Object>();

			if(objects.length > 1)
			{	
				int indexValue = 0;
				for (String item : objects) 
				{
					indexValue++;
					
					if(item.contains("filename"))				
					{					
						String key = item.replace("Content-Disposition: form-data; name=", "").replace("; filename=blob", "");
						map.put(key.trim(), objects[indexValue + 2].trim());
					}	
					
					else if(item.contains("name"))				
					{					
						String key = item.replace("Content-Disposition: form-data; name=", "");
						map.put(key.trim(), objects[indexValue + 1].trim());
					}				
				}
			}	
			
			return map;
		}
		catch (Exception e) 
		{			
			e.printStackTrace();
			return null;
		}
	}
}
