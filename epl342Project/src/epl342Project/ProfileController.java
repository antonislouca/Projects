package epl342Project;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProfileController {

	private User user;
	ProfileView view;
	private ProfileModel model;
	List<String> cy;
	List<String> work;
	List<String> education;
 	

	public ProfileController(Stage primaryStage, User user) throws IOException {
		cy = new ArrayList<String>();
		work = new ArrayList<String>();
		education = new ArrayList<String>();
		readWork();
		readCy();
		readEducation();
		this.user = user;
		this.view = new ProfileView(primaryStage);
		this.model = new ProfileModel();
		this.view.setController(this);
		try {
			this.view.generateTabPane();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//this.model.setController(this);

	}

	

	public static void startController(Stage primaryStage, User user) throws IOException {
		ProfileController controller = new ProfileController(primaryStage, user);
		controller.view.startView();
	}

	public void signIn(String username, String password) {
//		User user=this.model.getUser(username);
//		if (User.getPassword().equals(password)) {
//			if(User.isAdmin()==true) {
//				Admin
//			}
//			else {
//
//			}
//		}
//		else {
//			//oste na men tou pis oti exi tuto to username. gia na spamari meta. gia safty
//			AdminController.displayPopUp("There is no user with this username or with this password\n");
//			// TODO Auto-generated method stub
//		}
		AuthenticationController.displayPopUp("There is no user with this username or with this password\n");

	}
	
	
	public void readWork() throws IOException {
		
		
		work = this.model.readWorks();
	  
	
	}
	
	public void readEducation() throws IOException {
		

		  education = this.model.readEducations();
	
	}
	
	
	public void readCy() throws IOException {
		
		cy = this.model.readCities();
	
	}
	
	/**
	 * Takes as arguments the new grid AKA the new view of th the tab and changes the view of the tab.
	 * @param newGird
	 * @param tabIndex
	 */
	public void changeTabView(GridPane newGird, int tabIndex) {
		Tab tab = this.view.tabPane.getTabs().get(tabIndex);
		tab.setContent(newGird);
	}
	
	
	public static void displayPopUp(String message) {
		Stage popupwindow = new Stage();
		popupwindow.initModality(Modality.APPLICATION_MODAL);
		popupwindow.setTitle("Message");
		Label label1 = new Label(message);
		Button button1 = new Button("OK");
		button1.setOnAction(e -> popupwindow.close());
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label1, button1);
		layout.setAlignment(Pos.CENTER);
		Scene scene1 = new Scene(layout, 800, 800);
		popupwindow.setScene(scene1);
		popupwindow.showAndWait();

	}
	
	
	public User getUserInfo() {
		
		
		return user;
		
	}
	
	public User getUserInfo(int id) {
		
		User user = this.model.getUserInfo(id);
		
		return user;
		
	}
	
	public void searchPhoto(String name, String v) throws Exception {
		Photo ph;
		ArrayList<Photo> p;
		
		if(v.equals("mine")) {
						
			p =this.model.getUserPhotos(name, user.userID);
		

		}
		
		else {
			
			p = this.model.spShowPhotos(user.userID, name);
	

		}
		
		
		if(p != null) {
		ph = p.get(0);
	
		Scene newScene = new Scene(this.view.getPhoto(ph));
		this.view.primaryStage.setScene(newScene);
		this.view.primaryStage.show();
		}
	}
	
	
	public void searchVideo(String name, String v) throws Exception {
		Video ph = null;
		
		ArrayList<Video> p;
		
		if(v.equals("mine")) {
			
			 p =this.model.FindUserVideos(name, user.userID);

		}
		
		else {
			
			 p =this.model.spShowVideos(user.userID, name);

		}
		
		

	
		if(p != null) {
		ph = p.get(0);
	Scene newScene = new Scene(this.view.getVideo(ph));
	this.view.primaryStage.setScene(newScene);
	this.view.primaryStage.show();
		}
		else
			displayPopUp("no video founs!!!");
	}
	
	
	
	
	public String searchEvent(String name, String v) throws FileNotFoundException {
		

		if(v.equals("mine")) {
			
			ArrayList<Event> p =this.model.findUserEvents(name, user.userID);
			
	        if(p == null||p.isEmpty())
	        	return("No results Found!"); 
			
			Event p1 = p.get(0);
			
	        String s = ("Name : " + p1.name + " \nStart Time : " + p1.start_time + " \n" + 
					"Finish Time : " + p1.finish_time + " \n" + "Location : " + p1.location + " \n"
					+ "Venue : " + p1.venue2 + " \n" + "Description : " + p1.description + " \n") ;       
	        return s;
		}
		
		else {
			
			ArrayList<Event> p =this.model.spShowEventsName(user.userID, name);
			
			
	        if(p == null||p.isEmpty())
	        	return("No results Found!"); 
			
			Event p1 = p.get(0);
			
			Scene newScene = new Scene(this.view.getEvent(p1));
			this.view.primaryStage.setScene(newScene);
			this.view.primaryStage.show();
			

			return "success";
		}
	
	}
	
	
	
	public void searchAlbum(String name, String v) throws Exception {

		Album ph;
		List<Album> p ;

		if(v.equals("mine")) {
			
			p =this.model.getAlbumFromName(name, user.userID);
			
		}
		
		else {
			
			p=this.model.spShowAlbums(user.userID, name);
			
		}
		
		
		
		if(p != null) {
		ph = p.get(0);
		
	
		Scene newScene = new Scene(this.view.getAlbum(ph));
		this.view.primaryStage.setScene(newScene);
		this.view.primaryStage.show();
		}
	}
	
	
	public String searchLink(String name, String v) {
		
		Link ph;
		List<Link> p ;
		
		if(v.equals("mine")) {
			
			p =this.model.FindUserLinks(name, user.userID);
			
		}
		
		else {
			
			p =this.model.spShowLinks(user.userID, name);
			
		}
		
	       if(p == null)
	        	return "No results Found!";
	        else {
	        	ph = p.get(0);
		String s = ("Description : " + ph.description + " \nLink : " + ph.link+ " \n" + 
				"Caption : " + ph.caption + " \n" + "Message : " + ph.message + " \n") ;	
		return s;}
	}
	
	
	public void showEditInfoView() throws Exception {
		Scene newScene = new Scene(new ScrollPane(this.view.getEditInfoView()));
		this.view.primaryStage.setScene(newScene);
		this.view.primaryStage.show();
	}
	
	public void showEditPhotoView(Photo f) throws Exception {
		
		Scene newScene = new Scene(this.view.getEditPhotoView(f));
		this.view.primaryStage.setScene(newScene);
		this.view.primaryStage.show();
	}
	
	public void showEditVideoView(Video v) throws Exception {
		Scene newScene = new Scene(this.view.getEditVideoView(v));
		this.view.primaryStage.setScene(newScene);
		this.view.primaryStage.show();
	}
	
	
	public void showEditEventView(Event e) throws Exception {
		Scene newScene = new Scene(this.view.getEditEventView(e));
		this.view.primaryStage.setScene(newScene);
		this.view.primaryStage.show();
	}
	
	
	public void showEditLinkView(Link l) throws Exception {
		Scene newScene = new Scene(this.view.getEditLinkView(l));
		this.view.primaryStage.setScene(newScene);
		this.view.primaryStage.show();
	}
	
	public void showEditAlbumView(Album a) throws Exception {
		Scene newScene = new Scene(new ScrollPane(this.view.getEditAlbumView(a)));
		this.view.primaryStage.setScene(newScene);
		this.view.primaryStage.show();
	}
	
	
	public void showIgnoredView() throws Exception {
		Scene newScene = new Scene(new ScrollPane(this.view.getIgnoredFriendReq()));
		this.view.primaryStage.setScene(newScene);
		this.view.primaryStage.show();
	}
	
	
	
	
	public void showUserProfile(Friend fri) throws Exception {
		
		
		User u = this.getUserInfo(fri.id);
		Scene newScene = new Scene(new ScrollPane(this.view.getViewUser(u)));
		this.view.primaryStage.setScene(newScene);
		this.view.primaryStage.show();
	}
	
	
	public void showUserProfile(int id) throws Exception {
		
		
		User u = this.getUserInfo(id);
		Scene newScene = new Scene(new ScrollPane(this.view.getViewUser(u)));
		this.view.primaryStage.setScene(newScene);
		this.view.primaryStage.show();
	}
	
	
	
	public void simpleUserSearch(String s1,String s2,String s3 ){
		
		User us ;//= this.user;
		ArrayList<User> u = null;
		
		if(!s1.isEmpty() && s2.isEmpty() && s3.isEmpty())			
			u =this.model.SimpleSearchUser(s1, s2, s3, 1);		
		
		else if(s1.isEmpty() && !s2.isEmpty() && s3.isEmpty())			
			u =this.model.SimpleSearchUser(s1, s2, s3, 2);			
		
		else if(s1.isEmpty() && s2.isEmpty() && !s3.isEmpty())			
			u =this.model.SimpleSearchUser(s1, s2, s3, 3);		
		
		else if(!s1.isEmpty() && !s2.isEmpty() && s3.isEmpty())			
			u =this.model.SimpleSearchUser(s1, s2, s3, 4);		
		
		else if(!s1.isEmpty() && s2.isEmpty() && !s3.isEmpty())			
			u =this.model.SimpleSearchUser(s1, s2, s3, 5);		
		
		else if(s1.isEmpty() && !s2.isEmpty() && !s3.isEmpty())			
			u =this.model.SimpleSearchUser(s1, s2, s3, 6);		
		
		else if(!s1.isEmpty() && !s2.isEmpty() && !s3.isEmpty())			
			u =this.model.SimpleSearchUser(s1, s2, s3, 7);		
		
		
		
		
		
		
		if (u == null || u.isEmpty()) {
			this.displayPopUp("No results Found !");
		}
		else {
			us = u.get(0);
			Scene newScene = null;
			try {
				newScene = new Scene(new ScrollPane(this.view.getViewUser(us)));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.view.primaryStage.setScene(newScene);
			this.view.primaryStage.show();
		}
		
		
	}
	
	
	public void advanceUserSearch(String t , String in){
		ArrayList<User> us = null;
		
		if(t.equals("education")) {
			us = this.model.ComplexSearchUser(0, in);
		}
		else {
			 us =this.model.ComplexSearchUser(1, in);	
	}
		
		
		if (us == null) {
			this.displayPopUp("No results Found !");
		}
		else {
			
			String res = " Results : \n";
			
			for (int i = 0; i < us.size(); i++) {
				
				res+= us.get(i).name + " " + us.get(i).birthdate + " " + us.get(i).location +"\n";
			}
			
			this.displayPopUp(res);
		}
				
	}
	
	
	public String sameInterestsSearch() {
		
		ArrayList<User> us = this.model.sameIntrestsSearch(user.userID);
		
		String res = " Results : \n";
		
		for (int i = 0; i < us.size(); i++) {
			
			res+= us.get(i).name + " " + us.get(i).birthdate + " " + us.get(i).location +"\n";
		}
	
		
		return res;
	
	}
	
	
	public String mostPopularFriend() {
		
		ArrayList<User> us = this.model.findPopFriends(user.userID);
		
		String res = " Results : \n";
		
		for (int i = 0; i < us.size(); i++) {
			
			res+= us.get(i).name + " " + us.get(i).birthdate + " " + us.get(i).location +"\n";
		}
		
		
		return res;
	
	}
	
	
	public String biggestAlbum(int x , String v) {
		
		ArrayList<User> us = null;
		
		String res = " Results : \n";
		
		if(v.equals("all")) {
			
			us =this.model.findUsersWithLargestAlbums(user.userID, x);
		}
		else {
			
			
			us =this.model.findFriendsWithLargestAlbums(user.userID, x);

		}
		
	for (int i = 0; i < us.size(); i++) {
			
			res+= us.get(i).name + " " + us.get(i).birthdate + " " + us.get(i).location +"\n";
		}
		
	this.displayPopUp(res);
		
		return res;	
	}
	
	
	public String sameFriends() {
		
		
		ArrayList<User> us = null;
		
		String res = " Results : \n";
			
		us =this.model.findCommonFriends(user.userID);
	
		
	for (int i = 0; i < us.size(); i++) {
			
			res+= us.get(i).name + " " + us.get(i).birthdate + " " + us.get(i).location +"\n";
		}
		
		
		return res;	
	}
	
	
	public void sendFriendReq(int id) {
		
		this.model.sendFriendReq(user.userID, id);
		// sends a friend request to the given id
		
	}
	
	public void addFriend(int id) {
		
		
		this.model.acceptFriendReq(user.userID, id);
		// adds a friend
		
	}
	
	public void deleteFriend(Friend friend) {
		
		
		int id = friend.id;
		
		this.model.deleteFriend(user.userID, id);
		// deletes a friend
		
	}
	
	public void rejectFriendReq(int id) {
		
		
		this.model.rejectFriendReq(user.userID, id);
		// adds a friend
		
	}
	
	
	public void ignoreFriendReq(int index,int id) {
		
		this.model.ignoreFriendReq(user.userID, id, 1);
		// adds a friend
		
	}
	
	
	public void getUpdates(String s , int k) {
		
		
		ArrayList<Updates> l = new ArrayList<>();
		
		if(s.equals("Albums"))
			l = this.model.spFindUpdatesAlbum(k, user.userID);
		
		if(s.equals("Events"))
			l = this.model.spFindUpdatesEvents(k, user.userID);
		
		
		if(s.equals("Links"))
			l = this.model.spFindUpdatesLinks(k, user.userID);
		
		if(s.equals("Photos"))
			l = this.model.spFindUpdatesPhotos(k, user.userID);
		
		if(s.equals("Videos"))
			l = this.model.spFindUpdatesVideos(k, user.userID);
		
		if(s.equals("All"))
			l = this.model.getAllUpdates(k, user.userID);
		
		String d ="Results: \n";
		
		if(l == null || l.isEmpty())
		this.displayPopUp("no results found!");
		else {
			for (int i = 0; i < l.size(); i++) {
				
				d+= l.get(i).upType + " " + l.get(i).date.toString() + "\n";
			}
			
			this.displayPopUp(d);
			
		}
		
		
		
	}
	
	
	
	
	public void goingToEvent(Event t) {
		
		this.model.spMarkParticipation(user.userID, t.eventID);
		// adds a friend
		
	}
	
	
	public String getLeastPopular() {
		
		List<Event> l = new ArrayList<>();
		
		String s = "Results: \n";
		
		l = this.model.nonPopEvents();
		
		
		if(l == null || l.isEmpty())
		return("no results found!");
		else {
			for (int i = 0; i < l.size(); i++) {
				
				s+= l.get(i).eventID + " "+ l.get(i).name + " " + l.get(i).description +" " +  l.get(i).venue2 + " " + l.get(i).start_time + "\n";
			}
			
			
			
		}
		
		
		
		return s;
	}
	
	
	public double getAverageAge() {
		
		double d = this.model.getAverageage(user.userID);
		
		
		return d;
	}
	
	
	
	public List<Friend> getFriendList() {
		
		// Here we will call the function to return our friends
		
		
        List<User> friends = this.model.getFriendList(user.userID);//new ArrayList<Friend>(); 
        
        
        
        List<Friend> list = new ArrayList<Friend>();
       
        for (int i = 0; i < friends.size(); i++) {
			
        	String name = friends.get(i).name;
        	String birthdate = friends.get(i).birthdate.toString();
        	String email = friends.get(i).email;
        	String work = friends.get(i).work;
        	String web = friends.get(i).website;
        	
        	list.add(new Friend(friends.get(i).userID,name, birthdate , email, work , web));
        	
		}
        
	
		
		return list;
		
	}
	
	
	public List<Event> getEvents() {
		
		// Here we will call the function to return our friends
		
		
        List<Event> list = new ArrayList<Event>(); 
    	list = this.model.findEvents(user.userID);
        

		return list;
		
	}
	
	public List<Event> getEvents(int id) {
		
		   List<Event> list = new ArrayList<Event>(); 
		
		
		
		List<Event>r = new ArrayList<Event>(); 
		
		r = this.model.findEvents(id);
		
		if(r!=null)
		for (int i = 0; i < r.size(); i++) {			
			List<Event> a = this.model.spShowEventsName(user.userID, r.get(i).name);
			
			if(a!=null&&!a.isEmpty())
			list.add(a.get(0));				
		}
		
		


	
		return list;
		
	}
	
	
	public List<Album> getAlbum() throws SQLException{
		
		List<Album> list = new ArrayList<Album>(); 
		
		
		list = this.model.findAlbum(user.userID);
		
		List<Photo> f1 = new ArrayList<Photo>(); 
		
		
		List<Photo> f2 = new ArrayList<Photo>(); 
		List<Photo> f3 = new ArrayList<Photo>(); 
		
	// Dummy data	
		
		
		List<String> com = new ArrayList<String>(); 
		

		return list;
	}
	
	
	public List<Album> getAlbum(int id) throws SQLException{
		
		List<Album> list = new ArrayList<Album>(); 
		
		
		
		List<Album>r = new ArrayList<Album>(); 
		
		r = this.model.findAlbum(id);
			
		if(r!=null)
		for (int i = 0; i < r.size(); i++) {	
			
			List<Album> a = this.model.spShowAlbums(user.userID, r.get(i).name);
			
			if(a!=null&&!a.isEmpty())
			list.add(a.get(0));						
		}

		return list;
	}
	
	
	public List<Photo> getPhotos(){
		
		List<Photo> list = new ArrayList<Photo>(); 
		
		list = this.model.findPhotos(this.user.userID);
		
	
		
		
		return list;
	}
	
	
	public List<Video> getVideos(){
		
		List<Video> list = new ArrayList<Video>(); 
	
		list = this.model.findVideos(this.user.userID);
		
		return list;
	}
	
	
	public List<Video> getVideos(int id){
	
		List<Video> list = null, r = new ArrayList<Video>(); 
		
		r = this.model.findVideos(id);
			
		if(r!=null)
		for (int i = 0; i < r.size(); i++) {			
			List<Video> a = this.model.spShowVideos(user.userID, r.get(i).name);
			
			if(a!=null&&!a.isEmpty())
			list.add(a.get(0));				
		
		}
		

	
	return list;
	}
	
	public List<Link> getLink(){
		
		List<Link> list = new ArrayList<Link>(); 
		
		list = this.model.findLinks(user.userID);

		return list;
	}
	
	public List<Link> getLink(int id){
		
		List<Link> list = null, r = new ArrayList<Link>(); 
		
		r = this.model.findLinks(id);
			
		if(r!=null)
		for (int i = 0; i < r.size(); i++) {			
			List<Link> a = this.model.spShowLinks(user.userID, r.get(i).name);
			
			if(a!=null&&!a.isEmpty())
			list.add(a.get(0));	
						
		}
		

		
		
		return list;
	}
	
	
	
	public List<User> getFriendRequests(){
		
		ArrayList<User> users = this.model.getFriendReq(user.userID);

		 return users;
	}
	
	
	
	public List<User> getIgnoredFriendRequests(){
		
		 List<User> list = new ArrayList<User>(); 
		 
		 list = this.model.getIgnoredFriendReq(user.userID);

		 return list;
	}


	public void personalInfo(String username,String password,String name,String birthday,String email,String work, String edu, String web, String loc, String home) {		
		
		String[] d = name.split(" ");
		
		String f = d[0];
		String l = d[1];
		
		
		
		// Calling the update info function !!!!
		
		//this.model.(username,password,f,l,birthday,email,work,edu,web,loc,home);
				
		this.view.startView();
		
	}

	public void photoInfo(String name,String privacy) {				
		// Calling the update info function !!!!
			
		this.view.startView();	
		
	}
	
	
	public void albumInfo(String name,String desc, String location, String privacy) {				
		// Calling the update info function !!!!
			
		this.view.startView();	
		
	}

	
	
	public void videoInfo(String title,String desc) {				
		// Calling the update info function !!!!
			
		this.view.startView();	
		
	}


	public void EventInfo(String name,String desc, String link,String caption,String message,String pr, String loc) {				
		// Calling the update info function !!!!			
		this.view.startView();	
		
	}
	
	public void linkInfo(String name,String desc, String link,String caption,String message) {				
	// Calling the update info function !!!!
		
	this.view.startView();	
	
}

	public void showLogInView() {
		try {
			AuthenticationController.startController(this.view.primaryStage);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
