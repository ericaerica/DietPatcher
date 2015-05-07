package model;

public class UserBean {
	//TODO correggere in modo che ci siano solo username, password, email e controllarli con un autenticatore
	private String usernameLogin, password, usernameReg, email;

	public String getUsernameLogin() {
		return usernameLogin;
	}

	public void setUsernameLogin(String usernameLogin) {
		this.usernameLogin = usernameLogin;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsernameReg() {
		return usernameReg;
	}

	public void setUsernameReg(String usernameReg) {
		this.usernameReg = usernameReg;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}	
}
