package com.omnicrola.panoptes.data;

public enum ProjectGroup {
	CLIENT_BILLABLE("Client Billable"), INTERNAL_SUPPORT("Internal Support"), INTERNAL_PROJECT("Internal Project");

	private String name;

	private ProjectGroup(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
