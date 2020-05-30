package util;

import java.text.Normalizer;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import javax.swing.text.MaskFormatter;

import org.apache.commons.lang3.StringUtils;

public class StringUtil 
{
   public static final int CPF = 0;
    
	public static String formatarComMascara(String mascara, String str) {
		MaskFormatter mask;
		try {
			mask = new MaskFormatter(mascara);
			mask.setValueContainsLiteralCharacters(false);
			return mask.valueToString(str);
		} catch (ParseException e) {
			System.out.println("Erro: StringUtil.formatarComMascara() " + e.getMessage());
			return str != null ? str : "";
		}
	}

	public static boolean isEmpty(String str) {
		boolean empty = true;
		if (str != null && !str.replaceAll(" ", "").equals("")) {
			empty = false;
		}
		return empty;
	}

	public static boolean isNotEmpty(String str) {
		boolean empty = false;
		if (str != null && !str.replaceAll(" ", "").equals("")) {
			empty = true;
		}
		return empty;
	}

	public static String clearLowerCase(String str) {
		str = Normalizer.normalize(str, Normalizer.Form.NFD);
		str = str.replaceAll("[^\\p{ASCII}]", "");
		str = str.replace(" ", "").replace(".", "").replace("|", "").replace("\\", "").replace("/", "").replace(",", "")
				.replace("?", "").replace(":", "").replace(";", "").replace("<", "").replace(">", "").replace("[", "")
				.replace("]", "").replace("{", "").replace("}", "").replace("/", "").replace("'", "").replace("\"", "")
				.replace("!", "").replace("@", "").replace("#", "").replace("$", "").replace("%", "").replace("&", "")
				.replace("*", "").replace("(", "").replace(")", "").replace("-", "").replace("+", "").replace("=", "");
		str = str.toLowerCase();
		return str;
	}

	public static String clearLowerCaseNotTrim(String str) {
		str = Normalizer.normalize(str, Normalizer.Form.NFD);
		str = str.replaceAll("[^\\p{ASCII}]", "");
		str = str.replace(".", "").replace("|", "").replace("\\", "").replace("/", "").replace(",", "").replace("?", "")
				.replace(":", "").replace(";", "").replace("<", "").replace(">", "").replace("[", "").replace("]", "")
				.replace("{", "").replace("}", "").replace("/", "").replace("'", "").replace("\"", "").replace("!", "")
				.replace("@", "").replace("#", "").replace("$", "").replace("%", "").replace("&", "").replace("*", "")
				.replace("(", "").replace(")", "").replace("-", "").replace("+", "").replace("=", "");
		str = str.toLowerCase();
		return str;
	}

	public static String clear(String str) {
		str = Normalizer.normalize(str, Normalizer.Form.NFD);
		str = str.replaceAll("[^\\p{ASCII}]", "");
		return str;
	}

	public static String clearCep(String str) {
		return str.replace("-", "").replace(" ", "");
	}

	public static String getOnlyNumber(String str) {
		return str.replaceAll("[^0-9]", "");
	}

	public static String removeSpace(String str) {
		return str.replace(" ", "");
	}

	public static String completeToLeft(String str, String c, Integer number) {
		while (str.length() < number) {
			str = c + str;
		}
		return str;
	}

	public static boolean onlyNumbersAndLetters(String str) {
		Pattern p = Pattern.compile("^[A-Za-z0-9]*\\d+[A-Za-z0-9]*$");
		return p.matcher(str).matches();
	}

	public static boolean statusToBool(String status) {
		return !"Inativo".equalsIgnoreCase(status);
	}

	public static String boolToStatus(Boolean bStatus) {
		return bStatus != null ? bStatus ? "Ativo" : "Inativo" : "Inativo";
	}

	public static boolean validaCpf(String str)
	{
		if (str == null) return false;	

		String cpf = str.replace(".", "").replace("-", "");

		if (cpf.trim().equals("")) return false;
		

		if (cpf.equals("00000000000") || cpf.equals("11111111111") || cpf.equals("22222222222")
				|| cpf.equals("33333333333") || cpf.equals("44444444444") || cpf.equals("55555555555")
				|| cpf.equals("66666666666") || cpf.equals("77777777777") || cpf.equals("88888888888")
				|| cpf.equals("99999999999") || (cpf.length() != 11)) {
			return false;
		}

		char dig10, dig11;
		int sm, i, r, num, peso;

		try {
			sm = 0;
			peso = 10;
			for (i = 0; i < 9; i++) {
				num = (int) (cpf.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}
			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11)) {
				dig10 = '0';
			} else {
				dig10 = (char) (r + 48);
			}
			sm = 0;
			peso = 11;
			for (i = 0; i < 10; i++) {
				num = (int) (cpf.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}
			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11)) {
				dig11 = '0';
			} else {
				dig11 = (char) (r + 48);
			}

