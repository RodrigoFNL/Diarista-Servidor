package br.com.diarista.service;

import java.io.InputStream;
import java.net.URL;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import br.com.diarista.business.BasicBusiness;
import br.com.diarista.business.EnderecoBusiness;
import br.com.diarista.dto.LocalidadeDTO;
import br.com.diarista.entity.Endereco;
import br.com.diarista.entity.Localidade;

@RestController
@RequestMapping("/rest/adress")
public class EnderecoRestServe  extends BasicRestServe<Endereco>
{
	@Autowired
	private EnderecoBusiness endBusiness;
	
	@Override
	protected BasicBusiness<Endereco> business()
	{	
		return endBusiness;
	}

	@RequestMapping("/locality")
	public LocalidadeDTO getLocality(@RequestBody Map<String, Object> postObject)
	{
		try
		{	
			String cep = (String) postObject.get("cep") != null ? (String) postObject.get("cep") : null;	
			cep = cep != null? cep.trim(): null;
			if(cep == null) return null;
			
			Localidade localidade = endBusiness.getLocalityfindByCep(cep);			
			if(localidade != null) return  localidade.getDTO();
								
			URL url = new URL("http://viacep.com.br/ws/" + cep + "/json/");	
			url.openConnection();
				
			InputStream is = url.openStream();				
			Gson gson = new Gson();
			
			String response = new String(is.readAllBytes());	
			LocalidadeDTO dto = gson.fromJson(response, LocalidadeDTO.class);
			
			return dto != null && dto.getCep() != null? dto : null ;
		}
		catch (Exception e)
		{	
			e.printStackTrace();
			return null;
		}		
		
	}
	
}
