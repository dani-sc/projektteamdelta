/*
 * Copyright (c) 2015 Team F
 *
 * This file is part of Oculus.
 * Oculus is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * Oculus is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with Oculus.  If not, see <http://www.gnu.org/licenses/>.
 */

package at.oculus.teamf.presentation.view;

import at.oculus.teamf.application.facade.dependenceResolverTB2.exceptions.NotInitatedExceptions;
import at.oculus.teamf.domain.entity.interfaces.IDoctor;
import at.oculus.teamf.domain.entity.interfaces.IExaminationProtocol;
import at.oculus.teamf.domain.entity.interfaces.IPatient;
import at.oculus.teamf.presentation.view.models.Model;
import at.oculus.teamf.technical.loggin.ILogger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Fabian on 01.05.2015.
 */
public class DiagnosisController implements Initializable,ILogger {
    //private IPatient patient;

    @FXML private Button saveDiagnosisButton;
    @FXML private TextField textDiagnosisTitle;
    @FXML private TextArea textDiagnosisDescription;

    private Model _model = Model.getInstance();
    private IExaminationProtocol currexam;
    private Stage _dialogStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // get examination object
        currexam = _model.getExaminationModel().getCurrentExaminationProtocol();
//        log.debug("ADD Diagnosis to Examination: " + currexam.getPatient().getLastName());

        // load image resources for buttons
        Image imageSaveIcon = new Image(getClass().getResourceAsStream("/res/icon_save.png"));
        saveDiagnosisButton.setGraphic(new ImageView(imageSaveIcon));
    }

    // *****************************************************************************************************************
    //
    // BUTTON HANDLERS
    //
    // *****************************************************************************************************************

    @FXML
    public void saveDiagnosisButtonHandler (ActionEvent actionEvent){
        if (textDiagnosisTitle.getText().isEmpty() == false && textDiagnosisDescription.getText().isEmpty() == false) {
            try {
                _model.getExaminationModel().addNewPatientDiagnosis(textDiagnosisTitle.getText(), textDiagnosisDescription.getText(), (IDoctor) _model.getLoggedInUser(), currexam);
            } catch (NotInitatedExceptions notInitatedExceptions) {
                //Todo
                notInitatedExceptions.printStackTrace();
            }
            /* -- Team D: Removed because we don't have a dynamic tab loading system. -- */
//            IPatient currpatient  = _model.getTabModel().getPatientFromSelectedTab(_model.getTabModel().getSelectedTab());
//            Tab origintab = _model.getTabModel().getTabFromPatientAndID("newexamination", currpatient);
//            _model.getTabModel().closeSelectedAndSwitchTab(origintab);
            /* -- -- -- */
            /* -- Team D: Added in order to close the popup, because we don't use dynamic tabs. -- */
            _dialogStage.close();
            /* -- -- -- */
        } else {
            DialogBoxController.getInstance().showInformationDialog("Data needed", "Please add diagnosis title and/or description");
        }
    }
    
    /* -- Team D: Added because we use popups instead of dynamic tabs -- */
    public void setDialogStage(Stage dialogStage) {
    	_dialogStage = dialogStage;
    }
    /* -- -- -- */
}
