package com.rizzutih.view;

import java.util.EventObject;

public class FormEvent extends EventObject {

	private String key;
	//private String value;
	private String value2;
	private String value3;
	//private String value4;	
	
	public FormEvent(Object source) {
		super(source);	
	}
	
	public FormEvent(Object source, String key, /*String value,*/ String value2, String value3/*, String value4*/) {
		super(source);	
		this.key = key;
		//this.value = value;
		this.value2=value2;
		this.value3 = value3;
		//this.value4 = value4;
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String name) {
		this.key = name;
	}

/*	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}*/

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	public String getValue3() {
		return value3;
	}

	public void setValue3(String value3) {
		this.value3 = value3;
	}
/*	public String getValue4() {
		return value4;
	}

	public void setValue4(String value4) {
		this.value4 = value4;
	}*/

}
