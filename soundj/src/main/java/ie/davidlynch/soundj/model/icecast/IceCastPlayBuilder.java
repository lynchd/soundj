package ie.davidlynch.soundj.model.icecast;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import ie.davidlynch.soundj.model.Play;

/**
 * GET /admin/metadata?mode=updinfo&mount=/mount&charset=UTF%2d8&song=Arctic%20Monkeys%20%2d%20Arabella HTTP/1.0
 * 
 * 
 * @author David Lynch
 */
public class IceCastPlayBuilder 
{
	private final String EXPECTED_ENCODING = "UTF-8";
	private final String TITLE_KEY="song";
	
	private String metadata;
	private Map<String, String> parameterMap;
	
	private IceCastPlayBuilder(String metadata) 
	{ 
		this.metadata = metadata;
		extractQueryMap();
	}
	
	public static Play build(String metadata) 
	{
		IceCastPlayBuilder builder = new IceCastPlayBuilder(metadata);		
		return builder.build();
	}
	
	private Play build() 
	{
		Play play = new Play();
		try {
			metadata = URLDecoder.decode(metadata, EXPECTED_ENCODING);
			play.setTitle(parameterMap.get(TITLE_KEY));
		}
		catch(Exception ex) {  }
		return play;
	}

	
	public Map<String, String> extractQueryMap()  
	{  

	    String[] params = metadata.split("&");  
	    Map<String, String> map = new HashMap<String, String>();  
	    for (String param : params)  
	    {  
	        String name = param.split("=")[0];  
	        String value = param.split("=")[1];  
	        map.put(name, value);  
	    }  
	    return map;  
	}
}
