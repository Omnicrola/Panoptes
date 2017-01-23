package com.omnicrola.panoptes.bootstrap;

import java.awt.event.KeyEvent;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.omnicrola.panoptes.MainFrame;
import com.omnicrola.panoptes.control.DataController;
import com.omnicrola.panoptes.data.fileIO.FileDataLoader;
import com.omnicrola.panoptes.data.fileIO.FileDataWriter;
import com.omnicrola.panoptes.data.fileIO.XmlFileFilter;
import com.omnicrola.panoptes.data.fileIO.xls.ExcelExporter;
import com.omnicrola.panoptes.data.fileIO.xls.ExportModelBuilder;
import com.omnicrola.panoptes.data.fileIO.xls.ExportRowGrouper;
import com.omnicrola.panoptes.data.fileIO.xls.PersonalDataExporter;
import com.omnicrola.panoptes.data.fileIO.xls.TimesheetDataExporter;
import com.omnicrola.panoptes.data.fileIO.xls.TimesheetRowWriter;
import com.omnicrola.panoptes.data.fileIO.xls.VerticalSumFormulaWriter;
import com.omnicrola.panoptes.data.fileIO.xls.XlsFileFilter;
import com.omnicrola.panoptes.data.fileIO.xls.XlsUtilityToolbox;
import com.omnicrola.panoptes.settings.AppSettings;
import com.omnicrola.panoptes.ui.AboutDialogBuilder;
import com.omnicrola.panoptes.ui.DialogFactoryToolbox;
import com.omnicrola.panoptes.ui.listener.ExportCommandListener;
import com.omnicrola.panoptes.ui.listener.NewWorkspaceCommandListener;
import com.omnicrola.panoptes.ui.listener.OpenSOWDisplayCommand;
import com.omnicrola.panoptes.ui.listener.OpenWorkspaceListener;
import com.omnicrola.panoptes.ui.listener.QuitCommandListener;
import com.omnicrola.panoptes.ui.listener.SaveWorkspaceCommandListener;
import com.omnicrola.panoptes.ui.listener.ShowAboutDialogCommandListener;
import com.omnicrola.panoptes.ui.personaldata.OpenPersonalDataDisplayCommand;
import com.omnicrola.panoptes.ui.personaldata.PersonalDataDisplayFactory;
import com.omnicrola.panoptes.ui.preferences.OpenPreferencesDisplayCommand;
import com.omnicrola.panoptes.ui.preferences.PreferencesDialogControllerFactory;
import com.omnicrola.panoptes.ui.preferences.PreferencesViewFactory;
import com.omnicrola.panoptes.ui.sow.SowDialogFactory;
import com.omnicrola.panoptes.ui.sow.SowModelPresenter;
import com.omnicrola.util.ConstructorParameter;

public class MenuFactory {

	@ConstructorParameter("itemFactory")
	private final MenuItemFactory menuItemFactory;
	@ConstructorParameter("settings")
	public AppSettings appSettings;

	public MenuFactory(MenuItemFactory menuItemFactory, AppSettings settings) {
		this.menuItemFactory = menuItemFactory;
		this.appSettings = settings;
	}

	private JMenu buildFileMenu(DataController controller, MainFrame mainFrame) {
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);

		JMenuItem newItem = this.menuItemFactory.createMenuItemWithHotkey("New", KeyEvent.VK_N,
				new NewWorkspaceCommandListener(controller));
		JMenuItem openItem = this.menuItemFactory.createMenuItemWithHotkey("Open", KeyEvent.VK_O,
				createOpenCommand(controller, mainFrame));
		JMenuItem saveItem = this.menuItemFactory.createMenuItemWithHotkey("Save", KeyEvent.VK_S,
				createSaveCommand(controller, mainFrame));
		JMenuItem exportItem = this.menuItemFactory.createMenuItemWithHotkey("Export", KeyEvent.VK_X,
				createExportCommand(controller, mainFrame));
		JMenuItem quitItem = this.menuItemFactory.createMenuItemWithHotkey("Quit", KeyEvent.VK_Q,
				new QuitCommandListener());

