package at.itb13.oculus.presentation.view;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import at.itb13.oculus.application.ControllerFacade;
import at.itb13.oculus.application.exceptions.InvalidInputException;
import at.itb13.oculus.domain.readonlyinterfaces.PatientRO;
import at.itb13.oculus.domain.readonlyinterfaces.QueueEntryRO;
import at.itb13.oculus.domain.readonlyinterfaces.QueueRO;
import at.itb13.oculus.presentation.OculusMain;
import at.itb13.oculus.presentation.util.QueueSringConverter;

/**
 * TODO: Insert description here.
 * 
 * @author Caroline Meusburger
 * @since 15.04.2015
 */
public class TabQueueController {
	
	private static final Logger _logger = LogManager.getLogger(TabQueueController.class.getName());
	
	private static final int REFRESH_INTERVAL = 10000;	// in milliseconds
	
	@FXML
	private ListView<QueueEntryRO> _queueEntrysListView;
	@FXML
	private ComboBox<QueueRO> _queueBox;
	private QueueRO _queue;
	
	private ObservableList<QueueEntryRO> _queueEntryList = FXCollections.observableArrayList();
	
	@FXML
	private Label _dateTimeLabel;
	@FXML
	private Label _typeLabel;
	@FXML
	private Label _reasonLabel;
	@FXML
	private Button _endExaminationButton;
	@FXML
	private ComboBox<QueueRO> _nextQueueBox;
	@FXML
	private Button _insertButton;
	@FXML
	private Label _orLabel;
	@FXML
	private BorderPane _patientRecordBorderPane;
	//general Attributes
	private OculusMain _main;
	
	private Timer _timer;
			
	//general Methods
	public void setMain(OculusMain main) {
		_main = main;		        
	}
			 
	@FXML
	private void initialize() {
		
		setItemsToQueueBox();
		
		_queueEntrysListView.setItems(_queueEntryList);
		
		 _queueEntrysListView.setCellFactory(new Callback<ListView<QueueEntryRO>, ListCell<QueueEntryRO>>(){

			@Override
			public ListCell<QueueEntryRO> call(ListView<QueueEntryRO> param) {
				ListCell<QueueEntryRO> cell = new ListCell<QueueEntryRO>(){
					 
                    @Override
                    protected void updateItem(QueueEntryRO t, boolean bln) {
                        super.updateItem(t, bln);
                        if(t != null){
                        	setText(t.getPatient().getFirstName() + " "+ t.getPatient().getLastName()+"\n" +t.getArrivalTime());
                        }else{
                        	setText("");
                        }
                    }
				};
				return cell;
			}
			 
		 });
		showAppointmentInfo(null);	 
		
		_queueEntrysListView
			.getSelectionModel().selectedItemProperty().addListener(
					(observable, oldValue, newValue) -> {
						// if newValue == null, methods just clear the widgets if something was displayed before
						_main.showPatientRecord(_patientRecordBorderPane, (newValue == null) ? null : newValue.getPatient());
						showAppointmentInfo(newValue);
					}
			);
		
	}
	
