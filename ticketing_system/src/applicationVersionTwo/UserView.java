package applicationVersionTwo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class UserView {

	Stage primaryStage;
	TabPane tabPane;
	UserController controller;

	public void setController(UserController controller) {
		this.controller = controller;
	}

	public UserView(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public void generateTabPane() throws FileNotFoundException {
		this.tabPane= new TabPane();
		int index=0;

      Tab TicketsTab = new Tab("Create a Ticket",new ScrollPane(this.getViewCreateTicket(index++)));
      Tab FAQTab = new Tab("FAQ",new ScrollPane(this.getViewFAQUser(index++)));
      Tab policiesTab = new Tab("Policies", new ScrollPane(this.getViewPoliciesUser(index++)));
      Tab modifyPreviousTicketsTab = new Tab("View my tickets",new ScrollPane( this.getViewUserTicket(index++)));

      tabPane.getTabs().add(TicketsTab);
      tabPane.getTabs().add(FAQTab);
      tabPane.getTabs().add(policiesTab);
      tabPane.getTabs().add(modifyPreviousTicketsTab);


	}


	public void startView() {
		VBox vBox = new VBox(this.tabPane);
		Scene scene = new Scene(vBox, 800, 500);
		primaryStage.setScene(scene);
		primaryStage.setTitle("ATOS Help Desk");
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

	public void prepareSceneFAQUser(GridPane grid) throws FileNotFoundException {
		grid.setAlignment(Pos.BASELINE_LEFT);
		grid.setHgap(18);
		grid.setVgap(18);
		grid.setPadding(new Insets(00, 00, 00, 00));
		Text scenetitle = new Text("Frequently Asked Questions");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		grid.add(scenetitle, 1, 1, 5, 1);

		Image image = new Image(new FileInputStream("recourses/atoslogo.jpg"));

		// Setting the image view
		ImageView imageView = new ImageView(image);

		// Setting the position of the image
		imageView.setX(0);
		imageView.setY(0);

		// setting the fit height and width of the image view
		imageView.setFitHeight(100);
		imageView.setFitWidth(150);

		// Setting the preserve ratio of the image view
		imageView.setPreserveRatio(true);

		// Creating a Group object

		grid.add(imageView, 0, 0);

		Button logout = new Button("Logout");
		logout.setOnAction(event->{
			this.controller.showLogInView();
		});
		grid.add(logout, 8, 0);

	}

	public GridPane getViewFAQUser(int tabIndex) {
         FAQ[] FAQS = this.controller.viewFAQS();
		TreeView<String> tree;
		GridPane grid = new GridPane();

		TreeItem<String> root; // q1,q2,q3
	//	List<FAQ> list = new ArrayList<FAQ>();
	//	list.add(new FAQ(1, "Question 1", "Answer 1"));
	//	list.add(new FAQ(2, "Question 2", "Answer 2"));
	//	list.add(new FAQ(3, "Question 3", "Answer 3"));
	//	list.add(new FAQ(4, "Question 4", "Answer 4"));
	//	list.add(new FAQ(5, "Question 5", "Answer 5"));
	//	list.add(new FAQ(6, "Question 6", "Answer 6"));
		// root

		root = new TreeItem<>();
		root.setExpanded(false);
		for (int i = 0; i < FAQS.length; i++) {
			TreeItem<String> q = (makeBranch(FAQS[i].getFAQ_Q(), root));
			q.setExpanded(false);
			makeBranch(FAQS[i].getFAQ_A(), q);
		}
		tree = new TreeView<>(root);
		tree.setShowRoot(false);
		grid.add(tree, 2, 3);
		grid.setHgrow(tree, Priority.ALWAYS);
		grid.setVgrow(tree, Priority.ALWAYS);
		
		try {
			prepareSceneFAQUser(grid);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return grid;
		}
	
	
	public void prepareScenePoliciesUser(GridPane grid) throws FileNotFoundException {


		grid.setAlignment(Pos.BASELINE_LEFT);
		grid.setHgap(18);
		grid.setVgap(18);
		grid.setPadding(new Insets(00, 00, 00, 00));
		Text scenetitle = new Text("Policies");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		grid.add(scenetitle, 1, 1, 5, 1);

		Image image = new Image(new FileInputStream("recourses/atoslogo.jpg"));

		// Setting the image view
		ImageView imageView = new ImageView(image);

		// Setting the position of the image
		imageView.setX(0);
		imageView.setY(0);

		// setting the fit height and width of the image view
		imageView.setFitHeight(100);
		imageView.setFitWidth(150);

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
	
	
	
	
	
		public GridPane getViewPoliciesUser(int tabIndex) throws FileNotFoundException {
			String Policy = this.controller.viewPolicies();
			GridPane grid = new GridPane();
			Text pol = new Text("Policies");
			Text pol2 = new Text(Policy);
			VBox layout = new VBox(25);	       
		    layout.getChildren().add(pol2);
		    
		    Scene scene = new Scene(layout, 800, 500);
		    
		    pol2.wrappingWidthProperty().bind(scene.widthProperty().subtract(25));

			
			

	
		grid.add(pol, 2, 3);
		grid.add(pol2, 2, 4);
		// layout.getChildren().add(tree);
		// layout.getChildren().add(button);
		// layout.setAlignment(button,Pos.TOP_RIGHT);

		prepareScenePoliciesUser(grid);
		return grid;
	}

	public void prepareSceneCreateTicket(GridPane grid) throws FileNotFoundException {


		grid.setAlignment(Pos.BASELINE_LEFT);
		grid.setHgap(18);
		grid.setVgap(18);
		grid.setPadding(new Insets(00, 00, 00, 00));
		Text scenetitle = new Text("Place a Ticket");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		grid.add(scenetitle, 1, 1, 5, 1);

		Image image = new Image(new FileInputStream("recourses/atoslogo.jpg"));

		// Setting the image view
		ImageView imageView = new ImageView(image);

		// Setting the position of the image
		imageView.setX(0);
		imageView.setY(0);

		// setting the fit height and width of the image view
		imageView.setFitHeight(100);
		imageView.setFitWidth(150);

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

	public GridPane getViewCreateTicket(int tabIndex) throws FileNotFoundException{
		GridPane grid = new GridPane();


		Label userName = new Label("User Name:");
		grid.add(userName, 2, 3);

		// In this section we add user.name instead of a constant string
		String name = this.controller.getUserName();
		Label Name = new Label(name);
		grid.add(Name, 3, 3);

		Label cat = new Label("Category");
		grid.add(cat, 2, 4);

		final ComboBox TypeComboBox = new ComboBox();
		TypeComboBox.getItems().addAll("Hardware", "Software", "other");

		TypeComboBox.getSelectionModel().select(0);
		grid.add(TypeComboBox, 3, 4);

		Label pr = new Label("Priority");
		grid.add(pr, 2, 5);

		final ComboBox priorityComboBox = new ComboBox();
		priorityComboBox.getItems().addAll("High", "Normal", "Low");

		priorityComboBox.getSelectionModel().select(0);
		grid.add(priorityComboBox, 3, 5);

		Label desc = new Label("Description");
		grid.add(desc, 2, 6);

		TextArea descfield = new TextArea();
		descfield.setPrefSize(225, 80);
		descfield.setWrapText(true);
		grid.add(descfield, 3, 6);

		Button submit = new Button("Submit");

		
		
		
		grid.add(submit, 2, 8);

		Button cancel = new Button("Clear");
		
		cancel.setOnAction(event->{
			
			priorityComboBox.getSelectionModel().select(0);
			TypeComboBox.getSelectionModel().select(0);
			descfield.clear();
		});
		
		
		
		grid.add(cancel, 3, 8);

		// pop up for result

		Label popup = new Label();
		GridPane.setConstraints(popup, 2, 10);
		GridPane.setColumnSpan(popup, 2);
		grid.getChildren().add(popup);

		// Setting an action for the Submit button
		submit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				if ((descfield.getText().equals(""))) {
					popup.setText("Please add a Description of your problem!");
					popup.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
					
				} else {
					popup.setText("You have succesfully place your ticket!");
					popup.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
					String priority = priorityComboBox.getValue().toString();
					String type = TypeComboBox.getValue().toString();
					String des = descfield.getText();
					Ticket ticket= new Ticket(priority, type, des, "new", name);
					controller.AddTicket(ticket);
					Tab tab = tabPane.getTabs().get(tabIndex);
					try {
						tab.setContent(getViewCreateTicket(tabIndex));
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});

		prepareSceneCreateTicket(grid);
		return grid;
		/*
		 * Scene scene = new Scene(grid, 500 , 500); primaryStage.setScene(scene);
		 * primaryStage.show();
		 */
	}
	
	
	public void prepareSceneModifyTicket(GridPane grid) throws FileNotFoundException {

		grid.setAlignment(Pos.BASELINE_LEFT);
		grid.setHgap(18);
		grid.setVgap(18);
		grid.setPadding(new Insets(00, 00, 00, 00));
		Text scenetitle = new Text("Modify My Ticket");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		grid.add(scenetitle, 1, 1, 5, 1);

		Image image = new Image(new FileInputStream("recourses/atoslogo.jpg"));

		// Setting the image view
		ImageView imageView = new ImageView(image);

		// Setting the position of the image
		imageView.setX(0);
		imageView.setY(0);

		// setting the fit height and width of the image view
		imageView.setFitHeight(100);
		imageView.setFitWidth(150);

		// Setting the preserve ratio of the image view
		imageView.setPreserveRatio(true);

		// Creating a Group object

		grid.add(imageView, 0, 0);

		Button logout = new Button("Logout");
		logout.setOnAction(event->{
			this.controller.showLogInView();
		});
		grid.add(logout, 15, 0);
		
		Button GoBackButton = new Button("Go Back");
		GoBackButton.setOnAction(event->{
			this.controller.goBack();
		});
		grid.add(GoBackButton, 16 , 0);

	}

	public GridPane getModifyTicketView(int tabIndex, int ids, String priority, String category, String username,
			String description) throws FileNotFoundException {

		// CHANGE THIS HAVE AN ATTRIBUTE OF TICKET
		GridPane grid = new GridPane();

		/*
		 * GridPane grid = new GridPane(); grid.setAlignment(Pos.BASELINE_LEFT);
		 * grid.setHgap(18); grid.setVgap(18); grid.setPadding(new Insets(100, 100, 200,
		 * 200)); Text scenetitle = new Text("Modify My Ticket");
		 * scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		 * grid.add(scenetitle, 0, 0, 5, 1);
		 */
		Label userName = new Label("User Name:");
		grid.add(userName, 2, 3);

		// In this section we add user.name instead of a constant string
		Label Name = new Label(username);
		grid.add(Name, 3, 3);

		Label id = new Label("Ticket ID:");
		grid.add(id, 2, 4);

		// In this section we add ticket.id instead of a constant string
		Label lid = new Label(ids + "");
		grid.add(lid, 3, 4);

		Label cat = new Label("Category");
		grid.add(cat, 2, 5);

		final ComboBox TypeComboBox = new ComboBox();
		TypeComboBox.getItems().addAll("Hardware", "Software", "other");

		// based on the ticket.category we set the default value
		TypeComboBox.getSelectionModel().select(category);
		;
		grid.add(TypeComboBox, 3, 5);

		Label pr = new Label("Priority");
		grid.add(pr, 02, 6);

		final ComboBox priorityComboBox = new ComboBox();
		priorityComboBox.getItems().addAll("Low", "Normal", "High");

		// based on the ticket.priority we set the default value
		priorityComboBox.getSelectionModel().select(priority);
		grid.add(priorityComboBox, 3, 6);

		Label desc = new Label("Description");
		grid.add(desc, 2, 7);

		// Instead of string s we will have ticket.description
		String s = " Testing the value";
		TextArea descfield = new TextArea(description);
		descfield.setPrefSize(225, 80);
		descfield.setWrapText(true);
		grid.add(descfield, 3, 7);

		Button submit = new Button("Submit Changes");
		grid.add(submit, 2, 9);

		Button cancel = new Button("Clear");

		cancel.setOnAction(event -> {

			priorityComboBox.getSelectionModel().select(0);
			TypeComboBox.getSelectionModel().select(0);
			descfield.clear();
		});

		grid.add(cancel, 3, 9);

		// pop up for result

		Label popup = new Label();
		GridPane.setConstraints(popup, 2, 10);
		GridPane.setColumnSpan(popup, 2);
		grid.getChildren().add(popup);

		// Setting an action for the Submit button
		submit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				if ((descfield.getText().equals(""))) {
					popup.setText("Please add a Description of your problem!");
					popup.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");

				} else {
					popup.setText("You have succesfully modified your ticket!");
					popup.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");			
					String pr = priorityComboBox.getValue().toString();
					String ven = TypeComboBox.getValue().toString();
					String desc = descfield.getText();
					
					Ticket t = new Ticket(ids, pr, ven, desc, username);
					controller.updateTicket(t);
					AdminController.displayPopUp("Ticket Modified");
					Tab tab = tabPane.getTabs().get(tabIndex);
					try {
						tab.setContent(getViewUserTicket(tabIndex));
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});

		prepareSceneModifyTicket(grid);
		return grid;

	}



	public void prepareSceneUserTicket(GridPane grid) throws FileNotFoundException {

		grid.setAlignment(Pos.BASELINE_LEFT);
		grid.setHgap(18);
		grid.setVgap(18);
		// grid.setPadding(new Insets(00, 00, 00, 00));
		Text scenetitle = new Text("View My Tickets");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		grid.add(scenetitle, 1, 1, 8, 1);

		Image image = new Image(new FileInputStream("recourses/atoslogo.jpg"));

		// Setting the image view
		ImageView imageView = new ImageView(image);

		// Setting the position of the image
		imageView.setX(0);
		imageView.setY(0);

		// setting the fit height and width of the image view
		imageView.setFitHeight(100);
		imageView.setFitWidth(150);

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

	public GridPane getViewUserTicket(int tabIndex) throws FileNotFoundException {
		
		TableColumn id = new TableColumn("Ticket ID");
		// TableColumn username = new TableColumn("Username");
		TableColumn category = new TableColumn("Category");
		TableColumn priority = new TableColumn("Priority");
		TableColumn description = new TableColumn("Description");
		TableColumn edit = new TableColumn("Edit ticket");
		GridPane grid = new GridPane();
		ObservableList<TicketForView> tobeshown = this.createTableContent();


		TableView<TicketForView> table = new TableView();

		// column creation
		table.setEditable(true);

		// set cell values
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		// username.setCellValueFactory(new PropertyValueFactory<>("username"));
		category.setCellValueFactory(new PropertyValueFactory<>("category"));
		priority.setCellValueFactory(new PropertyValueFactory<>("priority"));
		description.setCellValueFactory(new PropertyValueFactory<>("description"));

		Callback<TableColumn<TicketForView, String>, TableCell<TicketForView, String>> cellFactory = //
				new Callback<TableColumn<TicketForView, String>, TableCell<TicketForView, String>>() {
					@Override

					public TableCell call(final TableColumn<TicketForView, String> param) {
						final TableCell<TicketForView, String> cell = new TableCell<TicketForView, String>() {

							final Button btn = new Button("Edit ticket");

							public void updateItem(String item, boolean empty) {
								super.updateItem(item, empty);
								if (empty) {
									setGraphic(null);
									setText(null);
								} else {
									btn.setOnAction(event -> {
										TicketForView btnsol = getTableView().getItems().get(getIndex());
										controller.editTicket(btnsol,tabIndex);
										controller.view.refresh(table);
										
									});

									setGraphic(btn);
									setText(null);

								}
							}

						};
						return cell;

					}

				};

		Button refresh = new Button("Refresh");
		grid.add(refresh, 2, 3);
		refresh.setOnAction(eventR -> {
			this.controller.view.refresh(table);
		});

		edit.setCellFactory(cellFactory);
		// puts items in table
		table.setItems(tobeshown);

		// puts columns in table
		table.getColumns().addAll(id, category, priority, description, edit);
		table.autosize();

		grid.add(table, 2, 4);

		prepareSceneUserTicket(grid);

		return grid;
		/*
		 * Scene scene = new Scene(grid, 500, 500); window.setScene(scene);
		 * window.show();
		 * 
		 */
	}
	
	private void refresh(TableView<TicketForView> table) {
		table.setItems(this.controller.view.createTableContent());
		table.refresh();
	}
	public ObservableList<TicketForView> createTableContent() {
		ArrayList<Ticket> newTickets = controller.currentickets();

		ArrayList<TicketForView> updatedtickets = new ArrayList<TicketForView>();
		while (!newTickets.isEmpty()) {
			updatedtickets.add(new TicketForView(newTickets.get(0)));
			newTickets.remove(0);
		}

		ObservableList<TicketForView> tobeshownupdate = FXCollections.observableArrayList(updatedtickets);
		return tobeshownupdate;
	}

	public static void prCompare(TableColumn priority) {
		priority.setSortable(true);

		Comparator<String> columnComparator = (String v1, String v2) -> {
			v1.toLowerCase();
			v2.toLowerCase();
			int index1=0,index2=0;
			if(v1.startsWith("h")) {
				index1=3;
			}else if(v1.startsWith("n")) {
				index1=2;
			}else {
				index1=1;
			}
			if(v2.startsWith("h")) {
				index2=3;
			}else if(v2.startsWith("n")) {
				index2=2;
			}else {
				index2=1;
			}
			
			if (index1>index2) {
				return 1;
			} else if (index1==index2) {
				return 0;
			} else
				return -1;

		};
		priority.setComparator(columnComparator);
	}
		
}
