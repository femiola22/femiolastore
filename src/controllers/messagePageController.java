package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import connectivity.ConnectionClass;
//import javafx.animation.KeyFrame;
//import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
//import javafx.util.Duration;

public class messagePageController implements Initializable{

    @FXML
    private Button btnSend;

    @FXML
    private TextField outgoingMessageField;

    @FXML
    private TextField incomingMessageField;

    @FXML
    private TextField recieverField;

    
    Connection con;
    PreparedStatement pst;
    @FXML
    void sendMessage(ActionEvent event) {

//    	String messageToSend = outgoingMessageField.getText();
//    	ConnectionClass cc = new ConnectionClass();
//		con = cc.getConnection();
//		String sender = loginPageController.getName();
//		String reciever = recieverField.getText();
//		String status = "unread";
		
		Alert alert2 = new Alert(AlertType.INFORMATION);
		alert2.setTitle("Error Information Dialog");
//		alert2.setHeaderText("Error Message");
		alert2.setContentText("Configuration removed.");
		alert2.show();


//		
//		
//		try {
//			
//		pst = con.prepareStatement("insert into messagetable (sender,reciever,message,status) values(?,?,?,?)");
//		
//		pst.setString(1, sender);
//		pst.setString(2, reciever);
//		pst.setString(3, messageToSend);
//		pst.setString(4, status);
//		
//		int statusx = pst.executeUpdate();
//		
//		if(statusx>0){
//			Alert alert = new Alert(AlertType.INFORMATION);
//			alert.setTitle("Information Dialog");
//			alert.setHeaderText("Information dialog");
//			alert.setContentText("Db products Table Updated successfully.");
////			alert.show();
//			
//		}
//			statusx = pst.executeUpdate();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
    }
    String msg;
    int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ConnectionClass cc = new ConnectionClass();
		con = cc.getConnection();
//		runListener();
		
//		Timeline timeline = new Timeline(
//				new KeyFrame(Duration.millis(500),ae -> runListener()));
//				timeline.setCycleCount(1); //leave as comment
//				timeline.setCycleCount(Timeline.INDEFINITE);
//				timeline.play();
				
	}
	
	
	public void runListener(){
		

		String recciever = loginPageController.getName();
		String status = "unread";
		
//		while(true){
		
		try {
					pst = con.prepareStatement("select * from messagetable where reciever=? and status=? ");
				
					pst.setString(1, recciever);
					pst.setString(2, status);
		
					ResultSet rs = pst.executeQuery();
				
					if(rs.next()){
					
						setId(rs.getInt("id"));
						setMsg(rs.getString("message"));
						incomingMessageField.setText(getMsg());
							
						try{
						String query6 = "UPDATE messagetable SET status = ? WHERE id =? ";
					
						String stat = "read";
						PreparedStatement pst6 =con.prepareStatement(query6);
						pst6.setString(1, stat);
						pst6.setInt(2, getId());
	                
						pst6.execute();
						pst6.close();
						}catch(Exception eee){
							System.out.println(eee.getMessage());
						}
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
//		}
	}
	
	@FXML
    void close(MouseEvent event) {
		
    	Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	stage.close();
    }

}
