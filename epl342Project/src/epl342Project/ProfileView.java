package epl342Project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

public class ProfileView {

	Stage primaryStage;
	TabPane tabPane;
	ProfileController controller;

	public void setController(ProfileController controller) {
		this.controller = controller;
	}

	public ProfileView(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public void generateTabPane() throws Exception {
		this.tabPane= new TabPane();
		int index=0;

      Tab profile = new Tab("Profile",new ScrollPane(this.getViewProfile(index++)));
      Tab friends = new Tab("Friends",new ScrollPane(this.getViewFriends(index++)));
      Tab requests = new Tab("Friend Requests", new ScrollPane(this.getFriendReqView(index++)));
      Tab photos = new Tab("Photos",new ScrollPane( this.getViewPhoto(index++)));
      Tab videos = new Tab("Videos", new ScrollPane(this.getViewVideo(index++)));
      Tab events = new Tab("Events",new ScrollPane( this.getViewEvent(index++)));
      Tab albums = new Tab("Albums",new ScrollPane( this.getViewAlbum(index++)));
      Tab link = new Tab("Links",new ScrollPane( this.getViewLink(index++)));
      Tab general = new Tab("General",new ScrollPane( this.getViewGeneral(index++)));
      Tab search = new Tab("Search",new ScrollPane( this.getViewSearch((index++))));
     
      


      tabPane.getTabs().add(profile);
      tabPane.getTabs().add(friends);
      tabPane.getTabs().add(requests);
      tabPane.getTabs().add(photos);
      tabPane.getTabs().add(videos);
      tabPane.getTabs().add(events);
      tabPane.getTabs().add(albums);
      tabPane.getTabs().add(link);
      tabPane.getTabs().add(general);
      tabPane.getTabs().add(search);


	}

	public void startView() {
		VBox vBox = new VBox(this.tabPane);
		Scene scene = new Scene(vBox, 800, 500);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Social Face");
		primaryStage.show();
	}

//	TreeView<String> tree;
//	GridPane grid = new GridPane();
	private TreeItem<String> makeBranch(String title, TreeItem<String> parent) {
		TreeItem<String> item = new TreeItem<>(title);
		item.setExpanded(true);
		parent.getChildren().add(item);
		return item;
	}
	
	
	public void scrollable(GridPane grid) {
		
		
		
	}
	
	
	public void prepareScene(GridPane grid) throws FileNotFoundException {
		grid.setAlignment(Pos.BASELINE_LEFT);
		grid.setHgap(18);
		grid.setVgap(18);
		// grid.setPadding(new Insets(00, 00, 00, 00));

		Image image = new Image(new FileInputStream("recourses/logo.png"));

		// Setting the image view
		ImageView imageView = new ImageView(image);

		// Setting the position of the image
		imageView.setX(0);
		imageView.setY(0);

		// setting the fit height and width of the image view
		imageView.setFitHeight(150);
		imageView.setFitWidth(250);
		// Setting the preserve ratio of the image view
		imageView.setPreserveRatio(true);

		// Creating a Group object

		grid.add(imageView, 0, 0);


		Button logout = new Button("Logout");
		logout.setOnAction(event->{
			this.controller.showLogInView();
		});
		grid.add(logout, 15, 0);

	}

	
	public GridPane getViewSearch(int index) throws Exception {
		
		GridPane grid = new GridPane();
		User user = controller.getUserInfo();
		prepareScene(grid);
		Text scenetitle = new Text("Search on Social Face");
		scenetitle.setStyle("-fx-text-fill: green; -fx-font-size: 40px;");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 32));
		grid.add(scenetitle, 1, 1, 10, 1);
	
		
		
		Label comptel = new Label("Search For a Video");
		grid.add(comptel, 2, 3);		
		TextField textcomptel = new TextField();
		grid.add(textcomptel, 3, 3);
		
		Button button = new Button("Search");
		button.setOnAction(event->{			
	    	if(textcomptel.getText().isEmpty())
	    		controller.displayPopUp("Please fille the text box with a name");
			else
				try {
					this.controller.searchVideo(textcomptel.getText(), "all");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		});
		
		grid.add(button, 4, 3);
		
		Label c1 = new Label("Search For an Album");
		grid.add(c1, 2, 4);		
		TextField t1 = new TextField();
		grid.add(t1, 3, 4);
		
		Button b1 = new Button("Search");
		b1.setOnAction(event->{			
	    	if(t1.getText().isEmpty())
	    		controller.displayPopUp("Please fill the text box with a name");
			else
				try {
					this.controller.searchAlbum(t1.getText() , "all");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		});
		
		grid.add(b1, 4, 4);
		
		
		
		Label c2 = new Label("Search For a Link");
		grid.add(c2, 2, 5);		
		TextField t2 = new TextField();
		grid.add(t2, 3, 5);
		
		Button b2 = new Button("Search");
		b2.setOnAction(event->{			
	    	if(t2.getText().isEmpty())
	    		controller.displayPopUp("Please fille the text box with a name");
	    	else	
	    		controller.displayPopUp(this.controller.searchLink(t2.getText(), "all"));
		});
		
		grid.add(b2, 4, 5);
		
		
		Label c3 = new Label("Search For a Photo");
		grid.add(c3, 2, 6);		
		TextField t3 = new TextField();
		grid.add(t3, 3, 6);
		
		Button b3 = new Button("Search");
		b3.setOnAction(event->{			
	    	if(t3.getText().isEmpty())
	    		controller.displayPopUp("Please fille the text box with a name");
			else
				try {
					this.controller.searchPhoto(t3.getText(), "all");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		});
		
		grid.add(b3, 4, 6);
		
		
		
		Label c4 = new Label("Search For a Event :");
		grid.add(c4, 2, 8);	
		
		
		
		Label c5 = new Label("Search by Name");
		grid.add(c5, 2, 9);		
		TextField t4 = new TextField();
		grid.add(t4, 3, 9);
		
		Button b4 = new Button("Search");
		b4.setOnAction(event->{			
	    	if(t4.getText().isEmpty())
	    		controller.displayPopUp("Please fille the text box with a name");
			else
				try {					
					String s = this.controller.searchEvent(t4.getText(), "all");					
					if (s.equals("No results Found!"))
						controller.displayPopUp(s);
				} catch (Exception e) {
					e.printStackTrace();
				}
		});
		
		grid.add(b4, 4, 9);
		
		Label c6 = new Label("Search by Venue");
		grid.add(c6, 2, 10);		
		
		final ComboBox v = new ComboBox();
        v.getItems().addAll(
        		this.controller.cy );       
        v.getSelectionModel().select(0);
		grid.add(v, 3, 10);
		
		
		Button b6 = new Button("Search");
		b6.setOnAction(event->{			
				try {
					String s = this.controller.searchEvent(t4.getText(), "all");					
					if (s.equals("No results Found!"))
						controller.displayPopUp(s);
				} catch (Exception e) {
					e.printStackTrace();
				}
		});
		
		grid.add(b6, 4, 10);
		
		
		Label c7 = new Label("Search by Description");
		grid.add(c7, 2, 11);		
		TextField t7 = new TextField();
		grid.add(t7, 3, 11);
		
		Button b7 = new Button("Search");
		b7.setOnAction(event->{			
	    	if(t7.getText().isEmpty())
	    		controller.displayPopUp("Please fill the text box with a name");
			else
				try {
					String s = this.controller.searchEvent(t4.getText(), "all");					
					if (s.equals("No results Found!"))
						controller.displayPopUp(s);
				} catch (Exception e) {
					e.printStackTrace();
				}
		});
		
		grid.add(b7, 4, 11);
		
		
		
		Label we = new Label("Seach for People by Album size:");
		grid.add(we, 2, 13);
		TextField d = new TextField();
	    grid.add(d, 3, 13);			
		
		    Button sr = new Button("Search");
		    
		    sr.setOnAction(event->{			
				
		    	if(d.getText().isEmpty())
		    		controller.displayPopUp("Please fill the text box with a number");
		    	else	
		    		controller.displayPopUp(this.controller.biggestAlbum(Integer.parseInt(d.getText()) , "all"));
			});
		    
		    grid.add(sr, 4, 13);
		

		return grid;
	}


	
	public GridPane getViewGeneral(int index) throws Exception {
		
		GridPane grid = new GridPane();
		User user = controller.getUserInfo();
		prepareScene(grid);
		Text scenetitle = new Text("Updates");
		scenetitle.setStyle("-fx-text-fill: green; -fx-font-size: 40px;");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 32));
		grid.add(scenetitle, 1, 1, 10, 1);
		
		
		Text userName = new Text("Number of Latest Updates");
		grid.add(userName, 2, 3 , 4 , 1);			
		TextField num = new TextField();
		grid.add(num, 5, 3, 7, 1);	
		
		
		
		Label Name = new Label("All");
		grid.add(Name, 2, 4);		
		Label birth = new Label("Photos");
		grid.add(birth, 3, 4);	
		Label date = new Label("Links");
		grid.add(date, 4, 4);		
		Label email = new Label("Events");
		grid.add(email, 5, 4);	
		Label mail = new Label("Videos");
		grid.add(mail, 6, 4);	
		Label vd = new Label("Albums");
		grid.add(vd, 7, 4);
		
		
		Button b1 = new Button("find");
		b1.setOnAction(event->{
			
			if(num.getText().isEmpty())
				controller.displayPopUp("Please give a number in the field above");
			else
				this.controller.getUpdates("All" , Integer.parseInt(num.getText()));
		});
		grid.add(b1, 2, 5);
		
		Button b4 = new Button("find");
		b4.setOnAction(event->{
			if(num.getText().isEmpty())
				controller.displayPopUp("Please give a number in the field above");
			else
				this.controller.getUpdates("Photos" , Integer.parseInt(num.getText()));
		});
		grid.add(b4, 3, 5);
		
		
		Button b2 = new Button("find");
		b2.setOnAction(event->{
			
			if(num.getText().isEmpty())
				controller.displayPopUp("Please give a number in the field above");
			else
				this.controller.getUpdates("Links" , Integer.parseInt(num.getText()));
		});
		grid.add(b2, 4, 5);
		
		
		Button b3 = new Button("find");
		b3.setOnAction(event->{
			if(num.getText().isEmpty())
				controller.displayPopUp("Please give a number in the field above");
			else
				this.controller.getUpdates("Events" , Integer.parseInt(num.getText()));
		});
		grid.add(b3, 5, 5);
		
		
		Button b5 = new Button("find");
		b5.setOnAction(event->{
			if(num.getText().isEmpty())
				controller.displayPopUp("Please give a number in the field above");
			else
				this.controller.getUpdates("Videos" , Integer.parseInt(num.getText()));
		});
		grid.add(b5, 6, 5);
		
		
		Button b6 = new Button("find");
		b6.setOnAction(event->{
			if(num.getText().isEmpty())
				controller.displayPopUp("Please give a number in the field above");
			else
				this.controller.getUpdates("Albums" , Integer.parseInt(num.getText()));
		});
		grid.add(b6, 7, 5);
		
		
		// least famous events
		
		Text sc = new Text("Events");
		sc.setFont(Font.font("Tahoma", FontWeight.NORMAL, 32));
		grid.add(sc, 2, 7, 10, 1);
		
		
		Text f = new Text("Find least Popular Events");
		grid.add(f, 2, 8 , 4 , 1);	
		
		Button b7 = new Button("Show");
		b7.setOnAction(event->{
			
			controller.displayPopUp(this.controller.getLeastPopular());
		});
		grid.add(b7, 5, 8);
		
		// average age of network

		Text sc1 = new Text("My Network");
		sc1.setFont(Font.font("Tahoma", FontWeight.NORMAL, 32));
		grid.add(sc1, 2, 10, 10, 1);
		
		
		Text f1 = new Text("Find Average age of Network");
		grid.add(f1, 2, 11, 4 , 1);	
		
		Button b8 = new Button("Show");
		b8.setOnAction(event->{
			
			controller.displayPopUp("The Average age of your Network is:  " + this.controller.getAverageAge());
		});
		grid.add(b8, 5, 11);

        return grid;
		
	}

