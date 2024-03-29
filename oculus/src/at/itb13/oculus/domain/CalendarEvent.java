package at.itb13.oculus.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import at.itb13.oculus.domain.readonlyinterfaces.CalendarEventRO;
import at.itb13.oculus.technicalServices.converter.LocalDateTimePersistenceConverter;
import at.itb13.teamD.domain.interfaces.ICalendar;
import at.itb13.teamD.domain.interfaces.ICalendarEvent;
import at.itb13.teamD.domain.interfaces.IEventType;
import at.itb13.teamD.domain.interfaces.IPatient;

/**
 * 
 * TODO: Insert description here.
 * 
 * @author Daniel Scheffknecht
 * @date 11.04.2015
 */
@Entity
@Table(name = "calendarevent", catalog = "oculus_d")
public class CalendarEvent implements java.io.Serializable, CalendarEventRO, ICalendarEvent {
	@SuppressWarnings("unused")
	private static final Logger _logger = LogManager.getLogger(CalendarEvent.class.getName());
	private static final long serialVersionUID = 1L;
	
	private Integer _calendarEventId;
	private Calendar _calendar;
	private EventType _eventType;
	private Patient _patient;
	private LocalDateTime _eventStart;
	private LocalDateTime _eventEnd;
	private String _description;
	private String _patientName;
	private boolean _isOpen;
	
	public CalendarEvent() {
	}

	public CalendarEvent(ICalendar calendar, IEventType eventtype, LocalDateTime eventStart, 
						 LocalDateTime eventEnd, String description, String patientName) {
		_calendar = (Calendar) calendar;
		_eventType = (EventType) eventtype;
		_eventStart = eventStart;
		_eventEnd = eventEnd;
		_description = description;
		_isOpen = true;
		_patientName = patientName;
	}

	public CalendarEvent(ICalendar calendar, IEventType eventtype, LocalDateTime eventStart, 
						 LocalDateTime eventEnd, String description, IPatient patient) {
		_calendar = (Calendar) calendar;
		_eventType = (EventType) eventtype;
		_patient = (Patient) patient;
		_eventStart = eventStart;
		_eventEnd = eventEnd;
		_description = description;
		_isOpen = true;
	}
	
	/**
	 * to create an object of the private CalendarEvent Constructor
	 * 
	 * @return an object of the CalendarEvent
	 */
	@Transient
	public static CalendarEvent getInstance(){
		CalendarEvent event = new CalendarEvent();
		return event;
	}
	
	/**
	 * to create an object of the private CalendarEvent Constructor
	 * 
	 * @param calendar for this calendar the CalendarEvent is made.
	 * @param eventtype the type of the Calendar Event.
	 * @param eventStart the start Date of the CalendarEvent. (inclusive)
	 * @param eventEnd the end Date of the CalendarEvent. (inclusive)
	 * @param isOpen true if the CalendarEvent is not checked as closed.
	 * @param patientName the name of the patient 
	 * @return an object of the CalendarEvent
	 */
	@Transient
	public static CalendarEvent getInstance(ICalendar calendar, IEventType eventtype, LocalDateTime eventStart, 
									LocalDateTime eventEnd, String description, String patientName){
		
		CalendarEvent event = new CalendarEvent(calendar, eventtype, eventStart, eventEnd, description, patientName);
		return event;
	}
	
	/**
	 * to create an object of the private CalendarEvent Constructor
	 * 
	 * @param calendar for this calendar the CalendarEvent is made.
	 * @param eventtype the type of the Calendar Event.
	 * @param patient an object of the Patient class which is referred to the CalendarEvent.
	 * @param eventStart the start Date of the CalendarEvent. (inclusive)
	 * @param eventEnd the end Date of the CalendarEvent. (inclusive)
	 * @param description includes some special notes about the CalendarEvent.
	 * @param isOpen true if the CalendarEvent is not checked as closed. 
	 * @return an object of the CalendarEvent
	 */
	@Transient
	public static CalendarEvent getInstance(ICalendar calendar, IEventType eventtype, 
									LocalDateTime eventStart, LocalDateTime eventEnd,
									String description, IPatient patient){
		
		CalendarEvent event = new CalendarEvent(calendar, eventtype, eventStart, eventEnd, 
												description, patient);
		return event;
	}
	
