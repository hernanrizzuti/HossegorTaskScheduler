package com.rizzutih.model;

import static org.mockito.Mockito.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.*;

public class IOHandlerTest {

	@Spy private IOHandler spyIO;
	@Mock private File mockFile;
	@Mock private BufferedWriter mockBW;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testWriter() throws IOException{
		doReturn(mockFile).when(spyIO).getFile(Extention.JSON);
		doReturn(mockBW).when(spyIO).getBufferedWriter(mockFile);
		spyIO.writer("Hi", Extention.JSON);
		verify(mockBW).write("Hi");
	}
	
	@Test
	public void testWriterFileDoesntExist() throws IOException{
		doReturn(mockFile).when(spyIO).getFile(Extention.JSON);
		doReturn(false).when(mockFile).exists();
		doReturn(mockBW).when(spyIO).getBufferedWriter(mockFile);
		spyIO.writer("Hi", Extention.JSON);
		verify(mockBW).write("Hi");
	}
	
	@Test
	public void testgetFileJSONExtention() throws IOException{
		IOHandler io = new IOHandler();
		assertEquals(true, io.getFile(Extention.JSON).getAbsolutePath().contains("json"));
	}
	
	@Test
	public void testgetFileTXTExtention() throws IOException{
		IOHandler io = new IOHandler();
		assertEquals(true, io.getFile(Extention.TXT).getAbsolutePath().contains("txt"));
	}
	
	
	@Test
	public void testGenJsonReturnsJsonFromTextPanelStringwithOnePairOfText() throws IOHandlerException{
		IOHandler io = new IOHandler();
		String textpanel = "tasks: 30-07-2016 until 30-07-2016, Cooking, 2;Drink Preparation, 1;House Keeping, 2;Photo Taking, 1;Washing Up, 2.people: 30-07-2016 until 30-07-2016, Nico, House Keeping;Yann, Drink  Preparation;Hernan, Cooking;Andy, none;Simon, none;Bilal, none;Faz, Photo Taking;Antoine, none.";
		String json = io.getJson(textpanel);
		System.out.println(json);
	}
	
	@Test
	public void testGenJsonReturnsJsonFromTextPanelStringwithTwoPairsOfText() throws IOHandlerException{
		IOHandler io = new IOHandler();
		String textpanel = "tasks: 30-07-2016 until 30-07-2016, Cooking, 2;Drink Preparation, 1;House Keeping, 2;Photo Taking, 1;Washing Up, 2."
				+ "people: 30-07-2016 until 30-07-2016, Nico, House Keeping;Yann, Drink  Preparation;Hernan, Cooking;Andy, none;Simon, none;Bilal, none;Faz, Photo Taking;Antoine, none."
				+ "tasks:01/08/2016 until 05/08/2016;Cooking,2;Drink Preparation,1;House Keeping,1;Photo Taking,1; Washing Up,2."
				+ "people:01/08/2016 until 05/08/2016;Nico,House Keeping;Yann,Drink Preparation;Hernan,Cooking;Simon,none;Bilal,none;Faz,Photo Taking;Antoine,none.";
		String json = io.getJson(textpanel);
		System.out.println(json);
	}	
	@Test
	public void testGenJsonReturnsJsonFromTextPanelStringwithThreePairsOfText() throws IOHandlerException{
		IOHandler io = new IOHandler();
		String textpanel = "Tasks:05/08/2016 until 06/08/2016;Cooking,1;Drink Preparation,1;House Keeping,1;Washing Up,1."
				+ "People:05/08/2016 until 06/08/2016;Nico,House Keeping;Yann,Drink Preparation;Simon,none;Antoine,none."
				+ "Tasks: 30-07-2016 until 30-07-2016, Cooking, 2;Drink Preparation, 1;House Keeping, 2;Photo Taking, 1;Washing Up, 2."
				+ "People: 30-07-2016 until 30-07-2016, Nico, House Keeping;Yann, Drink  Preparation;Hernan, Cooking;Andy, none;Simon, none;Bilal, none;Faz, Photo Taking;Antoine, none."
				+ "Tasks:01/08/2016 until 05/08/2016;Cooking,2;Drink Preparation,1;House Keeping,1;Photo Taking,1; Washing Up,2."
				+ "People:01/08/2016 until 05/08/2016;Nico,House Keeping;Yann,Drink Preparation;Hernan,Cooking;Simon,none;Bilal,none;Faz,Photo Taking;Antoine,none.";
				String json = io.getJson(textpanel);
		System.out.println(json);
	}
	
}
