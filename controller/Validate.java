package angularMotion.controller;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate {

    public Validate(){}

    public static boolean isNumber(String text){
        text = text.toLowerCase();
        Pattern pat = Pattern.compile("[a-z]");
        Matcher matcher = pat.matcher(text);
        return !matcher.find();
    }
}
