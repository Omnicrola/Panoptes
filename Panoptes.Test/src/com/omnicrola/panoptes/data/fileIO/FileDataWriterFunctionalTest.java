package com.omnicrola.panoptes.data.fileIO;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import com.omnicrola.panoptes.control.TimeblockSet;
import com.omnicrola.panoptes.data.DateWrapper;
import com.omnicrola.panoptes.data.IReadTimeblock;
import com.omnicrola.panoptes.data.TimeBlock;
import com.omnicrola.panoptes.data.TimeData;
import com.omnicrola.testing.util.EnhancedTestCase;

public class FileDataWriterFunctionalTest extends EnhancedTestCase {

	private static final String TEST_FILE_NAME = "panoptesMarshalTest.xml";

	@Test
	public void testMarshalling() throws Exception {
		IFileWriter fileDataWriter = new FileDataWriter();
		FileDataLoader fileDataLoader = new FileDataLoader();

		ArrayList<IReadTimeblock> timeblocks = createRandomTimeblocks();

		TimeblockSet timeblockSet = new TimeblockSet(timeblocks);
		DateWrapper expectedDate = createDateWrapper();
		fileDataWriter.writeDataToFile(new File(TEST_FILE_NAME), expectedDate, timeblockSet);

		ArrayList<TimeBlock> timeblockBuffer = new ArrayList<>();
		DateWrapper date = fileDataLoader.loadDataFromFile(new File(TEST_FILE_NAME), timeblockBuffer);
		assertEquals(timeblocks.size(), timeblockBuffer.size());
		assertEquals(expectedDate.getDate(), date.getDate());
	}

	private DateWrapper createDateWrapper() {
		Date time = randDate().getTime();
		return new DateWrapper(time);
	}

	private ArrayList<IReadTimeblock> createRandomTimeblocks() {
		ArrayList<IReadTimeblock> timeblocks = new ArrayList<>();

		timeblocks.add(createRandomBlock());
		timeblocks.add(createRandomBlock());
		timeblocks.add(createRandomBlock());
		timeblocks.add(createRandomBlock());
		timeblocks.add(createRandomBlock());
		timeblocks.add(createRandomBlock());
		timeblocks.add(createRandomBlock());

		return timeblocks;
	}

	private TimeBlock createRandomBlock() {
		TimeBlock timeBlock = new TimeBlock(randI(), randI());
		timeBlock.setOccupied(true);
		timeBlock.setTimeData(new TimeData(randString(50), randString(50), randString(50)));
		return timeBlock;
	}
}
