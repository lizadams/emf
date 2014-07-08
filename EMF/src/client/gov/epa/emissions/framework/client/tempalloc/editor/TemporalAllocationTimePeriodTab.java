package gov.epa.emissions.framework.client.tempalloc.editor;

import java.awt.BorderLayout;
import java.text.SimpleDateFormat;

import gov.epa.emissions.commons.gui.ComboBox;
import gov.epa.emissions.commons.gui.FormattedDateField;
import gov.epa.emissions.commons.gui.ManageChangeables;
import gov.epa.emissions.framework.client.EmfSession;
import gov.epa.emissions.framework.client.SpringLayoutGenerator;
import gov.epa.emissions.framework.services.EmfException;
import gov.epa.emissions.framework.services.tempalloc.TemporalAllocation;
import gov.epa.emissions.framework.services.tempalloc.TemporalAllocationResolution;
import gov.epa.emissions.framework.ui.Border;
import gov.epa.emissions.framework.ui.MessagePanel;
import gov.epa.emissions.framework.ui.SingleLineMessagePanel;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

public class TemporalAllocationTimePeriodTab extends JPanel implements TemporalAllocationTabView {
    
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("MM/dd/yyyy");
    
    private TemporalAllocation temporalAllocation;

    private ManageChangeables changeablesList;
    
    private ComboBox resolution;
    
    private TemporalAllocationResolution[] allResolutions;
    
    private FormattedDateField startDay, endDay;

    private EmfSession session;

    private MessagePanel messagePanel;
    
    public TemporalAllocationTimePeriodTab(TemporalAllocation temporalAllocation, EmfSession session, ManageChangeables changeablesList, SingleLineMessagePanel messagePanel) {
        super.setName("timeperiod");
        this.temporalAllocation = temporalAllocation;
        this.session = session;
        this.changeablesList = changeablesList;
        this.messagePanel = messagePanel;
    }
    
    public void setTemporalAllocation(TemporalAllocation temporalAllocation) {
        this.temporalAllocation = temporalAllocation;
    }
    
    public void display() {
        super.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(getBorderedPanel(createMainSection(), ""), BorderLayout.CENTER);
        super.add(panel, BorderLayout.NORTH);
    }

    private JPanel createMainSection() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JPanel panelTop = new JPanel(new SpringLayout());
        // panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        SpringLayoutGenerator layoutGenerator = new SpringLayoutGenerator();
        
        try {
            allResolutions = session.temporalAllocationService().getResolutions();
            resolution = new ComboBox("Choose an output resolution", allResolutions);
            resolution.setSelectedItem(temporalAllocation.getResolution());
        } catch (EmfException e) {
            messagePanel.setError("Could not retrieve temporal allocation measures");
        }
        changeablesList.addChangeable(resolution);
        layoutGenerator.addLabelWidgetPair("Resolution:", resolution, panelTop);

        startDay = new FormattedDateField("startDay", temporalAllocation.getStartDay(), DATE_FORMATTER, messagePanel);
        endDay = new FormattedDateField("endDay", temporalAllocation.getEndDay(), DATE_FORMATTER, messagePanel);
        changeablesList.addChangeable(startDay);
        changeablesList.addChangeable(endDay);
        layoutGenerator.addLabelWidgetPair("Time Period Start:", startDay, panelTop);
        layoutGenerator.addLabelWidgetPair("Time Period End:", endDay, panelTop);
        startDay.setToolTipText("Enter a date with the format MM/dd/yyyy");
        endDay.setToolTipText("Enter a date with the format MM/dd/yyyy");

        layoutGenerator.makeCompactGrid(panelTop, 3, 2, // rows, cols
                5, 5, // initialX, initialY
                10, 10);// xPad, yPad
        panel.add(panelTop);

        return panel;
    }

    private JPanel getBorderedPanel(JPanel component, String border) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new Border(border));
        panel.add(component);

        return panel;
    }
    
    public void save() {
        messagePanel.clear();
        temporalAllocation.setResolution((TemporalAllocationResolution) resolution.getSelectedItem());
        temporalAllocation.setStartDay(startDay.value());
        temporalAllocation.setEndDay(endDay.value());
    }
}