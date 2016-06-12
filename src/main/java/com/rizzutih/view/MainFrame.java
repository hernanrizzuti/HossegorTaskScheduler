package com.rizzutih.view;

import java.awt.BorderLayout;
import java.awt.Color;
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
import com.rizzutih.model.TaskHistory;

public class MainFrame extends JFrame{

	private JTextArea textPanel;
	private JTextArea errortextPanel;
	private FormPanel formPanel, formPanel2;
	private JButton uploadBtn;
	private JButton cleanBtn;
	static int count = 0;
	private String none = "none";
	private TaskHistory taskHistory;

	public MainFrame(TaskHistory taskHistory) {
		super("The Dudes");
		this.taskHistory=taskHistory;
		textPanel = new JTextArea();
		errortextPanel = new JTextArea();
		errortextPanel.setForeground(Color.RED);
		createPanels(taskHistory);
		createButtoms(taskHistory);

		JPanel nPnl = new JPanel();
		nPnl.add(formPanel, BorderLayout.WEST);
		nPnl.add(formPanel2, BorderLayout.EAST);
		add(nPnl, BorderLayout.NORTH);
		add(textPanel, BorderLayout.CENTER);
		JPanel sPnl = new JPanel();
		sPnl.add(uploadBtn, BorderLayout.WEST);
		sPnl.add(cleanBtn, BorderLayout.EAST);
		sPnl.add(errortextPanel, BorderLayout.SOUTH);
		add(sPnl,BorderLayout.SOUTH);

		setSize(900, 900);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);	
	}

	public void createButtoms(TaskHistory taskHistory) {
		cleanBtn = new JButton("CLEAR");
		cleanBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				textPanel.setText("");
				errortextPanel.setText("");
				taskHistory.clearHistory();
				count=0;
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
					taskHistory.clearHistory();
				} catch (IOHandlerException iohe) {
					setException(iohe);
				} catch (ObjectSplitterException ose) {
					setException(ose);
				}

			}
		});
	}

	public void createPanels(TaskHistory taskHistory) {
		formPanel = new FormPanel("Tasks", "Tasks", "People Needed");
		formPanel2 = new FormPanel("People", "People", "Favourite Task");
		formPanel.setFormListener(new FormListener(){
			public void formEventOccurred(FormEvent e) {
				String key = e.getKey();
				String value = e.getValue();
				String value2 = e.getValue2();
				String value3 = e.getValue3();
				String value4 = e.getValue4();
				if(!value2.equals("") || !value2.equals("")){
					if(key.equals("Tasks")){
						if((count == 0) || (count==1)){
							taskHistory.clearHistory();
						}
						try {
							value3 = checkNum(value3);
							taskHistory.add(none, 1);
							taskHistory.add(value2, Integer.parseInt(value3));
							outputText(key, value, value2, value3, value4);
						} catch (IOHandlerException e1) {
							setException(e1);
						}

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
				if(!value2.equals("") || !value2.equals("")){
					if(key.equals("People")){
						value3 = blankToNone(value3);
						try {
							checkTask(value3);
							checkFavouriteTaskNumber(value3);
							outputText(key, value, value2, value3, value4);
						} catch (IOHandlerException e1) {
							setException(e1);
						}

					}
				}
			}
		});
	}

	public void outputText(String key, String value, String value2,String value3, String value4) {
		errortextPanel.setText("");
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

	public String blankToNone(String favourityTask) {
		if (favourityTask.equals("")){
			favourityTask=none;
		}
		return favourityTask;
	}

	public String checkNum(String numberOfPeopleForTask) throws IOHandlerException {
		if(!numberOfPeopleForTask.matches("[0-9]")){
			throw new IOHandlerException("ERROR: People Needed Field Is Not A Number.");
		}
		return numberOfPeopleForTask;
	}

	public String checkTask(String taskName) throws IOHandlerException {
		String tempTaskName = taskName.toLowerCase();
		if(taskHistory.get(tempTaskName)==0){
			throw new IOHandlerException("ERROR: Task Is Not Part of The Task List.");
		}
		return taskName;
	}

	public void checkFavouriteTaskNumber(String favouriteTask) throws IOHandlerException {
		if(!none.equals(favouriteTask)){
			if(taskHistory.getTotalCountFavouriteTask(favouriteTask) <	taskHistory.get(favouriteTask)){
				taskHistory.countfavourityTask(favouriteTask);	
			}else
				throw new IOHandlerException("ERROR: Exceeded The Number Tasks available.");
		}
	}

	public void setException(Exception exception) {
		errortextPanel.setText("");
		errortextPanel.append("\n" + exception.getMessage());
	}
}
