package at.itb13.oculus.domain;

import java.util.Date;

/**
 * 
 * TODO: Insert description here.
 * 
 * @author Daniel Scheffknecht
 * @date 03.04.2015
 */
public class Calendarevent implements java.io.Serializable {

	private Integer calendarEventId;
	private Calendar calendar;
	private Eventtype eventtype;
	private Patient patient;
	private Date eventStart;
	private Date eventEnd;
	private String description;
	private String patientName;
	private boolean isOpen;

	public Calendarevent() {
	}

	public Calendarevent(Calendar calendar, Eventtype eventtype,
			Date eventStart, Date eventEnd, boolean isOpen) {
		this.calendar = calendar;
		this.eventtype = eventtype;
		this.eventStart = eventStart;
		this.eventEnd = eventEnd;
		this.isOpen = isOpen;
	}

	public Calendarevent(Calendar calendar, Eventtype eventtype,
			Patient patient, Date eventStart, Date eventEnd,
			String description, String patientName, boolean isOpen) {
		this.calendar = calendar;
		this.eventtype = eventtype;
		this.patient = patient;
		this.eventStart = eventStart;
		this.eventEnd = eventEnd;
		this.description = description;
		this.patientName = patientName;
		this.isOpen = isOpen;
	}

	public Integer getCalendarEventId() {
		return this.calendarEventId;
	}

	public void setCalendarEventId(Integer calendarEventId) {
		this.calendarEventId = calendarEventId;
	}

	public Calendar getCalendar() {
		return this.calendar;
	}

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

	public Eventtype getEventtype() {
		return this.eventtype;
	}

	public void setEventtype(Eventtype eventtype) {
		this.eventtype = eventtype;
	}

	public Patient getPatient() {
		return this.patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Date getEventStart() {
		return this.eventStart;
	}

	public void setEventStart(Date eventStart) {
		this.eventStart = eventStart;
	}

	public Date getEventEnd() {
		return this.eventEnd;
	}

	public void setEventEnd(Date eventEnd) {
		this.eventEnd = eventEnd;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPatientName() {
		return this.patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public boolean isIsOpen() {
		return this.isOpen;
	}

	public void setIsOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

}