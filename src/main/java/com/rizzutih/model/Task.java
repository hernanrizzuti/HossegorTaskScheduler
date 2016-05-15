package com.rizzutih.model;

import java.util.Date;

public class Task{

	private String taskName;
	private int people;
	private Date startDate;
	private Date endDate;
	
	public Task(String taskName, int people, Date startDate, Date endDate) {
		super();
		this.taskName = taskName;
		this.people = people;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public String getTaskName() {
		return taskName;
	}

	public int getPeople() {
		return people;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}
}
