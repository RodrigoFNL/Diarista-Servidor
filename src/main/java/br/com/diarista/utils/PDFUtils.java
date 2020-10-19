package br.com.diarista.utils;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.mapping.Subclass;

import br.com.diarista.entity.Usuario;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;


public class PDFUtils 
{

	private static String dba = "org.postgresql.Driver";
	

	private static String url = "jdbc:postgresql://127.0.0.1:5432/diaristaDBA"; 
	

	private static String user = "adm_diarista";
	

	private static String password = "diarista@2020#";

	public static byte[] getPDF(String jasper, Usuario usuario) throws JRException, ClassNotFoundException, SQLException
	{	
		URL arquivo = Subclass.class.getResource("/ireport/" + jasper);		
		
		JasperDesign designer = JRXmlLoader.load(arquivo.getPath());		
		JasperReport relatorio = JasperCompileManager.compileReport(designer);
				
		Class.forName(dba);		
		Connection con = DriverManager.getConnection( url , user , password );
				
		Statement stm = con.createStatement();
		String query = "select * from estado_civil";
		ResultSet rs = stm.executeQuery( query );
		
		JRResultSetDataSource jrRS = new JRResultSetDataSource( rs );
		
		Map<String, Object> parametros = new HashMap<String, Object> ();
		
		parametros.put("nome", usuario.getName());
		parametros.put("CPF", usuario.getCpf());		
		JasperPrint jasperPrint = JasperFillManager.fillReport( relatorio , parametros,    jrRS );		
		
		byte [] pdf = JasperExportManager.exportReportToPdf(jasperPrint);
		return pdf;
	}
}
