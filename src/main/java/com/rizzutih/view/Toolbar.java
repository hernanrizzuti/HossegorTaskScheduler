package com.rizzutih.view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Toolbar extends JPanel implements ActionListener {
	
	private JButton helloButton;
	private JButton byeButton;
	
	private StringListener textListener;

	public Toolbar() {
		setBorder(BorderFactory.createEtchedBorder());
		helloButton = new JButton("Hello");
		byeButton = new JButton("Bye");
		
		helloButton.addActionListener(this);
		byeButton.addActionListener(this);
		setLayout(new FlowLayout(new FlowLayout().LEFT));
		
		add(helloButton);
		add(byeButton);
	}

public void setStringListener(StringListener listener){
	this.textListener=listener;
}

	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton) e.getSource();
		if(clicked ==helloButton){
			if(textListener!=null){
				textListener.textEmitted("Hello\n");
			}
			
		}else if (clicked == byeButton){
			if(textListener!=null){
				textListener.textEmitted("Good Bye\n");
			}
		}
	}
	
}
