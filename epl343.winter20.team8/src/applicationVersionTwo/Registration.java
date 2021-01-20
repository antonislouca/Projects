package applicationVersionTwo;

public class Registration {
	int id;
	protected String username;

	protected String password;
	protected String phone;
	protected String email;
	protected String F_Name;
	protected String L_Name;

	public Registration(int id, String username, String password, String phone, String email, String f_Name,
			String l_Name) {
		super();
		this.setUsername(username);
		this.password = password;
		this.id = id;
		this.phone = phone;
		this.email = email;
		F_Name = f_Name;
		L_Name = l_Name;
	}

	@Override
	public String toString() {
		return "Registration [id=" + id + ", username=" + username + ", password=" + password + ", phone=" + phone
				+ ", email=" + email + ", F_Name=" + F_Name + ", L_Name=" + L_Name + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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
