package com.omnicrola.panoptes.data.fileIO.xls;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;

import org.junit.Test;

import com.omnicrola.panoptes.data.ProjectGroup;
import com.omnicrola.panoptes.data.WorkStatement;

public class ExportRowComparatorTest {

	@Test
	public void testComparesCardNumbers() {

		ExportRowComparator exportRowComparator = new ExportRowComparator();
		WorkStatement workStatement = new WorkStatement("Project", "client", "code", ProjectGroup.CLIENT_BILLABLE);
		ExportDataRow dataRow1 = new ExportDataRow(workStatement, "Project", "DEV", "121");
		ExportDataRow dataRow2 = new ExportDataRow(workStatement, "Project", "DEV", "122");
		ExportDataRow dataRowA = new ExportDataRow(workStatement, "Project", "DEV", "ABC");

		assertEquals(-1, exportRowComparator.compare(dataRow1, dataRow2));
		assertEquals(1, exportRowComparator.compare(dataRow2, dataRow1));
		assertEquals(0, exportRowComparator.compare(dataRow1, dataRow1));
		assertEquals(0, exportRowComparator.compare(dataRow2, dataRow2));

		assertEquals(0, exportRowComparator.compare(dataRowA, dataRowA));
		assertEquals(-1, exportRowComparator.compare(dataRowA, dataRow1));
		assertEquals(1, exportRowComparator.compare(dataRow1, dataRowA));
	}

	@Test
	public void testActualSorting() {
		WorkStatement workStatement = new WorkStatement("Project", "client", "code", ProjectGroup.CLIENT_BILLABLE);
		ExportDataRow dataRow1 = new ExportDataRow(workStatement, "Project", "DEV", "121");
		ExportDataRow dataRow2 = new ExportDataRow(workStatement, "Project", "DEV", "122");
		ExportDataRow dataRow3 = new ExportDataRow(workStatement, "Project", "DEV", "500");
		ExportDataRow dataRowA = new ExportDataRow(workStatement, "Project", "DEV", "ABC");
		ExportDataRow dataRowX = new ExportDataRow(workStatement, "Project", "DEV", "XYZ");

		ArrayList<ExportDataRow> list = new ArrayList<>();
		list.add(dataRow3);
		list.add(dataRowX);
		list.add(dataRow2);
		list.add(dataRowA);
		list.add(dataRow1);
		list.sort(new ExportRowComparator());

		assertSame(dataRowA, list.get(0));
		assertSame(dataRowX, list.get(1));
		assertSame(dataRow1, list.get(2));
		assertSame(dataRow2, list.get(3));
		assertSame(dataRow3, list.get(4));
	}
}
