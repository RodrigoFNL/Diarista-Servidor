package br.com.diarista.geral.rest;

import java.io.InputStream;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

@RestController
@RequestMapping("/rest/model")
public class ModelRest 
{
	private int x = 0;
	
	@RequestMapping("/html")
	public String getHTML()
	{
		return "<h1>TEXTO HTML</h1>";
	}
	
	@RequestMapping("/text")
	public String getText()
	{
		return "TEXTO";
	}	
	
	@RequestMapping("/json")
	public String getJson()
	{
		try
		{	
			Timer timer = new Timer(); 
			timer.scheduleAtFixedRate(new TimerTask() 
			{
				@Override 
				public void run()
				{ 
					try
					{		
						x++;
						URL url = new URL("https://www.casadoscontos.com.br/texto/202103899");	
						url.openConnection();				
						InputStream is = url.openStream();		
						String response = new String(is.readAllBytes(), "UTF-8");	
						
						System.out.println("=================================================");
						System.out.println(x);
						System.out.println("=================================================");
						
						System.out.println(response);
					}
					catch (Exception e)
					{			
						System.out.println("error");
					}					
				}		    
			}, 1000, 1000);			
		
			return "ok";
		}
		catch (Exception e)
		{	
			Gson gson = new Gson();			
			return gson.toJson(e);	
		}	
	}	
}
