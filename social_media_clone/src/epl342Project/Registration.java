package epl342Project;

import java.time.LocalDate;

public class Registration {
	private String username;
	private String password;
	private String email;
	private String F_Name;
	private String L_Name;
	private LocalDate birthday;

	public Registration(String username, String password, String f_Name,
			String l_Name, String email, LocalDate birthday) {
		super();
		this.setUsername(username);
		this.password = password;

		this.email = email;
		F_Name = f_Name;
		L_Name = l_Name;
		this.birthday = birthday;
	}
	
	public String toString() {
		return "Registration [username=" + username + ", password=" + password 
				+ ", email=" + email + ", F_Name=" + F_Name + ", L_Name=" + L_Name + ", "+birthday+"]";
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public LocalDate getBirthday() {
		return birthday;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getF_Name() {
		return F_Name;
	}

	public void setF_Name(String f_Name) {
		F_Name = f_Name;
	}

	public String getL_Name() {
		return L_Name;
	}

	public void setL_Name(String l_Name) {
		L_Name = l_Name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
