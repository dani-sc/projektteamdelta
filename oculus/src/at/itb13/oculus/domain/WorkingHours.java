package at.itb13.oculus.domain;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
@Table(name = "workinghours", catalog = "oculus_d", uniqueConstraints = @UniqueConstraint(columnNames = {
		"morningFrom", "morningTo", "afternoonFrom", "afternoonTo" }))
public class WorkingHours implements java.io.Serializable {
	private static final Logger _logger = LogManager.getLogger(WorkingHours.class.getName());
	
	private Integer workingHoursId;
	private Date morningFrom;
	private Date morningTo;
	private Date afternoonFrom;
	private Date afternoonTo;
	private Set<CalendarWorkingHours> calendarworkinghours = new HashSet<CalendarWorkingHours>(0);

	public WorkingHours() {
	}

	public WorkingHours(Date morningFrom, Date morningTo, Date afternoonFrom,
			Date afternoonTo, Set<CalendarWorkingHours> calendarworkinghourses) {
		this.morningFrom = morningFrom;
		this.morningTo = morningTo;
		this.afternoonFrom = afternoonFrom;
		this.afternoonTo = afternoonTo;
		this.calendarworkinghours = calendarworkinghourses;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "workingHoursId", unique = true, nullable = false)
	public Integer getWorkingHoursId() {
		return this.workingHoursId;
	}

	public void setWorkingHoursId(Integer workingHoursId) {
		this.workingHoursId = workingHoursId;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "morningFrom", length = 8)
	public Date getMorningFrom() {
		return this.morningFrom;
	}

	public void setMorningFrom(Date morningFrom) {
		this.morningFrom = morningFrom;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "morningTo", length = 8)
	public Date getMorningTo() {
		return this.morningTo;
	}

	public void setMorningTo(Date morningTo) {
		this.morningTo = morningTo;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "afternoonFrom", length = 8)
	public Date getAfternoonFrom() {
		return this.afternoonFrom;
	}

	public void setAfternoonFrom(Date afternoonFrom) {
		this.afternoonFrom = afternoonFrom;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "afternoonTo", length = 8)
	public Date getAfternoonTo() {
		return this.afternoonTo;
	}

	public void setAfternoonTo(Date afternoonTo) {
		this.afternoonTo = afternoonTo;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "workinghours")
	public Set<CalendarWorkingHours> getCalendarworkinghours() {
		return this.calendarworkinghours;
	}

	public void setCalendarworkinghours(
			Set<CalendarWorkingHours> calendarworkinghourses) {
		this.calendarworkinghours = calendarworkinghourses;
	}

}
