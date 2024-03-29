package at.itb13.oculus.application.calendar;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import at.itb13.oculus.domain.Calendar;
import at.itb13.oculus.domain.CalendarEvent;
import at.itb13.oculus.domain.Patient;
import at.itb13.oculus.domain.readonlyinterfaces.CalendarEventRO;
import at.itb13.oculus.domain.readonlyinterfaces.CalendarRO;
import at.itb13.oculus.domain.readonlyinterfaces.PatientRO;
import at.itb13.oculus.technicalServices.dao.CalendarEventDao;
import at.itb13.oculus.technicalServices.dao.PatientDao;
import at.itb13.teamD.application.exceptions.InvalidInputException;

/**
 * provides methodes used for the Calendar, 
 *
 * @author Florin Metzler, Daniel Scheffknecht
 * @since 09.04.2015
 */
public class CalendarController {
	private static final Logger _logger = LogManager.getLogger(CalendarController.class.getName());
	private Calendar _calendar;
	
	public CalendarController(Calendar calendar) {
		_calendar = calendar;
	}
	
	public CalendarRO getCalendar() {
		return _calendar;
	}
	
	/**
	 * Loads a list of CalendarEvent in a chosen timespan.
	 * 
	 * @param startDate the start Date of the timespan. (inclusive)
	 * @param endDate the end Date of the timespan. (inclusive)
	 * @return A list of CalendarEvent.
	 * @throws InvalidInputException when the startDate is bigger then the endDate.
	 */
	public List<? extends CalendarEventRO> getCalendarEventsInTimespan(LocalDateTime startDate, LocalDateTime endDate) throws InvalidInputException {
		if(startDate.isBefore(endDate)){
			return _calendar.getCalendarEventsForTimespan(startDate, endDate);			
		}else{
			throw new InvalidInputException();
		}
	}
	
	/**
	 * 
	 * @param calendarEventRO inserted into patientRO
	 * @param patientRO inserted into calendarEventRO
	 * @return The updated CalendarEvent; may be null if it failed to save the changes to the database.
	 */
	public CalendarEventRO connectCalendarEventWithPatient (CalendarEventRO calendarEventRO, PatientRO patientRO){
		Patient patient = PatientDao.getInstance().findById(patientRO.getPatientId());
		CalendarEventDao calEvDao = CalendarEventDao.getInstance();
		CalendarEvent calEv = calEvDao.findById(calendarEventRO.getCalendarEventId());
		calEv.setPatient(patient);
		((Patient)patientRO).addCalendarEvent(calEv);
		return calEvDao.makePersistent(calEv) ? calEv : null;
	}
	
	/**
	 * Note: calendarEventRO.isOpen() and state should differ. Otherwise this method does not access the database,
	 * because it's not needed to change something (in this case false is returned).
	 * 
	 * @param calendarEventRO
	 * @param state
	 * @return true if state has been successfully changed in the database
	 */
	public boolean setCalendarEventState(CalendarEventRO calendarEventRO, boolean state) {
		if(calendarEventRO.isOpen() != state) {	// only access the DB if necessary
			CalendarEventDao calEvDao = CalendarEventDao.getInstance();
			CalendarEvent calEv = calEvDao.findById(calendarEventRO.getCalendarEventId());
			calEv.setOpen(state);
			return calEvDao.makePersistent(calEv);
		} else {
			_logger.info("The CalendarEvent's state is already set to the desired state (" + state + "). No change in the database required.");
			return false;
		}
	}
}
