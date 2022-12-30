package epl342Project;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AuthenticationController {
	AuthenticationView view;
	AuthenticationModel model;

	public AuthenticationController(Stage primaryStage) {
		primaryStage.setOnCloseRequest(event->{
			System.out.println("Exiting Program");
			primaryStage.close();
		});
		this.view = new AuthenticationView(primaryStage);
		this.model = new AuthenticationModel();
		this.view.setController(this);
		this.model.setController(this);
	}
	
	public static void displayPopUp(String message) {
		Stage popupwindow = new Stage();
		popupwindow.initModality(Modality.APPLICATION_MODAL);
		popupwindow.setTitle("Pop Up Window");
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
	
	public void showRegisterView() throws FileNotFoundException {
		Scene newScene = new Scene(this.view.getRegisterView());
		this.view.primaryStage.setScene(newScene);
		this.view.primaryStage.show();
	}

	public void showLogInView() throws FileNotFoundException {
		Scene newScene = new Scene(this.view.getLogInView());
		this.view.primaryStage.setScene(newScene);
		this.view.primaryStage.show();
	}
	
	
	

	public User dummyUser() {
		
		User user = new User();
		
		user.name = " Andreas Hadjivasili";
		user.birthdate= LocalDate.parse("1999-05-16");
		user.email = "ahadji08@ucy.ac.cy";
		user.location = new Location("dali" , "cyprus");
		user.hometown = new Location("dali" , "cyprus");
		user.username = "Hadjis99";
		user.work = "CEO at Social Face " ;
		user.pPhoto = "recourses/test.jpg";
		
		return user;
	}
	
	
	public void showAdminView() throws Exception {
		Scene newScene = new Scene(new ScrollPane(this.view.adminView()));
		this.view.primaryStage.setScene(newScene);
		this.view.primaryStage.show();
	}
	

	public void signIn(String username, String password) throws IOException {
	
		
		User user=this.model.authenticate(username, password);
		System.out.println(user.userID);
		//User user= this.dummyUser(); // should be Deleted after connecting to the database.
		//System.out.print(user.isAdmin);
		if (user!=null && user.password.equals(password) ) {


				ProfileController.startController(this.view.primaryStage, user);
				//ProfileController.startController(this.view.primaryStage, dummyUser());
			}
	else {
			//oste na men tou pis oti exi tuto to username. gia na spamari meta. gia safty
			this.displayPopUp("There is no user with this username or with this password\n");
		}
		
		try {
			ProfileController.startController(this.view.primaryStage, user);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void startController(Stage primaryStage) throws FileNotFoundException {
		
		AuthenticationController controller = new AuthenticationController(primaryStage);
		controller.startConnection();
		
		controller.view.startView();
	}

	private void startConnection() {
		AuthenticationModel.connectToDB();
	}

	public void signUp(String username,String password,String name,String surname,String email,String birthday) throws FileNotFoundException {
		// other stuff
		
		String[] parts = birthday.split("/");
		
		LocalDate t = LocalDate.of(Integer.parseInt(parts[2]), Integer.parseInt(parts[1]), Integer.parseInt(parts[0]));
		
		Registration reg = new Registration(username , password , email , name , surname, t );
		
		if(this.model.register(reg)) {
			String message = "Your registration was put on queue ";
			displayPopUp(message);
		}else {
			String message = "Your registration had failed to be put in queue ";
			displayPopUp(message);
		}
		
		this.showLogInView();
	}
}
