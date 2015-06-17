package com.omnicrola.panoptes.data;

public class TimeData {

	public static final TimeData NULL = new TimeData("", "", "");
	private final String project;
	private final String card;
	private final String role;

	public TimeData(String project, String card, String role) {
		this.project = project;
		this.card = card;
		this.role = role;
	}

	public String getCard() {
		return this.card;
	}

	public String getProject() {
		return this.project;
	}

	public String getRole() {
		return this.role;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.card == null) ? 0 : this.card.hashCode());
		result = prime * result + ((this.project == null) ? 0 : this.project.hashCode());
		result = prime * result + ((this.role == null) ? 0 : this.role.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "TimeData[" + this.project + ", " + this.card + ", " + this.role + "]";
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
		TimeData other = (TimeData) obj;
		if (this.card == null) {
			if (other.card != null) {
				return false;
			}
		} else if (!this.card.equals(other.card)) {
			return false;
		}
		if (this.project == null) {
			if (other.project != null) {
				return false;
			}
		} else if (!this.project.equals(other.project)) {
			return false;
		}
		if (this.role == null) {
			if (other.role != null) {
				return false;
			}
		} else if (!this.role.equals(other.role)) {
			return false;
		}
		return true;
	}

}
