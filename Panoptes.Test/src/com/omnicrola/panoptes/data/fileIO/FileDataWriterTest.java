package com.omnicrola.panoptes.data.fileIO;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.omnicrola.panoptes.control.TimeblockSet;
import com.omnicrola.panoptes.data.DateWrapper;
import com.omnicrola.panoptes.data.IReadTimeblock;
import com.omnicrola.panoptes.data.TimeBlock;
import com.omnicrola.panoptes.data.TimeData;
import com.omnicrola.panoptes.data.fileIO.xml.XMLTimeData;
import com.omnicrola.panoptes.data.fileIO.xml.XMLTimeblock;
import com.omnicrola.panoptes.data.fileIO.xml.XMLTimeblockList;
import com.omnicrola.testing.util.EnhancedTestCase;

@RunWith(PowerMockRunner.class)
@PrepareForTest(JAXBContext.class)
public class FileDataWriterTest extends EnhancedTestCase {

	private JAXBContext mockContext;
	private Marshaller mockMarshaller;
	private File mockFile;
	private DateWrapper mockDate;
	private TimeblockSet mockTimeblockSet;
	private Capture<XMLTimeblockList> listCapture;

	@Before
	public void setupMocks() throws Exception {
		useStaticMock(JAXBContext.class);
		this.mockContext = useMock(JAXBContext.class);
		this.mockMarshaller = useMock(Marshaller.class);
		this.mockFile = useMock(File.class);
		this.mockDate = useMock(DateWrapper.class);
		this.mockTimeblockSet = useMock(TimeblockSet.class);

		this.listCapture = Capture.newInstance();
		expect(JAXBContext.newInstance(XMLTimeblockList.class)).andReturn(this.mockContext);
		expect(this.mockContext.createMarshaller()).andReturn(this.mockMarshaller);
		this.mockMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		this.mockMarshaller.marshal(capture(this.listCapture), eq(this.mockFile));
		expectLastCall().once();
	}

	@Test
	public void testWriteDataToFile_PassesDateThrough() throws Exception {
		List<IReadTimeblock> blockset = new ArrayList<>();
		expect(this.mockTimeblockSet.getBlockSet()).andReturn(blockset);
		startReplay();

		FileDataWriter fileDataWriter = new FileDataWriter();
		fileDataWriter.writeDataToFile(this.mockFile, this.mockDate, this.mockTimeblockSet);
		stopReplay();

		XMLTimeblockList actualXmlList = assertIsOfTypeAndGet(XMLTimeblockList.class, this.listCapture.getValue());
		assertSame(this.mockDate, actualXmlList.weekEnding);

	}

	@Test
	public void testWriteDataToFile_TransfersBlockData() throws Exception {
		TimeBlock timeBlock1 = new TimeBlock(randI(), randI());
		TimeBlock timeBlock2 = new TimeBlock(randI(), randI());
		TimeBlock timeBlock3 = new TimeBlock(randI(), randI());
		TimeBlock timeBlock4 = new TimeBlock(randI(), randI());
		timeBlock1.setOccupied(true);
		timeBlock2.setOccupied(true);
		timeBlock3.setOccupied(false);
		timeBlock4.setOccupied(true);
		List<IReadTimeblock> blockset = Arrays.asList(timeBlock1, timeBlock2, timeBlock3, timeBlock4);

		expect(this.mockTimeblockSet.getBlockSet()).andReturn(blockset);
		startReplay();

		FileDataWriter fileDataWriter = new FileDataWriter();
		fileDataWriter.writeDataToFile(this.mockFile, this.mockDate, this.mockTimeblockSet);
		stopReplay();

		XMLTimeblockList actualXmlList = assertIsOfTypeAndGet(XMLTimeblockList.class, this.listCapture.getValue());
		List<XMLTimeblock> actualTimeblocks = actualXmlList.timeblocks;
		assertEquals(3, actualTimeblocks.size());
		checkTimeblock(timeBlock1, actualTimeblocks.get(0));
	}

	private void checkTimeblock(TimeBlock expectedTimeBlock, XMLTimeblock xmlTimeblock) {
		assertEquals(expectedTimeBlock.getColumnIndex(), xmlTimeblock.blockIndex);
		assertEquals(expectedTimeBlock.getDayIndex(), xmlTimeblock.dayIndex);
		checkTimeData(expectedTimeBlock.getTimeData(), xmlTimeblock.timeData);
	}

	private void checkTimeData(TimeData expectedTimeData, XMLTimeData actualTimeData) {
		assertEquals(expectedTimeData.getProject(), actualTimeData.project);
		assertEquals(expectedTimeData.getCard(), actualTimeData.card);
		assertEquals(expectedTimeData.getRole(), actualTimeData.role);
	}
}
