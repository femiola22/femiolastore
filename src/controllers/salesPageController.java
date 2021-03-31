package controllers; 


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ResourceBundle;

import helper.Sales;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import connectivity.ConnectionClass;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class salesPageController implements Initializable{

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    @FXML
    private TableView<Sales> tableSales;

    @FXML
    private TableColumn<?, String> colSN;

    @FXML
    private TableColumn<Sales, String> colPname;

    @FXML
    private TableColumn<Sales, String> colQuantity;

    @FXML
    private TableColumn<Sales, String> colPrice;

    @FXML
    private TableColumn<Sales, String> colDate;

    @FXML
    private TableColumn<Sales, String> colTime;

    @FXML
    private TableColumn<Sales, String> colCashier;

    @FXML
    private DatePicker dateSelected;

    @FXML
    private Button btnGo;

    @FXML
    private Button btnClose;
    
    @FXML
    private Button btnGoXX;
    
    @FXML
    void close(MouseEvent event) {
    	Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	stage.close();
    }
    
    @FXML
    void cancel(ActionEvent event) {
    	Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	stage.close();

    }
    
    @FXML
    void loadSel(ActionEvent event) {

    	loadSelectedDate();
    }
    
    @FXML
    void loadInterval(ActionEvent event) {

    	loadDateRange();
    }
    
    Connection con;
    String theDate;

    public String getTheDate() {
		return theDate;
	}

	public void setTheDate(String theDate) {
		this.theDate = theDate;
	}

	ObservableList<Sales> oList = FXCollections.observableArrayList();
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void loadSelectedDate(){
    	
    	Date dateSel = Date.valueOf(dateSelected.getValue());

    	setTheDate(dateSel.toString());
		tableSales.getItems().clear();
    	ConnectionClass cc = new ConnectionClass();
		con = cc.getConnection();
		
		try {
			String query4 = "SELECT * FROM salesTable WHERE date = ? ";
			PreparedStatement pst4 = con.prepareStatement(query4);
            pst4.setDate(1, dateSel);
            ResultSet rs = pst4.executeQuery();     
			while(rs.next()){
				String priceWithFormat = NumberFormat.getInstance().format(rs.getDouble("price"));
				oList.add(new Sales(rs.getString("productName"), rs.getString("quantity"), priceWithFormat, rs.getString("date"), rs.getString("time"), rs.getString("cashier")));
//				oList.add(new Sales(rs.getString("productName"), rs.getString("quantity"), rs.getString("price"), rs.getString("date"), rs.getString("time"), rs.getString("cashier")));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		try{			
			colSN.setCellValueFactory(column-> new ReadOnlyObjectWrapper(tableSales.getItems().indexOf(column.getValue())+1));
			colPname.setCellValueFactory(new PropertyValueFactory<>("name"));
			colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
			colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
			colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
			colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
			colCashier.setCellValueFactory(new PropertyValueFactory<>("cashier"));
					
			}catch(Exception ee){
				System.out.println(ee.getMessage());
			}
			
			tableSales.setItems(oList);
			
    }    	
    
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void loadDateRange(){
    	

    	Date dateFrom = Date.valueOf(startDate.getValue());
    	Date dateTo = Date.valueOf(endDate.getValue());

    	String abcDate = dateFrom.toString()+" to "+dateTo.toString();
    	setTheDate(abcDate);
    	
		tableSales.getItems().clear();
    	ConnectionClass cc = new ConnectionClass();
		con = cc.getConnection();
		
		try {
			String query4 = "SELECT * FROM salesTable WHERE date BETWEEN ? AND ? ";
			PreparedStatement pst4 = con.prepareStatement(query4);
            pst4.setDate(1, dateFrom);
            pst4.setDate(2, dateTo);
            ResultSet rs = pst4.executeQuery();     
			while(rs.next()){
				String priceWithFormat = NumberFormat.getInstance().format(rs.getDouble("price"));
				oList.add(new Sales(rs.getString("productName"), rs.getString("quantity"), priceWithFormat, rs.getString("date"), rs.getString("time"), rs.getString("cashier")));
//				oList.add(new Sales(rs.getString("productName"), rs.getString("quantity"), rs.getString("price"), rs.getString("date"), rs.getString("time"), rs.getString("cashier")));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		try{			
			colSN.setCellValueFactory(column-> new ReadOnlyObjectWrapper(tableSales.getItems().indexOf(column.getValue())+1));
			colPname.setCellValueFactory(new PropertyValueFactory<>("name"));
			colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
			colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
			colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
			colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
			colCashier.setCellValueFactory(new PropertyValueFactory<>("cashier"));
					
			}catch(Exception ee){
				System.out.println(ee.getMessage());
			}
			
			tableSales.setItems(oList);
			
    	
    }
    @FXML
    private Button btnExport;

    
    @FXML
    void exportTable(ActionEvent event) {

    	@SuppressWarnings("resource")
		Workbook workbook = new HSSFWorkbook();
        Sheet spreadsheet = workbook.createSheet("sample");

        Row row = spreadsheet.createRow(0);

        String xyz = getTheDate();
        
        for (int j = 0; j < tableSales.getColumns().size(); j++) {
            row.createCell(j).setCellValue(tableSales.getColumns().get(j).getText());
        }

        for (int i = 0; i < tableSales.getItems().size(); i++) {
            row = spreadsheet.createRow(i + 1);
            for (int j = 0; j < tableSales.getColumns().size(); j++) {
                if(tableSales.getColumns().get(j).getCellData(i) != null) { 
                    row.createCell(j).setCellValue(tableSales.getColumns().get(j).getCellData(i).toString()); 
                }
                else {
                    row.createCell(j).setCellValue("");
                }   
            }
        }

        FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream("salesFor "+xyz+".xls");
			workbook.write(fileOut);
	        fileOut.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Status Message");
			alert.setHeaderText("Info...");
			alert.setContentText("File export Failed.");
			alert.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Status Message");
			alert.setHeaderText("Info...");
			alert.setContentText("File export Failed.");
			alert.show();
		}
        
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Status Message");
		alert.setHeaderText("Info...");
		alert.setContentText("File export success.");
		alert.show();

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		if(userPageController.getCaller()=="salesPage"){
			System.out.println(userPageController.getCaller());
			btnExport.setVisible(false);
		}else {
			System.out.println(userPageController.getCaller());
			btnExport.setVisible(true);
		}
	}

    


}
