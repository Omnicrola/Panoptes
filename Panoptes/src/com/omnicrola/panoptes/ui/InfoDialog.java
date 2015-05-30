package com.omnicrola.panoptes.ui;

import java.awt.BasicStroke;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.StrokeBorder;

public class InfoDialog {

    public static JDialog createBlockingDialog(String string) {
        JDialog dialog = createDialog(string, true);
        dialog.setUndecorated(true);
        dialog.getRootPane().setBorder(new StrokeBorder(new BasicStroke(4)));
        return dialog;
    }

    public static JDialog createDialog(String string, boolean isModal) {
        JDialog jDialog = new JDialog();
        jDialog.setSize(new Dimension(200, 100));
        jDialog.setModal(isModal);
        jDialog.setResizable(false);
        jDialog.setLocationRelativeTo(null);

        Container contentPane = jDialog.getContentPane();
        JLabel jLabel = new JLabel(string);
        jLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(jLabel);

        return jDialog;
    }

    public static JDialog showDialog(String string) {
        JDialog jDialog = createDialog(string, false);
        jDialog.setVisible(true);
        return jDialog;
    }

    public static JDialog showModalDialog(String string) {
        JDialog jDialog = createDialog(string, true);
        jDialog.setVisible(true);
        return jDialog;
    }
}
