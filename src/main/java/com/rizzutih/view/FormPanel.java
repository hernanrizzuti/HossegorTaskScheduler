package com.rizzutih.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class FormPanel extends JPanel{

	//private JLabel keyLabel;
	private JLabel valueLabel;
	private JTextField keyField;
	private JButton addBtn;
	private FormListener formListener;
	private JLabel valueLabel2;
	private JTextField valueField2;
	private JLabel valueLabel3;
	private JTextField valueField3;
	private JLabel valueLabel4;

	private JButton doneBtn;

	private JDatePickerImpl datePickerFrom;
	private JDatePickerImpl datePickerTo;

	public FormPanel(String key, String value, String value3) {
		Dimension dim = getPreferredSize();
		dim.width=450;
		dim.height=250;
		setPreferredSize(dim);

		//keyLabel = new JLabel("Name:");
		valueLabel2 = new JLabel(value +":");
		valueLabel = new JLabel("Date from:");
		valueLabel4 = new JLabel("Date to:");
		valueLabel3 = new JLabel(value3 +":");
		keyField = new JTextField(key, 10);
		valueField2 = new JTextField(10);
		valueField3 = new JTextField(10);

		addBtn = new JButton("ADD");
		addBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String key = keyField.getText();
				String value  = datePickerFrom.getJFormattedTextField().getText();//valueField.getText();
				String value2 = valueField2.getText();
				String value3 = valueField3.getText();
				String value4 = datePickerTo.getJFormattedTextField().getText();//valueField4.getText();
				FormEvent ev = new FormEvent(this, key, value,value2,value3,value4);
				if(formListener!=null){
					formListener.formEventOccurred(ev);
				}
			}
		});
		
		UtilDateModel modelFrom = new UtilDateModel();
		JDatePanelImpl datePanelFrom = new JDatePanelImpl(modelFrom);
		datePickerFrom = new JDatePickerImpl(datePanelFrom, new DateLabelFormatter());
		
		UtilDateModel modelTo = new UtilDateModel();
		JDatePanelImpl datePanelTo = new JDatePanelImpl(modelTo);
		datePickerTo = new JDatePickerImpl(datePanelTo, new DateLabelFormatter());
		
		doneBtn = new JButton("DONE");
		doneBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(MainFrame.count > 0){
					MainFrame.count = 1;
				}
			}
		});
		

		Border innerBorder = BorderFactory.createTitledBorder("Add " + value);
		Border outerBorder = BorderFactory.createEmptyBorder(5,5,5,5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		//////////////// FIRST ROW /////////////////////////
		
		gc.weightx = 1;
		gc.weighty = 0.1;

/*		gc.gridx = 0;
		gc.gridy = 0;
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(keyLabel, gc);

		gc.gridx = 1;
		gc.gridy = 0;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(keyField, gc);*/

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

		//////////////// FOURTH ROW /////////////////////////
		
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.gridy = 3;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(valueLabel2, gc);

		gc.gridx = 1;
		gc.gridy = 3;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(valueField2, gc);

		//////////////// FIFTH ROW /////////////////////////
		
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.gridy = 4;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(valueLabel3, gc);

		gc.gridx = 1;
		gc.gridy = 4;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(valueField3, gc);

		//////////////// SIXTH ROW /////////////////////////

		gc.weightx = 1;
		gc.weighty = 2;

		gc.gridx =2;
		gc.gridy =5;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(doneBtn, gc);
		
		//////////////// SEVENTH ROW /////////////////////////

		gc.weightx = 1;
		gc.weighty = 2;

		gc.gridx =1;
		gc.gridy =5;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(addBtn, gc);
	}
	
	public void setFormListener(FormListener formListener) {
		this.formListener = formListener;
	}
}
