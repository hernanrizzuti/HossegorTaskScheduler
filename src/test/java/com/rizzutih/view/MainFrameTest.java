package com.rizzutih.view;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.rizzutih.model.IOHandlerException;
import com.rizzutih.model.TaskHistory;

public class MainFrameTest {
	private MainFrame mf;
	@Mock private Map<String, Integer> mockmap;
	@Mock private TaskHistory th;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mf = new MainFrame(th);
	}

	@Test
	public void testblankToNoneRetursNoneString() {
		assertEquals("none",mf.blankToNone(""));
	}

	@Test
	public void testCheckNumRetursString2() throws IOHandlerException {
		assertEquals("2",mf.checkNum("2"));
	}

	@Test(expected=IOHandlerException.class)
	public void testCheckNumThrowsIOException() throws IOHandlerException {
		mf.checkNum("hfdgjhkfbjdsb");
	}

	@Test
	public void testCheckTaskReturnNum() throws IOHandlerException {
		when(mockmap.containsKey("cooking")).thenReturn(true);
		when(mockmap.get("cooking")).thenReturn(1);
		when(th.get("cooking")).thenReturn(1);
		mf.checkTask("Cooking");
		verify(th).get("cooking");
	}

	@Test(expected=IOHandlerException.class)
	public void testCheckTaskThrowsIOException() throws IOHandlerException {
		mf.checkTask("ndnd");
	}

}
