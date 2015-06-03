package at.itb13.oculus.presentation.gwt.client.appointmentRequestForm.rpc;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import at.itb13.oculus.presentation.gwt.shared.CalendarEvent;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * TODO: Insert description here.
 *
 * @author Florin Metzler
 * @since 29.05.2015
 */
@RemoteServiceRelativePath("AppointmentCheckService")
public interface AppointmentCheckService extends RemoteService {
	CalendarEvent getPossibleAppointment(String weekday, String from, String to, 
									Date start, Date end, boolean isSameDay, String appointmentType);
	
	List<String> getEventTypes();
	Boolean isInWorkingHours(String weekday, String from, String to);
}
