package com.tor.web.model;
// Generated 13.10.2017 17:29:18 by Hibernate Tools 5.1.0.Alpha2

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * IktElectronicCourses generated by hbm2java
 */
@Entity
@Table(name = "IKT_ELECTRONIC_COURSES", schema = "DCT_LOCAL")
@XmlRootElement
public class IktElectronicCourses implements java.io.Serializable {

	private Integer id;
	private String name;
	private String shortname;
	private Integer departmentId;

	public IktElectronicCourses() {
	}

	public IktElectronicCourses(Integer id, String name, String shortname,
			Integer departmentId) {
		this.id = id;
		this.name = name;
		this.shortname = shortname;
		this.departmentId = departmentId;
	}

	@Id
	@Column(name = "ID", nullable = false, precision = 22, scale = 0)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "NAME", nullable = false, length = 300)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "SHORTNAME", nullable = false, length = 80)
	public String getShortname() {
		return this.shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	@Column(name = "DEPARTMENT_ID", nullable = false, precision = 22, scale = 0)
	public Integer getDepartmentId() {
		return this.departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

}
