package com.omnicrola.panoptes.ui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;

import com.omnicrola.panoptes.MainFrame;
import com.omnicrola.panoptes.settings.AppSettings;

public class AboutDialogBuilder {

    private static final int DIALOG_WIDTH = 450;
    private static final int DIALOG_HEIGHT = 250;
    private final AppSettings appsettings;
    private final MainFrame mainFrame;

    public AboutDialogBuilder(AppSettings appsettings, MainFrame mainFrame) {
        this.appsettings = appsettings;
        this.mainFrame = mainFrame;
    }

    public JDialog build() {
        JDialog jDialog = new JDialog(this.mainFrame);
        jDialog.setSize(new Dimension(DIALOG_WIDTH, DIALOG_HEIGHT));
        jDialog.setResizable(false);
        jDialog.setModal(true);

        Container contentPane = jDialog.getContentPane();
        contentPane.setLayout(new FlowLayout());

        String aboutText = wrapTextInHTML(this.appsettings.getAboutText());
        JLabel jLabel = new JLabel(aboutText);
        jLabel.setMaximumSize(new Dimension(240, 100));
        contentPane.add(jLabel);

        return jDialog;
    }

    private String wrapTextInHTML(String text) {
        int labelWidth = DIALOG_WIDTH - 50;
        return "<html><body style='width:" + labelWidth + ";'>" + text + "</body></html>";
    }

}
