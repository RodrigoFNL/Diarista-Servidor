package br.com.diarista.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.mapping.Subclass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.diarista.folks.entity.Usuario;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

@Service
public class PDFUtils 
{	
	@Autowired
	private DataSource dataSource;		

	public  byte[] getPDF(String jasper, Usuario usuario) throws JRException, ClassNotFoundException, SQLException, IOException
	{		
		File signature = File.createTempFile("Contrato", "png");

		FileOutputStream str = new FileOutputStream(signature);		
		str.write(usuario.getSignature());
		str.flush();
		str.close();
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("nome", usuario.getName());
		parametros.put("CPF", StringUtils.formatCPF(usuario.getCpf()));			
		parametros.put("image", signature);	
			
		InputStream jasperStream = Subclass.class.getResourceAsStream("/ireport/" + jasper);		
		byte [] pdf = JasperRunManager.runReportToPdf(jasperStream, parametros, dataSource.getConnection());			
		signature.delete();
		return pdf;
	}
}


















