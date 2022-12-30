package applicationVersionTwo;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Report {


	private SimpleIntegerProperty id;
    private SimpleStringProperty username;
    private SimpleStringProperty category ;
    private SimpleStringProperty priority;
    private SimpleStringProperty handler;
    private SimpleDoubleProperty duration;
    
   Report(int id, String name, String cate, String pr, double dur, String hd) {
  	 
  	 this.id = new SimpleIntegerProperty(id);
  	 username = new SimpleStringProperty(name);
  	 category = new SimpleStringProperty(cate);
  	 priority = new SimpleStringProperty(pr);
  	 duration = new SimpleDoubleProperty(dur);
  	 handler = new SimpleStringProperty(hd);
  	  	 
   }
    
	public int getId() {
		return id.get();
	}
	public void setId(int id) {
		this.id.set(id);;
	}
	public String getUsername() {
		return username.get();
	}
	public void setUsername(String username) {
		this.username.set(username);;
	}
	public String getCategory() {
		return category.get();
	}
	public void setCategory(String category) {
		this.category.set(category);;
	}
	public String getPriority() {
		return priority.get();
	}
	public void setPriority(String priority) {
		this.priority.set(priority);;
	}
	public String getHandler() {
		return handler.get();
	}
	public void setHandler(String handler) {
		this.handler.set(handler);
	}
	public double getDuration() {
		return duration.get();
	}
	public void setDuration(double duration) {
		this.duration.set(duration);
	}


	
}