	/**
	 * Automatically refresh the queues every {@link #REFRESH_INTERVAL} seconds
	 */
	public void startQueueReloader() {
		if(_timer == null) {
			_timer = new Timer("QueueReloader");
			_timer.schedule(new TimerTask() {
				@Override
				public void run() {
					_logger.trace("Refreshing Queues");
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							refreshQueueOnce();
						}
					});
				}
			}, 0, REFRESH_INTERVAL);
		}
	}
	
	public void refreshQueueOnce() {
		System.out.println("refreshing start");
		ControllerFacade.getInstance().refreshQueueController();
		setItemsToQueueBox();
		setQueueEntriesInList();
		System.out.println("refreshing end");
	}
	
	public void stopQueueReloader() {
		if(_timer != null) {
			_timer.cancel();
			_timer = null;
		}
	}
	
	private void clearQueue() {
		_queueEntryList.clear();
		
	}

	private void setItemsToQueueBox() {
		_queueBox.setConverter(new QueueSringConverter());
		_nextQueueBox.setConverter(new QueueSringConverter());
		List<QueueRO> queues = new LinkedList<>();
		ControllerFacade.getInstance().getAllQueueController().forEach( qCol -> {queues.add(qCol.getQueue());} );
		_queueBox.getItems().setAll(queues);
		_nextQueueBox.getItems().setAll(queues);
	}
	
	@FXML
	private void handleQueueComboBox() {
		_queue = _queueBox.getSelectionModel().getSelectedItem();
		setQueueEntriesInList();
	}
	
	private void setQueueEntriesInList() {
		QueueEntryRO entrySelected = _queueEntrysListView.getSelectionModel().getSelectedItem();
		
		clearQueue();
		if(_queue != null) {
			at.itb13.oculus.application.queue.QueueController controller = ControllerFacade.getInstance().getQueueController(_queue);
			List<QueueEntryRO> entries = (List<QueueEntryRO>) controller.getQueueEntries();
			_queueEntryList.addAll(entries);
		
			if(entrySelected != null && _queue.contains(entrySelected.getQueueEntryId())) {	// reselect (necessary if updating)
				for(QueueEntryRO entry : entries) {
					if(entry.getQueueEntryId().equals(entrySelected.getQueueEntryId())) {
						_queueEntrysListView.getSelectionModel().select(entry);	// need to select an entry that's contained in entries; not another one!
						break;
					}
				}
			}
		}
	}
	
	private void showAppointmentInfo(QueueEntryRO entry){
		if(entry != null){
			_nextQueueBox.setVisible(true);
			_insertButton.setVisible(true);
			_endExaminationButton.setVisible(true);
			_orLabel.setText("or");
			
			
			if((entry.getCalendarEvent() != null)){
				_dateTimeLabel.setText(entry.getCalendarEvent().getEventStart().toString());
				_typeLabel.setText(entry.getCalendarEvent().getEventtype().getEventTypeName());
				_reasonLabel.setText(entry.getCalendarEvent().getDescription());
				
				
			}else{
				_dateTimeLabel.setText("");
				_typeLabel.setText("");
				_reasonLabel.setText("");
				
			}
		}else{
			_nextQueueBox.setVisible(false);
			_insertButton.setVisible(false);
			_endExaminationButton.setVisible(false);
			_orLabel.setText("");
		}
		
	}

	@FXML
	private void handleRelocateQueueButton() {

		QueueRO queueNext = _nextQueueBox.getSelectionModel().getSelectedItem();
		QueueRO queueOld = _queueBox.getSelectionModel().getSelectedItem();
		PatientRO patient = _queueEntrysListView.getSelectionModel().getSelectedItem().getPatient();

		if (queueNext != null && queueOld != null && patient != null) {
			at.itb13.oculus.application.queue.QueueController controllerNext = ControllerFacade.getInstance().getQueueController(queueNext);
			at.itb13.oculus.application.queue.QueueController controllerOld = ControllerFacade.getInstance().getQueueController(queueOld);
			
			if (controllerNext != null && controllerOld != null) {
				if(controllerOld.removeQueueEntry(patient)) {
					try {
						if(controllerNext.pushQueueEntry(patient)) {
							refreshQueueOnce();
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setContentText("Patient is added to Queue");
							alert.showAndWait();
							return;
						}
					} catch (InvalidInputException e) {
						_logger.error(e);	// Won't happen because the patient was just removed an instant ago
					}
				} else {
					_logger.error("Failed to remove patient (" + patient.getPatientId() + ") from queue.");
				}
			} else {
				_logger.error("Could not load QueueController for Queue with doctor '" + queueNext.getDoctor().getDoctorId()
						+ "' and with orthoptist '" + queueNext.getOrthoptist().getOrthoptistId() + "'!");
			}
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Failed to move patient to another queue!");
			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("No queue selected");
			alert.setContentText("Please choose a Queue before insert.");
			alert.setTitle("No queue selected");
			alert.showAndWait();
		}
	}
	
	@FXML
	private void handleEndExamination(){
		at.itb13.oculus.application.queue.QueueController controller = ControllerFacade.getInstance().getQueueController(_queue);
		if(controller.removeQueueEntry(_queueEntrysListView.getSelectionModel().getSelectedItem().getPatient())) {
			refreshQueueOnce();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("Examination is closed. Patient is no longer in a Waitinglist.");
			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Failed to close examination. Patient is still in a Waitinglist.");
			alert.showAndWait();
		}
	}
}
