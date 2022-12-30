package applicationVersionTwo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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

public class AdminView {

	Stage primaryStage;
	TabPane tabPane;
	AdminController controller;

	public void setController(AdminController controller) {
		this.controller = controller;
	}

	public AdminView(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public void generateTabPane() throws FileNotFoundException {
		this.tabPane = new TabPane();
		int index = 0;
		// tuto en kalo ala en to katalavo r bro.
		Tab ticketViewTab = new Tab("Current Tickets", new ScrollPane(this.getAdminTicketView(index++)));
		Tab StatusTab = new Tab("User Status", new ScrollPane(this.getUserStatusView(index++)));
		Tab approveUsersTab = new Tab("Approve Users",new ScrollPane( this.getApproveUsersView(index++)));
		Tab modifyPoliciesTab = new Tab("Policies", new ScrollPane(this.getViewPoliciesAdmin(index++)));
		// theli doulia touto to view.
		Tab modifyFAQTab = new Tab("FAQs", new ScrollPane(this.getFAQAdminView(index++)));
//      // theli doulia touto to view.
		// Tab modifyTicketsTab= new Tab("Modify ticket",
		// this.getModifyTicketView(index++, ids, priority, category, username,
		// description));
		Tab createReportTab = new Tab("Create Report", new ScrollPane(this.getCreateReportView(index++)));

//      // theli doulia touto to view.
		// Tab modifyTicketsTab = new Tab("modify tickets",
		// this.getModifyTicketView(index++));
//
		tabPane.getTabs().add(ticketViewTab);
		this.tabPane.getTabs().add(StatusTab);
		this.tabPane.getTabs().add(approveUsersTab);
		this.tabPane.getTabs().add(modifyPoliciesTab);
		this.tabPane.getTabs().add(modifyFAQTab);

		this.tabPane.getTabs().add(createReportTab);
		// this.tabPane.getTabs().add(modifyTicketsTab);

	}

	public void startView() {
		VBox vBox = new VBox(this.tabPane);
		Scene scene = new Scene(vBox, 800, 500);
		primaryStage.setScene(scene);
		primaryStage.setTitle("ATOS Help Desk");
		primaryStage.show();
	}

	protected static boolean is_field_sensitive(String filed_name) {
		String[] sensitive_info = { "password", "SSN", "isAdmin" };
		boolean is_sensitive = false;
		for (int index = 0; index < sensitive_info.length; index++) {
			if (filed_name.equals(sensitive_info[index])) {
				is_sensitive = true;
				break;
			}
		}
		return is_sensitive;
	}

	public GridPane getUserStatusView(int tabIndex) throws FileNotFoundException {
		// prepareScene() Code
		GridPane grid = new GridPane();
//
		grid.setAlignment(Pos.BASELINE_LEFT);
		grid.setHgap(18);
		grid.setVgap(18);
		//grid.setPadding(new Insets(05, 05, 05, 05));
		Text scenetitle = new Text("User's Status");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		grid.add(scenetitle, 1, 1, 20, 1);

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
		
		grid.add(logout, 20, 0);


		ArrayList<User> users = this.controller.getUsers();

		// set dimentions of the grid.
	//	grid.setHgap(8); // horizontal
	//	grid.setVgap(10);// vertical

		java.lang.reflect.Field[] all_fields = User.class.getDeclaredFields();
		ArrayList<java.lang.reflect.Field> fields = new ArrayList<java.lang.reflect.Field>();
		grid.addColumn(fields.size()+1, new Label("No."));
		for (int i = 0; i < all_fields.length; i++) {
			String field_name = all_fields[i].getName();
			if (AdminView.is_field_sensitive(field_name) == false) {
				fields.add(all_fields[i]);
				grid.addColumn(fields.size()+1, new Label(field_name));
			}
		}

		int initXlevel = 1;
		int initYlevel = 3;
		int rowCount = fields.size() + 2;
		for (int user_index = 0; user_index < users.size(); user_index++) {
			User user = users.get(user_index);
			grid.add(new Label(Integer.toString(user_index + 1)), initXlevel, user_index + initYlevel); // adding 2
																										// because i
																										// start
			// from (2,3)
			for (int field_index = 0; field_index < fields.size(); field_index++) {
				try {
					grid.add(new Label(fields.get(field_index).get(user).toString()), field_index + initXlevel + 1,
							user_index + initYlevel);// adding 2 because i start from (2,3)
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}

			Button showButton = new Button("Show Password");
			showButton.setOnAction(event -> {
				this.controller.showUserPassword(user);
			});
			grid.add(showButton, rowCount + initXlevel + 1, user_index + initYlevel); // adding 2 because i start from
																						// (2,3)

			if (user.isAdmin() == true) {
				Button removeAdminCap = new Button("Remove Admin Capabilities");
				removeAdminCap.setOnAction(event -> this.controller.removeAdminCapabilities(tabIndex, user));
				grid.add(removeAdminCap, rowCount + initXlevel + 2, user_index + initYlevel); // adding 2 because i
																								// start from (2,3)
			} else {
				Button giveAdminCap = new Button("Give Admin Capabilities");
				giveAdminCap.setOnAction(event -> this.controller.giveAdminCapabilities(tabIndex, user));
				grid.add(giveAdminCap, rowCount + initXlevel + 2, user_index + initYlevel); // adding 2 because i start
																							// from (2,3)
			}
			Button fireUserButton = new Button("Fire User");
			fireUserButton.setOnAction(event -> this.controller.fireUser(tabIndex, user));
			grid.add(fireUserButton, rowCount + 3 + initXlevel, user_index + initYlevel); // adding 2 because i start
																							// from (2,3)
		}
		/*
		Button logout = new Button("Logout");
		logout.setOnAction(event->{
			this.controller.showLogInView();
		});
		grid.add(logout, rowCount + 3, 0);
		*/
		return grid;
	}

	public GridPane getAddQuestionView(int tabnum) throws FileNotFoundException {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.BASELINE_LEFT);
		grid.setHgap(18);
		grid.setVgap(18);
		// grid.setPadding(new Insets(00, 00, 00, 00));
		Text scenetitle = new Text("Adding a frequently asked question");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		grid.add(scenetitle, 1, 1, 9, 1);

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
		
		grid.add(logout, 60, 0);
		
		
		Button GoBackButton = new Button("Go Back");
		GoBackButton.setOnAction(event->{
			this.controller.goBack();
		});
		grid.add(GoBackButton, 62 , 0);
		
		Button buttonAdd = new Button();
		buttonAdd.setText("Submit"); // Button to add

		TextArea QuestionText = new TextArea();
		TextArea AnswerText = new TextArea();
		QuestionText.setPrefWidth(60);
		QuestionText.setPrefHeight(60);
		AnswerText.setPrefWidth(500);
		AnswerText.setPrefHeight(500);
		buttonAdd.setOnAction(event -> {
			String newQuestion = QuestionText.getText();
			String newAnswer = AnswerText.getText();
			try {
				this.controller.addQ(newQuestion, newAnswer, tabnum);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		// Layout
		// StackPane layout = new StackPane();
		Label Question = new Label("Question :");
		grid.add(Question, 2, 3);
		grid.add(QuestionText, 2, 4);
		Label Answer = new Label("Answer :");
		grid.add(Answer, 2, 5);
		grid.add(AnswerText, 2, 6);
		grid.add(buttonAdd, 2, 7);
		// prepareScene();
		return grid;
	}

	public void prepareSceneAdminTicket(GridPane grid) throws FileNotFoundException {

		grid.setAlignment(Pos.BASELINE_LEFT);
		grid.setHgap(18);
		grid.setVgap(18);
		// grid.setPadding(new Insets(00, 00, 00, 00));
		Text scenetitle = new Text("Tickets for handling:");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		grid.add(scenetitle, 1, 1, 9, 1);

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

		grid.add(imageView, 0, 0);

		Button logout = new Button("Logout");
		logout.setOnAction(event->{
			this.controller.showLogInView();
		});
		grid.add(logout, 18, 0);

	}

	/*
	 *
	 * */
	public GridPane getAdminTicketView(int tabIndex) throws FileNotFoundException {
		GridPane grid = new GridPane();

		TableColumn id = new TableColumn("Ticket ID");
		TableColumn username = new TableColumn("Username");
		TableColumn category = new TableColumn("Category");
		TableColumn priority = new TableColumn("Priority");
		TableColumn description = new TableColumn("Description");
//		TableColumn status = new TableColumn("Status");
		TableColumn solve = new TableColumn("Solve");
		TableColumn edit = new TableColumn("Edit");
		TableColumn handler = new TableColumn("Ticket Handler");
		TableColumn duration = new TableColumn("Duration");

		ObservableList<TicketForView> tobeshown = this.createTableContent();

		TableView<TicketForView> table = new TableView();

		table.setEditable(true);

		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		username.setCellValueFactory(new PropertyValueFactory<>("username"));
		category.setCellValueFactory(new PropertyValueFactory<>("category"));
		priority.setCellValueFactory(new PropertyValueFactory<>("priority"));
		description.setCellValueFactory(new PropertyValueFactory<>("description"));
	//	status.setCellValueFactory(new PropertyValueFactory<>("status"));
		// solve.setCellValueFactory(new PropertyValueFactory<>("solve"));
		handler.setCellValueFactory(new PropertyValueFactory<>("Thandler"));
		// duration.setCellValueFactory(new PropertyValueFactory<>("duration"));

		AdminView.prCompare(priority);

		Callback<TableColumn<TicketForView, String>, TableCell<TicketForView, String>> cellFactory = //
				new Callback<TableColumn<TicketForView, String>, TableCell<TicketForView, String>>() {
					@Override

					public TableCell call(final TableColumn<TicketForView, String> param) {
						final TableCell<TicketForView, String> cell = new TableCell<TicketForView, String>() {

							final Button btnHandleTicket = new Button("Handle ticket");
							// final Button btnEditTicket = new Button("Edit ticket");
							TicketForView btnsol;
							long startTime;

							public void updateItem(String item, boolean empty) {
								super.updateItem(item, empty);
								if (empty) {
									setGraphic(null);
									setText(null);
								} else {
									btnHandleTicket.setOnAction(event -> {
										btnsol = getTableView().getItems().get(getIndex());
										btnsol.setThandler(new SimpleIntegerProperty(controller.getUser().getId()));
										btnsol.setStatus(new SimpleStringProperty(TicketStatus.toFinish()+" click to finish"));	
//										table.getColumns().get(0).setVisible(false);
//										table.getColumns().get(0).setVisible(true);
										btnHandleTicket.textProperty().setValue(btnsol.getStatus());
										startTime = System.currentTimeMillis();
									//	controller.updateTicket(btnsol.FromViewToTicket(), tabIndex);
										
										btnHandleTicket.setOnAction(event2 -> {
											btnsol.setStatus(new SimpleStringProperty(TicketStatus.finished()));
											btnHandleTicket.textProperty().setValue(btnsol.getStatus());
											btnsol.setStarttime(new Timestamp(startTime));
											long timeElapsed = System.currentTimeMillis() - startTime;
											btnsol.setDuration((long) (timeElapsed / 1000));
											controller.updateTicket(btnsol.FromViewToTicket(), tabIndex);
										
											AdminController.displayPopUp("Ticket Handled!");
											controller.view.refresh(table);
										});
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
		grid.add(refresh, 2, 3);
		refresh.setOnAction(eventR -> {
			this.controller.view.refresh(table);
			// table.setItems(this.controller.view.createTableContent());
			// table.refresh();
		});

		Callback<TableColumn<TicketForView, String>, TableCell<TicketForView, String>> cellFactoryEdit = //
				new Callback<TableColumn<TicketForView, String>, TableCell<TicketForView, String>>() {
					@Override

					public TableCell call(final TableColumn<TicketForView, String> param) {
						final TableCell<TicketForView, String> cell = new TableCell<TicketForView, String>() {

							final Button btnEditTicket = new Button("Edit ticket");

							public void updateItem(String item, boolean empty) {
								super.updateItem(item, empty);
								if (empty) {
									setGraphic(null);
									setText(null);
								} else {
									btnEditTicket.setOnAction(editEvent -> {
										TicketForView btnsol = getTableView().getItems().get(getIndex());
										controller.editTicket(btnsol, tabIndex);
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
		solve.setCellFactory(cellFactory);
		edit.setCellFactory(cellFactoryEdit);
		// puts items in table
		table.setItems(tobeshown);

		// puts columns in table
		table.getColumns().addAll(id, username, category, priority, description, solve, edit);
		table.autosize();

		grid.add(table, 2, 4);// solve,status,

		prepareSceneAdminTicket(grid);

		return grid;

		/*
		 * Scene scene = new Scene(grid, 500, 500); window.setScene(scene);
		 * window.show();
		 */
	}

	private void refresh(TableView<TicketForView> table) {
		table.setItems(this.controller.view.createTableContent());
	//	table.refresh();
	}

	private int bsearch(ObservableList<TicketForView> tobeshown, int x) {
		TicketForView arr[] = tobeshown.toArray(new TicketForView[0]);
		int l = 0, r = arr.length - 1;
		while (l <= r) {
			int m = l + (r - l) / 2;

			// Check if x is present at mid
			if (arr[m].getId() == x)
				return m;

			// If x greater, ignore left half
			if (arr[m].getId() < x)
				l = m + 1;

			// If x is smaller, ignore right half
			else
				r = m - 1;
		}
		return -1;

	}

	public ObservableList<TicketForView> createTableContent() {
		List<Ticket> newTickets = controller.getTickets();

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

	public void prepareSceneCreateReport(GridPane grid) throws FileNotFoundException {

		grid.setAlignment(Pos.BASELINE_LEFT);
		grid.setHgap(18);
		grid.setVgap(18);
		// grid.setPadding(new Insets(00, 00, 00, 00));
		Text scenetitle = new Text("Create Report");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		grid.add(scenetitle, 1, 1, 6, 1);

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

	public GridPane getCreateReportView(int tabIndex) throws FileNotFoundException {
		GridPane grid = new GridPane();

		ArrayList<Report> list = this.controller.createReport(tabIndex);

		// BOI you need to make this not static.
		// tulaxisto na sou ta dia ena function tou controller
		// je na ta xirizese alospos dame.

		/// list.add(new Report(3, "aB", "hardware", "normal", 5, "A"));
		// list.add(new Report(1, "aC", "hardware", "low", 8, "B"));
		// list.add(new Report(4, "aD", "software", "high", 4, "B"));
		// list.add(new Report(2, "aE", "hardware", "normal", 5, "C"));
		// list.add(new Report(9, "aF", "software", "low", 11, "A"));
		// list.add(new Report(7, "aG", "software", "normal", 3, "A"));

		// add your data here from any source
		ObservableList<Report> tickets = FXCollections.observableArrayList(list);

		TableView<Report> table = new TableView();

		final Label label = new Label("Report Preview");

		table.setEditable(true);

		TableColumn id = new TableColumn("Ticket ID");
		TableColumn username = new TableColumn("Username");
		TableColumn category = new TableColumn("Category");
		TableColumn priority = new TableColumn("Priority");
		TableColumn handler = new TableColumn("Ticket Handler");
		TableColumn duration = new TableColumn("Duration");

		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		username.setCellValueFactory(new PropertyValueFactory<>("username"));
		category.setCellValueFactory(new PropertyValueFactory<>("category"));
		priority.setCellValueFactory(new PropertyValueFactory<>("priority"));
		handler.setCellValueFactory(new PropertyValueFactory<>("handler"));
		duration.setCellValueFactory(new PropertyValueFactory<>("duration"));

		table.setItems(tickets);
		table.getColumns().addAll(id, username, category, priority, handler, duration);

		grid.add(label, 2, 3);
		grid.add(table, 2, 5);

		Button submit = new Button("Export Report");
		submit.setOnAction(event -> {
			this.controller.generateReport(list);
		});
		grid.add(submit, 2, 8);

		prepareSceneCreateReport(grid);

		return grid;
	}
	
	
	public void prepareSceneApproveUser(GridPane grid) throws FileNotFoundException {

		grid.setAlignment(Pos.BASELINE_LEFT);
		grid.setHgap(18);
		grid.setVgap(18);
		// grid.setPadding(new Insets(00, 00, 00, 00));
		Text scenetitle = new Text("Approve Registration");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		grid.add(scenetitle, 1, 1, 12, 1);

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
		grid.add(logout, 18, 0);


	}

	public GridPane getApproveUsersView(int tabIndex) {
		ArrayList<Registration> users = this.controller.getUnapprovedUsers();

		GridPane grid = new GridPane();
		//grid.setHgap(8); // horizontal
		//grid.setVgap(10);// vertical

		//grid.setAlignment(Pos.BASELINE_CENTER);
		//grid.setPadding(new Insets(25, 25, 25, 25));
		//grid.addColumn(0, new Label("No."));
		
		try {
			prepareSceneApproveUser(grid);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		java.lang.reflect.Field[] all_fields = Registration.class.getDeclaredFields();
		ArrayList<java.lang.reflect.Field> fields = new ArrayList<java.lang.reflect.Field>();
		grid.addColumn(fields.size()+1, new Label("No."));
		for (int i = 0; i < all_fields.length; i++) {
			String field_name = all_fields[i].getName();
			if (AdminView.is_field_sensitive(field_name) == false) {
				fields.add(all_fields[i]);
				grid.addColumn(fields.size()+1, new Label(field_name));
			}
		}

		for (int user_index = 0; user_index < users.size(); user_index++) {
			Registration user = users.get(user_index);
			grid.add(new Label(Integer.toString(user_index + 1)), 1, user_index + 4);
			for (int field_index = 0; field_index < fields.size(); field_index++) {
				try {
					grid.add(new Label(fields.get(field_index).get(user).toString()), field_index + 2, user_index + 4);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			Button approveButton = new Button("Approve");
			approveButton.setOnAction(event -> this.controller.approveUser(user, tabIndex));
			grid.add(approveButton, fields.size() + 3, user_index + 4);
			Button removeButton = new Button("Remove");
			removeButton.setOnAction(event -> this.controller.disapproveUser(user, tabIndex));
			grid.add(removeButton, fields.size() + 5, user_index + 4);
		}
		return grid;
	}

	public GridPane getEditQuestionView(int tabnum) throws FileNotFoundException {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.BASELINE_LEFT);
		grid.setHgap(18);
		grid.setVgap(18);
		grid.setPadding(new Insets(00, 00, 00, 00));
		Text scenetitle = new Text("Frequently Asked Questions");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		grid.add(scenetitle, 1, 1, 10, 1);

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
		
		Button buttonAdd = new Button();
		buttonAdd.setText("Done"); // Button to update
		// doulepse me touto.
		FAQ[] currentFAQs = this.controller.readFaq();
		TextArea[] QuestionArea = new TextArea[currentFAQs.length];
		TextArea[] AnswerArea = new TextArea[currentFAQs.length];
		Button[] deleteButtons = new Button[currentFAQs.length];
		int[] FaqIds = new int[currentFAQs.length];
		//buttonAdd.setText("Done");

		int j = 3;
		for (int i = 0; i < currentFAQs.length; i++) {
			int id = currentFAQs[i].getFAQ();
			FaqIds[i] = currentFAQs[i].getFAQ();
			deleteButtons[i] = new Button();
			deleteButtons[i].setText("Delete Question");
			deleteButtons[i].setOnAction(event -> {
				this.controller.deleteQuestion(id, tabnum);
			});
			Label Question = new Label("Question " + (i + 1) + " :");
			grid.add(Question, 2, j);
			grid.add(deleteButtons[i], 3, j + 1);
			j++;
			QuestionArea[i] = new TextArea(currentFAQs[i].getFAQ_Q());
			QuestionArea[i].setPrefHeight(60);
			QuestionArea[i].setPrefWidth(60);
			grid.add(QuestionArea[i], 2, j);
			j++;
			Label Answer = new Label("Answer " + (i + 1) + " :");
			grid.add(Answer, 2, j);
			j++;
			AnswerArea[i] = new TextArea(currentFAQs[i].getFAQ_A());
			AnswerArea[i].setPrefWidth(500);
			AnswerArea[i].setPrefHeight(500);
			grid.add(AnswerArea[i], 2, j);
			j++;
		}
		buttonAdd.setOnAction(event -> {
			try {
				this.controller.updateFAQ(FaqIds, QuestionArea, AnswerArea, tabnum);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		grid.add(buttonAdd, 2, j);
		ScrollPane sp = new ScrollPane(grid);
		sp.setFitToWidth(true);
		grid.setHgrow(sp, Priority.ALWAYS);
		sp.setContent(grid);
		return grid;
	}

	public GridPane getModifyPoliciesView(int tabIndex) throws FileNotFoundException {
		GridPane grid = new GridPane();
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
		grid.add(logout, 10, 0);
		
		Button GoBackButton = new Button("Go Back");
		GoBackButton.setOnAction(event->{
			this.controller.goBack();
		});
		grid.add(GoBackButton, 11 , 0);

		String oldPolicies = this.controller.getPolicies();

		Button buttonAdd = new Button();
		buttonAdd.setText("Update"); // Button to add

		TextArea NewPoliciesText = new TextArea(oldPolicies);
		NewPoliciesText.setPrefSize(500, 250);
		NewPoliciesText.setWrapText(true);

		NewPoliciesText.setPrefWidth(750);
		NewPoliciesText.setPrefHeight(750);

		// Layout
		// StackPane layout = new StackPane();
		grid.add(NewPoliciesText, 2, 4);

		buttonAdd.setOnAction(event -> {
			String newPolicies = NewPoliciesText.getText();
			this.controller.updatePolicies(newPolicies, tabIndex);
		});
		grid.add(buttonAdd, 2, 5);
		return grid;

	}

	// TO VIEW POLICIES TU ADMIN
	public GridPane getViewPoliciesAdmin(int tabIndex) throws FileNotFoundException {
		GridPane grid = new GridPane();
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
		// logout.setOnAction(event ->)
		grid.add(logout, 20, 0);
		String Policy = this.controller.getPolicies();
		Button buttonChangePolicies = new Button();
		buttonChangePolicies.setText("Edit Policies");
		buttonChangePolicies.setOnAction(event -> {
			try {
				this.controller.showEditPoliciesView(tabIndex);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		Text pol = new Text("Policies :");
		
		
		Text pol1 = new Text(Policy);
		
		VBox layout = new VBox(25);	       
	    layout.getChildren().add(pol1);
	    
	    Scene scene = new Scene(layout, 800, 500);
	    
	    pol1.wrappingWidthProperty().bind(scene.widthProperty().subtract(25));
		
	//	Label label = new Label(Policy);
	//	label.setWrapText(true);
	//	label.prefHeightProperty().bind(scene.heightProperty());

		grid.add(buttonChangePolicies, 2, 3);
		grid.add(pol, 2, 4);
		grid.add(layout, 2, 5);
		return grid;
	}

// TO FAQ ADMIN VIEW 
	public GridPane getFAQAdminView(int tabIndex) {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.BASELINE_LEFT);
		grid.setHgap(18);
		grid.setVgap(18);
		grid.setPadding(new Insets(00, 00, 00, 00));
		Text scenetitle = new Text("Frequently Asked Questions");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		grid.add(scenetitle, 1, 1, 5, 1);

		Image image = null;
		try {
			image = new Image(new FileInputStream("recourses/atoslogo.jpg"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
		grid.add(logout, 9, 0);
		FAQ[] FAQS = this.controller.readFaq();
		TreeView<String> tree;
		TreeItem<String> root;
		root = new TreeItem<>();
		root.setExpanded(true);
		for (int i = 0; i < FAQS.length; i++) {
			TreeItem<String> q = (makeBranch(FAQS[i].getFAQ_Q(), root));
			q.setExpanded(false);
			makeBranch(FAQS[i].getFAQ_A(), q);
		}
		tree = new TreeView<>(root);
		tree.setShowRoot(false);
		Button buttonAdd = new Button();
		buttonAdd.setText("Add Question"); // Button to add
		buttonAdd.setOnAction(event -> {
			try {
				this.controller.showAddFaqView(tabIndex);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		// buttonAdd.setOnAction(event ->
		// {this.controller.view.getAddQuestionView(tabIndex));
		// });
		Button buttonEdit = new Button();
		buttonEdit.setOnAction(event -> {
			try {
				this.controller.showEditFaqView(tabIndex);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		// buttonEdit.setOnAction(event ->
		// {this.controller.view.getEditQuestionView(tabIndex));
		// });
		buttonEdit.setText("Edit Questions"); // Button to add
		grid.add(buttonAdd, 2, 3);
		grid.add(buttonEdit, 2, 4);
		grid.add(tree, 2, 5);
		grid.setHgrow(tree, Priority.ALWAYS);
		grid.setVgrow(tree, Priority.ALWAYS);
		return grid;
	}

	private TreeItem<String> makeBranch(String title, TreeItem<String> parent) {
		TreeItem<String> item = new TreeItem<>(title);
		item.setExpanded(true);
		parent.getChildren().add(item);
		return item;
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
		grid.add(logout, 75, 0);
		
		Button GoBackButton = new Button("Go Back");
		GoBackButton.setOnAction(event->{
			this.controller.goBack();
		});
		grid.add(GoBackButton, 77 , 0);

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
					System.out.println("in modify"+t);
					controller.updateTicket(t, tabIndex);
				AdminController.displayPopUp("Ticket Modified");
					Tab tab = tabPane.getTabs().get(tabIndex);
					try {
						tab.setContent(getAdminTicketView(tabIndex));
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

}
