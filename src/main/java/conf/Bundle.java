package conf;

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

public class Bundle implements Serializable {

	private static final long serialVersionUID = -1868736489882406567L;
	private static Locale currentLocale;

    public static void englishLocale() {
        currentLocale = Locale.US;
    }

    public static void portugueseLocale() {
        currentLocale = new Locale("pt", "BR");
    }

    public static void spanishLocale() {
        currentLocale = new Locale("es", "ES");
    }

    public static Locale getCurrentLocale() {
        if (currentLocale == null) {
            setLocale();
        }
        return currentLocale;
    }

    private static void setLocale() {
        portugueseLocale(); 
    }

    public static String  getBundle(String key) {
        if (currentLocale == null) {
            setLocale();
        }
        ResourceBundle labels = ResourceBundle.getBundle("br.gov.sc.saude.sgs.messages.messages", currentLocale);
        return labels.getString(key);
    }
    
    public static String  getBundle(String key, Object... params) {
        if (currentLocale == null) {
            setLocale();
        }
        ResourceBundle labels = ResourceBundle.getBundle("br.gov.sc.saude.sgs.messages.messages", currentLocale);
        String result = labels.getString(key);
        int count = 0;
    	for (Object param : params) {
    		result = result.replace("{" + count + "}", getBundle((String)param));
    		++count;
    	}
        return result;
    }
    
    public static String  getBundle(String key, String[] values) {
        if (currentLocale == null) {
            setLocale();
        }
        ResourceBundle labels = ResourceBundle.getBundle("br.gov.sc.saude.sgs.messages.messages", currentLocale);
        String result = labels.getString(key);
        int count = 0;
    	for (String value : values) {
    		result = result.replace("{" + count + "}", value);
    		++count;
    	}
        return result;
    }
    
}