	public GridPane getViewProfile(int index) throws Exception {
		
		GridPane grid = new GridPane();
		User user = controller.getUserInfo();
		prepareScene(grid);
		Text scenetitle = new Text(user.name);
		scenetitle.setStyle("-fx-text-fill: green; -fx-font-size: 40px;");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		grid.add(scenetitle, 1, 1, 10, 1);
		Image image = new Image(new FileInputStream("recourses/" + user.pPhoto));  
	    ImageView imageView = new ImageView(image); 
	    imageView.setX(25); 
	    imageView.setY(25); 
	    imageView.setFitHeight(300); 
	    imageView.setFitWidth(350); 
	    imageView.setPreserveRatio(true);  	      
	    grid.add(imageView, 2, 3);
				
		Label subtitle = new Label("User Information");
		subtitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 28));
		grid.add(subtitle, 2, 4);	
		Label userName = new Label("User Name:");
		grid.add(userName, 2, 5);	
		Label Name = new Label(user.username);
		grid.add(Name, 3, 5);		
		Label birth = new Label("BirthDate:");
		grid.add(birth, 2, 6);	
		Label date = new Label(user.birthdate.toString());
		grid.add(date, 3, 6);		
		Label email = new Label("Email:");
		grid.add(email, 2, 7);	
		Label mail = new Label(user.email);
		grid.add(mail, 3, 7);		
		Label lives = new Label("Lives:");
		grid.add(lives, 2, 8);		
		Label location = new Label(user.locationStr);
		grid.add(location, 3, 8);
		Label born = new Label("Born at:");
		grid.add(born, 2, 9);	
		Label hometown = new Label(user.hometownStr);
		grid.add(hometown, 3, 9);		
		Label work = new Label("Works:");
		grid.add(work, 2, 10);	
		Label works = new Label(user.work);
		grid.add(works, 3, 10);

		
		
		Button edit = new Button("Edit User Information");
		edit.setOnAction(event-> {
			try {
				this.controller.showEditInfoView();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} );
		
		grid.add(edit, 2, 12);
		
        return grid;
		
	}
	
	

	public GridPane getEditInfoView() throws Exception {
		
		GridPane grid = new GridPane();
		prepareScene(grid);
		Text scenetitle = new Text("Edit Perosnal Information");
		scenetitle.setStyle("-fx-text-fill: green; -fx-font-size: 40px;");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		grid.add(scenetitle, 1, 1, 10, 1);
		
		User user = controller.getUserInfo();
		
		Button button = new Button();
		button.setText("Submit Changes");
		
		Label label1 = new Label("Username:");
		grid.add(label1, 2, 3); // i am starting from 2,3
		TextField textlabel1 = new TextField();
		textlabel1.setText(user.username);
		grid.add(textlabel1, 3, 3);
		
		Label pw = new Label("Password:");
		grid.add(pw, 2, 4);
		PasswordField pwBox = new PasswordField();
		grid.add(pwBox, 3, 4);
		
		Label name = new Label("Name :");
		grid.add(name, 2, 5);
		TextField textname = new TextField();
		textname.setText(user.name);
		grid.add(textname, 3, 5);
		
		Label bir = new Label("Birthday");
		grid.add(bir, 2, 6);
		TextField b = new TextField();
		b.setText(user.birthdate.toString());
		grid.add(b, 3, 6);
		
		Label comptel = new Label("Email:");
		grid.add(comptel, 2, 7);		
		TextField email = new TextField();
		email.setText(user.email);;
		grid.add(email, 3, 7);	
		
		
		Label comp = new Label("Work");
		grid.add(comp, 2, 8);
		final ComboBox w = new ComboBox();
	       w.getItems().addAll(
	        		this.controller.work );       
	        w.getSelectionModel().select(0);
	    	grid.add(w, 3, 8);
		
				
		Label edu = new Label("Education");
		grid.add(edu, 2, 9);	
		final ComboBox e = new ComboBox();
       e.getItems().addAll(
        		this.controller.education );       
        e.getSelectionModel().select(0);
    	grid.add(e, 3, 9);
		
		
		
		Label website = new Label("Website");
		grid.add(website, 2, 10);
		TextField web = new TextField();
		web.setText(user.website);
		grid.add(web, 3, 10);
		
		
		Label venue = new Label("Location");
		grid.add(venue, 2, 11);	
		final ComboBox v = new ComboBox();
        v.getItems().addAll(
        		this.controller.cy );       
        v.getSelectionModel().select(user.location);
    	grid.add(v, 3, 11);
    	
		Label home = new Label("Hometown");
		grid.add(home, 2, 12);	
		final ComboBox f = new ComboBox();
        f.getItems().addAll(
        		this.controller.cy );   
        f.getSelectionModel().select(user.hometown);
    	grid.add(f, 3, 12);
		
		
		button.setOnAction(event->{
			this.controller.personalInfo(textlabel1.getText(),pwBox.getText(),textname.getText(),b.getText(),email.getText(),
					w.getValue().toString(), e.getValue().toString(), web.getText(), v.getValue().toString() , f.getValue().toString() );
		});
		grid.add(button, 2, 14);

		return grid;

	}

	
	public GridPane getViewFriends(int index) throws Exception {
		
		GridPane grid = new GridPane();
		prepareScene(grid);
		Text scenetitle = new Text("Friends List");
		scenetitle.setStyle("-fx-text-fill: green; -fx-font-size: 40px;");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		grid.add(scenetitle, 1, 1, 10, 1);
				
		
        ObservableList<Friend> tickets = this.createTableContent();//FXCollections.observableArrayList(list);
		
		TableView<Friend> table = new TableView();
		table.setEditable(true);
	        final Label label = new Label("List Preview");	        
	        table.setEditable(true);	        
	        TableColumn username = new TableColumn("Username");
	        TableColumn birthdate = new TableColumn("Birthdate");
	        TableColumn email = new TableColumn("Email");
	        TableColumn work = new TableColumn("Work");
	        TableColumn website = new TableColumn("Website");
	    	TableColumn view = new TableColumn("View Profile");
			TableColumn delete = new TableColumn("Delete Friend");
	       	        
	        username.setCellValueFactory(new PropertyValueFactory<>("name"));
	        birthdate.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
	        email.setCellValueFactory(new PropertyValueFactory<>("email"));
	        work.setCellValueFactory(new PropertyValueFactory<>("work"));
	        website.setCellValueFactory(new PropertyValueFactory<>("website"));
 
	        
	    	Callback<TableColumn<Friend, String>, TableCell<Friend, String>> cellFactory = //
					new Callback<TableColumn<Friend, String>, TableCell<Friend, String>>() {
						@Override

						public TableCell call(final TableColumn<Friend, String> param) {
							final TableCell<Friend, String> cell = new TableCell<Friend, String>() {

								final Button btnHandleTicket = new Button("View Profile");
								// final Button btnEditTicket = new Button("Edit ticket");
								Friend btnsol;
								long startTime;

								public void updateItem(String item, boolean empty) {
									super.updateItem(item, empty);
									if (empty) {
										setGraphic(null);
										setText(null);
									} else {
										btnHandleTicket.setOnAction(event -> {
											
											btnsol = getTableView().getItems().get(getIndex());
											try {
												controller.showUserProfile(btnsol);
											} catch (Exception e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}	
											
										});

										setGraphic(btnHandleTicket);
										setText(null);
									}

								}

							};
							return cell;

						}

					};

			Button refresh = new Button("Refresh");
			grid.add(refresh, 2, 4);
			refresh.setOnAction(eventR -> {
				this.controller.view.refresh(table);
				// table.setItems(this.controller.view.createTableContent());
				// table.refresh();
			});

			Callback<TableColumn<Friend, String>, TableCell<Friend, String>> cellFactoryEdit = //
					new Callback<TableColumn<Friend, String>, TableCell<Friend, String>>() {
						@Override

						public TableCell call(final TableColumn<Friend, String> param) {
							final TableCell<Friend, String> cell = new TableCell<Friend, String>() {

								final Button btnEditTicket = new Button("Delete Friend");

								public void updateItem(String item, boolean empty) {
									super.updateItem(item, empty);
									if (empty) {
										setGraphic(null);
										setText(null);
									} else {
										btnEditTicket.setOnAction(editEvent -> {
											Friend btn = getTableView().getItems().get(getIndex());
											controller.deleteFriend(btn);
										
											controller.displayPopUp("Friend Deleted!");
											controller.view.refresh(table);
											controller.view.refresh(table);
											// table.refresh();
										});
										setGraphic(btnEditTicket);
										setText(null);
									}

								}

							};

							return cell;

						}
					};
			view.setCellFactory(cellFactory);
			delete.setCellFactory(cellFactoryEdit);
			// puts items in table
			table.setItems(tickets);

	        table.getColumns().addAll(username, birthdate, email, work, website, view,delete);
 
	        grid.add(label, 2, 3);	        
	        grid.add(table, 2, 5, 8, 8);	   
	        
	        
			Text sub = new Text("Search For People");
			sub.setFont(Font.font("Tahoma", FontWeight.NORMAL, 25));
			grid.add(sub, 2, 13);			
			Button s = new Button("Search");
					
			Label byname = new Label("Simple Search :");
			grid.add(byname, 2, 14);
			Label n = new Label("Name:");
			grid.add(n, 3, 14);
			TextField name = new TextField();
			grid.add(name, 4, 14);	
			
			Label b = new Label("Birthday");
			grid.add(b, 3, 15);
			TextField birth = new TextField();
			grid.add(birth, 4, 15);	
			
			Label l = new Label("Location");
			grid.add(l, 3, 16);

			final ComboBox loc = new ComboBox();
	        loc.getItems().addAll(
	        		this.controller.cy );       
	       // loc.getSelectionModel().select(0);
	    	grid.add(loc, 4, 16);
			
    	
			s.setOnAction(event->{			

				String s1 = "";
				String s2 = "";
				String s3 = "";
				
				
				if(!name.getText().isEmpty())
				s1 = name.getText();
				
				if(!birth.getText().isEmpty())
				s2 = birth.getText();
				
				if(!loc.getSelectionModel().isEmpty())
				s3 = loc.getValue().toString();
				
				this.controller.simpleUserSearch(s1,s2,s3);
			});

	    	grid.add(s, 5, 15);
	    	
	    	
	    	//ADV SEARCH
	    	Label adv = new Label("Advance Search :");
			grid.add(adv, 2, 19);
			
			Label byedu = new Label("Seach by Education:");
			grid.add(byedu, 3, 19);

			TextField e = new TextField();
		    grid.add(e, 4, 19);
			
		    Button se = new Button("Search");
		    
		    
		    se.setOnAction(event->{			
				
		    	if(e.getText().isEmpty())
		    		controller.displayPopUp("Please fille the text box with universities");
		    	else	
		    		this.controller.advanceUserSearch("education" , e.getText());
			});

		    
		    
		    grid.add(se, 5, 19);
			
			Label wk = new Label("Seach by Work:");
			grid.add(wk, 3, 20);
			TextField w = new TextField();
		    	grid.add(w, 4, 20);			
			
			    Button ser = new Button("Search");
			    
			    ser.setOnAction(event->{			
					
			    	if(w.getText().isEmpty())
			    		controller.displayPopUp("Please fille the text box with works");
			    	else	
			    		this.controller.advanceUserSearch("work" , w.getText());
				});

			    
			    grid.add(ser, 5, 20);
			    
				Label we = new Label("Seach by Album size:");
				grid.add(we, 3, 21);
				TextField d = new TextField();
			    grid.add(d, 4, 21);			
				
				    Button sr = new Button("Search");
				    
				    sr.setOnAction(event->{			
						
				    	if(d.getText().isEmpty())
				    		controller.displayPopUp("Please fille the text box with a number");
				    	else	
				    		this.controller.biggestAlbum(Integer.parseInt(d.getText()), "friends");
					});
				    
				    grid.add(sr, 5, 21);
			    
			    
				Label ax = new Label("Find my Friends with the same Interests");
				grid.add(ax, 2, 22);
			
				Button sc = new Button("Search");
				    
				    sc.setOnAction(event->{			
						
				    		controller.displayPopUp(this.controller.sameInterestsSearch());
					});
			    
			    
			    
			    grid.add(sc, 4, 22);
			    
			    
			    
				Label as = new Label("Find my most popular Friend");
				grid.add(as, 2, 23);
			
				Button sd = new Button("Search");
				    
				    sd.setOnAction(event->{			
						
				    		controller.displayPopUp(this.controller.mostPopularFriend());
					});
			    
			    
			    
			    grid.add(sd, 4, 23);
			    
			    
			    
				Label aq = new Label("Find my Friends with the same Friends");
				grid.add(aq, 2, 24);
			
				Button sq = new Button("Search");
				    
				    sq.setOnAction(event->{			
						
				    		controller.displayPopUp(this.controller.sameFriends());
					});
			    
			    
			    
			    grid.add(sq, 4, 24);
		
	        
	       	      	
		return grid;
	}
	
	
	
	public ObservableList<Friend> createTableContent() {
		
		List<Friend> list  = controller.getFriendList();
		ObservableList<Friend> tobeshownupdate = FXCollections.observableArrayList(list);
		return tobeshownupdate;
	}
	
		
	private void refresh(TableView<Friend> table) {
		table.setItems(this.controller.view.createTableContent());
		table.refresh();
	}
	
	
	
	public GridPane getIgnoredFriendReq() throws FileNotFoundException {
		
		GridPane grid = new GridPane();
		
		Text scenetitle = new Text("Ignored Friend Requests");
		scenetitle.setStyle("-fx-text-fill: green; -fx-font-size: 40px;");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		grid.add(scenetitle, 1, 1, 10, 1);		
		prepareScene(grid);
		
		
		List<User> users = this.controller.getIgnoredFriendRequests();

		grid.addColumn(1, new Label("No."));
		grid.addColumn(2, new Label("Name"));
		grid.addColumn(3, new Label("Username"));
		grid.addColumn(4, new Label("Hometown"));
					
		int initXlevel = 1;
		int initYlevel = 3;
		for (int user_index = 0; user_index < users.size(); user_index++) {
			User user = users.get(user_index);
			grid.add(new Label(Integer.toString(user_index + 1)), initXlevel, user_index + initYlevel); // adding 2

			grid.add(new Label(user.name), initXlevel + 1, user_index + initYlevel);
			grid.add(new Label(user.username), initXlevel + 2, user_index + initYlevel);
			grid.add(new Label(user.hometown.toString()), initXlevel + 3, user_index + initYlevel);
			
			Button showButton = new Button("Accept Request");
			showButton.setOnAction(event -> {
				this.controller.addFriend(user.userID);
				});
			grid.add(showButton,  + initXlevel + 4, user_index + initYlevel);
																					

			
			Button removeAdminCap = new Button("Reject Request");
			removeAdminCap.setOnAction(event -> this.controller.rejectFriendReq(user.userID));
			grid.add(removeAdminCap,  initXlevel + 5, user_index + initYlevel); 

			
			}
		
		
		
		Button view = new Button("Go Back");
		view.setOnAction(event -> this.startView());
		grid.add(view, 1, users.size() + 3, 10 , 1); 
		
		
		
		
		return grid;
		
		
		
		
		
		
	}
	
	
	
	
	public GridPane getFriendReqView(int tabIndex) throws FileNotFoundException {

		GridPane grid = new GridPane();
		
		Text scenetitle = new Text("Friend Requests");
		scenetitle.setStyle("-fx-text-fill: green; -fx-font-size: 40px;");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		grid.add(scenetitle, 1, 1, 10, 1);		
		prepareScene(grid);
		List<User> users = this.controller.getFriendRequests();

		grid.addColumn(1, new Label("No."));
		grid.addColumn(2, new Label("Name"));
		grid.addColumn(3, new Label("Username"));
		grid.addColumn(4, new Label("Hometown"));
					
		int initXlevel = 1;
		int initYlevel = 3;
		for (int user_index = 0; user_index < users.size(); user_index++) {
			User user = users.get(user_index);
			grid.add(new Label(Integer.toString(user_index + 1)), initXlevel, user_index + initYlevel); // adding 2

			grid.add(new Label(user.name), initXlevel + 1, user_index + initYlevel);
			grid.add(new Label(user.username), initXlevel + 2, user_index + initYlevel);
			grid.add(new Label(user.hometownStr), initXlevel + 3, user_index + initYlevel);
			
			Button showButton = new Button("Accept Request");
			showButton.setOnAction(event -> {
				this.controller.addFriend(user.userID);
				});
			grid.add(showButton,  + initXlevel + 4, user_index + initYlevel);
																					

			
			Button removeAdminCap = new Button("Reject Request");
			removeAdminCap.setOnAction(event -> this.controller.rejectFriendReq( user.userID));
			grid.add(removeAdminCap,  initXlevel + 5, user_index + initYlevel); 

																							
			
			Button fireUserButton = new Button("Ignore");
			fireUserButton.setOnAction(event -> this.controller.ignoreFriendReq(tabIndex, user.userID));
			grid.add(fireUserButton, 6 + initXlevel, user_index + initYlevel); 
			
			}
		
		
		
		Button view = new Button("View Ignored Requests");
		view.setOnAction(event -> {
			try {
				this.controller.showIgnoredView();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		grid.add(view, 1, users.size() + 4, 10 , 1); 
		
		
		
		
		return grid;
	}
	
	
	
	public GridPane getEditEventView(Event e) throws Exception {
		
		GridPane grid = new GridPane();
		prepareScene(grid);
		Text scenetitle = new Text("Edit Event Information");
		scenetitle.setStyle("-fx-text-fill: green; -fx-font-size: 40px;");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		grid.add(scenetitle, 1, 1, 10, 1);
		
		Button button = new Button();
		button.setText("Submit Changes");
		Label label1 = new Label("Name:");
		grid.add(label1, 2, 3); // i am starting from 2,3
		TextField textlabel1 = new TextField();
		
		textlabel1.setText(e.name);
		
		grid.add(textlabel1, 3, 3);
		Label pw = new Label("Description");
		grid.add(pw, 2, 4);
		TextField pwBox = new TextField();
		pwBox.setText(e.description);
		grid.add(pwBox, 3, 4);
		Label name = new Label("Start Time");
		grid.add(name, 2, 5);
		TextField textname = new TextField();
		textname.setText(e.start_time.toString());;
		grid.add(textname, 3, 5);
		Label surname = new Label("Finish Time:");
		grid.add(surname, 2, 6);
		TextField textsurname = new TextField();
		grid.add(textsurname, 3, 6);
		textsurname.setText(e.finish_time.toString());
		Label comptel = new Label("Location");
		grid.add(comptel, 2, 7);		
		TextField textcomptel = new TextField();
		textcomptel.setText(e.location);
		grid.add(textcomptel, 3, 7);
		

		Label location = new Label("Privacy");
		grid.add(location, 2, 8);		
		
		final ComboBox p = new ComboBox();
        p.getItems().addAll(
        		"OPEN" , "CLOSE" , "FRIENDS", "NETWORK" );   
		
        p.getSelectionModel().select(e.p);
      
    	grid.add(p, 3, 8);	
		
		
		
		Label venue = new Label("Venue");
		grid.add(venue, 2, 9);	
		// TODO TO THE THINK WITH THE TEXT FILE
		final ComboBox v = new ComboBox();
        v.getItems().addAll(
        		this.controller.cy );   
		
        
        v.getSelectionModel().select(e.venue);
    	grid.add(v, 3, 9);	
		
		button.setOnAction(event->{			
			String pr = p.getValue().toString();
			String ven = v.getValue().toString();
			this.controller.EventInfo(textlabel1.getText(),pwBox.getText(),textname.getText(),textsurname.getText(),textcomptel.getText(),pr,ven);
		});
		grid.add(button, 2, 13);

		return grid;

	}
	
	
	
	public GridPane getEvent(Event user) throws FileNotFoundException {

		GridPane grid = new GridPane();
		
		Text scenetitle = new Text("Event Found");
		scenetitle.setStyle("-fx-text-fill: green; -fx-font-size: 40px;");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		grid.add(scenetitle, 1, 1, 10, 1);		
		prepareScene(grid);
		
		//List<Event> users = this.controller.getEvents();

		//grid.addColumn(1, new Label("No."));
		grid.addColumn(2, new Label("Name"));
		grid.addColumn(3, new Label("Start Time"));
		grid.addColumn(4, new Label("Finish Time"));
		grid.addColumn(5, new Label("Location"));
		grid.addColumn(6, new Label("Venue"));
		grid.addColumn(7, new Label("Privacy"));
		grid.addColumn(8, new Label("Description"));
		

		
					
		int initXlevel = 1;
		int initYlevel = 3;
		//for (int user_index = 0; user_index < users.size(); user_index++) {
		//	Event user = users.get(user_index);
		//	grid.add(new Label(Integer.toString(user_index + 1)), initXlevel, user_index + initYlevel); // adding 2

			grid.add(new Label(user.name), initXlevel + 1, initYlevel);
			grid.add(new Label(user.start_time.toString()), initXlevel + 2, initYlevel);
			grid.add(new Label(user.finish_time.toString()), initXlevel + 3, initYlevel);
			grid.add(new Label(user.location), initXlevel + 4,  initYlevel);
			grid.add(new Label(user.venue2), initXlevel + 5, initYlevel);
			grid.add(new Label(user.p.toString()), initXlevel + 6, initYlevel);
			grid.add(new Label(user.description), initXlevel + 7, initYlevel);
			
			
			

			Button button = new Button();
			button.setText("Go back");

			button.setOnAction(event->{
				this.startView();
			});
			grid.add(button, 2, initXlevel + 7);
			
			
			Button b1 = new Button();
			b1.setText("Going");

			b1.setOnAction(event->{
				
				controller.goingToEvent(user);
				controller.displayPopUp("You are succesfully added to the going list of this event!!");
			});
			
			grid.add(b1, 3, initXlevel + 7);
			
	
		
	
		return grid;
	}
	
	
	public GridPane getViewEvent(int tabIndex) throws FileNotFoundException {

		GridPane grid = new GridPane();
		
		Text scenetitle = new Text("My Events");
		scenetitle.setStyle("-fx-text-fill: green; -fx-font-size: 40px;");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		grid.add(scenetitle, 1, 1, 10, 1);		
		prepareScene(grid);
		
		List<Event> users = this.controller.getEvents();

		grid.addColumn(1, new Label("No."));
		grid.addColumn(2, new Label("Name"));
		grid.addColumn(3, new Label("Start Time"));
		grid.addColumn(4, new Label("Finish Time"));
		grid.addColumn(5, new Label("Location"));
		grid.addColumn(6, new Label("Venue"));
		grid.addColumn(7, new Label("Privacy"));
		grid.addColumn(8, new Label("Description"));
		

		
					
		int initXlevel = 1;
		int initYlevel = 3;
		
		int y = 7;
		if(users!=null) {
		for (int user_index = 0; user_index < users.size(); user_index++) {
			Event user = users.get(user_index);
			grid.add(new Label(Integer.toString(user_index + 1)), initXlevel, user_index + initYlevel); // adding 2

			grid.add(new Label(user.name), initXlevel + 1, user_index + initYlevel);
			grid.add(new Label(user.start_time.toString()), initXlevel + 2, user_index + initYlevel);
			grid.add(new Label(user.finish_time.toString()), initXlevel + 3, user_index + initYlevel);
			grid.add(new Label(user.location), initXlevel + 4, user_index + initYlevel);
			grid.add(new Label(user.venue2), initXlevel + 5, user_index + initYlevel);
			grid.add(new Label(user.p.toString()), initXlevel + 6, user_index + initYlevel);
			grid.add(new Label(user.description), initXlevel + 7, user_index + initYlevel);
			
			Button showButton = new Button("Edit Event Information");
			showButton.setOnAction(event -> {
				try {
					this.controller.showEditEventView(user);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				});
			grid.add(showButton,  + initXlevel + 8, user_index + initYlevel);
		}
		
		
		 y = users.size() + 7;
		
	}
		
		Label comptel = new Label("Search For an Event");
		grid.add(comptel, 1, y);		
		TextField textcomptel = new TextField();
		grid.add(textcomptel, 2, y);
		
		Button button = new Button("Search");
		button.setOnAction(event->{			
	    	if(textcomptel.getText().isEmpty())
	    		controller.displayPopUp("Please fille the text box with a name");
			else
				try {
					controller.displayPopUp(this.controller.searchEvent(textcomptel.getText(), "mine"));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
		});
		
		grid.add(button, 3, y);
		
		
		
		return grid;
	}
	
	
	
	public GridPane getEditPhotoView(Photo f) throws Exception {
		
		GridPane grid = new GridPane();
		prepareScene(grid);
		Text scenetitle = new Text("Edit Photo Information");
		scenetitle.setStyle("-fx-text-fill: green; -fx-font-size: 40px;");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		grid.add(scenetitle, 1, 1, 10, 1);
		
		Button button = new Button();
		button.setText("Submit Changes");
		
		Label label1 = new Label("Name :");
		grid.add(label1, 2, 3); // i am starting from 2,3
		TextField textlabel1 = new TextField();
		textlabel1.setText(f.name);
		grid.add(textlabel1, 3, 3);
		
		Label pw = new Label("Privacy");
		grid.add(pw, 2, 4);
		final ComboBox p = new ComboBox();
        p.getItems().addAll(
        		"OPEN" , "CLOSE" , "FRIENDS", "NETWORK" );   
		
        p.getSelectionModel().select(0);
      
		grid.add(p, 3, 4);
			

		
		button.setOnAction(event->{
			this.controller.photoInfo(textlabel1.getText(),p.getValue().toString());
		});
		grid.add(button, 2, 6);

		return grid;

	}
	

	
	public GridPane getPhoto(Photo ph) throws Exception {
		
		GridPane grid = new GridPane();
		prepareScene(grid);
		Text scenetitle = new Text("Photo Found");
		scenetitle.setStyle("-fx-text-fill: green; -fx-font-size: 40px;");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		grid.add(scenetitle, 1, 1, 10, 1);	
		
	
			
			Image image = new Image(new FileInputStream(ph.link));
			ImageView imageView = new ImageView(image);
			imageView.setX(0);
			imageView.setY(0);

			imageView.setFitHeight(150);
			imageView.setFitWidth(200);
			imageView.setPreserveRatio(true);
			grid.add(imageView, 2 , 3);
						
			String com = "Title :"+ ph.name + " | size : " + ph.width + " x " +  ph.height + " | likes:" + ph.numOflikes; 
			final Label label = new Label(com);	  
			grid.add(label, 2 , 4);
			

			Button button = new Button();
			button.setText("Go back");

			button.setOnAction(event->{
				this.startView();
			});
			grid.add(button, 2, 6);
			
			return grid;
		}
	
	

	public GridPane getViewPhoto(int index) throws Exception {
		
		GridPane grid = new GridPane();
		prepareScene(grid);
		Text scenetitle = new Text("My Photos");
		scenetitle.setStyle("-fx-text-fill: green; -fx-font-size: 40px;");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		grid.add(scenetitle, 1, 1, 10, 1);	
		
		
		
		Label comptel = new Label("Search For a Photo : ");
		grid.add(comptel, 2, 3);		
		TextField textcomptel = new TextField();
		grid.add(textcomptel, 3, 3);
		
		Button button = new Button("Search");
		button.setOnAction(event->{			
	    	if(textcomptel.getText().isEmpty())
	    		controller.displayPopUp("Please fille the text box with a name");
			else
				try {
					this.controller.searchPhoto(textcomptel.getText(), "mine");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		});
		
		grid.add(button, 4, 3);
		
		int x = 2;
		int y = 6;
		
		List <Photo> ph = this.controller.getPhotos();
		
		if(ph!=null) {
		for (int i = 0; i < ph.size(); i++) {
			
			System.out.println(ph.get(i).link);
			Image image = new Image(new FileInputStream(ph.get(i).link));
			ImageView imageView = new ImageView(image);
			imageView.setX(0);
			imageView.setY(0);

			imageView.setFitHeight(150);
			imageView.setFitWidth(200);
			imageView.setPreserveRatio(true);
			grid.add(imageView, x , y + i);
						
			String com = "Title :"+ ph.get(i).name + " | size : " + ph.get(i).width + " x " +  ph.get(i).height + " | likes:" + ph.get(i).numOflikes; 
			final Label label = new Label(com);	  
			grid.add(label, x+1 , y + i);
			
			Button edit = new Button("Edit Information");
			grid.add(edit, x+2, y+i);
			Photo p = ph.get(i);
	
			edit.setOnAction(event -> {
				try {
					this.controller.showEditPhotoView(p);
				} catch (Exception e) {
					e.printStackTrace();
				}
				});
			
			
		}
	
		}
		
		
	
		return grid;
	}
	
	
	public GridPane getEditLinkView(Link l) throws Exception {
		
		GridPane grid = new GridPane();
		prepareScene(grid);
		Text scenetitle = new Text("Edit Event Information");
		scenetitle.setStyle("-fx-text-fill: green; -fx-font-size: 40px;");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		grid.add(scenetitle, 1, 1, 10, 1);
		
		Button button = new Button();
		button.setText("Submit Changes");
		Label label1 = new Label("Name:");
		grid.add(label1, 2, 3); // i am starting from 2,3
		TextField textlabel1 = new TextField();
		textlabel1.setText(l.name);
		grid.add(textlabel1, 3, 3);
		Label pw = new Label("Description:");
		grid.add(pw, 2, 4);
		TextField pwBox = new TextField();
		grid.add(pwBox, 3, 4);
		pwBox.setText(l.description);
		Label name = new Label("Link:");
		grid.add(name, 2, 5);
		TextField textname = new TextField();
		grid.add(textname, 3, 5);
		textname.setText(l.link);
		Label surname = new Label("Caption:");
		grid.add(surname, 2, 6);
		TextField textsurname = new TextField();
		grid.add(textsurname, 3, 6);
		textsurname.setText(l.caption);
		Label comptel = new Label("Message:");
		grid.add(comptel, 2, 7);		
		TextField textcomptel = new TextField();
		grid.add(textcomptel, 3, 7);	
		textcomptel.setText(l.message);

		
		button.setOnAction(event->{			
			this.controller.linkInfo(textlabel1.getText(),pwBox.getText(),textname.getText(),textsurname.getText(),textcomptel.getText());
		});
		grid.add(button, 2, 13);

		
		
		
		
		
		
		return grid;

	}
	
	
	public GridPane getViewLink(int tabIndex) throws FileNotFoundException {

		GridPane grid = new GridPane();
		
		Text scenetitle = new Text("My Links");
		scenetitle.setStyle("-fx-text-fill: green; -fx-font-size: 40px;");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		grid.add(scenetitle, 1, 1, 10, 1);		
		prepareScene(grid);
		
		List<Link> link = this.controller.getLink();

		grid.addColumn(1, new Label("No."));
		grid.addColumn(2, new Label("Name"));
		grid.addColumn(3, new Label("Description"));
		grid.addColumn(4, new Label("Link"));
		grid.addColumn(5, new Label("Caption"));
		grid.addColumn(6, new Label("Message"));
					
		int initXlevel = 1;
		int initYlevel = 3;
		int y = 6;
		
		if(link != null) {
		for (int user_index = 0; user_index < link.size(); user_index++) {
			Link l = link.get(user_index);
			grid.add(new Label(Integer.toString(user_index + 1)), initXlevel, user_index + initYlevel); // adding 2

			grid.add(new Label(l.name), initXlevel + 1, user_index + initYlevel);
			grid.add(new Label(l.description), initXlevel + 2, user_index + initYlevel);
			grid.add(new Label(l.link), initXlevel + 3, user_index + initYlevel);
			grid.add(new Label(l.caption), initXlevel + 4, user_index + initYlevel);
			grid.add(new Label(l.message), initXlevel + 5, user_index + initYlevel);
			
			Button showButton = new Button("Edit Link Information");
			showButton.setOnAction(event -> {
				try {
					this.controller.showEditLinkView(l);
				} catch (Exception e) {
					e.printStackTrace();
				}
				});
			grid.add(showButton,  + initXlevel + 7, user_index + initYlevel);
		}
		
		
		y += link.size();
		
		
		}
		
		Label comptel = new Label("Search For a Link");
		grid.add(comptel, 1, y);		
		TextField textcomptel = new TextField();
		grid.add(textcomptel, 2, y);
		
		Button button = new Button("Search");
		button.setOnAction(event->{			
	    	if(textcomptel.getText().isEmpty())
	    		controller.displayPopUp("Please fille the text box with a name");
	    	else	
	    		controller.displayPopUp(this.controller.searchLink(textcomptel.getText(), "mine"));
		});
		
		grid.add(button, 3, y);
		
		return grid;
	}
	
	
	
	
public GridPane getEditAlbumView(Album a) throws Exception {
		
		GridPane grid = new GridPane();
		prepareScene(grid);
		Text scenetitle = new Text("Edit Album Information");
		scenetitle.setStyle("-fx-text-fill: green; -fx-font-size: 40px;");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		grid.add(scenetitle, 1, 1, 10, 1);
		
		Button button = new Button();
		button.setText("Submit Changes");
		
		Label label1 = new Label("Name:");
		grid.add(label1, 2, 3); // i am starting from 2,3
		TextField textlabel1 = new TextField();
		grid.add(textlabel1, 3, 3);
		textlabel1.setText(a.name);
		
		
		Label pw = new Label("Description :");
		grid.add(pw, 2, 4);
		TextField pwBox = new TextField();
		grid.add(pwBox, 3, 4);
		pwBox.setText(a.description);
		
		Label name = new Label("Location :");
		grid.add(name, 2, 5);
		
		final ComboBox v = new ComboBox();
        v.getItems().addAll(
        		this.controller.cy );       
        v.getSelectionModel().select(a.city);
		
        grid.add(v, 3, 5);
		
		Label surname = new Label("Privacy :");
		grid.add(surname, 2, 6);
	
		final ComboBox p = new ComboBox();
        p.getItems().addAll(
        		"OPEN" , "CLOSE" , "FRIENDS", "NETWORK" );   
		
        p.getSelectionModel().select(a.p);

        grid.add(p, 3, 6);
        
		
		button.setOnAction(event->{
			this.controller.albumInfo(textlabel1.getText(),pwBox.getText(),v.getValue().toString(),p.getValue().toString());
		});
		grid.add(button, 2, 8);

		return grid;

	}


public GridPane getAlbum(Album ph) throws Exception {
	
	GridPane grid = new GridPane();
	prepareScene(grid);
	Text scenetitle = new Text("Album Found");
	scenetitle.setStyle("-fx-text-fill: green; -fx-font-size: 40px;");
	scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
	grid.add(scenetitle, 1, 1, 10, 1);	
	
	int x =2 ;
	int y = 3;
	
		Label subtitle = new Label( ph.name);			
		subtitle.setStyle("-fx-text-fill: purple; -fx-font-size: 28px;");
		subtitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 28));			
		grid.add(subtitle, x, y);	
		
		
		Label a1 = new Label( "Description : " + ph.description);
		grid.add(a1, x, y+1);	
		Label a2 = new Label( "Location : " + ph.city);
		grid.add(a2, x, y+2);	
		
		String s = "";
		for (int j = 0; j < ph.comments.size(); j++) {
			
			s += ph.comments.get(j) + " | " ;
		}
		
		Label a3 = new Label( "Comments : " + s);
		grid.add(a3, x, y+3);	
		
	
		for (int j = 0; j < ph.photos.size(); j++) {
			
			Image image = new Image(new FileInputStream(ph.photos.get(j).link));
			ImageView imageView = new ImageView(image);
			imageView.setX(0);
			imageView.setY(0);

			imageView.setFitHeight(150);
			imageView.setFitWidth(200);
			imageView.setPreserveRatio(true);
			grid.add(imageView, x , y + j + 4);
						
			String com = "Title : " + ph.photos.get(j).name + " size: " + ph.photos.get(j).width + " x " +  ph.photos.get(j).height + " | likes:" + ph.photos.get(j).numOflikes; 
			final Label label = new Label(com);	  
			grid.add(label, x+1 , y + j + 4);
			
		
		}


		y += ph.photos.size() + 8;
		Button bt = new Button();
		bt.setText("Go back");

		bt.setOnAction(event->{
			this.startView();
		});
		grid.add(bt, 2, y);
	
	

	return grid;
}

	

	public GridPane getViewAlbum(int index) throws Exception {
		
		GridPane grid = new GridPane();
		prepareScene(grid);
		Text scenetitle = new Text("My Albums");
		scenetitle.setStyle("-fx-text-fill: green; -fx-font-size: 40px;");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		grid.add(scenetitle, 1, 1, 10, 1);	
		
		
		Label comptel = new Label("Search For an Album in your List : ");
		grid.add(comptel, 2, 3);		
		TextField textcomptel = new TextField();
		grid.add(textcomptel, 3, 3);
		
		Button b = new Button("Search");
		b.setOnAction(event->{			
	    	if(textcomptel.getText().isEmpty())
	    		controller.displayPopUp("Please fille the text box with a name");
			else
				try {
					this.controller.searchAlbum(textcomptel.getText(), "mine");
				} catch (Exception e) {
					e.printStackTrace();
				}
		});
		
		grid.add(b, 4, 3);
		
		int x = 2;
		int y = 7;
		
		List <Album> ph = this.controller.getAlbum();
		
		if(ph != null) {
		for (int i = 0; i < ph.size(); i++) {
		
			Label subtitle = new Label( ph.get(i).name);			
			subtitle.setStyle("-fx-text-fill: purple; -fx-font-size: 28px;");
			subtitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 28));			
			grid.add(subtitle, x, y+i);	
			
			Button button = new Button("Edit Album Info");
			
			Album a = ph.get(i);
			
			button.setOnAction(event->{
				try {
					this.controller.showEditAlbumView(a);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			grid.add(button, x+1, y+i);
			
			Label a1 = new Label( "Description : " + ph.get(i).description);
			grid.add(a1, x, y+i+1);	
			Label a2 = new Label( "Location : " + ph.get(i).city);
			grid.add(a2, x, y+i+2);	
			
			String s = "";
			for (int j = 0; j < ph.get(i).comments.size(); j++) {
				
				s += ph.get(i).comments.get(j) + " | " ;
			}
			
			Label a3 = new Label( "Comments : " + s);
			grid.add(a3, x, y+i+3);	
			
			if( ph.get(i).photos != null) {
			for (int j = 0; j < ph.get(i).photos.size(); j++) {
				
				Image image = new Image(new FileInputStream(ph.get(i).photos.get(j).link));
				ImageView imageView = new ImageView(image);
				imageView.setX(0);
				imageView.setY(0);

				imageView.setFitHeight(150);
				imageView.setFitWidth(200);
				imageView.setPreserveRatio(true);
				grid.add(imageView, x , y + j + 4 + i);
							
				String com = "Title : " + ph.get(i).photos.get(j).name + " size: " + ph.get(i).photos.get(j).width + " x " +  ph.get(i).photos.get(j).height + " | likes:" + ph.get(i).photos.get(j).likes.size(); 
				final Label label = new Label(com);	  
				grid.add(label, x+1 , y + j + 4 + i);
				
			
			}
			
			y =  y + ph.get(i).photos.size() + 5;
			
			}
			
			y = y + 5;
		}
		
		
		}
		
	
		return grid;
	}
	
	
	
	public GridPane getEditVideoView(Video vd) throws Exception {
		
		GridPane grid = new GridPane();
		prepareScene(grid);
		Text scenetitle = new Text("Edit Photo Information");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		grid.add(scenetitle, 1, 1, 10, 1);
		
		Button button = new Button();
		button.setText("Submit Changes");
		Label label1 = new Label("Message:");
		grid.add(label1, 2, 3); // i am starting from 2,3
		TextField textlabel1 = new TextField();
		
		textlabel1.setText(vd.message);
		
		grid.add(textlabel1, 3, 3);
		Label pw = new Label("Description:");
		grid.add(pw, 2, 4);
		TextField pwBox = new TextField();
		pwBox.setText(vd.description);
		
		grid.add(pwBox, 3, 4);

	

		
		button.setOnAction(event->{
			this.controller.videoInfo(textlabel1.getText(),pwBox.getText());
		});
		grid.add(button, 2, 5);

		return grid;

	}
	
	
	
	
	public GridPane getVideo(Video ph) throws Exception {
		
		GridPane grid = new GridPane();
		prepareScene(grid);
		Text scenetitle = new Text("Video Found");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		grid.add(scenetitle, 1, 1, 10, 1);	
	
		
		
		int x = 2;
		int y = 3;
	
			
			Media media = new Media(ph.link); 
			MediaPlayer mediaplayer = new MediaPlayer(media);
			mediaplayer.setOnEndOfMedia(new Runnable() {
			       public void run() {
			         mediaplayer.seek(Duration.ZERO);
			       }
			   });
			mediaplayer.setAutoPlay(true); 

		    MediaView mediaView = new MediaView (mediaplayer);
		    
		    mediaView.setFitWidth(250);
		    mediaView.setFitHeight(250);
		    
			grid.add(mediaView, x , y );
						
			String com = "Title: " + ph.message + " | " +  ph.description + " | " + ph.comments.size();
			final Label label = new Label(com);	  
			grid.add(label, x +1 , y);

			
			Button bt = new Button();
			bt.setText("Go back");

			bt.setOnAction(event->{
				this.startView();
			});
			grid.add(bt, 2, y+2);
		
			
		return grid;
	}
	
	
	
	
	public GridPane getViewVideo(int index) throws Exception {
		
		GridPane grid = new GridPane();
		prepareScene(grid);
		Text scenetitle = new Text("My Videos");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		grid.add(scenetitle, 1, 1, 10, 1);	
	
		Label comptel = new Label("Search For a Video in your List : ");
		grid.add(comptel, 2, 3);		
		TextField textcomptel = new TextField();
		grid.add(textcomptel, 3, 3);
		
		Button button = new Button("Search");
		button.setOnAction(event->{			
	    	if(textcomptel.getText().isEmpty())
	    		controller.displayPopUp("Please fille the text box with a name");
			else
				try {
					this.controller.searchVideo(textcomptel.getText(), "mine");
				} catch (Exception e) {
					e.printStackTrace();
				}
		});
		
		grid.add(button, 4, 3);
		
		int x = 2;
		int y = 7;
		
		List <Video> ph = this.controller.getVideos();
		
		if(ph != null) {

			for (int i = 0; i < ph.size(); i++) {
			
			Media media = new Media(ph.get(i).link); 
			MediaPlayer mediaplayer = new MediaPlayer(media);
			mediaplayer.setOnEndOfMedia(new Runnable() {
			       public void run() {
			         mediaplayer.seek(Duration.ZERO);
			       }
			   });
			mediaplayer.setAutoPlay(true); 

		    MediaView mediaView = new MediaView (mediaplayer);
		    
		    mediaView.setFitWidth(250);
		    mediaView.setFitHeight(250);
		    
			grid.add(mediaView, x , y + i);
						
			String com = "Title: " + ph.get(i).message + " | " +  ph.get(i).description + " | " + ph.get(i).comments.size();
			final Label label = new Label(com);	  
			grid.add(label, x+1 , y + i);
			
			Video vd = ph.get(i);
			Button edit = new Button("Edit Video Information");
			edit.setOnAction(event-> {
				try {
					this.controller.showEditVideoView(vd);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} );
			
			grid.add(edit, x+ 2, y + i);
			
		}
		
		}
			
		return grid;
	}
	
	
	public GridPane getViewUser(User user) throws Exception {
		
		GridPane grid = new GridPane();

		grid.setAlignment(Pos.BASELINE_LEFT);
		grid.setHgap(18);
		grid.setVgap(18);
		Image image = new Image(new FileInputStream("recourses/logo.png"));

		ImageView imageView = new ImageView(image);

		imageView.setX(0);
		imageView.setY(0);
		imageView.setFitHeight(150);
		imageView.setFitWidth(250);
		imageView.setPreserveRatio(true);
		grid.add(imageView, 0, 0);

		Button logout = new Button("Logout");
		logout.setOnAction(event->{
			this.controller.showLogInView();
		});
		grid.add(logout, 5, 0);
		
		
		
		Button back = new Button("Go back");
		back.setOnAction(event->{
			this.startView();
		});
		grid.add(back, 4, 0);
		
		

		Text scenetitle = new Text(user.name);
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		grid.add(scenetitle, 1, 1, 10, 1);
		
		System.out.println(user.pPhoto);
		Image i1 = new Image(new FileInputStream("recourses/"+user.pPhoto));  
	    ImageView imageV = new ImageView(i1); 
	    imageV.setX(25); 
	    imageV.setY(25); 
	    imageV.setFitHeight(300); 
	    imageV.setFitWidth(350); 
	    imageV.setPreserveRatio(true);  	      
	    grid.add(imageV, 2, 3);
	    
	    
		Button add = new Button("Add Friend");
		add.setOnAction(event-> {
			try {
				this.controller.sendFriendReq(user.userID);
				controller.displayPopUp("Friend Request sent succesfully!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} );
		
		grid.add(add, 4, 1);
				
	    
	    // Basic Info
	    
		Label subtitle = new Label("User Information");
		subtitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 28));
		grid.add(subtitle, 2, 4);	
		
		
		Label userName = new Label("User Name:");
		grid.add(userName, 2, 5);	
		Label Name = new Label(user.username);
		grid.add(Name, 3, 5);		
		Label birth = new Label("BirthDate:");
		grid.add(birth, 2, 6);	
		Label date = new Label(user.birthdate.toString());
		grid.add(date, 3, 6);		
		Label email = new Label("Email:");
		grid.add(email, 2, 7);	
		Label mail = new Label(user.email);
		grid.add(mail, 3, 7);		
		Label lives = new Label("Lives:");
		grid.add(lives, 2, 8);	
		Label location = new Label(user.locationStr);
		grid.add(location, 3, 8);
		Label born = new Label("Born at:");
		grid.add(born, 2, 9);	
		Label hometown = new Label(user.hometownStr);
		grid.add(hometown, 3, 9);		
		Label work = new Label("Works:");
		grid.add(work, 2, 10);	
		Label works = new Label(user.work);
		grid.add(works, 3, 10);

		// Albums
		

		Text sc = new Text("Albums");
		sc.setFont(Font.font("Tahoma", FontWeight.NORMAL, 28));
		grid.add(sc, 2, 12, 10, 1);	
		
		
		int x = 2;
		int y = 13;
		
		List <Album> ph = this.controller.getAlbum(user.userID);
		
		if(ph!=null && !ph.isEmpty()) {
		for (int i = 0; i < ph.size(); i++) {
		
			Label sub = new Label( ph.get(i).name);
			sub.setFont(Font.font("Tahoma", FontWeight.NORMAL, 22));
			grid.add(sub, x, y+i);	
			
			
			Label a1 = new Label( "Description : " + ph.get(i).description);
			grid.add(a1, x, y+i+1);	
			Label a2 = new Label( "Location : " + ph.get(i).city);
			grid.add(a2, x, y+i+2);	
			
			String s = "";
			for (int j = 0; j < ph.get(i).comments.size(); j++) {
				
				s += ph.get(i).comments.get(j) + " | " ;
			}
			
			Label a3 = new Label( "Comments : " + s);
			grid.add(a3, x, y+i+3);	
		
			if(!ph.get(i).photos.isEmpty()) {
			for (int j = 0; j < ph.get(i).photos.size(); j++) {
				
				Image img = new Image(new FileInputStream(ph.get(i).photos.get(j).link));
				ImageView imgView = new ImageView(img);
				imgView.setX(0);
				imgView.setY(0);

				imgView.setFitHeight(150);
				imgView.setFitWidth(200);
				imgView.setPreserveRatio(true);
				grid.add(imgView, x , y + j + 4 + i);
							
				String com = "Title : " + ph.get(i).photos.get(j).name + " size: " + ph.get(i).photos.get(j).width + " x " +  ph.get(i).photos.get(j).height + " | likes:" + ph.get(i).photos.get(j).numOflikes; 
				final Label label = new Label(com);	  
				grid.add(label, x+1 , y + j + 4 + i);
				
			
			}
			
			y =  y + ph.get(i).photos.size() + 5;
			
			}
		
		
		}
		if(!ph.isEmpty()) 
		y = y + ph.get(ph.size()-1).photos.size();
		
		}
		
		y++;
		
		// Videos
		
	
		Text sw = new Text("Videos");
		sw.setFont(Font.font("Tahoma", FontWeight.NORMAL, 28));
		grid.add(sw, 2, y);	

		y = y + 2;
		List <Video> phv = this.controller.getVideos(user.userID);
		
		if(phv!=null && !phv.isEmpty()) {
		for (int i = 0; i < phv.size(); i++) {
			
			Media media = new Media(phv.get(i).link); 
			MediaPlayer mediaplayer = new MediaPlayer(media);
			mediaplayer.setOnEndOfMedia(new Runnable() {
			       public void run() {
			         mediaplayer.seek(Duration.ZERO);
			       }
			   });
			mediaplayer.setAutoPlay(true); 

		    MediaView mediaView = new MediaView (mediaplayer);
		    
		    mediaView.setFitWidth(250);
		    mediaView.setFitHeight(250);
		    
			grid.add(mediaView, x , y + i);
						
			String com = "Title: " + phv.get(i).message + " | " +  phv.get(i).description + " | " + phv.get(i).comments.size();
			final Label label = new Label(com);	  
			grid.add(label, x+1 , y + i);
						
		}
		y = y + phv.size();
		}
		
	y++;
		
		// Links
		
		Text sq = new Text("Links");
		sq.setFont(Font.font("Tahoma", FontWeight.NORMAL, 28));
		grid.add(sq, 2, y);		
		
		List<Link> link = this.controller.getLink(user.userID);

		y++;
		grid.add(new Label("Name"), 2 , y);
		grid.add(new Label("Description"), 3 , y);
		grid.add(new Label("Link"), 4 , y);
		grid.add(new Label("Caption"), 5 , y);
		grid.add(new Label("Message"), 6 , y);
					
		int initXlevel = 1;
		int initYlevel = y+1;
		
		if(link!=null &&!link.isEmpty()) {
		for (int user_index = 0; user_index < link.size(); user_index++) {
			Link l = link.get(user_index);
			grid.add(new Label(l.name), initXlevel + 1, user_index + initYlevel);
			grid.add(new Label(l.description), initXlevel + 2, user_index + initYlevel);
			grid.add(new Label(l.link), initXlevel + 3, user_index + initYlevel);
			grid.add(new Label(l.caption), initXlevel + 4, user_index + initYlevel);
			grid.add(new Label(l.message), initXlevel + 5, user_index + initYlevel);
			
			
		}
		y += link.size();
		
		}
		
		
	y+=2;
		// Events

		Text sa = new Text("Events");
		sa.setFont(Font.font("Tahoma", FontWeight.NORMAL, 28));
		grid.add(sa, 2, y);		
		y++;
		List<Event> users = this.controller.getEvents(user.userID);

		grid.add(new Label("Name"), 2 , y);
		grid.add(new Label("Start Time"), 3 , y);
		grid.add( new Label("Finish Time"), 4 , y);
		grid.add(new Label("Location"), 5 , y);
		grid.add(new Label("Privacy"), 6 , y);
		grid.add(new Label("Description"), 7 , y);
					
		initXlevel = 1;
		initYlevel = y+1;
		
		if(users!=null && !users.isEmpty()) {
		for (int user_index = 0; user_index < users.size(); user_index++) {
			Event us = users.get(user_index);
			
			grid.add(new Label(us.name), initXlevel + 1, user_index + initYlevel);
			grid.add(new Label(us.start_time.toString()), initXlevel + 2, user_index + initYlevel);
			grid.add(new Label(us.finish_time.toString()), initXlevel + 3, user_index + initYlevel);
			grid.add(new Label(us.location), initXlevel + 4, user_index + initYlevel);
			grid.add(new Label(us.p.toString()), initXlevel + 5, user_index + initYlevel);
			grid.add(new Label(us.description), initXlevel + 6, user_index + initYlevel);
			
	}
		
		}
	
				
        return grid;
		
	}
	
	
	
	
	
	

}
