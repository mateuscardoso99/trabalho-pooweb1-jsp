package utils;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Pattern;

public class Validator {
    public static boolean isEmptyOrNull(String input){
        return input == null || input.equals("");
    }
    public static boolean isEmail(String email){
        String regexPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return Pattern.compile(regexPattern).matcher(email).matches();
    }
    public static boolean isValidPassword(String password){
        return password.length() >= 4;
    }
    public static boolean isNumber(String numero){
        try{
            Long.parseLong(numero);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }
    public static boolean isValidURL(String url){
        try{
            new URL(url).toURI();
            return true;
        }
        catch(MalformedURLException e){
            e.printStackTrace();
            return false;
        }
        catch(URISyntaxException e){
            e.printStackTrace();
            return false;
        }
    }
}