	/**
	 * Checks if the CalendarEvent is in a timespan.
	 * 
	 * @param startDate the strat Date of the timespan. (inclusive)
	 * @param endDate the end Date of the timespan. (inclusive)
	 * @return true if the CalendarEvent is in the timespan and false if not.
	 */
	@Transient
	@Override
	public boolean isInTimespan(LocalDateTime startDate, LocalDateTime endDate){
		return ( _eventStart.isAfter(startDate) || _eventStart.isEqual(startDate) )
				&& ( _eventEnd.isBefore(endDate) || _eventEnd.isEqual(endDate) );
	}
	
	/**
	 * Checks if the CalendarEvent is in a timespan but also if the CalendarEvent starts befor timespan as long the end date
	 * is in timespan, or the CalendarEvent ends after timespan as long the start date is in timespan.
	 * 
	 * @param startDate the strat Date of the timespan.
	 * @param endDate the end Date of the timespan.
	 * @return true if the CalendarEvent is in the timespan, starts befor timespan or ends after timespan and false if not.
	 */
	@Transient
	@Override
	public boolean isPartInTimespan(LocalDateTime startDate, LocalDateTime endDate){
		if((_eventStart.isAfter(startDate) || _eventStart.isEqual(startDate))
			&& (_eventEnd.isBefore(endDate) || _eventEnd.isEqual(endDate))){
			return true;
		} else if(_eventStart.isBefore(startDate) && _eventEnd.isBefore(endDate) && _eventEnd.isAfter(startDate)
				 || _eventStart.isBefore(startDate) && _eventEnd.isEqual(endDate) && _eventEnd.isAfter(startDate)){
			return true;
		} else if(_eventStart.isAfter(startDate) && _eventEnd.isAfter(endDate) && _eventStart.isBefore(endDate)
				 || _eventStart.isEqual(startDate) && _eventEnd.isAfter(endDate) && _eventStart.isBefore(endDate)){
			return true;
		} else{
			return false;
		}
	}
	
	/**
	 * TODO
	 * 
	 * @param listCalEv
	 * @return
	 */
	@Transient
	public static List<? extends CalendarEventRO> sortCalendarEventsByStartDate(Set<CalendarEvent> listCalEv) {
		List<CalendarEvent> listSorted = new LinkedList<>(listCalEv);
		Collections.sort(listSorted, new Comparator<CalendarEvent>() {
			@Override
			public int compare(CalendarEvent o1, CalendarEvent o2) {
				// -1 -> o1 < o2
				// 0 -> o1 == 02
				// 1 -> o1 > 02
				if(o1.getEventStart().isEqual(o2.getEventStart())) {
					return 0;
				} else if (o1.getEventStart().isAfter(o2.getEventStart())) {
					return 1;
				} else {	// if(o1.getEventStart().isBefore(o2.getEventStart()))
					return -1;
				}
			}
		});
		return listSorted;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "calendarEventId", unique = true, nullable = false)
	public Integer getCalendarEventId() {
		return _calendarEventId;
	}

	public void setCalendarEventId(Integer calendarEventId) {
		_calendarEventId = calendarEventId;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "calendarId", nullable = false)
	@Override
	public Calendar getCalendar() {
		return _calendar;
	}

	public void setCalendar(Calendar calendar) {
		_calendar = calendar;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "eventTypeId", nullable = false)
	@Override
	public EventType getEventType() {
		return _eventType;
	}

	public void setEventType(EventType eventtype) {
		_eventType = eventtype;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "patientId")
	@Override
	public Patient getPatient() {
		return _patient;
	}

	public void setPatient(Patient patient) {
		_patient = patient;
	}

	@Convert(converter = LocalDateTimePersistenceConverter.class)
	@Column(name = "eventStart", nullable = false, length = 19)
	@Override
	public LocalDateTime getEventStart() {
		return _eventStart;
	}

	public void setEventStart(LocalDateTime eventStart) {
		_eventStart = eventStart;
	}

	@Convert(converter = LocalDateTimePersistenceConverter.class)
	@Column(name = "eventEnd", nullable = false, length = 19)
	@Override
	public LocalDateTime getEventEnd() {
		return _eventEnd;
	}

	public void setEventEnd(LocalDateTime eventEnd) {
		_eventEnd = eventEnd;
	}

	@Column(name = "description", length = 65535)
	@Override
	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	@Column(name = "patientName", length = 100)
	@Override
	public String getPatientName() {
		return _patientName;
	}

	public void setPatientName(String patientName) {
		_patientName = patientName;
	}

	@Column(name = "isOpen", nullable = false)
	@Override
	public boolean isOpen() {
		return _isOpen;
	}

	public void setOpen(boolean isOpen) {
		_isOpen = isOpen;
	}
}
