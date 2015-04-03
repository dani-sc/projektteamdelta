// default package
// Generated 03.04.2015 16:43:15 by Hibernate Tools 4.3.1

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

/**
 * Servicecode generated by hbm2java
 */
@Entity
@Table(name = "servicecode", catalog = "oculusdb", uniqueConstraints = @UniqueConstraint(columnNames = "serviceCode"))
public class Servicecode implements java.io.Serializable {

	private Integer serviceCodeId;
	private String serviceCode;
	private String description;
	private Set<Examinationprotocolservicecode> examinationprotocolservicecodes = new HashSet<Examinationprotocolservicecode>(
			0);

	public Servicecode() {
	}

	public Servicecode(String serviceCode, String description,
			Set<Examinationprotocolservicecode> examinationprotocolservicecodes) {
		this.serviceCode = serviceCode;
		this.description = description;
		this.examinationprotocolservicecodes = examinationprotocolservicecodes;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "serviceCodeId", unique = true, nullable = false)
	public Integer getServiceCodeId() {
		return this.serviceCodeId;
	}

	public void setServiceCodeId(Integer serviceCodeId) {
		this.serviceCodeId = serviceCodeId;
	}

	@Column(name = "serviceCode", unique = true, length = 10)
	public String getServiceCode() {
		return this.serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	@Column(name = "description", length = 65535)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "servicecode")
	public Set<Examinationprotocolservicecode> getExaminationprotocolservicecodes() {
		return this.examinationprotocolservicecodes;
	}

	public void setExaminationprotocolservicecodes(
			Set<Examinationprotocolservicecode> examinationprotocolservicecodes) {
		this.examinationprotocolservicecodes = examinationprotocolservicecodes;
	}

}
