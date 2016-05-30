package com.rizzutih.view;

import java.util.EventListener;

import com.rizzutih.model.IOHandlerException;

public interface FormListener extends EventListener{
	
	public void formEventOccurred(FormEvent e);

}
