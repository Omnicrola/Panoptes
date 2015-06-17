package com.omnicrola.panoptes.data;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.omnicrola.testing.util.EnhancedTestCase;

public class TimeDataTest extends EnhancedTestCase {

	@Test
	public void testValues() throws Exception {
		String expectedProject = randString(50);
		String expectedCard = randString(50);
		String expectedRole = randString(50);

		TimeData timeData = new TimeData(expectedProject, expectedCard, expectedRole);
		assertEquals(expectedProject, timeData.getProject());
		assertEquals(expectedCard, timeData.getCard());
		assertEquals(expectedRole, timeData.getRole());
	}

	@Test
	public void testToString() throws Exception {
		String expectedProject = randString(50);
		String expectedCard = randString(50);
		String expectedRole = randString(50);
		String expectedToString = "TimeData[" + expectedProject + ", " + expectedCard + ", " + expectedRole + "]";

		TimeData timeData = new TimeData(expectedProject, expectedCard, expectedRole);
		assertEquals(expectedToString, timeData.toString());
	}

	@Test
	public void testNullInstance() throws Exception {

		assertEquals("", TimeData.NULL.getProject());
		assertEquals("", TimeData.NULL.getCard());
		assertEquals("", TimeData.NULL.getRole());
	}

	@Test
	public void testHashAndEquals() throws Exception {
		String expectedProject = randString(50);
		String expectedCard = randString(50);
		String expectedRole = randString(50);

		TimeData originalData = new TimeData(expectedProject, expectedCard, expectedRole);
		TimeData equalData = new TimeData(expectedProject, expectedCard, expectedRole);
		TimeData notEqualData1 = new TimeData(randString(50), expectedCard, expectedRole);
		TimeData notEqualData2 = new TimeData(expectedProject, randString(50), expectedRole);
		TimeData notEqualData3 = new TimeData(expectedProject, expectedCard, randString(50));
		TimeData notEqualData4 = new TimeData(randString(50), randString(50), randString(50));

		checkHashCodeAndEquality(originalData, equalData, notEqualData1, notEqualData2, notEqualData3, notEqualData4);

	}
}
