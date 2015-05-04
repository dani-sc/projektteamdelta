package at.itb13.oculus.domain;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import at.itb13.oculus.domain.CalendarWorkingHours.WeekDayKey;
import at.itb13.oculus.domain.interfaces.ICalendar;
import at.itb13.oculus.domain.interfaces.IWorkingHours;
import at.itb13.oculus.domain.readonlyinterfaces.CalendarRO;

/**
 * 
 * TODO: Insert description here.
 * 
 * @author Daniel Scheffknecht
 * @date 14.04.2015
 */
@Entity
@Table(name = "calendar", catalog = "oculus_d")
public class Calendar implements java.io.Serializable, CalendarRO, ICalendar {
	private static final Logger _logger = LogManager.getLogger(Calendar.class.getName());
	private static final long serialVersionUID = 1L;
	
	private Integer _calendarId;
	private String _title;
	private Doctor _doctor;
	private Orthoptist _orthoptist;
	private Set<CalendarEvent> _calendarEvents = new HashSet<CalendarEvent>(0);
	private Set<CalendarWorkingHours> _calendarWorkingHours = new HashSet<CalendarWorkingHours>(
			0);

	public Calendar() {
	}

	public Calendar(String title, Doctor doctor, Orthoptist orthoptist, Set<CalendarEvent> calendarevents,
			Set<CalendarWorkingHours> calendarworkinghourses) {
		_title = title;
		_doctor = doctor;
		_orthoptist = orthoptist;
		_calendarEvents = calendarevents;
		_calendarWorkingHours = calendarworkinghourses;
	}
	
	/**
	 * Creates a list of Calendar Event for a chosen timespan.
	 * 
	 * @param startDate the start Date of the timespan. (inclusive)
	 * @param endDate the end Date of the timespan. (inclusive)
	 * @return A list of CalendarEvent.
	 */
	@Transient
	@Override
	public List<CalendarEvent> getCalendarEventsForTimespan(LocalDateTime startDate, LocalDateTime endDate) {
		List<CalendarEvent> listCalEv = new LinkedList<>();
		for (CalendarEvent c : _calendarEvents) {
			if (c.isInTimespan(startDate, endDate)) {
				listCalEv.add(c);
			}
		}
		return listCalEv;
	}
	
	/**
	 * searches for a CalendarEvent by the calendarEventId.
	 * 
	 * @param calendarEventId the ID of the CalendarEvent.
	 * @return a CalendarEvent.
	 */
	public CalendarEvent getCalendarEventById(int calendarEventId){
		for (CalendarEvent c : _calendarEvents) {
			if (c.getCalendarEventId() == calendarEventId) {
				return c;
			}
		}
		return null;
	}
	
	/**
	 * Returns the Working Hours of a chosen day of the week.
	 * 
	 * @param weekDay is an Enum of the days of the week.
	 * @return A IWorkingHours.
	 */
	@Transient
	@Override
	public WorkingHours getWorkingHoursOfWeekDay(WeekDayKey weekDay) {
		WorkingHours workingHours = new WorkingHours();
		for(CalendarWorkingHours wh: _calendarWorkingHours) {
			if(wh.getWeekDayKey() == weekDay){
				workingHours = wh.getWorkinghours();
			}
		}
		return workingHours;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "calendarId", unique = true, nullable = false)
	public Integer getCalendarId() {
		return _calendarId;
	}

	public void setCalendarId(Integer calendarId) {
		_calendarId = calendarId;
	}

	@Column(name = "title")
	public String getTitle() {
		return _title;
	}

	public void setTitle(String title) {
		_title = title;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "calendar")
	public Doctor getDoctor() {
		return _doctor;
	}

	public void setDoctor(Doctor doctor) {
		_doctor = doctor;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "calendar")
	public Orthoptist getOrthoptist() {
		return _orthoptist;
	}

	public void setOrthoptist(Orthoptist orthoptist) {
		_orthoptist = orthoptist;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "calendar")
	public Set<CalendarEvent> getCalendarEvents() {
		return _calendarEvents;
	}

	public void setCalendarEvents(Set<CalendarEvent> calendarEvents) {
		_calendarEvents = calendarEvents;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "calendar")
	public Set<CalendarWorkingHours> getCalendarWorkingHours() {
		return _calendarWorkingHours;
	}

	public void setCalendarWorkingHours (
			Set<CalendarWorkingHours> calendarWorkingHours) {
		_calendarWorkingHours = calendarWorkingHours;
	}
}
