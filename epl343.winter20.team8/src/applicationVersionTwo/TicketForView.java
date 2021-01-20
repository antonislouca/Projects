package applicationVersionTwo;

import java.sql.Timestamp;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TicketForView {

	private SimpleIntegerProperty id;
	private SimpleStringProperty username;
	private SimpleStringProperty category;
	private SimpleStringProperty priority;
	private SimpleStringProperty status;
	private SimpleIntegerProperty Thandler;
	private SimpleStringProperty description;
	private Timestamp startTime;
	private long duration;

	public TicketForView(Ticket tk) {
		setId(new SimpleIntegerProperty(tk.getId()));
		setUsername(new SimpleStringProperty(tk.getUsername()));
		setCategory(new SimpleStringProperty(tk.getCategory()));
		setPriority(new SimpleStringProperty(tk.getPriority()));
		duration=tk.getDuration();
		setThandler(new SimpleIntegerProperty(tk.getAssignedID()));
		setStatus(new SimpleStringProperty(tk.getStatus()));
		setDescription(new SimpleStringProperty(tk.getDescription()));
		this.startTime =null;
	}

	public int getId() {
		return id.get();
	}

	public Timestamp getStarttime() {
		return startTime;
	}

	public void setStarttime(Timestamp start) {
		this.startTime=start;
	}

	public void setId(SimpleIntegerProperty id) {
		this.id = id;
	}

	public String getUsername() {
		return username.get();
	}

	public void setUsername(SimpleStringProperty username) {
		this.username = username;
	}

	public String getCategory() {
		return category.get();
	}

	public void setCategory(SimpleStringProperty category) {
		this.category = category;
	}

	public String getPriority() {
		return priority.get();
	}

	public void setPriority(SimpleStringProperty priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return status.get();
	}

	public void setStatus(SimpleStringProperty status) {
		this.status = status;
	}

	public Integer getThandler() {
		return Thandler.get();
	}

	public void setThandler(SimpleIntegerProperty thandler) {
		Thandler = thandler;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getDescription() {
		return description.get();
	}

	public void setDescription(SimpleStringProperty description) {
		this.description = description;
	}

	public Ticket FromViewToTicket() {
		return new Ticket(this.getId(), this.getPriority(), this.getCategory(), this.getDescription(), this.getStatus(),
				this.getStarttime(), this.getDuration(), this.getUsername(), this.getThandler());

	}
}
