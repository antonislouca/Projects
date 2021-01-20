package applicationVersionTwo;

public class User {

	protected int id;
	protected String username;
	protected String password;
	protected String email;
	protected String Phone;
	protected boolean isAdmin;
	protected String FirstName;
	protected String LastName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public User(int id, String username, String password, String email, String phone, boolean isAdmin, String firstName,
			String lastName) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.isAdmin = isAdmin;
		this.Phone = phone;
		this.FirstName = firstName;
		this.LastName = lastName;
	}


	public User(int id) {
		this.id = id;
		this.username = "";
		this.password =  "";
		this.email =  "";
		this.isAdmin = false;
		this.Phone =  "";
		this.FirstName =  "";
		this.LastName =  "";
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", isAdmin=" + isAdmin + ", Phone=" + Phone + ", FirstName=" + FirstName + ", LastName=" + LastName
				+ "]";
	}

}
