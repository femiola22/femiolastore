 package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

//import javax.swing.JOptionPane;

import connectivity.ConnectionClass;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class loginPageController implements Initializable {
	
	
	@FXML private Label labForFeedback;
 	@FXML private Button buttonLogin;
	@FXML private TextField fieldUsername;
	@FXML private TextField fieldPassword;
	
	@FXML
    void close(MouseEvent event) {
    	Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	stage.close();
    	Platform.exit();
    }
	
	Connection con;
	PreparedStatement  pst;
	ResultSet rs;
	static String name;
	
	public static String getName() {
		return name;
	}

	public void setName(String name) {
		loginPageController.name = name;
	}

	double x,y;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
//		fieldUsername.setText("admin");
//		fieldPassword.setText("letus");
		
		/***
		 * Method created to test network availability...
		 */
//		Socket sock = new Socket();
//		InetSocketAddress addr = new InetSocketAddress("www.google.com",80);
//		try{
//			sock.connect(addr,3000);
//			labForFeedback.setText("true");
//		}catch(Exception e){
//			labForFeedback.setText("google conn error...");
//		}finally{
//			try {
//				sock.close();
//			}catch (Exception ee){
//				
//			}
//		}
		
		
		ConnectionClass cc = new ConnectionClass();
		if( cc.getConnection() !=null){
		
		try {
			labForFeedback.setText("...Live");
			labForFeedback.setTextFill(Paint.valueOf("#61b14f"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		}else{
			
			
			buttonLogin.setDisable(true);
			labForFeedback.setText("Error Connecting to Database... Please Start Server.");
		}
		
		
	}
	
	public void printAction(){

		String username = fieldUsername.getText();
		String password = fieldPassword.getText();

		
			try {
				ConnectionClass cc = new ConnectionClass();
				con = cc.getConnection();
				
				pst = con.prepareStatement("select * from uandp where username=? and password=?");
				
				pst.setString(1, username);
				pst.setString(2, password);
				
				rs = pst.executeQuery();
				
				if(rs.next()){
//					JOptionPane.showMessageDialog(null, "Login Success");
					
//					Alert alert = new Alert(AlertType.CONFIRMATION, "Login Success...!!");
//					alert.setHeaderText("Login Status");
//					alert.show();
					
					setName(username);
					try{
			    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/userPage.fxml"));
			    		Parent root1 = (Parent) fxmlLoader.load();
			    		Stage stage = new Stage ();
			    		stage.initStyle(StageStyle.UNDECORATED);
			    		stage.setTitle("POS Page");
			    		stage.setScene(new Scene(root1));
			    		//drag it here
				        root1.setOnMousePressed(event -> {
				            x = event.getSceneX();
				            y = event.getSceneY();
				        });
				        root1.setOnMouseDragged(event -> {

				            stage.setX(event.getScreenX() - x);
				            stage.setY(event.getScreenY() - y);

				        });
			    		stage.show();

						
			    		
			    	}catch(Exception e){
			    		System.out.println(e.getMessage());
			    	}
					
				}
				else{
					
					fieldPassword.setText(null);
				
					Alert alert = new Alert(AlertType.CONFIRMATION, "Login Failed.");
					alert.setHeaderText("Login Status");
					alert.show();
					
//					///////////////////////////temporary
//					try{
//						setName(username);
//			    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/application/userPage.fxml"));
//			    		Parent root1 = (Parent) fxmlLoader.load();
//			    		Stage stage = new Stage ();
//			    		stage.initStyle(StageStyle.UNDECORATED);
//			    		stage.setTitle("POS Page");
//			    		stage.setScene(new Scene(root1));
//			    		
//			    		//drag it here
//				        root1.setOnMousePressed(event -> {
//				            x = event.getSceneX();
//				            y = event.getSceneY();
//				        });
//				        root1.setOnMouseDragged(event -> {
//
//				            stage.setX(event.getScreenX() - x);
//				            stage.setY(event.getScreenY() - y);
//
//				        });
//				        
//			    		stage.show();
//			    		
//			    	}catch(Exception e){
//			    		System.out.println(e.getMessage());
//			    	} //////////////////////////////////////////////temporary

				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}

}
