package com.omnicrola.panoptes.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class WorkStatement {

	public static final WorkStatement EMPTY = new WorkStatement("None", "", "", ProjectGroup.CLIENT_BILLABLE);

	private String projectName;
	private String client;
	private String projectCode;
	private ProjectGroup projectGroup;

	public WorkStatement() {
		this.projectName = "";
		this.client = "";
		this.projectCode = "";
		this.projectGroup = ProjectGroup.CLIENT_BILLABLE;
	}

	public WorkStatement(String projectName, String client, String projectCode, ProjectGroup projectGroup) {
		this.projectName = projectName;
		this.client = client;
		this.projectCode = projectCode;
	}

	public String getClient() {
		return this.client;
	}

	public String getProjectCode() {
		return this.projectCode;
	}

	public String getProjectName() {
		return this.projectName;
	}

	public ProjectGroup getProjectGroup() {
		return this.projectGroup;
	}

	public void setData(String projectName, String client, String projectCode, ProjectGroup group) {
		this.projectName = projectName;
		this.client = client;
		this.projectCode = projectCode;
		this.projectGroup = group;
	}

	@Override
	public String toString() {
		return this.projectName + " " + this.projectCode + " " + this.client;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.client == null) ? 0 : this.client.hashCode());
		result = prime * result + ((this.projectCode == null) ? 0 : this.projectCode.hashCode());
		result = prime * result + ((this.projectGroup == null) ? 0 : this.projectGroup.hashCode());
		result = prime * result + ((this.projectName == null) ? 0 : this.projectName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		WorkStatement other = (WorkStatement) obj;
		if (this.client == null) {
			if (other.client != null) {
				return false;
			}
		} else if (!this.client.equals(other.client)) {
			return false;
		}
		if (this.projectCode == null) {
			if (other.projectCode != null) {
				return false;
			}
		} else if (!this.projectCode.equals(other.projectCode)) {
			return false;
		}
		if (this.projectGroup != other.projectGroup) {
			return false;
		}
		if (this.projectName == null) {
			if (other.projectName != null) {
				return false;
			}
		} else if (!this.projectName.equals(other.projectName)) {
			return false;
		}
		return true;
	}

}
