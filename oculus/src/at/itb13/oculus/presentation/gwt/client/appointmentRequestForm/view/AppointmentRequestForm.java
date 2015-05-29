package at.itb13.oculus.presentation.gwt.client.appointmentRequestForm.view;

import at.itb13.oculus.presentation.gwt.client.Index;
import at.itb13.oculus.presentation.gwt.client.appointmentOverview.view.AppointmentOverview;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
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

	interface AppointmentRequestFormUiBinder extends
			UiBinder<Widget, AppointmentRequestForm> {
	}

	public AppointmentRequestForm() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	private boolean validInput;
	
	@UiField
	ListBox fromToTable;
	
	@UiField
	TextBox FromTextBox;
	
	@UiField
	TextBox ToTextBox;
	
	@UiField
	DatePicker datepicker1;
	
	@UiField
	DatePicker datepicker2;
	
	@UiField
	Button addButton;
	
	@UiHandler("addButton")
	void onClickLoginButton(ClickEvent event) {
		String weekday = fromToTable.getItemText(0); //TODO index
		String from = FromTextBox.getText();
		String to = ToTextBox.getText();

		System.out.println("Tag: " + weekday);
		System.out.println("von: " + from);
		System.out.println("bis: " + to);
	}
}
