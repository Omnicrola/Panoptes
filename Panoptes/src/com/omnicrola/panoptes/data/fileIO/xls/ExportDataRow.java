package com.omnicrola.panoptes.data.fileIO.xls;

import com.omnicrola.panoptes.data.WorkStatement;

public class ExportDataRow {

	public static final ExportDataRow EMPTY = new ExportDataRow(WorkStatement.EMPTY, "", "", "");

	private final WorkStatement workStatement;
	private final float[] hourArray;
	private final String project;
	private final String role;
	private final String card;

	public ExportDataRow(WorkStatement workStatement, String project, String role, String card) {
		this.workStatement = workStatement;
		this.project = project;
		this.role = role;
		this.card = card;
		this.hourArray = new float[7];

	}

	public void addTime(int dayIndex, float time) {
		this.hourArray[dayIndex] += time;
	}

	public String getCard() {
		return this.card;
	}

	public float getDay(int dayIndex) {
		return this.hourArray[dayIndex];
	}

	public String getDescription() {
		return this.project + " " + this.role + " " + this.card;
	}

	public String getRole() {
		return this.role;
	}

	public float getTotalTime() {
		float total = 0;
		for (int i = 0; i < this.hourArray.length; i++) {
			total += this.hourArray[i];
		}
		return total;
	}

	public WorkStatement getWorkStatement() {
		return this.workStatement;
	}

}
