package applicationVersionTwo;

import java.io.FileNotFoundException;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
		Scene scene1 = new Scene(layout, 300, 250);
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
	
	

//	public User dummyAdminUser() {
//		return new User(0, "sherod01","password","email","phone",
//				true,"kostis","ne");
//	}
//	public User dummyNoNAdminUser() {
//		return new User(0, "sherod01","password","email","phone",
//				false,"kostis","ne");
//	}

	
	public void signIn(String username, String password) throws FileNotFoundException {
		User user=this.model.authenticate(username, password);
		if (user!=null && user.getPassword().equals(password) ) {
			if(user.isAdmin()==true) {

				AdminController.startController(this.view.primaryStage, user);
			}
			else {
				UserController.startController(this.view.primaryStage, user);
			}
		}
		else {
			//oste na men tou pis oti exi tuto to username. gia na spamari meta. gia safty
			AdminController.displayPopUp("There is no user with this username or with this password\n");
		}
//    AdminController.startController(this.view.primaryStage, user);
//	UserController.startController(this.view.primaryStage, user);
	}

	public static void startController(Stage primaryStage) throws FileNotFoundException {
		AuthenticationController controller = new AuthenticationController(primaryStage);
		controller.startConnection();
		controller.view.startView();
	}

	private void startConnection() {
		AuthenticationModel.connectToDB();
	}

	public void signUp(String username,String password,String name,String surname,String companyTel,String compName, String email) throws FileNotFoundException {
		// other stuff
		Registration reg = new Registration(0 , username , password , companyTel , email , name , surname );
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
