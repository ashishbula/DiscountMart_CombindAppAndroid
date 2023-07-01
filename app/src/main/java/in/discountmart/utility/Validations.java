
package in.discountmart.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validations {
	
	public static boolean isEmailValid(String email) {
	    boolean isValid = true;

	    String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
	    CharSequence inputStr = email;

	    Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(inputStr);
	    if (matcher.matches()) {
	        isValid = false;
	    }
	    return isValid;
	}
	
	public static boolean checkUpperCase(String s) {

		boolean isUpperCase = false;
		for (int i = 0; i < s.length(); i++) {
			isUpperCase = Character.isUpperCase(s.charAt(i));
			if (isUpperCase == true)
				break;
		}
		return isUpperCase;
	}
 
	public static boolean isValidPassword(String password) {
	    // TODO Auto-generated method stub
		return password != null && password.trim().length() >= 4 && password.trim().length() <= 12;
	}

	public static boolean isValidPhoneNumber(String phoneNumber) {
		String regexStr = "^[0-9]$";

		return phoneNumber.length() > 10 || phoneNumber.length() < 10 || phoneNumber.matches(regexStr) == true;
	}

	public static boolean isStringContainSpecialCharacter(String String) {
		Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(String);
		boolean b = m.find();
		return b;

	}

	public static boolean isStringContainWhiteSpace(String str) {
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}



}
