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
 * Servicecode generated by hbm2java
 */
@Entity
@Table(name = "servicecode", catalog = "oculus_d", uniqueConstraints = @UniqueConstraint(columnNames = "serviceCode"))
public class ServiceCode implements java.io.Serializable {

	private static final Logger _logger = LogManager.getLogger(ServiceCode.class
			.getName());
	private static final long serialVersionUID = 1L;
	private Integer serviceCodeId;
	private String serviceCode;
	private String description;
	private Set<ExaminationProtocolServiceCode> examinationprotocolservicecodes = new HashSet<ExaminationProtocolServiceCode>(
			0);

	public ServiceCode() {
	}

	public ServiceCode(String serviceCode, String description,
			Set<ExaminationProtocolServiceCode> examinationprotocolservicecodes) {
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
	public Set<ExaminationProtocolServiceCode> getExaminationprotocolservicecodes() {
		return this.examinationprotocolservicecodes;
	}

	public void setExaminationprotocolservicecodes(
			Set<ExaminationProtocolServiceCode> examinationprotocolservicecodes) {
		this.examinationprotocolservicecodes = examinationprotocolservicecodes;
	}

}
