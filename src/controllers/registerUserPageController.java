package controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import connectivity.ConnectionClass;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class registerUserPageController implements Initializable{

	
	@FXML
    private TextField textFieldFullName;

    @FXML
    private TextField textFieldAddress;

    @FXML
    private TextField textFieldPhoneNumber;

    @FXML
    private TextField textFieldUsername;

    @FXML
    private PasswordField textFieldPassword;

    @FXML
    private ImageView imageViewProfileImage;

    @FXML
    private Button buttonUpload;

    @FXML
    private Button buttonClear;

    @FXML
    void clearAllFields(ActionEvent event) {

    	textFieldFullName.setText(null);
    	textFieldAddress.setText(null);
    	textFieldPhoneNumber.setText(null);;
    	textFieldUsername.setText(null);
    	textFieldPassword.setText(null);
    	imageViewProfileImage.setImage(null);
    }

    @FXML
    void selectImage(ActionEvent event) {

    	chooseimage();
    }

    @FXML
    void uploadToDB(ActionEvent event) {

    	String fullName = textFieldFullName.getText();
    	String address = textFieldAddress.getText();
    	String phoneNo = textFieldPhoneNumber.getText();
    	String username = textFieldUsername.getText();
    	String password = textFieldPassword.getText();
    	
    	try {
			fin = new FileInputStream(file2);
			len = (int) file2.length();
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
//			e1.printStackTrace();
			System.out.println(e1.getMessage());
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error Information Dialog");
			alert.setHeaderText("Error Message");
			alert.setContentText("No image selected");
			alert.show();
		} catch (Exception e) {
			// TODO: handle exception
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error Information Dialog");
			alert.setHeaderText("Error Message");
			alert.setContentText("No image selected");
			alert.show();
			return;
			
		}
    	try {
			
    	ConnectionClass cc = new ConnectionClass();
		con = cc.getConnection();
		
		pst = con.prepareStatement("insert into uandp (username,password,fullName,address,phoneNum,profileImage ) values(?,?,?,?,?,?)");
		
		
		
		pst.setString(1, username);
		pst.setString(2, password);
		pst.setString(3, fullName);
		pst.setString(4, address);
		pst.setString(5, phoneNo);
		pst.setBinaryStream(6, fin,len);
		
		int status = pst.executeUpdate();
		
		if(status>0){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("Information dialog");
			alert.setContentText("Information Inserted Successfully.");
			alert.show();
		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error Information Dialog");
			alert.setHeaderText("Error Message");
			alert.setContentText("Ome/More empty/Invalid Input");
			alert.show();
		}
		
    }
    
    int len;
	FileInputStream fin;
    public File file,file2;
    BufferedImage bufferedImage, bImage;
    PreparedStatement  pst;
    Connection con;
    
/////Resize image mock method.../////////////////////////
    Image scaled;
    public Image scale(Image source, int targetWidth, int targetHeight, boolean preserveRatio) {
        ImageView imageView = new ImageView(source);
        imageView.setPreserveRatio(preserveRatio);
        imageView.setFitWidth(targetWidth);
        imageView.setFitHeight(targetHeight);
        return imageView.snapshot(null, null);
    }
    
    //// Choose Image method...///////////////////////////
    public void chooseimage(){
    	FileChooser fileChooser = new FileChooser();
    	try{
    	file = fileChooser.showOpenDialog(null);    	
    		bufferedImage = ImageIO.read(file);
    		Image image = SwingFXUtils.toFXImage(bufferedImage,null);    		
    		scaled = scale(image, 145, 145, false);    		
    		file2 = new File("formattedImg.png");
    		bImage = SwingFXUtils.fromFXImage(scaled, null);
    		try{
    			ImageIO.write(bImage, "png", file2);
    		}catch(IOException ioe){
    			System.out.println(ioe.getMessage());
    		}
    		imageViewProfileImage.setImage(scaled);    		
    	} catch(IOException ex){
    		System.out.println(ex.getMessage());
    	}catch(Exception e){
    		System.out.println(e.getMessage());
    	}
    }
    	
    @FXML
    void close(MouseEvent event) {
    	Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	stage.close();
    }
    
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		
	}

}
