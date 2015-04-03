// default package
// Generated 01.04.2015 15:28:33 by Hibernate Tools 4.3.1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Referralletter generated by hbm2java
 */
@Entity
@Table(name = "referralletter", catalog = "oculusdb")
public class Referralletter implements java.io.Serializable {

	private Integer referralLetterId;
	private Examinationprotocol examinationprotocol;
	private Patient patient;

	public Referralletter() {
	}

	public Referralletter(Examinationprotocol examinationprotocol,
			Patient patient) {
		this.examinationprotocol = examinationprotocol;
		this.patient = patient;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "referralLetterId", unique = true, nullable = false)
	public Integer getReferralLetterId() {
		return this.referralLetterId;
	}

	public void setReferralLetterId(Integer referralLetterId) {
		this.referralLetterId = referralLetterId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "examinationProtocolId")
	public Examinationprotocol getExaminationprotocol() {
		return this.examinationprotocol;
	}

	public void setExaminationprotocol(Examinationprotocol examinationprotocol) {
		this.examinationprotocol = examinationprotocol;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "patientId")
	public Patient getPatient() {
		return this.patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

}