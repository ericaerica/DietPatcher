package model;

import javax.persistence.*;



@Entity
@Table(name="user")
public class UserBean {
	//TODO controllarli con un autenticatore
	@Id
	@Column(name="email")
	private String email;
	
	@Column(name="username")
	private String usernameLogin;
	
	@Column(name="password")
	private String password;
	
	@Column(name="gender")
	private String gender;
	
	@Column(name="age")
	private String age;
	
	@Column(name="height")
	private String height;
	
	@Column(name="weight")
	private String weight;
	
	@Column(name="waist")
	private String waist;

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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getWaist() {
		return waist;
	}

	public void setWaist(String waist) {
		this.waist = waist;
	}	
	
	
}

