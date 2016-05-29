package com.rizzutih.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.rizzutih.model.Extention;
import com.rizzutih.model.IOHandler;
import com.rizzutih.model.IOHandlerException;
import com.rizzutih.model.ObjectSplitterException;

public class MainFrame extends JFrame{

	private JTextArea textPanel;
	private FormPanel formPanel, formPanel2;
	private JButton uploadBtn;
	private JButton cleanBtn;
	static int count = 0;

	public MainFrame() {
		super("The Dudes");
		setLayout(new GridLayout(3,2,3,3));
		textPanel = new JTextArea();
		formPanel = new FormPanel("Tasks", "Tasks", "People Needed");
		formPanel2 = new FormPanel("People", "People", "Favourite Task");
		formPanel.setFormListener(new FormListener(){
			public void formEventOccurred(FormEvent e){
				String key = e.getKey();
				String value = e.getValue();
				String value2 = e.getValue2();
				String value3 = e.getValue3();
				String value4 = e.getValue4();
				if(!value.equals("") || !value.equals("")){
					if(key.equals("Tasks")){
						if(count == 0){
							textPanel.append(key + ": " + value + " until " + value4 +";"+value2 + ","+ value3);
							count++;
						}else if(count == 1){
							textPanel.append(".\n"+ key + ": " + value + " until " + value4 +";"+value2 + ","+ value3);
						}else{ 
							textPanel.append(";"+value2 + ","+ value3);
						}
						count++;
					}
				}
			}
		});

		formPanel2.setFormListener(new FormListener(){
			public void formEventOccurred(FormEvent e){
				String key = e.getKey();
				String value = e.getValue();
				String value2 = e.getValue2();
				String value3 = e.getValue3();
				String value4 = e.getValue4();
				if(!value.equals("") || !value.equals("")){
					if(key.equals("People")){
						if(count == 0){
							textPanel.append(key + ":" + value + " until " + value4 +";"+ value2 + ","+ value3);
							count++;
						}else if(count == 1){
							textPanel.append(".\n"+ key + ": " + value + " until " + value4 +";"+value2 + ","+ value3);
						}else{textPanel.append(";"+value2 + ","+ value3);
						}
						count++;
					}
				}
			}
		});
		
		cleanBtn = new JButton("CLEAR");
		cleanBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				textPanel.setText("");
				
			}
		});

		uploadBtn = new JButton("UPLOAD");
		uploadBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				String baseUrl = System.getProperty("user.home");
				String path = null;
				IOHandler ioHandler = new IOHandler();
				try {
					ioHandler.writer(ioHandler.getJson(textPanel.getText()+"."), Extention.JSON);
					path = ioHandler.writer(ioHandler.printSchedule(ioHandler.reader(baseUrl+"/"+"Hossegor.json")), Extention.TXT);
					textPanel.append("\n\nDONE!");
					textPanel.append("\nFind schedule in: "+ path);
				} catch (IOHandlerException iohe) {
					textPanel.append("\n" + iohe.getMessage());
				} catch (ObjectSplitterException ose) {
					textPanel.append("\n" + ose.getMessage());
					
				}
				
			}
		});
		

		JPanel nPnl = new JPanel();
		nPnl.add(formPanel, BorderLayout.WEST);
		nPnl.add(formPanel2, BorderLayout.EAST);
		add(nPnl, BorderLayout.NORTH);
		add(textPanel, BorderLayout.CENTER);
		JPanel sPnl = new JPanel();
		sPnl.add(uploadBtn, BorderLayout.WEST);
		sPnl.add(cleanBtn, BorderLayout.EAST);
		add(sPnl,BorderLayout.SOUTH);

		setSize(900, 900);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);	
	}
}
