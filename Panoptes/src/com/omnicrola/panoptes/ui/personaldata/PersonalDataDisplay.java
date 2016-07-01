package com.omnicrola.panoptes.ui.personaldata;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.omnicrola.panoptes.ui.DialogFactoryToolbox;
import com.omnicrola.panoptes.ui.IDialog;

public class PersonalDataDisplay extends JDialog implements IDialog {

	private static final long serialVersionUID = 7028738974911496480L;
	private static final Font LABEL_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 12);

	public static final String BUTTON_SAVE = "button-save";
	public static final String BUTTON_CLOSE = "button-close";

	public static final String FIRST_NAME = "first-name";
	public static final String LAST_NAME = "last-name";
	public static final String COMPANY_NAME = "company-name";
	public static final String ADDRESS = "address";
	public static final String CITY = "city";
	public static final String STATE = "state";
	public static final String ZIP = "zipcode";
	public static final String PHONE = "phone";
	public static final String EMAIL = "email";

	private HashMap<String, Component> namedComponents;

	public PersonalDataDisplay() {
		this.setModal(true);
		this.setSize(425, 300);
		this.setTitle("Personal Data");
		this.setResizable(false);
		this.namedComponents = new HashMap<>();
		initialize();
	}

	private void initialize() {
		JPanel leftPanel = new JPanel();
		JPanel rightPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));

		JPanel editorPanel = new JPanel();
		editorPanel.setLayout(new FlowLayout());
		editorPanel.add(leftPanel);
		editorPanel.add(rightPanel);

		addLabels(leftPanel);
		addFields(rightPanel);
		JPanel buttonPanel = buildButtons();

		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(editorPanel, BorderLayout.CENTER);
		contentPane.add(buttonPanel, BorderLayout.PAGE_END);

	}

	private void addLabels(JPanel leftPanel) {
		leftPanel.add(DialogFactoryToolbox.makeLabel("First Name:", LABEL_FONT));
		leftPanel.add(DialogFactoryToolbox.makeLabel("Last Name:", LABEL_FONT));
		leftPanel.add(DialogFactoryToolbox.makeLabel("Company Name:", LABEL_FONT));
		leftPanel.add(Box.createVerticalStrut(20));

		leftPanel.add(DialogFactoryToolbox.makeLabel("Address:", LABEL_FONT));
		leftPanel.add(DialogFactoryToolbox.makeLabel("City:", LABEL_FONT));
		leftPanel.add(DialogFactoryToolbox.makeLabel("State:", LABEL_FONT));
		leftPanel.add(DialogFactoryToolbox.makeLabel("ZIP:", LABEL_FONT));
		leftPanel.add(Box.createVerticalStrut(20));

		leftPanel.add(DialogFactoryToolbox.makeLabel("Phone:", LABEL_FONT));
		leftPanel.add(DialogFactoryToolbox.makeLabel("Email:", LABEL_FONT));
	}

	public void addFields(JPanel rightPanel) {
		int width = 20;

		rightPanel.add(makeField(width, FIRST_NAME));
		rightPanel.add(makeField(width, LAST_NAME));
		rightPanel.add(makeField(width, COMPANY_NAME));
		rightPanel.add(Box.createVerticalStrut(20));
		rightPanel.add(makeField(width, ADDRESS));
		rightPanel.add(makeField(width, CITY));
		rightPanel.add(makeField(width, STATE));
		rightPanel.add(makeField(width, ZIP));
		rightPanel.add(Box.createVerticalStrut(20));
		rightPanel.add(makeField(width, PHONE));
		rightPanel.add(makeField(width, EMAIL));

	}

	private JTextField makeField(int width, String name) {
		JTextField field = DialogFactoryToolbox.makeField(width);
		storeComponent(field, name);
		return field;
	}

	private JPanel buildButtons() {
		JButton saveButton = new JButton("Save");
		storeComponent(saveButton, BUTTON_SAVE);
		saveButton.setAlignmentX(SwingConstants.RIGHT);

		JButton closeButton = new JButton("Close");
		storeComponent(closeButton, BUTTON_CLOSE);
		;

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(closeButton);
		buttonPanel.add(saveButton);
		return buttonPanel;

	}

	public <T extends Component> T getNamedComponent(String name) {
		Component component = this.namedComponents.get(name);
		return (T) component;
	}

	private void storeComponent(Component component, String name) {
		component.setName(name);
		this.namedComponents.put(name, component);
	}

	@Override
	public void closeDisplay() {
		this.setVisible(false);
	}

	@Override
	public void showDisplay() {
		this.setVisible(false);
	}
}
