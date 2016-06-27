package com.omnicrola.panoptes.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class WorkStatement {

	public static final WorkStatement EMPTY = new WorkStatement("None", "", "", "", 0, ProjectGroup.CLIENT_BILLABLE);

	private String projectName;
	private String client;
	private String projectCode;
	private String sowCode;
	private float billableRate;
	private ProjectGroup projectGroup;

	public WorkStatement() {
		this.projectName = "";
		this.client = "";
		this.projectCode = "";
		this.sowCode = "";
		this.projectGroup = ProjectGroup.CLIENT_BILLABLE;
	}

	public WorkStatement(String projectName, String client, String projectCode, String sowCode, float billableRate,
			ProjectGroup projectGroup) {
		this.projectName = projectName;
		this.client = client;
		this.projectCode = projectCode;
		this.sowCode = sowCode;
		this.billableRate = billableRate;
	}

	public float getBillableRate() {
		return this.billableRate;
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

	public String getSowCode() {
		return this.sowCode;
	}

	public ProjectGroup getProjectGroup() {
		return this.projectGroup;
	}

	public void setData(String projectName, String client, String projectCode, String sowCode, float billableRate,
			ProjectGroup group) {
		this.projectName = projectName;
		this.client = client;
		this.projectCode = projectCode;
		this.sowCode = sowCode;
		this.billableRate = billableRate;
		this.projectGroup = group;
	}

	@Override
	public String toString() {
		return this.sowCode + " : " + this.projectName + " " + this.projectCode + " " + this.client;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(this.billableRate);
		result = prime * result + ((this.client == null) ? 0 : this.client.hashCode());
		result = prime * result + ((this.projectCode == null) ? 0 : this.projectCode.hashCode());
		result = prime * result + ((this.projectGroup == null) ? 0 : this.projectGroup.hashCode());
		result = prime * result + ((this.projectName == null) ? 0 : this.projectName.hashCode());
		result = prime * result + ((this.sowCode == null) ? 0 : this.sowCode.hashCode());
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
		if (Float.floatToIntBits(this.billableRate) != Float.floatToIntBits(other.billableRate)) {
			return false;
		}
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
		if (this.sowCode == null) {
			if (other.sowCode != null) {
				return false;
			}
		} else if (!this.sowCode.equals(other.sowCode)) {
			return false;
		}
		return true;
	}

}
