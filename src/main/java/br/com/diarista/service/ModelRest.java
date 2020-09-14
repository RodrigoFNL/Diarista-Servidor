package br.com.diarista.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/model")
public class ModelRest 
{
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
	public List<Object> getJson()
	{
		List<Object> lista = new ArrayList<Object>();
		return lista;
	}	
}
