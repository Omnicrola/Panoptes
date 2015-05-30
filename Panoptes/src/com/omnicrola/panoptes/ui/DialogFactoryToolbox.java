package com.omnicrola.panoptes.ui;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class DialogFactoryToolbox {

    public JTextField makeField(int width) {
        return makeField(width, "");
    }

    public JTextField makeField(int width, String content) {
        JTextField jTextField = new JTextField(width);
        jTextField.setText(content);
        return jTextField;

    }

    public JLabel makeLabel(String string, Font font) {
        JLabel jLabel = new JLabel(string);
        jLabel.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
        jLabel.setAlignmentX(SwingConstants.RIGHT);
        jLabel.setFont(font);
        return jLabel;
    }

}
