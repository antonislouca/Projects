package applicationVersionTwo;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UserController {

	private User user;
	 UserView view;
	 private UserModel model;

	public UserController(Stage primaryStage, User user) throws FileNotFoundException {
		this.user = user;
		this.view = new UserView(primaryStage);
		this.model = new UserModel();
		this.view.setController(this);
		this.view.generateTabPane();
		this.model.setController(this);

	}

	public static void startController(Stage primaryStage, User user) throws FileNotFoundException {
		UserController controller = new UserController(primaryStage, user);
		controller.view.startView();
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
	
	
	public void goBack() {
		try {
			this.view.generateTabPane();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.view.startView();
	}
	

	public boolean AddTicket(Ticket tick) {
		return (this.model.addTicket(this.user, tick));
		
	}

//	public Ticket[] currentickets(User user) {
//		Ticket[] currentTicket = this.model.currentTickets(user);
//		return currentTicket;
//	}

	// In views i dont know the user to give it as a parameter in the
	// function. Controller has the user and returns the tickets of the user.
	public ArrayList<Ticket> currentickets() {
		Ticket[] currentTicket = this.model.currentTickets(this.user);
		ArrayList<Ticket> list= new ArrayList<Ticket>();
		for (int i = 0; i < currentTicket.length; i++) {
			list.add(currentTicket[i]);
			
		}
		return list;
		
		
	}

	// return user name for ticket .
	public String getUserName() {
		return user.username;
	}

	// return pinakas apo tickets .
	public boolean updateTicket( Ticket tick) {
		System.out.println(tick);
	return (this.model.updateTicket(this.user, tick));
	
	}

	public  FAQ[] readDummyFaq() {
		return AdminModel.readFAQ();
	}
	public FAQ[] viewFAQS() {
		return (this.model.currentFAQS());
//		return readDummyFaq();
	}

	public String viewPolicies() {
		return (this.model.seePolicies());
	}

	public void editTicket(TicketForView btnsol, int tabIndex) {
		Tab tab = this.view.tabPane.getTabs().get(tabIndex);
        try {
			tab.setContent(this.view.getModifyTicketView(tabIndex,btnsol.getId(),btnsol.getPriority(),btnsol.getCategory(),btnsol.getUsername(),btnsol.getDescription()));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub

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