		fileMenu.add(newItem);
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.addSeparator();
		fileMenu.add(exportItem);
		fileMenu.addSeparator();
		fileMenu.add(quitItem);

		return fileMenu;
	}

	private JMenu buildHelpMenu(AppSettings appsettings, MainFrame mainFrame) {
		JMenu helpMenu = new JMenu("Help");
		JMenuItem aboutItem = this.menuItemFactory.createMenuItemWithHotkey("About", KeyEvent.VK_H,
				new ShowAboutDialogCommandListener(new AboutDialogBuilder(appsettings, mainFrame)));

		helpMenu.add(aboutItem);
		return helpMenu;
	}

	public JMenuBar buildMenuBar(DataController controller, MainFrame mainFrame) {
		JMenuBar jMenuBar = new JMenuBar();

		jMenuBar.add(buildFileMenu(controller, mainFrame));
		jMenuBar.add(buildSettingsMenu(controller));
		jMenuBar.add(buildHelpMenu(this.appSettings, mainFrame));

		return jMenuBar;
	}

	private JMenu buildSettingsMenu(DataController controller) {
		JMenu settingsMenu = new JMenu("Settings");
		settingsMenu.setMnemonic(KeyEvent.VK_S);

		DialogFactoryToolbox dialogFactoryToolbox = new DialogFactoryToolbox();
		OpenSOWDisplayCommand actionCommand = new OpenSOWDisplayCommand(new SowModelPresenter(controller),
				new SowDialogFactory(dialogFactoryToolbox));
		JMenuItem sowItem = this.menuItemFactory.createMenuItemWithHotkey("Statements of Work", KeyEvent.VK_S,
				actionCommand);

		OpenPersonalDataDisplayCommand openPersonalDataDisplayCommand = new OpenPersonalDataDisplayCommand(
				new PersonalDataDisplayFactory(controller));
		JMenuItem personalDataItem = this.menuItemFactory.createMenuItemWithHotkey("Personal Data", KeyEvent.VK_P,
				openPersonalDataDisplayCommand);

		OpenPreferencesDisplayCommand openSettingsDisplayCommand = new OpenPreferencesDisplayCommand(
				new PreferencesViewFactory(), new PreferencesDialogControllerFactory(controller));
		JMenuItem preferencesItem = this.menuItemFactory.createMenuItemWithHotkey("Preferences", KeyEvent.VK_E,
				openSettingsDisplayCommand);

		settingsMenu.add(sowItem);
		settingsMenu.add(personalDataItem);
		settingsMenu.add(preferencesItem);

		return settingsMenu;
	}

	private ExportCommandListener createExportCommand(DataController controller, MainFrame mainFrame) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(XlsFileFilter.FILTER);

		XlsUtilityToolbox toolbox = new XlsUtilityToolbox();
		ExportModelBuilder exportModelBuilder = new ExportModelBuilder(controller);
		PersonalDataExporter personalDataWriter = new PersonalDataExporter(toolbox);

		ExcelExporter excelExporter = new ExcelExporter(controller, exportModelBuilder, personalDataWriter,
				new TimesheetDataExporter(toolbox, new ExportRowGrouper(), new TimesheetRowWriter(),
						new VerticalSumFormulaWriter()));

		return new ExportCommandListener(controller, fileChooser, mainFrame, excelExporter, this.appSettings);
	}

	private OpenWorkspaceListener createOpenCommand(DataController controller, MainFrame mainFrame) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(XmlFileFilter.FILTER);
		return new OpenWorkspaceListener(controller, new FileDataLoader(), fileChooser, mainFrame);
	}

	private SaveWorkspaceCommandListener createSaveCommand(DataController controller, MainFrame mainFrame) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(XmlFileFilter.FILTER);
		return new SaveWorkspaceCommandListener(controller, new FileDataWriter(), fileChooser, mainFrame);
	}

}
