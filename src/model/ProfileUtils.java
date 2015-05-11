package model;
/*
 * Provide some static methods to check user input from the servlet using regular expressions.
 * 
 */
public class ProfileUtils {
	private ProfileUtils(){}	//make the class uninstantiatable
	
	//check username at login
	/**
	 * 
	 * @param username
	 * @return
	 */
	public static boolean isUsernameLoginValid(String username){
		boolean valid = false;
		if(username.matches("[a-zA-Z]+(-|_)*[a-zA-Z0-9]*")){
			valid = true;
		}
		return valid;
	}
	
	/*check password at login: the password must be at least 8 chararcters long and can contain
	 alphanumeric characters and symbols */
	public static boolean checkPasswordLogin(String password){
		boolean valid = false;
		if(password.matches("[a-zA-Z0-9!?-_~]{8,}")){
			valid = true;
		}
		return valid;
	}
	
	//check username at registration
	public static boolean checkUsernameReg(String username){
		boolean valid = false;
		if(username.matches("[a-zA-Z]+(-|_)*[a-zA-Z0-9]*")){
			valid = true;
		}
		return valid;
	}
	
	//check password and/or password confirmation at registration
	public static boolean checkPasswordReg(String password){
		boolean valid = false;
		if(password.matches("[a-zA-Z0-9!?-_~]{8,}")){
			valid = true;
		}
		return valid;
	}
	
	//check email address at registration
	public static boolean checkEmail(String email){
		boolean valid = false;
		if(email.matches("[a-zA-Z]+[a-zA-Z0-9]*((-|_|.)?[a-zA-Z0-9]+)*@[a-zA-Z]+[a-zA-Z0-9]*((-|.)?[a-zA-Z0-9]+)*\\.[a-zA-Z]{2,4}")){
			valid = true;
		}
		return valid;
	}
}
