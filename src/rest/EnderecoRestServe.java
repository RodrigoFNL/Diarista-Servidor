package rest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import business.BasicBusiness;
import business.EnderecoBusiness;
import dto.LocalidadeDTO;
import model.Endereco;

@RequestScoped
@Path("adress")
public class EnderecoRestServe  extends BasicRestServe<Endereco>
{
	private EnderecoBusiness endBusiness;
	
	@Override
	protected BasicBusiness<Endereco> business()
	{	
		return endBusiness;
	}

	
	@POST
	@Path("locality")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public LocalidadeDTO getLocality(Map<String, Object> postObject)
	{
		try
		{	
			String cep = (String) postObject.get("cep") != null ? (String) postObject.get("cep") : null;	
			cep = cep != null? cep.trim(): null;
			if(cep == null) return null;

			URL url = new URL("http://viacep.com.br/ws/" + cep + "/json/");	
			url.openConnection();
			InputStream is = url.openStream();

			InputStreamReader isr = new InputStreamReader(is, "utf-8");
			BufferedReader br = new BufferedReader(isr);
			String linha = br.readLine();		

			LocalidadeDTO dto = new LocalidadeDTO();

			while (linha != null) 
			{		
				linha = linha.replace("\"", "").replace(",", "");						

				if(linha.contains("bairro")) 		dto.setBairro(linha.replace("bairro:", "").trim());
				if(linha.contains("cep")) 			dto.setCep(linha.replace("cep:", "").trim());
				if(linha.contains("complemento")) 	dto.setComplemento(linha.replace("complemento:", "").trim());
				if(linha.contains("ibge")) 			dto.setIbge(linha.replace("ibge:", "").trim());
				if(linha.contains("localidade")) 	dto.setLocalidade(linha.replace("localidade:", "").trim());
				if(linha.contains("logradouro")) 	dto.setLogradouro(linha.replace("logradouro:", "").trim());
				if(linha.contains("uf")) 			dto.setUf(linha.replace("uf:", "").trim());

				linha = br.readLine();				
			}
			
			br.close();
			isr.close();
			is.close();
			return dto;				
		}
		catch (Exception e)
		{	
			e.printStackTrace();
			return null;
		}		
		
	}
	
}
