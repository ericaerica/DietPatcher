package model;

public class UserBean {
	//TODO correggere in modo che ci siano solo username, password, email e controllarli con un autenticatore
	private String usernameLogin, passwordLogin, usernameReg, passwordReg1, passwordReg2, email;

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

	public String getUsernameReg() {
		return usernameReg;
	}

	public void setUsernameReg(String usernameReg) {
		this.usernameReg = usernameReg;
	}

	public String getPasswordReg1() {
		return passwordReg1;
	}

	public void setPasswordReg1(String passwordReg1) {
		this.passwordReg1 = passwordReg1;
	}

	public String getPasswordReg2() {
		return passwordReg2;
	}

	public void setPasswordReg2(String passwordReg2) {
		this.passwordReg2 = passwordReg2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}	
}
