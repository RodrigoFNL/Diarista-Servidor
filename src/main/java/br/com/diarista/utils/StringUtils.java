package br.com.diarista.utils;


import java.util.Random;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class StringUtils 
{

	public static Boolean notEnpty(String value)
	{
		if(value == null) return false;
		if(value.trim().equals("")) return false;
		else return true;
	}

	//gera um número de cupom aleátorio
	public static String generateCoupon(Long time)
	{
		
		StringBuilder retorno = new StringBuilder();		
		Random rand = new Random();		
		retorno.append(rand.nextInt(9));
		retorno.append(IntString.valueOff(rand.nextInt(25)));
		retorno.append(rand.nextInt(9));
		retorno.append(IntString.valueOff(rand.nextInt(25)));
		retorno.append(rand.nextInt(9));
		retorno.append(IntString.valueOff(rand.nextInt(25)));
		retorno.append(rand.nextInt(9));
		retorno.append(IntString.valueOff(rand.nextInt(25)));		
		return retorno.toString();
	}

	//criptografa uma string
	public static String encrypt(String value)
	{
		try
		{
			BCryptPasswordEncoder pass = new BCryptPasswordEncoder();
			return pass.encode(value);		
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	
	public enum IntString
	{
		A(0, "A"),
		B(1, "B"),
		C(2, "C"),
		E(3, "E"),
		F(4, "F"),
		G(5, "G"),
		H(6, "H"),
		I(7, "I"),
		J(8, "J"),
		L(9, "L"),
		K(10, "K"),
		M(11, "M"),
		N(12, "N"),
		O(13, "O"),
		P(14, "P"),
		Q(15, "Q"),
		R(16, "R"),
		S(17, "S"),
		T(18, "T"),
		U(19, "U"),
		V(20, "V"),
		W(21, "W"),
		X(22, "X"),
		Y(23, "Y"),
		Z(24, "Z");	
		
		private final int key;
		private final String value; 
		
		IntString(int key, String value)
		{
			this.key = key;
			this.value = value;
		}
		
		public static String valueOff(int key)
		{
			for (IntString item : IntString.values())
			{
				if(item.key == key) return item.value;		
			}
			return null;
		}
	}

	public static String removeCharacters(String value)
	{
		if(value == null) return null;
		value = value.replace(".", "").replace(":", "").replace("-", "").replace("/", "").replace("\\", "").replace(";", "").replace(":", "").replace(" ", "").replace(")", "").replace("(", "").replace("_", "");
		return value.trim();
	}

	public static Boolean isNotNull(String value) 
	{
		if(value == null) return false;
		value = value.replace("null", "").replace("undefined", "");	
		return !value.trim().isEmpty()? true: false;
	}

	public static boolean isNull(String value) 
	{
		return !isNotNull(value);
	}

	public static String formatCPF(String cpf) 
	{
		if(cpf == null)   return null;
		cpf = cpf.trim();		
		if(cpf.isEmpty()) return null;
		if(cpf.length() != 11) return null;
		
		String block01 = cpf.substring(0, 3);
		String block02 = cpf.substring(3, 6);
		String block03 = cpf.substring(6, 9);	
		String block04 = cpf.substring(9);
		
		StringBuilder cpfFormat = new StringBuilder().append(block01).append(".").append(block02).append(".").append(block03).append("-").append(block04);
		return cpfFormat.toString();
	}

	public static boolean validPassword(String oldPassword, String password) 
	{		
		try
		{
			BCryptPasswordEncoder pass = new BCryptPasswordEncoder();
			return pass.matches(oldPassword, password);
		}
		catch (Exception e)
		{
			return false;
		}
	}
}













