package com.omnicrola.panoptes.ui.autocomplete;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.omnicrola.panoptes.control.DataController;
import com.omnicrola.panoptes.control.IControlObserver;
import com.omnicrola.panoptes.control.TimeblockSet;
import com.omnicrola.panoptes.data.IReadTimeblock;
import com.omnicrola.panoptes.data.TimeData;
import com.omnicrola.testing.util.EnhancedTestCase;

public class CardNumberProviderTest extends EnhancedTestCase {

	private DataController mockController;
	private String expectedNumber1;
	private String expectedNumber2;
	private String expectedNumber3;

	@Test
	public void testImplementsInterfaces() throws Exception {
		assertImplementsInterface(IOptionProvider.class, CardNumberProvider.class);
		assertImplementsInterface(IControlObserver.class, CardNumberProvider.class);
	}

	@Test
	public void testConstructorParams() throws Exception {
		DataController mockController = useMock(DataController.class);

		startReplay();
		CardNumberProvider cardNumberProvider = new CardNumberProvider(mockController);
		assertConstructionParamSame("dataController", mockController, cardNumberProvider);

	}

	@Test
	public void testDataChanged_UpdatesOptionList() throws Exception {
		setupMockTimeblockSet();
		startReplay();
		CardNumberProvider cardNumberProvider = new CardNumberProvider(this.mockController);
		assertEquals(0, cardNumberProvider.getOptionsList().size());

		cardNumberProvider.dataChanged();
		List<Object> optionsList = cardNumberProvider.getOptionsList();
		assertEquals(3, optionsList.size());
		assertTrue(optionsList.contains(this.expectedNumber1));
		assertTrue(optionsList.contains(this.expectedNumber2));
		assertTrue(optionsList.contains(this.expectedNumber3));
	}

	@Test
	public void testTimeblockSetChanged_UpdatesOptionList() throws Exception {
		TimeblockSet mockTimeblockSet = useMock(TimeblockSet.class);
		setupMockTimeblockSet();

		startReplay();
		CardNumberProvider cardNumberProvider = new CardNumberProvider(this.mockController);
		assertEquals(0, cardNumberProvider.getOptionsList().size());

		cardNumberProvider.timeblockSetChanged(mockTimeblockSet);

		List<Object> optionsList = cardNumberProvider.getOptionsList();
		assertEquals(3, optionsList.size());
		assertTrue(optionsList.contains(this.expectedNumber1));
		assertTrue(optionsList.contains(this.expectedNumber2));
		assertTrue(optionsList.contains(this.expectedNumber3));
	}

	public void setupMockTimeblockSet() {

		TimeblockSet mockTimeblockSet = useMock(TimeblockSet.class);
		this.expectedNumber1 = "cardnumber";
		this.expectedNumber2 = "a different number";
		this.expectedNumber3 = "duplicate";
		IReadTimeblock mockTimeblock1 = createMockBlockWithCardNumber(this.expectedNumber1);
		IReadTimeblock mockTimeblock2 = createMockBlockWithCardNumber(this.expectedNumber2);
		IReadTimeblock mockTimeblock3 = createMockBlockWithCardNumber(this.expectedNumber3);
		IReadTimeblock mockTimeblock4 = createMockBlockWithCardNumber(this.expectedNumber3);
		List<IReadTimeblock> timblocks = Arrays.asList(mockTimeblock1, mockTimeblock2, mockTimeblock3, mockTimeblock4);
		expect(mockTimeblockSet.getBlockSet()).andReturn(timblocks);
		this.mockController = useMock(DataController.class);
		expect(this.mockController.getAllTimeblocks()).andReturn(mockTimeblockSet);
	}

	private IReadTimeblock createMockBlockWithCardNumber(String expectedNumber) {
		IReadTimeblock mockTimeblock = useMock(IReadTimeblock.class);
		TimeData mockData = useMock(TimeData.class);
		expect(mockTimeblock.getTimeData()).andReturn(mockData);
		expect(mockData.getCard()).andReturn(expectedNumber);
		return mockTimeblock;
	}
}
