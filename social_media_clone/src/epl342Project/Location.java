package epl342Project;

public class Location {

	
	String city;
	String country;
	
	
	public Location(String city, String country) {
		super();
		this.city = city;
		this.country = country;
	}
	
	public Location(Object[] locationRS) {
		city	= (String) locationRS[0];
		country = (String) locationRS[1];
	}
	
	
	public String toString() {
		
		return ("" + city + ", " + country);
		
	}
	
}
