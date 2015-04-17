// default package
// Generated 14.04.2015 20:32:57 by Hibernate Tools 4.3.1

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
 * Usergroup generated by hbm2java
 */
@Entity
@Table(name = "usergroup", catalog = "oculusdb", uniqueConstraints = @UniqueConstraint(columnNames = "userGroupName"))
public class Usergroup implements java.io.Serializable {

	private Integer userGroupId;
	private String userGroupName;
	private String description;
	private Set<Userpermission> userpermissions = new HashSet<Userpermission>(0);
	private Set<User> users = new HashSet<User>(0);

	public Usergroup() {
	}

	public Usergroup(String userGroupName) {
		this.userGroupName = userGroupName;
	}

	public Usergroup(String userGroupName, String description,
			Set<Userpermission> userpermissions, Set<User> users) {
		this.userGroupName = userGroupName;
		this.description = description;
		this.userpermissions = userpermissions;
		this.users = users;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "userGroupId", unique = true, nullable = false)
	public Integer getUserGroupId() {
		return this.userGroupId;
	}

	public void setUserGroupId(Integer userGroupId) {
		this.userGroupId = userGroupId;
	}

	@Column(name = "userGroupName", unique = true, nullable = false, length = 50)
	public String getUserGroupName() {
		return this.userGroupName;
	}

	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
	}

	@Column(name = "description", length = 65535)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usergroup")
	public Set<Userpermission> getUserpermissions() {
		return this.userpermissions;
	}

	public void setUserpermissions(Set<Userpermission> userpermissions) {
		this.userpermissions = userpermissions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usergroup")
	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

}
