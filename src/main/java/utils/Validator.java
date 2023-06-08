package utils;

import java.util.regex.Pattern;

public class Validator {
    public static boolean isEmptyOrNull(String input){
        return input == null || input == "";
    }
    public static boolean isEmail(String email){
        String regexPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return Pattern.compile(regexPattern).matcher(email).matches();
    }
    public static boolean isValidPassword(String password){
        return password.length() > 4;
    }
}
