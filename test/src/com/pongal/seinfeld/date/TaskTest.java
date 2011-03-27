package com.pongal.seinfeld.date;

import com.pongal.seinfeld.data.Date;
import com.pongal.seinfeld.data.Task;

import junit.framework.TestCase;

public class TaskTest extends TestCase {

    public void testLongestChain() {
   Task t = new Task();
   t.addAccomplishedDates(new Date(2011, 1, 1));
   t.addAccomplishedDates(new Date(2011, 1, 2));
   t.addAccomplishedDates(new Date(2011, 1, 3));
   assertEquals(3, t.getChainLengths()[1]);
   assertEquals(0, t.getChainLengths()[0]);
    }

    public void testLongestChainWithBreaks() {
   Task t = new Task();
   t.addAccomplishedDates(new Date(2011, 1, 5));
   t.addAccomplishedDates(new Date(2011, 1, 6));
   t.addAccomplishedDates(new Date(2011, 1, 10));
   t.addAccomplishedDates(new Date(2011, 1, 11));
   t.addAccomplishedDates(new Date(2011, 1, 12));
   t.addAccomplishedDates(new Date(2011, 1, 13));
   t.addAccomplishedDates(new Date(2011, 1, 14));
   assertEquals(5, t.getChainLengths()[1]);
   assertEquals(0, t.getChainLengths()[0]);
    }

    public void testLongestChainSpanningAcrossMonths() {
   Task t = new Task();
   t.addAccomplishedDates(new Date(2011, 1, 30));
   t.addAccomplishedDates(new Date(2011, 1, 31));
   t.addAccomplishedDates(new Date(2011, 2, 1));
   assertEquals(3, t.getChainLengths()[1]);
   assertEquals(0, t.getChainLengths()[0]);
    }

    public void testChainLengthForRecentEntries() {
   Task t = new Task();
   Date today = new Date();
   Date yesterday = today.clone();
   yesterday.addDays(-1);
   t.addAccomplishedDates(yesterday);
   t.addAccomplishedDates(today);
   assertEquals(2, t.getChainLengths()[1]);
   assertEquals(2, t.getChainLengths()[0]);
    }

    public void testChainLengthForRecentEntriesWithoutToday() {
   Task t = new Task();
   Date today = new Date();
   Date yesterday = today.clone();
   yesterday.addDays(-1);
   Date dayBeforeYesterday = yesterday.clone();
   dayBeforeYesterday.addDays(-1);
   t.addAccomplishedDates(dayBeforeYesterday);
   t.addAccomplishedDates(yesterday);
   assertEquals(2, t.getChainLengths()[1]);
   assertEquals(2, t.getChainLengths()[0]);
    }
    
    public void testChainLenghtForRecentEntriesWithoutTodayAndYesterday() {
   Task t = new Task();
   Date today = new Date();
   Date yesterday = today.clone();
   yesterday.addDays(-1);
   Date dayBeforeYesterday = yesterday.clone();
   dayBeforeYesterday.addDays(-1);
   Date dayBefore2Days = dayBeforeYesterday.clone();
   dayBefore2Days.addDays(-1);
   
   t.addAccomplishedDates(dayBefore2Days);
   t.addAccomplishedDates(dayBeforeYesterday);
   assertEquals(2, t.getChainLengths()[1]);
   assertEquals(0, t.getChainLengths()[0]);  
    }

}