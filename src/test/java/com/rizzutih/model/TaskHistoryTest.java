package com.rizzutih.model;

import static org.junit.Assert.*;


import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TaskHistoryTest {
	
	@Mock private Map<String, Integer> mockmap;
	@Mock private Map<String, Integer> mockcountmap;
	private TaskHistory th;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		th = new TaskHistory(mockmap,mockcountmap);
	}

	@Test
	public void testAddTaskToHistoryCallsPut() {
		th.add("Washing up",1);
		verify(mockmap).put("Washing up".toLowerCase(), 1);
	}
	
	@Test
	public void testGetTaskFromHistoryCallsGet() {
		when(mockmap.containsKey("Washing up".toLowerCase())).thenReturn(true);
		when(mockmap.get("Washing up".toLowerCase())).thenReturn(1);
		th.get("Washing up".toLowerCase());
		verify(mockmap).get("Washing up".toLowerCase());
	}
	
	@Test
	public void testCountFavourityTaskReturn1(){
		when(mockcountmap.put("Washing up".toLowerCase(), 1)).thenReturn(1);
		assertEquals(1, th.countfavourityTask("Washing up".toLowerCase()));
	}
	
	@Test
	public void testCountFavourityTask(){
		when(mockcountmap.containsKey("Washing up".toLowerCase())).thenReturn(true);
		when(mockcountmap.get("Washing up".toLowerCase())).thenReturn(1);
		when(mockcountmap.put("Washing up".toLowerCase(), 2)).thenReturn(2);
		assertEquals(2, th.countfavourityTask("Washing up".toLowerCase()));
	}
	@Test
	public void testgetCountFavouriteTaskCallsGet() {
		when(mockcountmap.containsKey("Washing up".toLowerCase())).thenReturn(true);
		when(mockcountmap.get("Washing up".toLowerCase())).thenReturn(1);
		th.getTotalCountFavouriteTask("Washing up".toLowerCase());
		verify(mockcountmap).get("Washing up".toLowerCase());
	}
	
	@Test
	public void testgetCountFavouriteTaskReturnZero() {
		when(mockcountmap.containsKey("Washing up")).thenReturn(false);
		when(mockcountmap.get("Washing up")).thenReturn(1);
		th.getTotalCountFavouriteTask("Washing up");
		verify(mockcountmap, never()).get("Washing up");
	}
}