			if ((dig10 != cpf.charAt(9)) || (dig11 != cpf.charAt(10))) {
				return false;
			}

		} 
		catch (Exception erro) 
		{
			return false;
		}

		return true;
	}

	public static boolean validaCns(String s) {
		if (s.matches("[1-2]\\d{10}00[0-1]\\d") || s.matches("[7-9]\\d{14}")) {
			return somaPonderada(s) % 11 == 0;
		}
		return false;
	}

	private static int somaPonderada(String s) {
		char[] cs = s.toCharArray();
		int soma = 0;
		for (int i = 0; i < cs.length; i++) {
			soma += Character.digit(cs[i], 10) * (15 - i);
		}
		return soma;
	}

	public static String clearLocalizacao(String str) {
		str = clearLowerCaseNotTrim(str);
		str = " " + str + " ";
		str = str.replace(" alameda ", " ").replace(" alm ", " ").replace(" avenida ", " ").replace(" avn ", " ")
				.replace(" beco ", " ").replace(" bec ", " ").replace(" boulevard ", " ").replace(" blv ", " ")
				.replace(" caminho ", " ").replace(" cam ", " ").replace(" cais ", " ").replace(" cas ", " ")
				.replace(" campo ", " ").replace(" cmp ", " ").replace(" escada ", " ").replace(" esc ", " ")
				.replace(" estrada ", " ").replace(" etr ", " ").replace(" favela ", " ").replace(" fav ", " ")
				.replace(" fazenda ", " ").replace(" faz ", " ").replace(" floresta ", " ").replace(" flt ", " ")
				.replace(" ilha ", " ").replace(" ilh ", " ").replace(" jardim ", " ").replace(" jrd ", " ")
				.replace(" ladeira ", " ").replace(" lad ", " ").replace(" largo ", " ").replace(" lrg ", " ")
				.replace(" loteamento ", " ").replace(" ltm ", " ").replace(" lugar ", " ").replace(" lug ", " ")
				.replace(" morro ", " ").replace(" mrr ", " ").replace(" parque ", " ").replace(" pqe ", " ")
				.replace(" passeio ", " ").replace(" pas ", " ").replace(" praia ", " ").replace(" pra ", " ")
				.replace(" praca ", " ").replace(" prc ", " ").replace(" recanto ", " ").replace(" rec ", " ")
				.replace(" rodovia ", " ").replace(" rod ", " ").replace(" rua ", " ").replace(" rua ", " ")
				.replace(" servidao", " ").replace(" srv ", " ").replace(" travessa ", " ").replace(" trv ", " ")
				.replace(" via ", " ").replace(" via ", " ").replace(" vila ", " ").replace(" vil ", " ");
		return str.trim();
	}

	public static boolean validaCnpj(String cnpj) {
		if (!cnpj.substring(0, 1).equals("")) {
			try {
				cnpj = cnpj.replace('.', ' ');// onde há ponto coloca espaço
				cnpj = cnpj.replace('/', ' ');// onde há barra coloca espaço
				cnpj = cnpj.replace('-', ' ');// onde há traço coloca espaço
				cnpj = cnpj.replaceAll(" ", "");// retira espaço
				int soma = 0, dig;
				String cnpj_calc = cnpj.substring(0, 12);
				if (cnpj.length() != 14) {
					return false;
				}
				char[] chr_cnpj = cnpj.toCharArray();
				/* Primeira parte */
				for (int i = 0; i < 4; i++) {
					if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
						soma += (chr_cnpj[i] - 48) * (6 - (i + 1));
					}
				}
				for (int i = 0; i < 8; i++) {
					if (chr_cnpj[i + 4] - 48 >= 0 && chr_cnpj[i + 4] - 48 <= 9) {
						soma += (chr_cnpj[i + 4] - 48) * (10 - (i + 1));
					}
				}
				dig = 11 - (soma % 11);
				cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(dig);
				/* Segunda parte */
				soma = 0;
				for (int i = 0; i < 5; i++) {
					if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
						soma += (chr_cnpj[i] - 48) * (7 - (i + 1));
					}
				}
				for (int i = 0; i < 8; i++) {
					if (chr_cnpj[i + 5] - 48 >= 0 && chr_cnpj[i + 5] - 48 <= 9) {
						soma += (chr_cnpj[i + 5] - 48) * (10 - (i + 1));
					}
				}
				dig = 11 - (soma % 11);
				cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(dig);
				return cnpj.equals(cnpj_calc);
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Formata uma string de busca para ser pesquisada alterando todos os espaços
	 * (multiplos ou não) em % e depois efetua o transforma ela em uppercase.
	 * 
	 * @param str - Valor para ser convertido
	 * @return uma string formatada em padrão SQL para ser usada com o LIKE em uma
	 *         coluna com UPPER()
	 */
	public static String toBuscaSQL(String str) {
		StringBuilder sb = new StringBuilder();
		sb.append(" ").append(str).append(" ");
		return sb.toString().replaceAll("( )\\1+| ", "%").toUpperCase();
	}

	public static Boolean toBoolean(Object obj) throws Exception {
		if (obj != null && isEmpty(obj.toString())) {
			throw new Exception("Campo ");
		}
		Boolean b = Boolean.valueOf(toString(obj));
		return b;
	}

	public static String toString(Object obj) {
		if (obj != null && !isEmpty(obj.toString())) 
		{	   
		   return String.valueOf(obj);
		}
		return null;
	}

   public static Long toLong(Object obj) throws Exception {
		return toLong(toString(obj));
	}

	public static Long toLong(String number) throws Exception {
		if (!StringUtils.isNumeric(number)) {
			throw new Exception("Número não identificado.");
		}
		Long l = Long.parseLong(number);
		return l;
	}

	public static Integer toInteger(Object obj) throws Exception {
		return toInteger(toString(obj));
	}

	public static Integer toInteger(String number) throws Exception {
		if (!StringUtils.isNumeric(number)) {
			throw new Exception("Sem número identificador.");
		}
		Integer i = Integer.parseInt(number);
		return i;
	}

	public static Date localDateToDate(LocalDate localDate) 
	{		
		Calendar calendar = Calendar.getInstance();
		calendar.set(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth());
		return calendar.getTime();
	}
	
	public static double retirarMascaraDinheiro(String dinheiro){
		String newString = null;
		newString = dinheiro.toString().replace("R$", "");
		newString = newString.toString().replace(",00", "");
		newString = newString.toString().replace(" ", "");
		newString = newString.toString().replace(".", "");
		newString = newString.toString().replace(",", ".");
			
		double resultado = Double.parseDouble(newString);
		return resultado;
	}

   public static String removeAcentos(String text)
   {
	  if(StringUtil.isEmpty(text)) return "";	  
	  text = Normalizer.normalize(text, Normalizer.Form.NFD);
	  text = text.replaceAll("[^\\p{ASCII}]", "");	   
	  return text.toUpperCase();
   }

   public static String removeCharacterSpeciais(String string)
   {	  
	  return string.replace(",", "").replace(".", "").replace("-", "").replace("_", "").replace(":", "").replace(";", "");
   }

   public static String docFormat(String value, int stringUtil_opcao)
   {	  
	 
	  if(isEmpty(value)) return "";
	 
	 value = value.trim();
	 StringBuilder retorno = new StringBuilder(); 	 
	 
	 
	 if(stringUtil_opcao == CPF && value.length() == 11) return retorno.append(value.substring(0, 3)).append(".").append(value.substring(3, 6)).append(".").append(value.substring(6, 9)).append("-").append(value.substring(9, 11)).toString();
	 
	 return value;
	 
   }

   public static String insertZero(Long number, int totalZero)
   {
	 if(totalZero < 1) return number.toString();
	 
	 Long insertZero = totalZero - (number / 10);
	 int y = Integer.valueOf(insertZero.toString());
	 
	 StringBuilder response = new StringBuilder();
	 
	 if(y > 0)
	 {
		for(int x = 1; x < y; x++ )
		{
		   response.append("0");
		}		
	 }	  
	  response.append(number.toString());
	  return response.toString();
   }

   public static String insertZero(Short number, int totalZero)
   {
	  if(totalZero < 1) return number.toString();
		 
		 int y = totalZero - (number / 10);		 
		 StringBuilder response = new StringBuilder();
		 
		 if(y > 0)
		 {
			for(int x = 1; x < y; x++ )
			{
			   response.append("0");
			}		
		 }	  
		  response.append(number.toString());
		  return response.toString();
   }

}
