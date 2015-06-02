package at.itb13.oculus.application.patient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import at.itb13.oculus.application.ControllerFacade;
import at.itb13.oculus.domain.Calendar;
import at.itb13.oculus.domain.CalendarEvent;
import at.itb13.oculus.domain.Patient;
import at.itb13.oculus.technicalServices.dao.CalendarDao;
import at.itb13.oculus.technicalServices.dao.CalendarEventDao;
import at.itb13.oculus.technicalServices.dao.DoctorDao;
import at.itb13.oculus.technicalServices.dao.PatientDao;
import at.itb13.teamD.domain.interfaces.IEventType;



/**
 * TODO: Insert description here.
 * 
 * @author Daniel Scheffknecht
 * @date May 27, 2015
 */
public class NewAppointment {
	private static final Logger _logger = LogManager
			.getLogger(NewAppointment.class.getName());

	/**
	 * Checks if a patient with the given email exists, and if the password is
	 * correct. In addition, it selects the patient in the ControllerFacade.
	 * 
	 * @param email
	 *            the patient's email
	 * @param password
	 *            the password that corresponds to the given email
	 * @return true if the login credentials are valid
	 */
	public at.itb13.oculus.presentation.gwt.shared.Patient isLoginCredentialsValid(String email, String password) {
		if(email == null || password == null) {
			throw new NullPointerException();
		}
		
		Patient patient = PatientDao.getInstance().findByEmail(email);
		at.itb13.oculus.presentation.gwt.shared.Patient patShared = null;

		if (patient != null && patient.isEqualPassword(password)) {
			ControllerFacade.setPatientSelected(patient);
			patShared = new at.itb13.oculus.presentation.gwt.shared.Patient();
			patShared.setName(patient.getFirstName() + " " + patient.getLastName());
			patShared.setSin(patient.getSocialInsuranceNr());
			if(patient.getDoctor() != null) {
				patShared.setDoctor(patient.getDoctor().getUser().getFirstName() + " " + patient.getDoctor().getUser().getLastName());
			}
		}

		return patShared;
	}
	
	/**
	 * In reference to the selected patient in the ControllerFacade.
	 * 
	 * @return true, if the selected patient has a future appointment.
	 */
	public Boolean hasFutureAppointment() {
		CalendarEvent calEv = ((Patient)(ControllerFacade.getPatientSelected())).getNextAppointment();
		return calEv != null;
	}
	
	@SuppressWarnings("static-access")
	public LocalDateTime getPossibleAppointment(String weekday, String from, String to, 
											Date start, Date end, boolean isSameDay, String appointmentType){
		
		int appointmentDuration = getAppointmentDuration(appointmentType);
		System.out.println("Typ: " + appointmentType);
		System.out.println("Dauer: " + appointmentDuration);
		List<LocalDateTime> list = createLocalDateTimeOfStrings(weekday, from, to);
		LocalDateTime startTime = list.get(0);
		LocalDateTime endTime = list.get(1);
		System.out.println("createdDate");
		Patient patient = (Patient) ControllerFacade.getInstance().getPatientSelected();
		System.out.println("Patient: " + patient.getFirstName());
		Calendar calendar = patient.getDoctor().getCalendar();
		LocalDateTime eventTime = calendar.findPossibleAppointment(startTime, endTime, appointmentDuration);
		System.out.println("Termin: " + eventTime);
		return eventTime;
	}
	
	private List<LocalDateTime> createLocalDateTimeOfStrings(String weekday, String from, String to){
		LocalTime lt1;
		LocalTime lt2;
		System.out.println("hallo");
		if(!from.isEmpty()){
			System.out.println("not empty");
			lt1 = createLocalTimeOfString(from);
		}else{
			lt1 = LocalTime.of(8, 00);
		}
		if(!to.isEmpty()){
			lt2 = createLocalTimeOfString(to);
		}else{
			lt2 = LocalTime.of(18, 00);
		}
		System.out.println("made time");
		LocalDate ld = createLocalDateOfString(weekday);
		System.out.println("made day");
		LocalDateTime ldt1 = LocalDateTime.of(ld, lt1);
		LocalDateTime ldt2 = LocalDateTime.of(ld, lt2);
		System.out.println("start: " + ldt1);
		System.out.println("end: " + ldt2);
		List<LocalDateTime> list = new LinkedList<>();
		list.add(ldt1);
		list.add(ldt2);
		return list;
	}
	
