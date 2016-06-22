package com.rizzutih.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class CalendarPanel extends JPanel{

	private JLabel valueLabel;
	private JLabel valueLabel4;
	protected JDatePickerImpl datePickerFrom;
	protected JDatePickerImpl datePickerTo;
	
	public CalendarPanel() {
		System.out.println("Called");
		Dimension dim = getPreferredSize();
		dim.width=425;
		dim.height=150;
		setPreferredSize(dim);
		valueLabel = new JLabel("Date from:");
		valueLabel4 = new JLabel("Date to:");
		
		UtilDateModel modelFrom = new UtilDateModel();
		JDatePanelImpl datePanelFrom = new JDatePanelImpl(modelFrom);
		datePickerFrom = new JDatePickerImpl(datePanelFrom, new DateLabelFormatter());

		UtilDateModel modelTo = new UtilDateModel();
		JDatePanelImpl datePanelTo = new JDatePanelImpl(modelTo);
		datePickerTo = new JDatePickerImpl(datePanelTo, new DateLabelFormatter());
		
		Border innerBorder = BorderFactory.createTitledBorder("Dates");
		Border outerBorder = BorderFactory.createEmptyBorder(5,5,5,5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		//////////////// SECOND ROW /////////////////////////

		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.gridy = 1;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(valueLabel, gc);

		gc.gridx = 1;
		gc.gridy = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(datePickerFrom, gc);

		////////////////THIRD ROW /////////////////////////

		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.gridy = 2;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(valueLabel4, gc);

		gc.gridx = 1;
		gc.gridy = 2;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(datePickerTo, gc);
	}

	public JDatePickerImpl getDatePickerFrom() {
		return datePickerFrom;
	}

	public JDatePickerImpl getDatePickerTo() {
		return datePickerTo;
	}
}
