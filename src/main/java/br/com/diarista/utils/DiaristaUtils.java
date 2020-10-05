package br.com.diarista.utils;

public class DiaristaUtils 
{

	public static Boolean validCPF(String cpf)
	{
		if(cpf == null) return false;
		if(cpf.isEmpty()) return false;
		
        cpf = StringUtils.removeCharacters(cpf);

        if(cpf.length() != 11)      return false;
        if(cpf == "00000000000")    return false;
        if(cpf == "11111111111")    return false;
        if(cpf == "22222222222")    return false;
        if(cpf == "33333333333")    return false;
        if(cpf == "44444444444")    return false;
        if(cpf == "55555555555")    return false;
        if(cpf == "66666666666")    return false;
        if(cpf == "77777777777")    return false;
        if(cpf == "88888888888")    return false;
        if(cpf == "99999999999")    return false;
        
        String numeros = cpf.substring(0,9);
        String digitos = cpf.substring(9);
        Integer soma = 0;

        for (int i = 10; i > 1; i--) 
        {
            soma += Integer.valueOf(numeros.charAt(10 - i)) * i;
        } 
        
        int  resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;

        if (resultado != Integer.valueOf(digitos.charAt(0))) return false;
            
        numeros = cpf.substring(0,10);
        soma = 0;
        for (int i = 11; i > 1; i--)
        {
            soma += Integer.valueOf(numeros.charAt(11 - i)) * i;            
        }
        resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;   
        if (resultado != Integer.valueOf(digitos.charAt(1))) return false;              
   
        return true;	
	}

	public static boolean validTelefone(String numberPhone) 
	{
		if(StringUtils.isNull(numberPhone)) return false;		
		StringUtils.removeCharacters(numberPhone);		
		if(numberPhone.length() < 10) return false;
		return true;
	}

	public static String genarateLogin(String login)
	{
		if(login == null) return null;
		return null;
	}
}