	private LocalTime createLocalTimeOfString(String time){
		System.out.println("befor split");
		String[] parts = time.split(":");
		int hour = Integer.parseInt(parts[0]);
		int minute = Integer.parseInt(parts[1]);
		System.out.println(hour);
		System.out.println(minute);
		LocalTime lt = LocalTime.of(hour, minute);
		return lt;
	}
	
	private LocalDate createLocalDateOfString(String weekday){
		System.out.println("in LocalDate");
		LocalDate ld = LocalDate.now();
		while(!(weekday.equalsIgnoreCase(ld.getDayOfWeek().name()))){
			System.out.println("1:" + weekday);
			System.out.println("2:" + ld.getDayOfWeek().name());
			ld = ld.plusDays(1);
		}
		System.out.println(ld);
		return ld;
	}
	
	private int getAppointmentDuration(String appointmentType){
		List<IEventType> list = ControllerFacade.getInstance().getAllEventTypes();
		int duration = 0;
		for(IEventType type : list){
			if(type.getEventTypeName().equals(appointmentType)){
				duration = type.getEstimatedTime();
				break;
			}
		}
		return duration;
	}
	
	// TODO: DELETE
	public String[] getPatientData(String email){
		String[] patientdata = new String[3];
		Patient patient = PatientDao.getInstance().findByEmail(email);
		patientdata[0] = patient.getFirstName()+" "+patient.getLastName();
		patientdata[1] = patient.getSocialInsuranceNr();
		patientdata[2] = patient.getDoctor().getUser().getFirstName()+" "+
				 patient.getDoctor().getUser().getLastName();
		return patientdata;
	}
	
	public at.itb13.oculus.presentation.gwt.shared.CalendarEvent getPatientAppointment(at.itb13.oculus.presentation.gwt.shared.Patient pa) {
		//String[] patientAppointment = new String[4];
		at.itb13.oculus.presentation.gwt.shared.CalendarEvent event = new at.itb13.oculus.presentation.gwt.shared.CalendarEvent(); 
		Patient patient = PatientDao.getInstance().findBySocialInsuranceNr(pa.getSin());
		CalendarEvent ce = patient.getNextAppointment();
		if (ce != null) {
			event.setId(ce.getCalendarEventId());
			event.setDate(ce.getEventStart().toString());
			if (ce.getCalendar().getDoctor() != null) {
				event.setDoctor(ce.getCalendar().getDoctor().getUser()
						.getFirstName()
						+ " "
						+ ce.getCalendar().getDoctor().getUser().getLastName());
			} else if (ce.getCalendar().getOrthoptist() != null) {
				event.setDoctor(ce.getCalendar().getOrthoptist()
						.getUser().getFirstName()
						+ " "
						+ ce.getCalendar().getOrthoptist().getUser()
								.getLastName());
			}
			event.setType(ce.getEventType().getEventTypeName());
			event.setReason(ce.getDescription());
		}
		return event;
	}

	/**
	 * @param calEventId
	 * @return
	 */
	public boolean deleteAppointment(int calEventId) {
		CalendarEvent cal = CalendarEventDao.getInstance().findById(calEventId);
		
		return CalendarEventDao.getInstance().makeTransient(cal);
	}
	
	public boolean addAppointment(at.itb13.oculus.presentation.gwt.shared.Patient patient, at.itb13.oculus.presentation.gwt.shared.CalendarEvent calendarEvent){
		CalendarEvent domainEvent = new CalendarEvent();
		Patient pa = PatientDao.getInstance().findBySocialInsuranceNr(patient.getSin());
		domainEvent.setCalendar(pa.getDoctor().getCalendar());
		domainEvent.setDescription(calendarEvent.getReason());
		
		domainEvent.setEventStart();
		domainEvent.setPatient(pa);
		
		return CalendarEventDao.getInstance().makePersistent(domainEvent);
	}
}
