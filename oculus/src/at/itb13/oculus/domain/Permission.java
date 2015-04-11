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

/**
 * Permission generated by hbm2java
 */
@Entity
@Table(name = "permission", catalog = "oculusdb", uniqueConstraints = @UniqueConstraint(columnNames = "permissionName"))
public class Permission implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer permissionId;
	private String permissionName;
	private String description;
	private Set<UserPermission> userpermissions = new HashSet<UserPermission>(0);

	public Permission() {
	}

	public Permission(String permissionName, String description,
			Set<UserPermission> userpermissions) {
		this.permissionName = permissionName;
		this.description = description;
		this.userpermissions = userpermissions;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "permissionId", unique = true, nullable = false)
	public Integer getPermissionId() {
		return this.permissionId;
	}

	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}

	@Column(name = "permissionName", unique = true, length = 50)
	public String getPermissionName() {
		return this.permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	@Column(name = "description", length = 65535)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "permission")
	public Set<UserPermission> getUserpermissions() {
		return this.userpermissions;
	}

	public void setUserpermissions(Set<UserPermission> userpermissions) {
		this.userpermissions = userpermissions;
	}

}
