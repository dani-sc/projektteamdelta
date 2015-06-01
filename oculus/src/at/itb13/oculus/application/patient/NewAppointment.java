package at.itb13.oculus.application.patient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import at.itb13.oculus.domain.Calendar;
import at.itb13.oculus.domain.CalendarEvent;
import at.itb13.oculus.domain.Patient;
import at.itb13.oculus.technicalServices.dao.PatientDao;

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
	 * correct.
	 * 
	 * @param email
	 *            the patient's email
	 * @param password
	 *            the password that corresponds to the given email
	 * @return true if the login credentials are valid
	 */
	public Boolean isLoginCredentialsValid(String email, String password) {
		if(email == null || password == null) {
			throw new NullPointerException();
		}
		
		Patient patient = PatientDao.getInstance().findByEmail(email);

		if (patient != null) {
			return patient.isEqualPassword(password);
		}

		return false;
	}
	
	public LocalDateTime getPossibleAppointment(String weekday, String from, String to, 
											Date start, Date end, String socialInsuranceNumber, String appointmentType){
		Patient patient = PatientDao.getInstance().findBySocialInsuranceNr(socialInsuranceNumber);
		int appointmentDuration = getAppointmentDuration(appointmentType);
		List<LocalDateTime> list = createLocalDateTimeOfStrings(weekday, from, to);
		LocalDateTime startTime = list.get(0);
		LocalDateTime endTime = list.get(1);
		Calendar calendar = patient.getDoctor().getCalendar();
		LocalDateTime eventTime = calendar.findPossibleAppointment(startTime, endTime, appointmentDuration);
		return eventTime;
	}
	
	private List<LocalDateTime> createLocalDateTimeOfStrings(String weekday, String from, String to){
		LocalTime lt1 = createLocalTimeOfString(from);
		LocalTime lt2 = createLocalTimeOfString(to);
		LocalDate ld = createLocalDateOfString(weekday);
		LocalDateTime ldt1 = LocalDateTime.of(ld, lt1);
		LocalDateTime ldt2 = LocalDateTime.of(ld, lt2);
		List<LocalDateTime> list = new LinkedList<>();
		list.add(ldt1);
		list.add(ldt2);
		return list;
	}
	
	private LocalTime createLocalTimeOfString(String time){
		String[] parts = time.split(":");
		int hour = Integer.parseInt(parts[0]);
		int minute = Integer.parseInt(parts[1]);
		LocalTime lt = LocalTime.of(hour, minute);
		return lt;
	}
	
	private LocalDate createLocalDateOfString(String weekday){
		LocalDate ld = LocalDate.now();
		while(!(weekday.equalsIgnoreCase(ld.getDayOfWeek().name()))){
			ld.plusDays(1);
		}
		return ld;
	}
	
	private int getAppointmentDuration(String appointmentType){
		
		return 0;
	}
	
	public String[] getPatientData(String email){
		String[] patientdata = new String[3];
		Patient patient = PatientDao.getInstance().findByEmail(email);
		patientdata[0] = patient.getFirstName()+" "+patient.getLastName();
		patientdata[1] = patient.getSocialInsuranceNr();
		patientdata[2] = patient.getDoctor().getUser().getFirstName()+" "+
				 patient.getDoctor().getUser().getLastName();
		return patientdata;
	}
	
	public String[] getPatientAppointment(String email){
		String[] patientAppointment = new String[4];
		Patient patient = PatientDao.getInstance().findByEmail(email);
		CalendarEvent ce = patient.getNextAppointment();
		patientAppointment[0] = ce.getEventStart().toString();
		if (ce.getCalendar().getDoctor()!=null){
		patientAppointment[1] = ce.getCalendar().getDoctor().getUser().getFirstName()+" "+
				ce.getCalendar().getDoctor().getUser().getLastName()	;
		}else if (ce.getCalendar().getOrthoptist() != null){
			patientAppointment[1] = ce.getCalendar().getOrthoptist().getUser().getFirstName()+" "+
					ce.getCalendar().getOrthoptist().getUser().getLastName()	;
		}
		patientAppointment[2] = ce.getEventType().getEventTypeName();
		patientAppointment[3] = ce.getDescription();
		return patientAppointment;
	}
	
}
