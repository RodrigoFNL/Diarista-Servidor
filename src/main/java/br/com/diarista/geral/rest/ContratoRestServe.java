package br.com.diarista.geral.rest;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.diarista.conf.ConstantsSecurity;
import br.com.diarista.folks.business.BasicBusiness;
import br.com.diarista.folks.business.UsuarioBusiness;
import br.com.diarista.folks.entity.Usuario;
import br.com.diarista.folks.rest.BasicRestServe;
import br.com.diarista.geral.business.ContratoBusiness;
import br.com.diarista.geral.entity.Contrato;
import br.com.diarista.utils.PDFUtils;
import br.com.diarista.utils.StringUtils;

@RestController
@RequestMapping("/rest/contract")
public class ContratoRestServe extends BasicRestServe<Contrato>
{
	@Autowired
	private ContratoBusiness business;
	
	@Autowired
	private UsuarioBusiness userBusiness;
	
	@Autowired
	PDFUtils utils;

	@Override
	protected BasicBusiness<Contrato> business()
	{	
		return business;
	}
	
	
	@PostMapping("/download_contrato")
	public void download(@RequestBody Map<String, Object> postObjec,  HttpServletResponse response,  HttpServletRequest request)
	{		
		try
		{			
			String token = request.getHeader(ConstantsSecurity.HEADER);			
			
			if(token == null) return;			
			Usuario user = userBusiness.findUserByToken(token);	
			if(user == null) return;		
			
		    String cpf =  postObjec.containsKey("cpf") ? (String) postObjec.get("cpf") : null;		    
			if(cpf == null) return;		
					
			cpf = StringUtils.removeCharacters(cpf);			
			if(!user.getCpf().equals(cpf)) return;			
			
			byte [] pdf = utils.getPDF("Contrato.jasper", user);		
			if(pdf == null) return;
			
			String fileName = "";
			FileCopyUtils.copy(pdf, response.getOutputStream());		
			fileName = "Contrato";

			response.setContentType("application/pdf");
			response.flushBuffer();
			response.addHeader("Content-Disposition", "attachment; filename=" + fileName + ".pdf" );
			response.getOutputStream().flush();		
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();			
		}
	}
}
