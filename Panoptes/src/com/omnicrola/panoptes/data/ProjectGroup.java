package com.omnicrola.panoptes.data;

public enum ProjectGroup {
	CLIENT_BILLABLE("Client Billable", 1), INTERNAL_SUPPORT("Internal Support", 3), INTERNAL_PROJECT(
			"Internal Project", 2), NONE("None", 4);

	private String name;
	private int priority;

	private ProjectGroup(String name, int priority) {
		this.name = name;
		this.priority = priority;
	}

	public int getPriority() {
		return this.priority;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
