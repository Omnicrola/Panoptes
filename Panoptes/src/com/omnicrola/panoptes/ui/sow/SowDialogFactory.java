package com.omnicrola.panoptes.ui.sow;

import java.awt.*;
import java.awt.event.ActionListener;
import java.math.RoundingMode;
import java.text.NumberFormat;

import javax.swing.*;

import com.omnicrola.panoptes.data.WorkStatement;
import com.omnicrola.panoptes.ui.DialogFactoryToolbox;
import com.omnicrola.panoptes.ui.SelfSortingComboBoxModel;
import com.omnicrola.panoptes.ui.listener.NewSowActionListener;
import com.omnicrola.panoptes.ui.listener.SowSaveButtonActionListener;
import com.omnicrola.panoptes.ui.listener.SowSelectionActionListener;
import com.omnicrola.panoptes.ui.listener.SowTextFieldUpdater;

public class SowDialogFactory {

    private static final Font LABEL_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 12);
    private final DialogFactoryToolbox toolbox;

    public SowDialogFactory(DialogFactoryToolbox toolbox) {
        this.toolbox = toolbox;
    }

    private void addButtons(Container contentPane, ActionListener actionListener,
            SowModelPresenter sowModelPresenter) {
        JPanel jPanel = new JPanel();
        jPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 15, 5));
        jPanel.setLayout(new FlowLayout());

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(actionListener);
        jPanel.add(saveButton);

        JButton newButton = new JButton("New");
        newButton.addActionListener(new NewSowActionListener(sowModelPresenter));
        jPanel.add(newButton);

        contentPane.add(jPanel, BorderLayout.PAGE_END);
    }

    private JComboBox<WorkStatement> addDropdown(Container contentPane,
            SowModelPresenter sowModelPresenter) {
        SelfSortingComboBoxModel<WorkStatement> selfSortingModel = new SelfSortingComboBoxModel<>(
                sowModelPresenter.getAllWorkStatements(), new WorkStatementComparator());
        JComboBox<WorkStatement> workStatementDropdown = new JComboBox<WorkStatement>(
                selfSortingModel);
        workStatementDropdown.setPreferredSize(new Dimension(400, 25));

        SowSelectionActionListener sowSelectionActionListener = new SowSelectionActionListener(
                workStatementDropdown, sowModelPresenter);
        workStatementDropdown.addActionListener(sowSelectionActionListener);
        SowComboBoxUpdater sowComboBoxUpdater = new SowComboBoxUpdater(selfSortingModel);
        sowModelPresenter.addView(sowComboBoxUpdater);

        JPanel jPanel = new JPanel();
        jPanel.add(workStatementDropdown);
        contentPane.add(jPanel, BorderLayout.PAGE_START);

        return workStatementDropdown;
    }

    private void addEditor(Container contentPane, SowModelPresenter sowModelPresenter) {
        JPanel editorPanel = new JPanel();
        editorPanel.setLayout(new FlowLayout());
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();

        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
        addLabels(leftPanel);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
        ActionListener actionListener = addTextFields(rightPanel, sowModelPresenter);
        addButtons(contentPane, actionListener, sowModelPresenter);

        editorPanel.add(leftPanel);
        editorPanel.add(rightPanel);

        contentPane.add(editorPanel, BorderLayout.CENTER);
    }

    private void addLabels(JPanel leftPanel) {
        JLabel projectNameLabel = this.toolbox.makeLabel("Project Name:", LABEL_FONT);
        JLabel clientLabel = this.toolbox.makeLabel("Client:", LABEL_FONT);
        JLabel projectCodeLabel = this.toolbox.makeLabel("Project Code:", LABEL_FONT);
        JLabel sowLabel = this.toolbox.makeLabel("SOW Code:", LABEL_FONT);
        JLabel rateLabel = this.toolbox.makeLabel("Billable Rate:", LABEL_FONT);

        leftPanel.add(projectNameLabel);
        leftPanel.add(clientLabel);
        leftPanel.add(projectCodeLabel);
        leftPanel.add(sowLabel);
        leftPanel.add(rateLabel);
    }

    private ActionListener addTextFields(JPanel rightPanel, SowModelPresenter sowModelPresenter) {
        JTextField projectNameField = this.toolbox.makeField(20);
        JTextField clientField = this.toolbox.makeField(20);
        JTextField projectCodeField = this.toolbox.makeField(20);
        JTextField sowField = this.toolbox.makeField(20);
        JFormattedTextField rateField = createNumericTextField();

        SowTextFieldUpdater sowTextFieldViewUpdater = new SowTextFieldUpdater(projectNameField,
                clientField, projectCodeField, sowField, rateField);
        sowModelPresenter.addView(sowTextFieldViewUpdater);

        ActionListener saveButtonActionListener = new SowSaveButtonActionListener(sowModelPresenter,
                projectNameField, clientField, projectCodeField, sowField, rateField);

        rightPanel.add(projectNameField);
        rightPanel.add(clientField);
        rightPanel.add(projectCodeField);
        rightPanel.add(sowField);
        rightPanel.add(rateField);

        return saveButtonActionListener;
    }

    public SowDialog build(SowModelPresenter sowModelPresenter) {
        SowDialog sowDialog = new SowDialog();
        Container contentPane = sowDialog.getContentPane();
        contentPane.setLayout(new BorderLayout());

        JComboBox<WorkStatement> comboBox = addDropdown(contentPane, sowModelPresenter);
        addEditor(contentPane, sowModelPresenter);

        WorkStatement workStatement = sowModelPresenter.getAllWorkStatements().get(1);
        sowModelPresenter.setCurrentWorkStatement(workStatement);
        comboBox.setSelectedItem(workStatement);

        return sowDialog;
    }

    private JFormattedTextField createNumericTextField() {
        NumberFormat currencyFormatter = NumberFormat.getNumberInstance();
        currencyFormatter.setMaximumFractionDigits(2);
        currencyFormatter.setRoundingMode(RoundingMode.HALF_UP);
        currencyFormatter.setParseIntegerOnly(false);
        return new JFormattedTextField(currencyFormatter);
    }

}
