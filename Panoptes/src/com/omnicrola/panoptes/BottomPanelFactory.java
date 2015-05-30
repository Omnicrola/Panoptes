package com.omnicrola.panoptes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;

import com.omnicrola.panoptes.control.DataController;
import com.omnicrola.panoptes.data.DisplayBlock;
import com.omnicrola.panoptes.ui.BlockPanel;
import com.omnicrola.panoptes.ui.BottomPanel;
import com.omnicrola.panoptes.ui.ColorProvider;
import com.omnicrola.panoptes.ui.DisplayBlockFlashGenerator;
import com.omnicrola.panoptes.ui.DisplayBlockModelPresenter;
import com.omnicrola.panoptes.ui.DisplayBlockMouseHandler;
import com.omnicrola.panoptes.ui.DisplayBlockPanelUpdateObserver;
import com.omnicrola.panoptes.ui.HorizontalAxisPanel;
import com.omnicrola.panoptes.ui.MouseTip;
import com.omnicrola.panoptes.ui.VerticalAxisPanel;
import com.omnicrola.panoptes.ui.listener.DisplayBlockMouseListener;
import com.omnicrola.panoptes.ui.listener.DisplayBlockRepaintObserver;
import com.omnicrola.panoptes.ui.listener.HorizontalScrollListener;
import com.omnicrola.util.ConstructorParameter;

public class BottomPanelFactory {

	private static final int MAX_PANEL_SIZE = 32768;
	@ConstructorParameter("settings")
	private final AppSettings settings;

	public BottomPanelFactory(AppSettings settings) {
		this.settings = settings;
	}

	private List<List<DisplayBlock>> buildBlockList(DataController controller) {
		List<List<DisplayBlock>> allDisplayBlocks = new ArrayList<List<DisplayBlock>>();
		int totalRows = this.settings.getDaysInWeek();
		int totalColumns = this.settings.getTotalBlocks();
		for (int row = 0; row < totalRows; row++) {
			ArrayList<DisplayBlock> displayblockRow = new ArrayList<DisplayBlock>();
			for (int column = 0; column < totalColumns; column++) {
				displayblockRow.add(new DisplayBlock(row, column));
			}
			allDisplayBlocks.add(displayblockRow);
		}
		return allDisplayBlocks;
	}

	public BottomPanel buildBottomPanel(DataController controller) {

		List<List<DisplayBlock>> blockList = buildBlockList(controller);
		DisplayBlockModelPresenter displayBlockModel = new DisplayBlockModelPresenter(blockList, new ColorProvider(),
				new DisplayBlockFlashGenerator());
		DisplayBlockMouseHandler mouseHandler = new DisplayBlockMouseHandler(displayBlockModel, controller);

		BlockPanel displayBlockPanel = new BlockPanel(displayBlockModel, new MouseTip(this.settings, controller),
				mouseHandler);

		DisplayBlockRepaintObserver displayBlockRepaintObserver = new DisplayBlockRepaintObserver(displayBlockPanel);
		displayBlockModel.addObserver(displayBlockRepaintObserver);

		DisplayBlockPanelUpdateObserver blockPanelUpdateObserver = new DisplayBlockPanelUpdateObserver(
				displayBlockModel, controller);
		controller.addObserver(blockPanelUpdateObserver);

		DisplayBlockMouseListener blockMouseListener = new DisplayBlockMouseListener(displayBlockPanel, mouseHandler);
		displayBlockPanel.addMouseListener(blockMouseListener);
		displayBlockPanel.addMouseMotionListener(blockMouseListener);

		BottomPanel bottomPanel = new BottomPanel(displayBlockModel);
		bottomPanel.setLayout(new BorderLayout());

		HorizontalAxisPanel hourAxisPanel = buildHourAxis(this.settings);
		VerticalAxisPanel dayAxisPanel = buildDayAxis(this.settings);

		bottomPanel.add(hourAxisPanel, BorderLayout.PAGE_START);
		bottomPanel.add(dayAxisPanel, BorderLayout.LINE_START);

		JScrollPane bottomScrollPane = new JScrollPane(displayBlockPanel);
		bottomScrollPane.getHorizontalScrollBar().addAdjustmentListener(new HorizontalScrollListener(hourAxisPanel));
		bottomPanel.add(bottomScrollPane, BorderLayout.CENTER);

		return bottomPanel;
	}

	private VerticalAxisPanel buildDayAxis(AppSettings settings) {
		VerticalAxisPanel dayAxisPanel = new VerticalAxisPanel(settings);
		dayAxisPanel.setPreferredSize(new Dimension(settings.getDayAxisWidth(), MAX_PANEL_SIZE));
		return dayAxisPanel;
	}

	private HorizontalAxisPanel buildHourAxis(AppSettings settings) {
		HorizontalAxisPanel hourAxisPanel = new HorizontalAxisPanel(settings);
		hourAxisPanel.setPreferredSize(new Dimension(MAX_PANEL_SIZE, settings.getHourAxisHeight()));
		return hourAxisPanel;
	}

}
