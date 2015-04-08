package at.itb13.oculus.application.patient;

import java.util.List;

import at.itb13.oculus.domain.Patient;
import at.itb13.oculus.technicalServices.dao.PatientDao;

/**
 * TODO: Insert description here.
 * 
 * @author Daniel Scheffknecht
 * @date 08.04.2015
 */
public class PatientSearch {
	
	public static final Integer SOCIAL_INSURANCE_NR_LENGTH = 10;
	
	public List<Patient> searchPatientByName(String firstName, String lastName) throws InvalidInputException {
		List<Patient> patients = null;
		PatientDao patientDao = new PatientDao();
		if(firstName != null && lastName != null){
			patients = patientDao.findByFullName(firstName, lastName);
			return patients;
		}else if(firstName != null){
			//TODO
			return patients;
		}else if(lastName != null){
			//TODO
			return patients;
		}else{
			throw new InvalidInputException();
		}
	}
	
	/**
	 * TODO
	 * 
	 * @param socialInsuranceNr
	 * @return
	 */
	public Patient searchPatientBySocialInsuranceNr(String socialInsuranceNr) {
		Patient patient = null;
		if(isSocialInsuranceNrValid(socialInsuranceNr)) {
			PatientDao patientDao = new PatientDao();
			patient = patientDao.findBySocialInsuranceNr(socialInsuranceNr);
		} else {
			throw new IllegalArgumentException();
		}
		
		return patient;
	}
	
	/**
	 * TODO
	 * @param socialInsuranceNr
	 * @return
	 */
	public boolean isSocialInsuranceNrValid(String socialInsuranceNr) {
		boolean isValid;
		isValid = socialInsuranceNr != null && socialInsuranceNr.length() == SOCIAL_INSURANCE_NR_LENGTH;
		// TODO: RegEx-Check, if valid?
		return isValid;
	}
}
