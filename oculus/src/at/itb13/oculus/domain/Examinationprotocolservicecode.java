package at.itb13.oculus.domain;

/**
 * 
 * TODO: Insert description here.
 * 
 * @author Daniel Scheffknecht
 * @date 03.04.2015
 */
public class Examinationprotocolservicecode implements java.io.Serializable {

	private ExaminationprotocolservicecodeId id;
	private Examinationprotocol examinationprotocol;
	private Insurancecarrier insurancecarrier;
	private Servicecode servicecode;

	public Examinationprotocolservicecode() {
	}

	public Examinationprotocolservicecode(ExaminationprotocolservicecodeId id,
			Examinationprotocol examinationprotocol,
			Insurancecarrier insurancecarrier, Servicecode servicecode) {
		this.id = id;
		this.examinationprotocol = examinationprotocol;
		this.insurancecarrier = insurancecarrier;
		this.servicecode = servicecode;
	}

	public ExaminationprotocolservicecodeId getId() {
		return this.id;
	}

	public void setId(ExaminationprotocolservicecodeId id) {
		this.id = id;
	}

	public Examinationprotocol getExaminationprotocol() {
		return this.examinationprotocol;
	}

	public void setExaminationprotocol(Examinationprotocol examinationprotocol) {
		this.examinationprotocol = examinationprotocol;
	}

	public Insurancecarrier getInsurancecarrier() {
		return this.insurancecarrier;
	}

	public void setInsurancecarrier(Insurancecarrier insurancecarrier) {
		this.insurancecarrier = insurancecarrier;
	}

	public Servicecode getServicecode() {
		return this.servicecode;
	}

	public void setServicecode(Servicecode servicecode) {
		this.servicecode = servicecode;
	}

}