package controller;

public class UserBean {
	//TODO correggere in modo che ci siano solo username, password, email e controllarli con un autenticatore
	private String usernameLogin, passwordLogin, inputUserNameReg, subPassword, inputPasswordAgain,inputEmail;

	public String getUsernameLogin() {
		return usernameLogin;
	}

	public void setUsernameLogin(String usernameLogin) {
		this.usernameLogin = usernameLogin;
	}

	public String getPasswordLogin() {
		return passwordLogin;
	}

	public void setPasswordLogin(String passwordLogin) {
		this.passwordLogin = passwordLogin;
	}

	public String getInputUserNameReg() {
		return inputUserNameReg;
	}

	public void setInputUserNameReg(String inputUserNameReg) {
		this.inputUserNameReg = inputUserNameReg;
	}

	public String getSubPassword() {
		return subPassword;
	}

	public void setSubPassword(String subPassword) {
		this.subPassword = subPassword;
	}

	public String getInputPasswordAgain() {
		return inputPasswordAgain;
	}

	public void setInputPasswordAgain(String inputPasswordAgain) {
		this.inputPasswordAgain = inputPasswordAgain;
	}

	public String getInputEmail() {
		return inputEmail;
	}

	public void setInputEmail(String inputEmail) {
		this.inputEmail = inputEmail;
	}	
}
