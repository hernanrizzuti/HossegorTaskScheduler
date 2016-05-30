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
	private TaskHistory th;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		th = new TaskHistory(mockmap);
	}

	@Test
	public void testAddTaskToHistoryCallsPut() {
		th.add("Washing up",1);
		verify(mockmap).put("Washing up", 1);
	}
	
	@Test
	public void testgetTaskfromHistoryCallsGet() {
		when(mockmap.get("Washing up")).thenReturn(1);
		th.get("Washing up");
		verify(mockmap).get("Washing up");
	}

}
