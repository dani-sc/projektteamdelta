package at.itb13.oculus.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * TODO: Insert description here.
 * 
 * @author Daniel Scheffknecht
 * @date 14.04.2015
 */
@Entity
@Table(name = "eventtype", catalog = "oculusdb", uniqueConstraints = @UniqueConstraint(columnNames = "eventTypeName"))
public class EventType implements java.io.Serializable {
	private static final Logger _logger = LogManager.getLogger(EventType.class.getName());
	
	private Integer eventTypeId;
	private String eventTypeName;
	private Integer estimatedTime;
	private String description;
	private Set<CalendarEvent> calendarevents = new HashSet<CalendarEvent>(0);

	public EventType() {
	}

	public EventType(String eventTypeName) {
		this.eventTypeName = eventTypeName;
	}

	public EventType(String eventTypeName, Integer estimatedTime,
			String description, Set<CalendarEvent> calendarevents) {
		this.eventTypeName = eventTypeName;
		this.estimatedTime = estimatedTime;
		this.description = description;
		this.calendarevents = calendarevents;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "eventTypeId", unique = true, nullable = false)
	public Integer getEventTypeId() {
		return this.eventTypeId;
	}

	public void setEventTypeId(Integer eventTypeId) {
		this.eventTypeId = eventTypeId;
	}

	@Column(name = "eventTypeName", unique = true, nullable = false, length = 50)
	public String getEventTypeName() {
		return this.eventTypeName;
	}

	public void setEventTypeName(String eventTypeName) {
		this.eventTypeName = eventTypeName;
	}

	@Column(name = "estimatedTime")
	public Integer getEstimatedTime() {
		return this.estimatedTime;
	}

	public void setEstimatedTime(Integer estimatedTime) {
		this.estimatedTime = estimatedTime;
	}

	@Column(name = "description", length = 65535)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "eventtype")
	public Set<CalendarEvent> getCalendarevents() {
		return this.calendarevents;
	}

	public void setCalendarevents(Set<CalendarEvent> calendarevents) {
		this.calendarevents = calendarevents;
	}

}
