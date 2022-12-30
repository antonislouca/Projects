package applicationVersionTwo;

import javafx.scene.control.Button;

import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage primaryStage) throws FileNotFoundException {
		AuthenticationController.startController(primaryStage);
	}


//	@Override	
//	public void start(Stage primaryStage) {
//		
//		boolean succ = LoginModel.connectToDB();
//		System.out.println(succ);
//		
//		primaryStage.setTitle("Place a Ticket");
//		GridPane grid = new GridPane();
//		grid.setAlignment(Pos.CENTER);
//		grid.setHgap(10);
//		grid.setVgap(10);
//		grid.setPadding(new Insets(25, 25, 25, 25));
//		Text scenetitle = new Text("Place a Ticket");
//		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
//		grid.add(scenetitle, 0, 0, 10, 1);
//
//		Label userName = new Label("User Name:");
//		grid.add(userName, 0, 2);
//
//		Label Name = new Label(" Periklis Petrou ");
//		grid.add(Name, 1, 2);
//
//		Label cat = new Label("Category");
//		grid.add(cat, 0, 3);
//		
//				
//		 final ComboBox TypeComboBox = new ComboBox();
//	        TypeComboBox.getItems().addAll(
//	            "Hardware",
//	            "Softwaree",
//	            "other" 
//	        );        
//	        
//	      grid.add(TypeComboBox, 1, 3);
//
//		
//		Label pr = new Label("Priority");
//		grid.add(pr, 0, 4);
//		
//		
//		
//		  final ComboBox priorityComboBox = new ComboBox();
//	        priorityComboBox.getItems().addAll(
//	            "High",
//	            "Normal",
//	            "Low"
//	        );   
//
//		grid.add(priorityComboBox, 1, 4);	
//		
//		Label desc = new Label("Description");
//		grid.add(desc, 0, 5);
//
//		TextField descfield = new TextField();
//		grid.add(descfield, 1, 5);
//		
//		
//        Button button = new Button("Submit");
//        grid.add(button, 0, 7);
//			
//		
//
//		Scene scene = new Scene(grid, 500 , 500);
//		primaryStage.setScene(scene);	
//        primaryStage.show();
//	}
//	
//	public static void main(String[] args) {
//		System.out.println("ayyy");
//		launch(args);
//	
//	}
}