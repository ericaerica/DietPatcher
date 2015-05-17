package model;
/**
 * Provide some static methods to check user input using regular expressions and to calculate BMI and BAI
 * 
 */
public class ProfileUtils {
	private ProfileUtils(){}
	
	/**
	 * Checks the username: the username must start with an alphanumeric character
	 * and can contain alphanumeric characters and the minus and the underscore symbols.
	 * @param username
	 * @return True if it matches the regex, False otherwise
	 */
	public static boolean isUsernameValid(String username){
		boolean valid = false;
		if(username.matches("[a-zA-Z]+(-|_)*[a-zA-Z0-9]*")){
			valid = true;
		}
		return valid;
	}
	
	/**
  	 * Checks the password: the password must be at least 8 characters long and can contain
	 * alphanumeric characters and symbols 
	 * @param password
	 * @return True if it matches the regex, False otherwise
	 */
	public static boolean isPasswordValid(String password){
		boolean valid = false;
		if(password.matches("[a-zA-Z0-9!?-_~]{8,}")){
			valid = true;
		}
		return valid;
	}
	

	/**
	 * Checks if the email matches the regex.
	 * @param password
	 * @return True if it matches the regex, False otherwise
	 */
	public static boolean isEmailValid(String email){
		boolean valid = false;
		if(email.matches("[a-zA-Z]+[a-zA-Z0-9]*((-|_|.)?[a-zA-Z0-9]+)*@[a-zA-Z]+[a-zA-Z0-9]*((-|.)?[a-zA-Z0-9]+)*\\.[a-zA-Z]{2,4}")){
			valid = true;
		}
		return valid;
	}
	
	//TODO methods to check every other input (profile.jsp)
	
	/**
	 * Calculate body mass index.
	 * @param height
	 * @param weight
	 * @return	double	BMI
	 */
	public static double calculateBMI(double height, double weight){
		double BMI = 0.0;
		
		return BMI;
	}
	
	/**
	 * Calculate body adiposity index.
	 * @param height
	 * @param waist
	 * @return	double	BAI
	 */
	public static double calculateBAI(double height, double waist){
		double BAI = 0.0;
		
		return BAI;
	}
}
