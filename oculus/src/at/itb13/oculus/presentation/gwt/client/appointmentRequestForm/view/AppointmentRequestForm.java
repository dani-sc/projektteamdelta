
package at.itb13.oculus.presentation.gwt.client.appointmentRequestForm.view;

import java.util.Date;
import java.util.List;

import at.itb13.oculus.presentation.gwt.client.appointmentRequestForm.rpc.AppointmentCheckService;
import at.itb13.oculus.presentation.gwt.client.appointmentRequestForm.rpc.AppointmentCheckServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DatePicker;

public class AppointmentRequestForm extends Composite {
	private static AppointmentRequestFormUiBinder uiBinder = GWT
			.create(AppointmentRequestFormUiBinder.class);
	
	private final AppointmentCheckServiceAsync appointmentCheckService = GWT
		.create(AppointmentCheckService.class);
	
	interface AppointmentRequestFormUiBinder extends
			UiBinder<Widget, AppointmentRequestForm> {
	}

	public AppointmentRequestForm() {
		initWidget(uiBinder.createAndBindUi(this));
		datepicker1.addValueChangeHandler(new ValueChangeHandler<Date>() {
			public void onValueChange(ValueChangeEvent<Date> event) {
				datepicker1ErrorLabel.setText(event.getValue().toString());
			}
		});

		datepicker2.addValueChangeHandler(new ValueChangeHandler<Date>() {
			public void onValueChange(ValueChangeEvent<Date> event) {
				datepicker2ErrorLabel.setText(event.getValue().toString());
			}
		});
		
		AsyncCallback<List<String>> callback = new AsyncCallback<List<String>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Failed to connect to server. Please try again in a few minutes, or contact the system administrator.");
			}

