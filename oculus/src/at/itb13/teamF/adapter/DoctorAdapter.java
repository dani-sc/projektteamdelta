package at.itb13.teamF.adapter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import at.itb13.oculus.domain.Calendar;
import at.itb13.oculus.domain.Doctor;
import at.itb13.oculus.domain.Patient;
import at.itb13.teamF.interfaces.IAdapter;
import at.oculus.teamf.domain.entity.exception.CantLoadPatientsException;
import at.oculus.teamf.domain.entity.interfaces.ICalendar;
import at.oculus.teamf.domain.entity.interfaces.IDoctor;
import at.oculus.teamf.domain.entity.interfaces.IPatient;
import at.oculus.teamf.domain.entity.interfaces.IPatientQueue;

/**
 * Implementation of IDoctor of Team F.
 * Throws NotImplementedException for things concerning the queue, as 
 * the queue is not even used by the received classes from Team F.
 * 
 * @author Karin Trommelschläger
 * @date 18.05.2015
 * 
 */
public class DoctorAdapter implements IAdapter, IDoctor {
	private Doctor _doctor;

	public DoctorAdapter() {

	}

	public DoctorAdapter(Doctor doctor) {
		_doctor = doctor;
	}

	/*
	 * @see at.oculus.teamf.domain.entity.interfaces.IUser#getUserId()
	 */
	@Override
	public int getUserId() {
		return _doctor.getUser().getUserId();
	}

	/*
	 * @see at.oculus.teamf.domain.entity.interfaces.IUser#setUserId(int)
	 */
	@Override
	public void setUserId(int id) {
		_doctor.getUser().setUserId(id);

	}

	/*
	 * @see at.oculus.teamf.domain.entity.interfaces.IUser#getUserGroupId()
	 */
	@Override
	public Integer getUserGroupId() {
		return _doctor.getUser().getUsergroup().getUserGroupId();
	}

	/*
	 * @see
	 * at.oculus.teamf.domain.entity.interfaces.IUser#setUserGroupId(java.lang
	 * .Integer)
	 */
	@Override
	public void setUserGroupId(Integer userGroupId) {
		_doctor.getUser().getUsergroup().setUserGroupId(userGroupId);

	}

	/*
	 * @see at.oculus.teamf.domain.entity.interfaces.IUser#getUserName()
	 */
	@Override
	public String getUserName() {
		return _doctor.getUser().getUserName();
	}

	/*
	 * @see
	 * at.oculus.teamf.domain.entity.interfaces.IUser#setUserName(java.lang.
	 * String)
	 */
	@Override
	public void setUserName(String userName) {
		_doctor.getUser().setUserName(userName);

	}

	/*
	 * @see at.oculus.teamf.domain.entity.interfaces.IUser#getPassword()
	 */
	@Override
	public String getPassword() {
		return _doctor.getUser().getPassword();
	}

	/*
	 * @see
	 * at.oculus.teamf.domain.entity.interfaces.IUser#setPassword(java.lang.
	 * String)
	 */
	@Override
	public void setPassword(String password) {
		_doctor.getUser().setPassword(password);
	}

	/*
	 * @see at.oculus.teamf.domain.entity.interfaces.IUser#getTitle()
	 */
	@Override
	public String getTitle() {
		return _doctor.getUser().getTitle();
	}

	/*
	 * @see
	 * at.oculus.teamf.domain.entity.interfaces.IUser#setTitle(java.lang.String)
	 */
	@Override
	public void setTitle(String title) {
		_doctor.getUser().setTitle(title);

	}

	/*
	 * @see at.oculus.teamf.domain.entity.interfaces.IUser#getFirstName()
	 */
	@Override
	public String getFirstName() {
		return _doctor.getUser().getFirstName();
	}

	/*
	 * @see
	 * at.oculus.teamf.domain.entity.interfaces.IUser#setFirstName(java.lang
	 * .String)
	 */
	@Override
	public void setFirstName(String firstName) {
		_doctor.getUser().setFirstName(firstName);

	}

	/*
	 * @see at.oculus.teamf.domain.entity.interfaces.IUser#getLastName()
	 */
	@Override
	public String getLastName() {
		return _doctor.getUser().getLastName();
	}

	/*
	 * @see
	 * at.oculus.teamf.domain.entity.interfaces.IUser#setLastName(java.lang.
	 * String)
	 */
	@Override
	public void setLastName(String lastName) {
		_doctor.getUser().setLastName(lastName);

	}

	/*
	 * @see at.oculus.teamf.domain.entity.interfaces.IUser#getEmail()
	 */
	@Override
	public String getEmail() {
		return _doctor.getUser().getEmail();

	}

	/*
	 * @see
	 * at.oculus.teamf.domain.entity.interfaces.IUser#setEmail(java.lang.String)
	 */
	@Override
	public void setEmail(String email) {
		_doctor.getUser().setEmail(email);

	}

