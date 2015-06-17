package com.omnicrola.panoptes;

import static com.omnicrola.panoptes.ui.SwingComponentFactory.createComboBox;
import static com.omnicrola.panoptes.ui.SwingComponentFactory.createLabel;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import com.omnicrola.panoptes.control.DataController;
import com.omnicrola.panoptes.data.DateWrapper;
import com.omnicrola.panoptes.settings.AppSettings;
import com.omnicrola.panoptes.ui.DisplayBlockModelPresenter;
import com.omnicrola.panoptes.ui.SimpleFocusTraversalPolicy;
import com.omnicrola.panoptes.ui.TopPanel;
import com.omnicrola.panoptes.ui.TopPanelInputSet;
import com.omnicrola.panoptes.ui.autocomplete.AutoCompletePopupFactory;
import com.omnicrola.panoptes.ui.autocomplete.CardNumberProvider;
import com.omnicrola.panoptes.ui.autocomplete.ProjectOptionProvider;
import com.omnicrola.panoptes.ui.listener.AddTimeblockListener;
import com.omnicrola.panoptes.ui.listener.ComboBoxUpdateObserver;
import com.omnicrola.panoptes.ui.listener.TextHighlightingFocusListener;
import com.omnicrola.panoptes.ui.listener.WeekEndingComboBoxDataObserver;
import com.omnicrola.util.ConstructorParameter;

public class TopPanelFactory {

	private static final long ONE_WEEK_IN_MILLIS = 7 * 24 * 60 * 60 * 1000 - 1;
	@ConstructorParameter("settings")
	private final AppSettings settings;
	@ConstructorParameter("autoCompletePopupFactory")
	private final AutoCompletePopupFactory autoCompleteFactory;

	public TopPanelFactory(AppSettings settings, AutoCompletePopupFactory autoCompleteFactory) {
		this.settings = settings;
		this.autoCompleteFactory = autoCompleteFactory;
	}

	public TopPanel buildTopPanel(DataController controller, DisplayBlockModelPresenter displayModelPresenter) {

		JLabel weekEndingLabel = createLabel("Week Ending:", 100);
		JLabel dayLabel = createLabel("Day:", 100);
		JLabel startLabel = createLabel("Start:", 50);
		JLabel endLabel = createLabel("End:", 50);
		JLabel projectLabel = createLabel("Project:", 50);
		JLabel cardLabel = createLabel("Card:", 50);
		JLabel roleLabel = createLabel("Role:", 50);

		DateWrapper[] weekEndingDays = getWeekEndingDays();
		JComboBox<DateWrapper> weekEndingComboBox = createComboBox(weekEndingDays);
		JComboBox<String> dayComboBox = createComboBox(this.settings.getDays());
		JComboBox<String> startComboBox = createComboBox(this.settings.getTimeIncrements());
		JComboBox<String> endComboBox = createComboBox(this.settings.getTimeIncrements());
		JTextField projectField = buildProjectField(controller);
		JTextField cardField = buildCardField(controller);
		JComboBox<String> roleField = createComboBox(this.settings.getRoles());
		JButton addButton = new JButton("Add");

		TopPanelInputSet topPanelInputSet = new TopPanelInputSet(displayModelPresenter, controller, weekEndingComboBox,
				dayComboBox, startComboBox, endComboBox, projectField, cardField, roleField);

		controller.addObserver(new WeekEndingComboBoxDataObserver(controller, weekEndingComboBox, weekEndingDays));
		displayModelPresenter.addObserver(new ComboBoxUpdateObserver(topPanelInputSet));

		AddTimeblockListener addTimeblockListener = new AddTimeblockListener(controller, topPanelInputSet);
		addButton.addActionListener(addTimeblockListener);
		topPanelInputSet.setSelection(1, 1, 1);

		TopPanel topPanel = new TopPanel();
		topPanel.setPreferredSize(new Dimension(this.settings.getScreenWidth(), 100));
		topPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

		topPanel.setLayout(new GridBagLayout());

		topPanel.add(weekEndingLabel, buildConstraints(0, 0));
		topPanel.add(dayLabel, buildConstraints(0, 1));
		topPanel.add(startLabel, buildConstraints(2, 0));
		topPanel.add(endLabel, buildConstraints(2, 1));
		topPanel.add(projectLabel, buildConstraints(4, 0));
		topPanel.add(cardLabel, buildConstraints(4, 1));
		topPanel.add(roleLabel, buildConstraints(6, 0));

		topPanel.add(weekEndingComboBox, buildConstraints(1, 0));
		topPanel.add(dayComboBox, buildConstraints(1, 1));
		topPanel.add(startComboBox, buildConstraints(3, 0));
		topPanel.add(endComboBox, buildConstraints(3, 1));
		topPanel.add(projectField, buildConstraints(5, 0));
		topPanel.add(cardField, buildConstraints(5, 1));
		topPanel.add(roleField, buildConstraints(7, 0));
		topPanel.add(addButton, buildConstraints(7, 1));

		List<Component> traversalList = Arrays.<Component> asList(weekEndingComboBox, dayComboBox, startComboBox,
				endComboBox, projectField, cardField, roleField, addButton);
		topPanel.setFocusTraversalPolicyProvider(true);
		topPanel.setFocusTraversalPolicy(new SimpleFocusTraversalPolicy(traversalList));

		return topPanel;
	}

	private JTextField buildCardField(DataController dataController) {
		JTextField cardField = new JTextField("", 10);
		cardField.addFocusListener(new TextHighlightingFocusListener(cardField));
		CardNumberProvider cardNumberProvider = new CardNumberProvider(dataController);
		dataController.addObserver(cardNumberProvider);
		this.autoCompleteFactory.addAutoComplete(cardField, cardNumberProvider, 3);
		return cardField;
	}

	private GridBagConstraints buildConstraints(int gridX, int gridY) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = gridX;
		constraints.gridy = gridY;
		constraints.insets = new Insets(2, 2, 2, 2);
		return constraints;
	}

	private JTextField buildProjectField(DataController controller) {
		JTextField projectField = new JTextField("", 10);
		projectField.addFocusListener(new TextHighlightingFocusListener(projectField));
		this.autoCompleteFactory.addAutoComplete(projectField, new ProjectOptionProvider(controller), 3);
		return projectField;
	}

	private void findFriday(GregorianCalendar calendar) {
		while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY) {
			calendar.add(Calendar.DATE, 1);
		}
	}

	private DateWrapper[] getWeekEndingDays() {
		long twoWeeksFromNow = System.currentTimeMillis() + ONE_WEEK_IN_MILLIS;

		GregorianCalendar calendar = new GregorianCalendar();
		calendar.add(Calendar.MONTH, -3);

		findFriday(calendar);

		List<DateWrapper> weekEndingDates = new ArrayList<>();
		while (calendar.getTimeInMillis() < twoWeeksFromNow) {
			weekEndingDates.add(new DateWrapper(calendar.getTime()));
			calendar.add(Calendar.DATE, 7);
		}

		Collections.reverse(weekEndingDates);
		DateWrapper[] dateArray = new DateWrapper[weekEndingDates.size()];
		weekEndingDates.toArray(dateArray);
		return dateArray;

	}
}
