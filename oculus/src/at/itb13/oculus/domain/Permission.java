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
 * 
 * TODO: Insert description here.
 * 
 * @author Daniel Scheffknecht
 * @date 23.04.2015
 */
@Entity
@Table(name = "permission", catalog = "oculus_d", uniqueConstraints = @UniqueConstraint(columnNames = "permissionName"))
public class Permission implements java.io.Serializable {
	@SuppressWarnings("unused")
	private static final Logger _logger = LogManager.getLogger(Permission.class.getName());
	private static final long serialVersionUID = 1L;
	
	private Integer _permissionId;
	private String _permissionName;
	private String _description;
	private Set<UserPermission> _userPermissions = new HashSet<>(0);

	public Permission() {
	}

	public Permission(String permissionName, String description,
			Set<UserPermission> userpermissions) {
		_permissionName = permissionName;
		_description = description;
		_userPermissions = userpermissions;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "permissionId", unique = true, nullable = false)
	public Integer getPermissionId() {
		return _permissionId;
	}

	public void setPermissionId(Integer permissionId) {
		_permissionId = permissionId;
	}

	@Column(name = "permissionName", unique = true, length = 50)
	public String getPermissionName() {
		return _permissionName;
	}

	public void setPermissionName(String permissionName) {
		_permissionName = permissionName;
	}

	@Column(name = "description", length = 65535)
	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "permission")
	public Set<UserPermission> getUserpermissions() {
		return _userPermissions;
	}

	public void setUserpermissions(Set<UserPermission> userpermissions) {
		_userPermissions = userpermissions;
	}

}