			@Override
			public void onSuccess(List<String> result) {
				//add all eventtypes to ListBox
//				result.forEach(s -> eventTypeListBox.addItem(s));
				for(String s : result) {
					eventTypeListBox.addItem(s);
				}
			}
			
		};
		appointmentCheckService.getEventTypes(callback);
		
		addButton2.setVisible(false);
		fromTextBox2.setVisible(false);
		fromTextBox3.setVisible(false);
		toTextBox2.setVisible(false);
		toTextBox3.setVisible(false);
		fromLabel2.setVisible(false);
		toLabel2.setVisible(false);
		fromLabel3.setVisible(false);
		toLabel3.setVisible(false);
	}
	
	private boolean validInput;

	@UiField
	ListBox weekdayListBox;

	@UiField
	TextBox fromTextBox1;

	@UiField
	TextBox toTextBox1;
	
	@UiField
	TextBox fromTextBox2;

	@UiField
	TextBox toTextBox2;
	
	@UiField
	TextBox fromTextBox3;

	@UiField
	TextBox toTextBox3;

	@UiField
	DatePicker datepicker1;

	@UiField
	DatePicker datepicker2;

	@UiField
	Button addButton1;

	@UiField
	Label fromLabel1;
	
	@UiField
	Label fromLabel2;
	
	@UiField
	Label fromLabel3;
	
	@UiField
	Label toLabel1;
	
	@UiField
	Label toLabel2;
	
	@UiField
	Label toLabel3;
	
	@UiField
	Label fromErrorLabel1;

	@UiField
	Label toErrorLabel1;

	@UiField
	Label weekdayErrorLabel1;

	@UiField
	Button addButton2;

	@UiField
	Label fromErrorLabel2;

	@UiField
	Label toErrorLabel2;

	@UiField
	Label weekdayErrorLabel2;

	@UiField
	Label fromErrorLabel3;

	@UiField
	Label toErrorLabel3;

	@UiField
	Label weekdayErrorLabel3;
	
	@UiField
	Label datepicker1ErrorLabel;

	@UiField
	Label datepicker2ErrorLabel;
	
	@UiField
	ListBox eventTypeListBox;
	
	Button submitButton;
	
	@UiHandler("addButton1")
	void addButton1(ClickEvent event){
		addButton2.setVisible(true);
		fromTextBox2.setVisible(true);
		toTextBox2.setVisible(true);
		fromLabel2.setVisible(true);
		toLabel2.setVisible(true);
		addButton1.setVisible(false);
	}
	
	@UiHandler("addButton2")
	void addButton2(ClickEvent event){
		fromTextBox3.setVisible(true);
		toTextBox3.setVisible(true);
		fromLabel3.setVisible(true);
		toLabel3.setVisible(true);
		addButton2.setVisible(false);
	}

	@UiHandler({"fromTextBox1", "toTextBox1"})
	void onBox1Change(ValueChangeEvent<String> event) {
		if (event.getValue().length() != 0) {
			if(isTimeValid(event.getValue())){
				fromErrorLabel1.setText("This is not a valid time");
				fromErrorLabel1.setVisible(true);
				validInput = false;
			}
		} else {
			fromErrorLabel1.setVisible(false);
			validInput = true;
		}
	}
	
	@UiHandler({"fromTextBox2", "toTextBox2"})
	void onBox2Change(ValueChangeEvent<String> event) {
		if (event.getValue().length() != 0) {
			if(isTimeValid(event.getValue())){
				fromErrorLabel2.setText("This is not a valid time");
				fromErrorLabel2.setVisible(true);
				validInput = false;
			}
		} else {
			fromErrorLabel2.setVisible(false);
			validInput = true;
		}
	}
	
	@UiHandler({"fromTextBox3", "toTextBox3"})
	void onBox3Change(ValueChangeEvent<String> event) {
		if (event.getValue().length() != 0) {
			if(isTimeValid(event.getValue())){
				fromErrorLabel3.setText("This is not a valid time");
				fromErrorLabel3.setVisible(true);
				validInput = false;
			}
		} else {
			fromErrorLabel3.setVisible(false);
			validInput = true;
		}
	}
	
	private boolean isTimeValid(String time) {
		final String timePattern = "^(1?[0-9]|2[0-3]):[0-5][0-9]$";

		RegExp regExp = RegExp.compile(timePattern);
		MatchResult matcher = regExp.exec(time);

		return matcher != null;
	}
	
	@UiHandler("submitButton")
	void submitButton(ClickEvent event) {
		datepicker1.setValue(new Date(), true);
		datepicker2.setValue(new Date(), true);

		int index = weekdayListBox.getSelectedIndex();
		String weekday = weekdayListBox.getItemText(index);
		
		String from1 = fromTextBox1.getText();
		String to1 = toTextBox1.getText();
		
		String from2 = fromTextBox2.getText();
		String to2 = toTextBox2.getText();
		
		String from3 = fromTextBox3.getText();
		String to3 = toTextBox3.getText();
		
		Date date1 = datepicker1.getHighlightedDate();
		Date date2 = datepicker2.getHighlightedDate();
		
		String socialInsuranceNumber = "3333333333";
		String appointmentType = "Child Treatment";
		
		String dateString1 = DateTimeFormat.getFormat("MM/dd/yyyy").format(
				date1);
		String dateString2 = DateTimeFormat.getFormat("MM/dd/yyyy").format(
				date2);

		AsyncCallback<String> callback = new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Failed to connect to server. Please try again in a few minutes, or contact the system administrator.");
			}

			@Override
			public void onSuccess(String result) {
				
			}
		};
				
		appointmentCheckService.getPossibleAppointment(weekday, from1, to1, date1, date2, socialInsuranceNumber, appointmentType, callback);
		
		datepicker1ErrorLabel.setText(dateString1);
		datepicker2ErrorLabel.setText(dateString2);
		weekdayErrorLabel1.setText("Tag: " + weekday);
		fromErrorLabel1.setText("von: " + from1);
		toErrorLabel1.setText("bis: " + to1);
	}

}
