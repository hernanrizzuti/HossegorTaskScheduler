package com.rizzutih.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.rizzutih.model.Extention;
import com.rizzutih.model.IOHandler;

public class MainFrame extends JFrame{

	private TextPanel textPanel;
	private FormPanel formPanel, formPanel2;
	private JButton uploadBtn;
	static int count = 0;

	public MainFrame() {
		super("Hossegor Dudes");
		setLayout(new BorderLayout());
		textPanel = new TextPanel();
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
							textPanel.appendText(key + ": " + value + " until " + value4 +";"+value2 + ","+ value3);
							count++;
						}else if(count == 1){
							textPanel.appendText(".\n"+ key + ": " + value + " until " + value4 +";"+value2 + ","+ value3);
						}else{ 
							textPanel.appendText(";"+value2 + ","+ value3);
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
							textPanel.appendText(key + ":" + value + " until " + value4 +";"+ value2 + ","+ value3);
							count++;
						}else if(count == 1){
							textPanel.appendText(".\n"+ key + ": " + value + " until " + value4 +";"+value2 + ","+ value3);
						}else{textPanel.appendText(";"+value2 + ","+ value3);
						}
						count++;
					}
				}
			}
		});

		uploadBtn = new JButton("UPLOAD");
		uploadBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				IOHandler ioHandler = new IOHandler();
				ioHandler.writer(ioHandler.getJson(textPanel.getText()+"."), Extention.JSON);
				String baseUrl = System.getProperty("user.home");
				ioHandler.writer(ioHandler.printSchedule(ioHandler.reader(baseUrl+"/"+"Hossegor.json")), Extention.TXT);
			}
		});

		add(textPanel, BorderLayout.SOUTH);
		add(formPanel, BorderLayout.WEST);
		add(formPanel2, BorderLayout.EAST);
		add(uploadBtn, BorderLayout.NORTH);

		setSize(800, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);	
	}
}