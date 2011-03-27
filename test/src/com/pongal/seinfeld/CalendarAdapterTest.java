package com.pongal.seinfeld;

import java.util.Calendar;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.pongal.seinfeld.data.Date;
import com.pongal.seinfeld.data.Task;

public class CalendarAdapterTest extends TestCase {

	public void testTheCount() {
		CalendarAdapter adapter = new CalendarAdapter(null);
		adapter.setData(new Task(1,"abc"), new Date());
		Assert.assertEquals(35, adapter.getCount());
		Assert.assertEquals(Calendar.JANUARY, adapter.startDate.getMonth());
		Assert.assertEquals(30, adapter.startDate.getDay());
	}
}