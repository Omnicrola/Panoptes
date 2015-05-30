package com.omnicrola.panoptes.bootstrap;

import java.awt.BorderLayout;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import com.omnicrola.panoptes.AppSettings;
import com.omnicrola.panoptes.BottomPanelFactory;
import com.omnicrola.panoptes.MainFrame;
import com.omnicrola.panoptes.TopPanelFactory;
import com.omnicrola.panoptes.control.DataController;
import com.omnicrola.panoptes.ui.BottomPanel;
import com.omnicrola.panoptes.ui.listener.MainFilenameObserver;
import com.omnicrola.util.ConstructorParameter;

public class MainWindowFactory {

	private static final String CLOCK_ICON_FILENAME = "/resources/clockIcon.png";

	@ConstructorParameter("settings")
	private final AppSettings settings;
	@ConstructorParameter("menuFactory")
	private final MenuFactory menuFactory;
	@ConstructorParameter("bottomPanelFactory")
	private final BottomPanelFactory bottomPanelFactory;
	@ConstructorParameter("topPanelFactory")
	private final TopPanelFactory topPanelFactory;

	public MainWindowFactory(AppSettings settings, MenuFactory menuFactory, TopPanelFactory topPanelFactory,
			BottomPanelFactory bottomPanelFactory) {
		this.settings = settings;
		this.menuFactory = menuFactory;
		this.topPanelFactory = topPanelFactory;
		this.bottomPanelFactory = bottomPanelFactory;

	}

	public MainFrame build(DataController controller) {
		MainFrame mainFrame = new MainFrame();
		mainFrame.setTitle(this.settings.getApplicationTitle());
		Image clockIcon = getClockIcon();
		mainFrame.setIconImage(clockIcon);

		controller.addObserver(new MainFilenameObserver(mainFrame, this.settings));
		JMenuBar jMenuBar = this.menuFactory.buildMenuBar(controller, this.settings, mainFrame);
		mainFrame.setJMenuBar(jMenuBar);

		mainFrame.setLayout(new BorderLayout());
		BottomPanel bottomPanel = this.bottomPanelFactory.buildBottomPanel(controller);

		JPanel topPanel = this.topPanelFactory.buildTopPanel(controller, bottomPanel.getDisplayBlockModelPresenter());

		mainFrame.add(topPanel, BorderLayout.PAGE_START);
		mainFrame.add(bottomPanel, BorderLayout.CENTER);
		return mainFrame;
	}

	private Image getClockIcon() {
		URL resourceUrl = getClass().getResource(CLOCK_ICON_FILENAME);
		ImageIcon clockIcon = new ImageIcon(resourceUrl);
		return clockIcon.getImage();
	}

}
