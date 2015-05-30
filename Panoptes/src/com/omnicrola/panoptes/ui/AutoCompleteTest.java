package com.omnicrola.panoptes.ui;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.omnicrola.panoptes.ui.autocomplete.AutoCompletePopupFactory;
import com.omnicrola.panoptes.ui.autocomplete.IOptionProvider;

public class AutoCompleteTest {

    public static void main(String[] args) {
        JFrame jDialog = new JFrame();
        jDialog.getContentPane().setLayout(new FlowLayout());

        AutoCompletePopupFactory factory = new AutoCompletePopupFactory();

        final JTextField jTextField = new JTextField(15);
        jDialog.add(jTextField);
        final ArrayList<Object> arrayList = new ArrayList<>();
        arrayList.add("one");
        arrayList.add("oneone");
        arrayList.add("onetwo");
        arrayList.add("two");
        arrayList.add("twothree");
        factory.addAutoComplete(jTextField, new IOptionProvider() {

            @Override
            public List<Object> getOptionsList() {
                return arrayList;
            }
        }, 5);

        jDialog.setSize(200, 200);
        jDialog.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jDialog.setLocationRelativeTo(null);
        jDialog.setVisible(true);
    }
}