	/*
	 * @see at.oculus.teamf.domain.entity.interfaces.IUser#getCreateDate()
	 */
	@Override
	public Date getCreateDate() {
		LocalDateTime localDateTime = _doctor.getUser().getCreateDate();
		Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault())
				.toInstant());
		return date;

	}

	/*
	 * @see
	 * at.oculus.teamf.domain.entity.interfaces.IUser#setCreateDate(java.util
	 * .Date)
	 */
	@Override
	public void setCreateDate(Date createDate) {
		LocalDateTime localDateTime = createDate.toInstant()
				.atZone(ZoneId.systemDefault()).toLocalDateTime();
		_doctor.getUser().setCreateDate(localDateTime);

	}

	/*
	 * @see at.oculus.teamf.domain.entity.interfaces.IUser#getIdleDate()
	 */
	@Override
	public Date getIdleDate() {
		LocalDateTime localDateTime = _doctor.getUser().getIdleDate();
		Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault())
				.toInstant());
		return date;

	}

	/*
	 * @see
	 * at.oculus.teamf.domain.entity.interfaces.IUser#setIdleDate(java.util.
	 * Date)
	 */
	@Override
	public void setIdleDate(Date idleDate) {
		LocalDateTime localDateTime = idleDate.toInstant()
				.atZone(ZoneId.systemDefault()).toLocalDateTime();
		_doctor.getUser().setCreateDate(localDateTime);

	}

	/*
	 * @see at.oculus.teamf.domain.entity.interfaces.IOrthoptist#getId()
	 */
	@Override
	public int getId() {
		return _doctor.getDoctorId();
	}

	/*
	 * @see at.oculus.teamf.domain.entity.interfaces.IOrthoptist#setId(int)
	 */
	@Override
	public void setId(int id) {
		_doctor.setDoctorId(id);

	}

	/*
	 * @see at.oculus.teamf.domain.entity.interfaces.IOrthoptist#getCalendar()
	 */
	@Override
	public ICalendar getCalendar() {
		Calendar calendar = _doctor.getCalendar();
		if (calendar != null) {
			return new CalendarAdapter(calendar);
		}
		return null;

	}

	/*
	 * @see
	 * at.oculus.teamf.domain.entity.interfaces.IOrthoptist#setCalendar(at.oculus
	 * .teamf.domain.entity.interfaces.ICalendar)
	 */
	@Override
	public void setCalendar(ICalendar calendar) {
		if (calendar != null) {
			CalendarAdapter calendarAdapter = (CalendarAdapter) calendar;
			Calendar cal = (Calendar) calendarAdapter.getDomainObject();
			_doctor.setCalendar(cal);
		}

	}

	/*
	 * @see
	 * at.oculus.teamf.domain.entity.interfaces.IOrthoptist#setQueue(at.oculus
	 * .teamf.domain.entity.interfaces.IPatientQueue)
	 */
	@Override
	public void setQueue(IPatientQueue queue) {
		throw new NotImplementedException();
	}

	/*
	 * @see at.oculus.teamf.domain.entity.interfaces.IDoctor#getQueue()
	 */
	@Override
	public IPatientQueue getQueue() {
		throw new NotImplementedException();
	}

	/*
	 * @see at.itb13.teamF.interfaces.IAdapter#getDomainObject()
	 */
	@Override
	public Object getDomainObject() {
		return _doctor;
	}

	/**
	 * @return the _doctor
	 */
	public Doctor get_doctor() {
		return _doctor;
	}

	/**
	 * @param _doctor
	 *            the _doctor to set
	 */
	public void set_doctor(Doctor _doctor) {

		this._doctor = _doctor;
	}

	/*
	 * @see
	 * at.oculus.teamf.domain.entity.interfaces.IDoctor#getDoctorSubstitude()
	 */
	@Override
	public IDoctor getDoctorSubstitude() {
		if (_doctor.getDoctorSubstitute() != null) {
			return new DoctorAdapter(_doctor.getDoctorSubstitute());

		}
		return null;
	}

	/*
	 * @see
	 * at.oculus.teamf.domain.entity.interfaces.IDoctor#setDoctorSubstitude(
	 * at.oculus.teamf.domain.entity.interfaces.IDoctor)
	 */
	@Override
	public void setDoctorSubstitude(IDoctor doctorSubstitude) {
		if (doctorSubstitude != null) {
			DoctorAdapter doctorAdapter = (DoctorAdapter) doctorSubstitude;
			Doctor doc = (Doctor) doctorAdapter.getDomainObject();
			_doctor.setDoctorSubstitute(doc);
		}

	}

	/*
	 * @see
	 * at.oculus.teamf.domain.entity.interfaces.IDoctor#addPatient(at.oculus
	 * .teamf.domain.entity.interfaces.IPatient)
	 */
	@Override
	public void addPatient(IPatient patient) {
		if (patient != null) {
			Collection<Patient> coll;
			if (_doctor.getPatients() != null) {
				coll = _doctor.getPatients();
			} else {
				coll = new HashSet<Patient>();
			}
			PatientAdapter patAda = (PatientAdapter) patient;
			coll.add((Patient) patAda.getDomainObject());

			_doctor.setPatients((Set<Patient>) coll);

		}
	}

	/*
	 * @see at.oculus.teamf.domain.entity.interfaces.IDoctor#getPatients()
	 */
	@Override
	public Collection<IPatient> getPatients() throws CantLoadPatientsException {
		if (_doctor.getPatients() != null) {

			Set<Patient> set = _doctor.getPatients();
			Collection<IPatient> coll = new HashSet<>();
			for (Patient pat : set) {
				PatientAdapter patAda = new PatientAdapter(pat);
				coll.add(patAda);
			}
			return coll;
		} else {
			return null;
		}
	}

	/*
	 * @see
	 * at.oculus.teamf.domain.entity.interfaces.IDoctor#setPatients(java.util
	 * .Collection)
	 */
	@Override
	public void setPatients(Collection<IPatient> ipatients) {
		Set<Patient> patients = new HashSet<Patient>(0);
		PatientAdapter patientAdapter = null;
		Patient p = null;
		if (ipatients != null) {
			for (IPatient ip : ipatients) {
				patientAdapter = (PatientAdapter) ip;
				p = (Patient) patientAdapter.getDomainObject();
				patients.add(p);
			}
			_doctor.setPatients(patients);
		}
	}

}
