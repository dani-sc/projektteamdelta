package at.itb13.oculus.domain.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.time.LocalDateTime;

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
import javax.persistence.UniqueConstraint;

import at.itb13.oculus.domain.CalendarEvent;
import at.itb13.oculus.domain.Doctor;
import at.itb13.oculus.domain.Orthoptist;
import at.itb13.oculus.domain.Patient;
import at.itb13.oculus.technicalServices.converter.LocalDateTimePersistenceConverter;

/**
 * 
 * TODO: Insert description here.
 * 
 * @author Daniel Scheffknecht
 * @date 11.04.2015
 */
@Entity
@Table(name = "queue", catalog = "oculus_d", uniqueConstraints = @UniqueConstraint(columnNames = "patientId"))
public class QueueEntity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer queueId;
	private Doctor doctor;
	private Orthoptist orthoptist;
	private Patient patient;
	private CalendarEvent calendarEvent;
	private LocalDateTime arrivalTime;
	private Integer queueIdParent;

	public QueueEntity() { }

	public QueueEntity(Patient patient, LocalDateTime arrivalTime) {
		this.patient = patient;
		this.arrivalTime = arrivalTime;
	}

	public QueueEntity(Doctor doctor, Orthoptist orthoptist, Patient patient,
			CalendarEvent calendarEvent, LocalDateTime arrivalTime, Integer queueIdParent) {
		this.doctor = doctor;
		this.orthoptist = orthoptist;
		this.patient = patient;
		this.calendarEvent = calendarEvent;
		this.arrivalTime = arrivalTime;
		this.queueIdParent = queueIdParent;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "queueId", unique = true, nullable = false)
	public Integer getQueueId() {
		return this.queueId;
	}

	public void setQueueId(Integer queueId) {
		this.queueId = queueId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doctorId")
	public Doctor getDoctor() {
		return this.doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orthoptistId")
	public Orthoptist getOrthoptist() {
		return this.orthoptist;
	}

	public void setOrthoptist(Orthoptist orthoptist) {
		this.orthoptist = orthoptist;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "patientId", unique = true, nullable = false)
	public Patient getPatient() {
		return this.patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	@Convert(converter = LocalDateTimePersistenceConverter.class)
	@Column(name = "arrivalTime", nullable = false, length = 19)
	public LocalDateTime getArrivalTime() {
		return this.arrivalTime;
	}

	public void setArrivalTime(LocalDateTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	@Column(name = "queueIdParent")
	public Integer getQueueIdParent() {
		return queueIdParent;
	}

	public void setQueueIdParent(Integer queueIdParent) {
		this.queueIdParent = queueIdParent;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "calendarEventId", unique = true)
	public CalendarEvent getCalendarEvent() {
		return calendarEvent;
	}

	public void setCalendarEvent(CalendarEvent calendarEvent) {
		this.calendarEvent = calendarEvent;
	}
}
