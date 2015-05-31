package com.omnicrola.panoptes.data.fileIO.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.omnicrola.panoptes.AppSettings;
import com.omnicrola.panoptes.data.PersonalData;
import com.omnicrola.panoptes.data.WorkStatement;
import com.omnicrola.panoptes.data.fileIO.PanoptesException;

public class SettingsFileManager {

    private final AppSettings settings;
    private final XmlFileAdapter<XMLSettings> xmlFileReader;

    public SettingsFileManager(AppSettings settings, XmlFileAdapter<XMLSettings> xmlFileReader) {
        this.settings = settings;
        this.xmlFileReader = xmlFileReader;
    }

    private List<WorkStatement> buildDefaultWorkStatements() {
        ArrayList<WorkStatement> workStatementList = new ArrayList<WorkStatement>();
        workStatementList.add(new WorkStatement("P-2 Presales", "Menlo", "P00002", "NYE", 0));
        workStatementList.add(new WorkStatement("P-3 General", "Menlo", "P00003", "NYE", 0));
        workStatementList.add(new WorkStatement("P-5 Marketing", "Menlo", "P00005", "NYE", 0));
        workStatementList.add(new WorkStatement("P-7 Personnel", "Menlo", "P00007", "NYE", 0));
        workStatementList.add(new WorkStatement("P-10 Operations", "Menlo", "P000010", "NYE", 0));
        workStatementList.add(new WorkStatement("P-15 IT", "Menlo", "P000015", "NYE", 0));
        workStatementList.add(new WorkStatement("P-16 Facilities", "Menlo", "P000016", "NYE", 0));
        return workStatementList;
    }

    public XMLSettings load() {
        File fileSource = this.settings.getSettingsSaveLocation();
        try {
            XMLSettings worklist = this.xmlFileReader.loadObject(fileSource);
            return worklist;
        } catch (PanoptesException e) {
            XMLSettings xmlSettings = new XMLSettings();
            xmlSettings.personalData = new PersonalData();
            xmlSettings.statements = buildDefaultWorkStatements();
            return xmlSettings;
        }

    }

    public void save(List<WorkStatement> workStatements, PersonalData personalData) {
        File settingsSaveLocation = this.settings.getSettingsSaveLocation();
        XMLSettings xmlSettings = new XMLSettings();
        xmlSettings.statements = workStatements;
        xmlSettings.personalData = personalData;

        try {
            this.xmlFileReader.saveObject(xmlSettings, settingsSaveLocation);
        } catch (PanoptesException e) {
            e.printStackTrace();
        }

    }
}