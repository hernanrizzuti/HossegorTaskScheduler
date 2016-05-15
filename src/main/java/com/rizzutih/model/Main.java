package com.rizzutih.model;

import javax.swing.SwingUtilities;
import com.rizzutih.view.MainFrame;

public class Main {
	public static void main(String[] args){
/*		IOHandler io =new IOHandler();
		io.writer(io.printSchedule(new IOHandler().reader("C:/Users/euror/OneDrive/Documentos/taskpeople.json")));*/
		//IOHandler io =new IOHandler();
		//io.writer(io.printSchedule(new IOHandler().reader("C:/Users/euror/OneDrive/Documentos/taskpeople2.json")));
		SwingUtilities.invokeLater(new Runnable() {
			
			public void run() {
				new MainFrame();
							
			}
		});

		
	}

}
