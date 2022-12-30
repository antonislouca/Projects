package applicationVersionTwo;

import java.sql.Timestamp;

public class Ticket {
    
	private int id;
	private String priority;
	private String category;
	private String description;
	private String status;
	private Timestamp startTime;
//	private long startTime;
	private long duration;
	private String username;
	private int handlerID;
	private String handlerUsername;
	
	public int getHandlerID() {
		return handlerID;
	}

	public void setHandlerID(int handlerID) {
		this.handlerID = handlerID;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", priority=" + priority + ", category=" + category + ", description=" + description
				+ ", status=" + status + ", startTime=" + startTime + ", duration=" + duration + ", username="
				+ username + ", handlerID=" + handlerID + ", handlerUsername=" + handlerUsername + "]";
	}

	public Ticket(int id, String priority, String category, String description, String status, Timestamp startTime,
			long duration, String username, int handlerID) {
		super();
		this.id = id;
		this.priority = priority;
		this.category = category;
		this.description = description;
		this.status = status;
		this.startTime = startTime;
		this.duration = duration;
		//this.empID = empID;
		this.username=username;
		this.handlerID = handlerID;
	}
	
	
	public Ticket(int id, String priority, String category, String description, String status, Timestamp startTime,
			long duration, String username, String handlerID) {
		super();
		this.id = id;
		this.priority = priority;
		this.category = category;
		this.description = description;
		this.status = status;
		this.startTime = startTime;
		this.duration = duration;
		//this.empID = empID;
		this.username=username;
		this.handlerUsername = handlerID;
	}
	
	
	public Ticket(int id, String priority, String category, String description, String status, 
			 String username) {
		super();
		this.id = id;
		this.priority = priority;
		this.category = category;
		this.description = description;
		this.status = status;
		this.startTime = new Timestamp(0);
		this.duration = 0;
		//this.empID = empID;
		this.username=username;
		this.handlerID = 0;
	}
	
	
	public Ticket(int id, String priority, String category, String description,String username) {
		super();
		this.id = id;
		this.priority = priority;
		this.category = category;
		this.description = description;
		this.status = TicketStatus.toStart();
		this.startTime = new Timestamp(0);
		this.duration = 0;
		//this.empID = empID;
		this.username=username;
		this.handlerID = 0;
	}
	
	public Ticket(String priority, String category, String description, String status, 
			 String username) {
		//super();
		this.id = 0;
		this.priority = priority;
		this.category = category;
		this.description = description;
		this.status = status;
		this.startTime = new Timestamp(0);
		this.duration = 0;
		//this.empID = empID;
		this.username=username;
		this.handlerID = 0;
	}
	
	

	public int getId() {
		return id;
	}

	public Ticket setId(int id) {
		this.id = id;
		return this;
	}

	public String getPriority() {
		return priority;
	}
	
	public Ticket setHandlerUsername(String handlerUsername) {
		this.handlerUsername = handlerUsername;
		return this;
	}
	public String getHandlerUsername() {
		return handlerUsername;
	}

	public Ticket setPriority(String priority) {
		this.priority = priority;
		return this;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public Ticket setDescription(String description) {
		this.description = description;
		return this;
	}

	public String getStatus() {
		return status;
	}

	public Ticket setStatus(String status) {
		this.status = status;
		return this;
	}

	public Timestamp getStartTime() {
		return this.startTime;
	}

	public Ticket setStartTime(long startTime) {
		this.startTime = new Timestamp(startTime);
		return this;
	}

	public long getDuration() {
		return duration;
	}

	public Ticket setDuration(long duration) {
		this.duration = duration;
		return this;
	}

	public String getUsername() {
		return this.username;
	}



	public int getAssignedID() {
		return handlerID;
	}

	public Ticket setAssignedID(int assignedID) {
		this.handlerID = assignedID;
		return this;
	}

}

class TicketStatus {
	static String toStart() {
		return "ToStart";
	}
	static String toFinish() {
		return "Started";
	}
	static String finished() {
		return "Finished";
	}
}
