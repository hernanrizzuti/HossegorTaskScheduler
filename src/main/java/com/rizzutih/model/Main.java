package com.rizzutih.model;

import java.util.HashMap;

import javax.swing.SwingUtilities;

import com.rizzutih.view.MainFrame;

public class Main {
	public static void main(String[] args){

		SwingUtilities.invokeLater(new Runnable() {
			
			public void run() {
				new MainFrame(new TaskHistory(new HashMap<String, Integer>()));
							
			}
		});

		
	}

}
