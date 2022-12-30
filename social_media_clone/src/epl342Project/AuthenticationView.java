package epl342Project;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.sun.scenario.effect.impl.prism.PrImage;
import com.sun.security.auth.NTSidPrimaryGroupPrincipal;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public  class AuthenticationView{
	Stage primaryStage;
	AuthenticationController controller;
	
	public AuthenticationView(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	public void setController(AuthenticationController controller) {
		this.controller=controller;
	}
	
	public void startView() throws FileNotFoundException {
	    Scene scene = new Scene(this.getLogInView(), 800, 500);
        this.primaryStage.setScene(scene);
        primaryStage.setTitle("Social Face");
        primaryStage.show();
	}

	
	public void prepareSceneLogin(GridPane grid) throws FileNotFoundException {

		grid.setAlignment(Pos.BASELINE_LEFT);
		grid.setHgap(18);
		grid.setVgap(18);
		// grid.setPadding(new Insets(00, 00, 00, 00));
		Text scenetitle = new Text("Welcome to Social Face! ");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		grid.add(scenetitle, 1, 1, 10, 1);

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

	}
	
	public GridPane getLogInView() throws FileNotFoundException {

		GridPane grid = new GridPane();
		//grid.setAlignment(Pos.CENTER);
		//grid.setHgap(10);
		//grid.setVgap(10);
		//grid.setPadding(new Insets(25, 25, 25, 25));
		//Text scenetitle = new Text("Welcome to ATOS Help Desk");
		//scenetitle.setTextAlignment(TextAlignment.CENTER);
		//scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 25));
		//grid.add(scenetitle, 0, 0, 10, 1);

		Label userName = new Label("User Name:");
		grid.add(userName, 2, 3);

		TextField userTextField = new TextField();
		userTextField.setAlignment(Pos.CENTER);
		grid.add(userTextField, 4, 3);

		Label pw = new Label("Password:");
		grid.add(pw, 2, 4);

		TextField pwBox = new TextField();
		pwBox.setAlignment(Pos.CENTER);
		grid.add(pwBox, 4, 4);

		Button signInButton = new Button("Sign in");
		signInButton.setOnAction(event-> {
			String username =userTextField.getText();
			String password = pwBox.getText();
			try {
				this.controller.signIn(username,password);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			});
		HBox hbox = new HBox(10);
		hbox.setAlignment(Pos.BOTTOM_RIGHT);



		Button register_btn = new Button("Sign up");
		register_btn.setOnAction(event-> {
			try {
				this.controller.showRegisterView();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} );
		
		
		Button btn = new Button("Admin Features");
		btn.setOnAction(event-> {
			try {
				this.controller.showAdminView();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} );
		
		hbox.getChildren().add(btn);
		hbox.getChildren().add(register_btn);
		hbox.getChildren().add(signInButton);
		grid.add(hbox, 4, 6);
		
		prepareSceneLogin(grid);
		return grid;
	}
	
	public void prepareScene(GridPane grid) throws FileNotFoundException {
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
			try {
				this.controller.showLogInView();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		grid.add(logout, 15, 0);

	}

	
	
	public GridPane adminView() throws Exception {
	
		GridPane grid = new GridPane();
		prepareScene(grid);
		Text scenetitle = new Text("Data Base Admin Capabilities ");
		scenetitle.setStyle("-fx-text-fill: green; -fx-font-size: 40px;");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		grid.add(scenetitle, 1, 1, 10, 1);
			
			
		Label userName = new Label("Import Database");
		grid.add(userName, 2, 3);	
		
		TextField userTextField = new TextField();
		grid.add(userTextField, 3, 3);
		

		//**********************************************************
		Button imp = new Button("import");
		imp.setOnAction(event-> {
			try {
				
				AdminFunctions.importData(userTextField.getText());
				this.controller.displayPopUp("Database Imported Successfully");;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} );
		
		grid.add(imp, 4, 3);
		
		//***********************************************
		Label birth = new Label("Export Database:");
		grid.add(birth, 2, 4);	
		Button ex = new Button("export");
		ex.setOnAction(event-> {
			try {
				AdminFunctions.exportDB();
				this.controller.displayPopUp("Database Exported Successfully");;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} );
		grid.add(ex, 3, 4);
		
		
				
		Label email = new Label("Delete Database:");
		grid.add(email, 2, 5);		
		
		Button edit = new Button("delete");
		edit.setOnAction(event-> {
			try {
				AdminFunctions.deleteDataFromTables();
				this.controller.displayPopUp("Database Deleted Successfully");;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} );
		
		grid.add(edit, 3, 5);
		
        return grid;
		
	}
	
	
	
	
	public void prepareSceneRegister(GridPane grid) throws FileNotFoundException {

		grid.setAlignment(Pos.BASELINE_LEFT);
		grid.setHgap(18);
		grid.setVgap(18);
		// grid.setPadding(new Insets(00, 00, 00, 00));
		Text scenetitle = new Text("Registration ");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		grid.add(scenetitle, 1, 1, 10, 1);

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
	}
	
	
	public GridPane getRegisterView() throws FileNotFoundException {
		Button button = new Button();
		button.setText("Create account");
		GridPane lay = new GridPane();
		//lay.setPadding(new Insets(10, 10, 10, 10));
		//lay.setVgap(10);
		//lay.setHgap(10);
		// LABEL
		//Text scenetitle = new Text("Registration ");
		//lay.add(scenetitle, 0, 0, 10, 1);
		//scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		Label label1 = new Label("Username:");
		lay.add(label1, 2, 3); // i am starting from 2,3
		// TEXTBOX
		TextField textlabel1 = new TextField();
		lay.add(textlabel1, 3, 3);
		Label pw = new Label("Password:");
		lay.add(pw, 2, 4);
		PasswordField pwBox = new PasswordField();
		lay.add(pwBox, 3, 4);
		Label name = new Label("Name :");
		lay.add(name, 2, 5);
		// TEXTBOX
		TextField textname = new TextField();
		lay.add(textname, 3, 5);
		Label surname = new Label("Surname :");
		lay.add(surname, 2, 6);
		// TEXTBOX
		TextField textsurname = new TextField();
		lay.add(textsurname, 3, 6);
		Label comptel = new Label("Email:");
		lay.add(comptel, 2, 7);
		// TEXTBOX
		
		
		TextField textcomptel = new TextField();
		lay.add(textcomptel, 3, 7);
	
		Label comp = new Label("Birthday");
		lay.add(comp, 2, 8);
		TextField textcomp = new TextField();
		lay.add(textcomp, 3, 8);
		
		
		/*
		Label email = new Label("");
		lay.add(email, 2, 9);
		// TEXTBOX
		TextField textemail = new TextField();
		lay.add(textemail, 3, 9);
		*/
		
		button.setOnAction(event->{
			try {
				this.controller.signUp(textlabel1.getText(),pwBox.getText(),textname.getText(),textsurname.getText(),textcomptel.getText(),textcomp.getText());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		lay.add(button, 2, 10);
		
		prepareSceneRegister(lay);
		return lay;
	}

}
