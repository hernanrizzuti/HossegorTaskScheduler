package com.rizzutih.model;

import java.util.Date;

public class People {

	private String name;
	private String favouriteTask;
	private Date startDate;
	private Date endDate;
	private long totalDays;
	private long favouriteTaskDays;
	
	public People(String name, Date startDate, Date endDate, String favouriteTask) {
		super();
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.favouriteTask = favouriteTask;
	}

	public long getTotalDates() {
		return totalDays;
	}

	public void setTotalDates(long totalDays) {
		this.totalDays = totalDays;
	}

	public long getFavouriteTaskDays() {
		return favouriteTaskDays;
	}

	public void setFavouriteTaskDates(long favouriteTaskDays) {
		this.favouriteTaskDays = favouriteTaskDays;
	}

	public String getName() {
		return name;
	}

	public String getFavouriteTask() {
		return favouriteTask;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	@Override
	public String toString() {
		return "People [name=" + name + ", favouriteTask=" + favouriteTask + ", startDate=" + startDate + ", endDate="
				+ endDate + ", totalDays=" + totalDays + ", favouriteTaskDays=" + favouriteTaskDays + "]";
	}
	
}
