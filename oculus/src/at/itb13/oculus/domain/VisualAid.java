package at.itb13.oculus.domain;
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
 * Visualaid generated by hbm2java
 */
@Entity
@Table(name = "visualaid", catalog = "oculusdb")
public class VisualAid implements java.io.Serializable {

	private Integer visualAidId;
	private Diagnosis diagnosis;

	public VisualAid() {
	}

	public VisualAid(Diagnosis diagnosis) {
		this.diagnosis = diagnosis;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "visualAidId", unique = true, nullable = false)
	public Integer getVisualAidId() {
		return this.visualAidId;
	}

	public void setVisualAidId(Integer visualAidId) {
		this.visualAidId = visualAidId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "diagnosisId")
	public Diagnosis getDiagnosis() {
		return this.diagnosis;
	}

	public void setDiagnosis(Diagnosis diagnosis) {
		this.diagnosis = diagnosis;
	}

}