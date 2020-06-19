package util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Random;

public class StringUtils 
{

	public static Boolean notEnpty(String value)
	{
		if(value == null) return false;
		if(value.trim().equals("")) return false;
		else return true;
	}

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

	public static String encrypt(String value)
	{
		try
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			BigInteger hash = new BigInteger(1, md.digest(value.getBytes()));
			return hash.toString(15);
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
		value = value.replace(".", "").replace(":", "").replace("-", "").replace("/", "").replace("\\", "").replace(";.", "").replace(":", "");
		return value.trim();
	}
}
