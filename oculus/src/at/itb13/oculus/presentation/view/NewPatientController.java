package at.itb13.oculus.presentation.view;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javafx.scene.control.ToggleGroup;
import at.itb13.oculus.application.patient.PatientCreation;
import at.itb13.oculus.domain.Doctor;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * TODO: Insert description here.
 * 
 * @author Caroline Meusburger
 * @since 10.04.2015
 */
public class NewPatientController {
	@FXML
	private TextField _firstNameField;
	@FXML
	private TextField _lastNameField;
	@FXML
	private TextField _SINField;
	@FXML
	private TextField _birthdayField;
	
	private ToggleGroup _genderGroup;
	
	@FXML
	private RadioButton _male;
	@FXML
	private RadioButton _female;	
	private String _gender;
	@FXML
	private ChoiceBox<Doctor> _doctorBox;
	@FXML
	private TextField _streetField;
	@FXML
	private TextField _postalCodeField;
	@FXML
	private TextField _cityField;
	@FXML
	private TextField _countryISOField;
	@FXML
	private TextField _phoneField;
	@FXML
	private TextField _emailField;
	
	private DateTimeFormatter _dateFormatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
	private LocalDate _date;
	
	private Stage _dialogStage;
  //  private PatientWithProperties2 _patient;
    private boolean okClicked = false;
    
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    ///	_doctorBox.setItems(FXCollections.observableArrayList("Dr. Hot", "Dr. Cool"));
    	_genderGroup = new ToggleGroup();
    	_female.setToggleGroup(_genderGroup);
    	_male.setToggleGroup(_genderGroup);
    	_female.setSelected(true);
    	
    }
    
    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        _dialogStage = dialogStage;
        
        // Set the application icon.
        _dialogStage.getIcons().add(new Image("file:resources/images/eye.png"));
    }
    public boolean isOkClicked() {
        return okClicked;
    }
    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {   
        	        	
        	//creating a new Patient and save it in the database
        	PatientCreation pc = new PatientCreation();
        	pc.createPatient(null, _SINField.getText(), _firstNameField.getText(), _lastNameField.getText(),_date, _gender, _streetField.getText(), _postalCodeField.getText(),_cityField.getText(), _countryISOField.getText(), _phoneField.getText(), _emailField.getText());
        	okClicked = true;
            _dialogStage.close();
        }
    }
    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        _dialogStage.close();
    }
    @FXML
    private void handleGender(){
    	if(_male.isSelected()){
    		_gender = "M";
    	}
    	else if(_female.isSelected()){
    		_gender = "F";
    	}
    	else{
    		_gender = "F";
    	}
    }
    
    
    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() throws ParseException {
        String errorMessage = "";

        if (_firstNameField.getText() == null || _firstNameField.getText().length() == 0) {
            errorMessage += "No valid first name!\n"; 
        }
        if (_lastNameField.getText() == null || _lastNameField.getText().length() == 0) {
            errorMessage += "No valid last name!\n"; 
        }
        if(_birthdayField.getText() != null && _birthdayField.getText().length() > 0){
        	if(_birthdayField.getText().matches("([0-9]{4})-([0-9]{2})-([0-9]{2})")){
        		_date = (LocalDate) _dateFormatter.parse(_birthdayField.getText().toString());
        	}else{
        		errorMessage += "No valid Birthday! Please make sure that you have choose the correct date format!\n";
        	}
        	
        }
        else{
        	_date = null;
        }


        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(_dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

}
