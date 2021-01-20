package applicationVersionTwo;

import javafx.scene.control.ScrollPane;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class AdminController {

	User user;

	// the user as parameter
	 AdminView view;
	 AdminModel model;
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public void showLogInView() {
		try {
			AuthenticationController.startController(this.view.primaryStage);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public AdminController(Stage primaryStage, User user) throws FileNotFoundException {
		this.view = new AdminView(primaryStage);
		this.model = new AdminModel();
		this.view.setController(this);
		this.view.generateTabPane();
		this.model.setController(this);
		this.user = user;
	}

	public static void startController(Stage primaryStage, User setuser) throws FileNotFoundException {
		AdminController controller = new AdminController(primaryStage, setuser);
		controller.view.startView();
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
		Scene scene1 = new Scene(layout, 300, 250);
		popupwindow.setScene(scene1);
		popupwindow.showAndWait();

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

	public ArrayList<User> getUsers() {
		return this.model.getUsers();

	}

	public ArrayList<Registration> getUnapprovedUsers() {
		Registration[] temp = AdminModel.getPendingRegistrations();
		if (temp.length == 0) {
			String message = "No pending registrations";
			displayPopUp(message);
		}
		return new ArrayList<Registration>(Arrays.asList(temp));
	}

//	public static ArrayList<User> generateDummyUserData() {
//		ArrayList<User> temp = new ArrayList<User>();
//		temp.add(new User(0, "kostis", "password", "hi@ucy.ac.cy", "99999999", false, "kostis", "andreou"));
//		temp.add(new User(0, "Giannis", "password1234", "hiBOI@ucy.ac.cyyyyyyyyyyyyyyyyyyyyy", "98999999", true, "kostis", "andreou"));
//		return temp;
//	}

	public ArrayList<Ticket> getTickets() {
//		ArrayList<Ticket> list=new ArrayList<>();
//		
//		list.add(new Ticket(3, "normal", "hardware", "sth", "not started yet", "A"));
//		list.add(new Ticket(1, "low", "hardware", "sth", "not started yet", "B"));
//		list.add(new Ticket(4, "high", "software", "sth","not started yet", "B"));
//		list.add(new Ticket(2, "normal", "hardware", "sth", "not started yet", "C"));
//		list.add(new Ticket(9, "low", "software", "sth", "not started yet", "A"));
//		list.add(new Ticket(7, "normal", "software", "sth", "not started yet", "A"));
//		return list;
		return AdminModel.getTickets();

	}

	public  User getInfoforUser(int id) {
		return AdminModel.getUserInfo(id);

	}

	public void showUserPassword(User user) {
		String message = user.getUsername() + "'s Password is: " + user.getPassword();
		displayPopUp(message);
	}

	public void removeAdminCapabilities(int tabIndex, User user) {
		boolean wasSuccessfull=this.model.RemoveUserFromAdmin(user);
		
//		boolean wasSuccessfull=false;
		if (wasSuccessfull==false) {
			String message = "Could not remove admin abilities";
			displayPopUp(message);
		}
		else {
			// ksanadixni to getUserStatusView
			Tab tab= this.view.tabPane.getTabs().get(tabIndex);
			try {
				tab.setContent(this.view.getUserStatusView(tabIndex));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void giveAdminCapabilities(int tabIndex, User user) {
		boolean wasSuccessfull=this.model.promoteUserToAdmin(user);
//		boolean wasSuccessfull=false;
		if (wasSuccessfull==false) {
			String message = "Could not promote user to admin";
			displayPopUp(message);
		}
		else {
			// ksanadixni to getUserStatusView
			Tab tab= this.view.tabPane.getTabs().get(tabIndex);
			try {
				tab.setContent(this.view.getUserStatusView(tabIndex));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void fireUser(int tabIndex,  User user) {
		boolean wasSuccessfull=this.model.fireUser(user.id);
//		boolean wasSuccessfull=false;
		if (wasSuccessfull==false) {
			String message = "Could not fire User";
			displayPopUp(message);
		}
		else {
			// ksanadixni to getUserStatusView
			Tab tab= this.view.tabPane.getTabs().get(tabIndex);
			this.displayPopUp("you have fired user "+ user.username);
			try {
				tab.setContent(this.view.getUserStatusView(tabIndex));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void approveUser(Registration user, int tabIndex) {
		boolean wasSuccessfull=this.model.approveUser(user);
//		boolean wasSuccessfull=false;
		if (wasSuccessfull==false) {
			String message = "Could not approve User";
			displayPopUp(message);
		}
		else {
			// ksanadixni to getUserStatusView
			Tab tab= this.view.tabPane.getTabs().get(tabIndex);
			Tab othertab= this.view.tabPane.getTabs().get(tabIndex-1);
			try {
				tab.setContent(this.view.getApproveUsersView(tabIndex));
				othertab.setContent(this.view.getUserStatusView(tabIndex-1));

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public void disapproveUser(Registration user, int tabIndex) {
		boolean wasSuccessfull=this.model.disapproveUser(user);
//		boolean wasSuccessfull=false;
		if (wasSuccessfull==false) {
			String message = "Could not disapprove User";
			displayPopUp(message);
		}
		else {
			// ksanadixni to getUserStatusView
			Tab tab= this.view.tabPane.getTabs().get(tabIndex);
			tab.setContent(this.view.getApproveUsersView(tabIndex));
		}
	}


	
	public  ArrayList<Report> createReport(int tabIndex) {
		
		ArrayList<Report> list = new ArrayList<Report>();
		List<Ticket> array = AdminModel.createReport();
		
		for (int i = 0; i < array.size(); i++) {
			
			int id = array.get(i).getId();
			String name = array.get(i).getUsername();
			String cate = array.get(i).getCategory();
			String pr = array.get(i).getPriority();
			double dur = array.get(i).getDuration();			
			String hd = array.get(i).getHandlerUsername();
			
			list.add(new Report(id,name,cate,pr,dur,hd));
			
		}

		return list;

	}


	public  FAQ[] readFaq() {
		
		return AdminModel.readFAQ();

	}

	public  void updateFAQ(int [] IDS,TextArea [] Questions , TextArea [] Answers, int tabIndex) throws FileNotFoundException {
		FAQ [] NEWFAQ= new FAQ[IDS.length];
		for(int k=0;k<NEWFAQ.length;k++) {
			NEWFAQ[k]=new FAQ(IDS[k],Questions[k].getText(),Answers[k].getText());
		}
		if (!AdminModel.updateFAQ(NEWFAQ)) {
			String message = "Could not update FAQ section";
			displayPopUp(message);
		} else {
			String message = "FAQ updated successfully.";
			displayPopUp(message);
		}
		Tab tab=this.view.tabPane.getTabs().get(tabIndex);
		tab.setContent(this.view.getFAQAdminView(tabIndex));
	}

	public  void updateTicket(Ticket ticket, int tabIndex) {
		//System.out.println(ticket);
		AdminModel.updateTicket(ticket);
	
//		Tab tab= this.view.tabPane.getTabs().get(tabIndex);
//		tab.setContent(this.view.getApproveUsersView(tabIndex));
	}

	public  void addQ(String question, String answer, int tabIndex) throws FileNotFoundException {
	  	if (!AdminModel.addQuestion(question, answer)) {
	  		String message = "A faq could not be added";
	  		displayPopUp(message);
	  		
	  	} else {
	  		String message = "Faq added successfully";
	  		displayPopUp(message);
	  		
	  	}
		Tab tab=this.view.tabPane.getTabs().get(tabIndex);
		tab.setContent(this.view.getFAQAdminView(tabIndex));
		
	}

	public void updatePolicies(String newPolicies, int tabIndex) {
		if(model.updatePolicies(newPolicies)) {
			displayPopUp("Updated Policies");
		} else {
		displayPopUp("The Policies were not able to be updated");
		}

		try {
			Tab tab = this.view.tabPane.getTabs().get(tabIndex);
			tab.setContent(this.view.getViewPoliciesAdmin(tabIndex));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Object approveUser(int tabIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPolicies() {
		return (this.model.seePolicies());
	}


	public void AssignTicket(int id, int tabIndex) {
		// TODO Auto-generated method stub

	}
	public void showEditFaqView(int tabnum) throws FileNotFoundException {
		try {
			Tab tab = this.view.tabPane.getTabs().get(tabnum);
			tab.setContent(new ScrollPane(this.view.getEditQuestionView(tabnum)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void showAdminFaqView(int tabnum) throws FileNotFoundException {
		Tab tab = this.view.tabPane.getTabs().get(tabnum);
		tab.setContent(this.view.getFAQAdminView(tabnum));
	}
	public void showAdminPoliciesView(int tabnum) throws FileNotFoundException {
		Tab tab = this.view.tabPane.getTabs().get(tabnum);
		tab.setContent(this.view.getViewPoliciesAdmin(tabnum));
	}
	public void showAddFaqView(int tabnum) throws FileNotFoundException {
		Tab tab = this.view.tabPane.getTabs().get(tabnum);
		tab.setContent(this.view.getAddQuestionView(tabnum));
	}
	
	public void showEditPoliciesView(int tabnum) throws FileNotFoundException {
		Tab tab = this.view.tabPane.getTabs().get(tabnum);
		tab.setContent(this.view.getModifyPoliciesView(tabnum));
	}
	
	
	public void editTicket(TicketForView btnsol,int tabIndex) {
		try {
			Tab tab = this.view.tabPane.getTabs().get(tabIndex);
	        tab.setContent(this.view.getModifyTicketView(tabIndex,btnsol.getId(),btnsol.getPriority(),btnsol.getCategory(),btnsol.getUsername(),btnsol.getDescription()));
		//	displayPopUp("Ticket Modified");
			//this.view.primaryStage.setScene(new Scene(this.view.getAdminTicketView(tabIndex)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//calls for editTicketVIEW
		
		
	}
	// delete the FAQ with this ID from the base ty . 
	public void deleteQuestion(int ID,int tabnum) {
		if(this.model.deleteQuestion(ID)) {
			displayPopUp("Question deleted successfully");
		}else {
			displayPopUp("Failed to be deleted");
		}
		try {
			Tab tab=this.view.tabPane.getTabs().get(tabnum);
			tab.setContent(new ScrollPane(this.view.getEditQuestionView(tabnum)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @param list
	 */
	public void generateReport(ArrayList<Report> list) {
		
        XSSFWorkbook work = new XSSFWorkbook();
        XSSFSheet sheet = work.createSheet("Monthly_Report");
        
        
        
        Object[][] bookData = new Object[list.size()][6];
        
 	   bookData[0][0] = "Ticket id";
 	   bookData[0][1] = "Username";
 	   bookData[0][2] = "Priority";
 	   bookData[0][3] = "Category";
 	   bookData[0][4] = "Handler";
 	   bookData[0][5] = "Duration";
        
       for (int i = 1; i < list.size(); i++) {
		
    	   bookData[i][0] = list.get(i).getId();
    	   bookData[i][1] = list.get(i).getUsername();
    	   bookData[i][2] = list.get(i).getPriority();
    	   bookData[i][3] = list.get(i).getCategory();
    	   bookData[i][4] = list.get(i).getHandler();
    	   bookData[i][5] = list.get(i).getDuration() + " ";
	}
        
 
        int rowCount = 0;
         
        for (Object[] aBook : bookData) {
            Row row = sheet.createRow(++rowCount);
             
            int columnCount = 0;
             
            for (Object field : aBook) {
                Cell cell = row.createCell(++columnCount);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }
             
        }
         
        Calendar cal = Calendar.getInstance();
        String month = new SimpleDateFormat("MMM").format(cal.getTime());
        
        displayPopUp("Report has been exported successfully!");
        
        try (FileOutputStream outputStream = new FileOutputStream(month + "_Report.xlsx")) {
            work.write(outputStream);
        } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	
	

}
