package com.pongal.seinfeld.date;

import java.util.Calendar;

import com.pongal.seinfeld.data.Date;

import junit.framework.TestCase;

public class DateTest extends TestCase {

   public void testDateEquals() {
      java.util.Date c = Calendar.getInstance().getTime();
      Date d1 = new Date(c);
      Date d2 = new Date();
      assertTrue(d1.equals(d2));
   }
   
   public void testCheckClonable() {
      Date d1 = new Date();
      Date d2 = (Date)d1.clone();
      assertEquals(2011, d1.getYear());
      assertEquals(2011, d2.getYear());
      assertEquals(d1,d2);
   }
   
   public void testFutureDates() {
       Date d1 = new Date(2011,3,17);
       assertTrue(d1.isFutureDate());
       assertFalse(new Date(2011,2,28).isFutureDate());
       
   }
   
}