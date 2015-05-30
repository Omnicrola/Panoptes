package com.omnicrola.panoptes;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;

import javax.swing.WindowConstants;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.omnicrola.panoptes.bootstrap.BagOfFactories;
import com.omnicrola.panoptes.bootstrap.MainWindowFactory;
import com.omnicrola.panoptes.bootstrap.Singularity;
import com.omnicrola.panoptes.control.DataController;
import com.omnicrola.testing.util.EnhancedTestCase;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Singularity.class)
public class PanoptesTest extends EnhancedTestCase {

	@Test
	public void testAfterffectsOfSingularity() throws Exception {
		useStaticMock(Singularity.class);
		MainWindowFactory mockWindowFactory = useMock(MainWindowFactory.class);
		DataController mockDataController = useMock(DataController.class);
		MainFrame mockFrame = useStrictMock(MainFrame.class);
		BagOfFactories mockFactoryBag = useMock(BagOfFactories.class);

		expect(Singularity.bang()).andReturn(mockFactoryBag).once();
		expect(mockFactoryBag.buildDataController()).andReturn(mockDataController).once();
		expect(mockFactoryBag.getMainWindowFactory()).andReturn(mockWindowFactory).once();
		expect(mockWindowFactory.build(mockDataController)).andReturn(mockFrame).once();

		mockFrame.setSize(800, 600);
		expectLastCall().once();
		mockFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		expectLastCall().once();
		mockFrame.setLocationRelativeTo(null);
		expectLastCall().once();
		mockFrame.setVisible(true);
		expectLastCall().once();

		startReplay();
		Panoptes.main(null);
		stopReplay();

	}
}
