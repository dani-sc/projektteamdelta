package at.itb13.teamF.adapter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import at.itb13.oculus.domain.Diagnosis;
import at.itb13.oculus.domain.Doctor;
import at.itb13.oculus.domain.ExaminationProtocol;
import at.itb13.oculus.domain.ExaminationResult;
import at.itb13.oculus.domain.Orthoptist;
import at.itb13.oculus.domain.Patient;
import at.itb13.teamF.interfaces.IAdapter;
import at.oculus.teamf.domain.entity.exception.CouldNotGetExaminationResultException;
import at.oculus.teamf.domain.entity.interfaces.IDiagnosis;
import at.oculus.teamf.domain.entity.interfaces.IDoctor;
import at.oculus.teamf.domain.entity.interfaces.IExaminationProtocol;
import at.oculus.teamf.domain.entity.interfaces.IExaminationResult;
import at.oculus.teamf.domain.entity.interfaces.IOrthoptist;
import at.oculus.teamf.domain.entity.interfaces.IPatient;

/**
 * Implementation of IExaminationProtocol of Team F.
 * 
 * @author Andrew Sparr
 * @date 18.05.2015
 */
public class ExaminationProtocolAdapter implements IAdapter,
		IExaminationProtocol {
	private ExaminationProtocol _examinationProtocol;

	public ExaminationProtocolAdapter() {
	}

	public ExaminationProtocolAdapter(ExaminationProtocol examinationProtocol) {
		_examinationProtocol = examinationProtocol;
	}

	@Override
	public int getId() {
		return _examinationProtocol.getExaminationProtocolId();
	}

	@Override
	public void setId(int id) {
		_examinationProtocol.setExaminationProtocolId(id);
	}

	@Override
	public Date getStartTime() {
		LocalDateTime localDateTime = _examinationProtocol.getStartProtocol();
		Date date = null;
		if(localDateTime != null){
			date = Date.from(localDateTime.atZone(ZoneId.systemDefault())
					.toInstant());
		}
		
		return date;
	}

	@Override
	public void setStartTime(Date startTime) {
		LocalDateTime localDateTime = startTime.toInstant()
				.atZone(ZoneId.systemDefault()).toLocalDateTime();
		_examinationProtocol.setStartProtocol(localDateTime);
	}

	@Override
	public Date getEndTime() {
		LocalDateTime localDateTime = _examinationProtocol.getEndProtocol();
		Date date = null;
		if(localDateTime != null){
			date = Date.from(localDateTime.atZone(ZoneId.systemDefault())
					.toInstant());
		}
		
		return date;
	}

	@Override
	public void setEndTime(Date endTime) {
		LocalDateTime localDateTime = endTime.toInstant()
				.atZone(ZoneId.systemDefault()).toLocalDateTime();
		_examinationProtocol.setStartProtocol(localDateTime);
	}

	@Override
	public String getDescription() {
		return _examinationProtocol.getDescription();
	}

	@Override
	public void setDescription(String description) {
		_examinationProtocol.setDescription(description);
	}

	@Override
	public IDoctor getDoctor() {
		Doctor doctor = _examinationProtocol.getUser().getDoctor();
		if (doctor != null) {
			return new DoctorAdapter(doctor);
		} else {
			return null;
		}
	}

	@Override
	public void setDoctor(IDoctor doctor) {
		if(doctor != null){
			DoctorAdapter doctorAdapter = (DoctorAdapter) doctor;
			Doctor doc = (Doctor) doctorAdapter.getDomainObject();
			_examinationProtocol.getUser().setDoctor(doc);
		}
	}

	@Override
	public IOrthoptist getOrthoptist() {
		Orthoptist orthoptist = _examinationProtocol.getUser().getOrthoptist();
		if (orthoptist != null) {
			return new OrthoptistAdapter(orthoptist);
		} else {
			return null;
		}
	}

	@Override
	public void setOrthoptist(IOrthoptist orthoptist) {
		if(orthoptist != null){
			OrthoptistAdapter orthoptistAdapter = (OrthoptistAdapter) orthoptist;
			Orthoptist ort = (Orthoptist) orthoptistAdapter.getDomainObject();
			_examinationProtocol.getUser().setOrthoptist(ort);
		}
	}

	@Override
	public IDiagnosis getTeamFDiagnosis() {
		Diagnosis diagnosis = _examinationProtocol.getDiagnosis();
		if(diagnosis != null) {
			return new DiagnosisAdapter(diagnosis);
		} else {
			return null;
		}
		
	}

	@Override
	public void setDiagnosis(IDiagnosis diagnosis) {
		if(diagnosis != null){
			DiagnosisAdapter diagnosisAdapter = (DiagnosisAdapter) diagnosis;
			Diagnosis diag = (Diagnosis) diagnosisAdapter.getDomainObject();
			_examinationProtocol.setDiagnosis(diag);
		}
	}

	@Override
	public IPatient getPatient() {
		Patient patient = _examinationProtocol.getPatient();
		if(patient != null){
			return new PatientAdapter(patient);
		}
		return null;
	}

	@Override
	public void setPatient(IPatient patient) {
		if(patient != null){
			PatientAdapter patientAdapter = (PatientAdapter) patient;
			Patient pat = (Patient) patientAdapter.getDomainObject();
			_examinationProtocol.setPatient(pat);
		}
	}

	@Override
	public Collection<IExaminationResult> getExaminationResults()
			throws CouldNotGetExaminationResultException {
		if(_examinationProtocol.getExaminationResults() != null){
			
			Set<ExaminationResult> set = _examinationProtocol.getExaminationResults();
			Collection<IExaminationResult> coll = new HashSet<>();
			for(ExaminationResult result : set) {
				ExaminationResultAdapter resultAda = new ExaminationResultAdapter(result);
				coll.add(resultAda);
			}
			return coll;
		}else{
			return null;
		}
	}

	@Override
	public Object getDomainObject() {
		return _examinationProtocol;
	}

	/*
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return _examinationProtocol.getStartProtocol().format(formatter);
	}
}
