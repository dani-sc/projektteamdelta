package at.itb13.oculustests.domain;

import static at.itb13.oculustests.exceptioncatcher.ThrowableAssertion.assertThrown;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import at.itb13.oculus.domain.Calendar;
import at.itb13.oculus.domain.CalendarEvent;
import at.itb13.oculus.domain.CalendarWorkingHours;
import at.itb13.oculus.domain.WorkingHours;
import at.itb13.oculus.technicalServices.dao.CalendarDao;
import at.itb13.teamD.application.exceptions.InvalidInputException;

/**
 * TODO: JUnit-Tests for methods of Calendar
 * 
 * @author Karin Trommelschläger
 * @date 12.05.2015
 * 
 */
public class Calendar_UnitTests {
	
	/* -- getWorkingHoursOfWeek -- */
	@Test
	public void testGetWorkingHoursOfWeekDay_Null() {
		Calendar calendar = new Calendar();
		
		assertThrown(() -> calendar.getWorkingHoursOfWeekDay(null))
			.isInstanceOf(NullPointerException.class);
		
		calendar.setCalendarWorkingHours(new HashSet<>());
		
		assertNull(calendar.getWorkingHoursOfWeekDay(DayOfWeek.MONDAY));
	}
	
	@Test
	public void testGetWorkingHoursOfWeekDay_Valid() {
		// Set up
		Calendar calendar = new Calendar();
		LocalDateTime monday9AM = LocalDateTime.of(2015, 6, 1, 9, 0);
		WorkingHours workingHours = new WorkingHours();
		workingHours.setMorningFrom(monday9AM.toLocalTime().minusHours(2));
		workingHours.setMorningTo(monday9AM.toLocalTime().plusHours(2));
		CalendarWorkingHours calendarWorkingHours = new CalendarWorkingHours(calendar, workingHours, DayOfWeek.MONDAY);
		Set<CalendarWorkingHours> setWorkingHours = new HashSet<>();
		setWorkingHours.add(calendarWorkingHours);
		calendar.setCalendarWorkingHours(setWorkingHours);
		
		// Tests
		WorkingHours retrievedWorkingHours = calendar.getWorkingHoursOfWeekDay(DayOfWeek.MONDAY);
		assertNotNull(retrievedWorkingHours);
		assertTrue(retrievedWorkingHours.getMorningFrom().equals(monday9AM.toLocalTime().minusHours(2)));
		assertNull(calendar.getWorkingHoursOfWeekDay(DayOfWeek.TUESDAY));
	}
	
	/* TODO: Old tests; check if good enough */
	@Test
	public void getCalendarEventsForTimespan_Test(){
		List<CalendarEvent> ce = null;
		Calendar cal = new Calendar();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String strDate2 = "2015-04-01 10:11";
		LocalDateTime endDate = LocalDateTime.parse(strDate2, formatter);
		LocalDateTime startDate = LocalDateTime.now();
		ce = cal.getCalendarEventsForTimespan(startDate, endDate);
		assertEquals(true, ce!=null);
	}
	
	@Test
	public void isOneCalendarEventinTimespan_Test(){
		Calendar cal = new Calendar();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String strDate2 = "2015-04-01 10:11";
		LocalDateTime endDate = LocalDateTime.parse(strDate2, formatter);
		LocalDateTime startDate = LocalDateTime.now();
		assertEquals(false, cal.isOneCalendarEventInTimespan(startDate, endDate));
	}
	
	@Test
	public void getCalendarEventById(){
		Calendar cal = new Calendar();
		CalendarEvent ce;
		ce = (CalendarEvent) cal.getCalendarEventById(1);
		assertEquals(true, ce==null);
	}
	
	@Test
	public void findPossibleAppointment(){
		Calendar cal = new Calendar();
		LocalDateTime startTime = LocalDateTime.of(2015, 6, 15, 8, 0);
		LocalDateTime endTime = LocalDateTime.now().plusMinutes(120);
		assertThrown(() -> cal.findPossibleAppointment(startTime, endTime, 60, false));
	}
	
	@Test
	public void findPossibleAppointment2(){
		Calendar cal = new Calendar();
		LocalDateTime startTime = LocalDateTime.of(2015, 6, 16, 8, 0);
		LocalDateTime endTime = LocalDateTime.now().plusMinutes(360);
		assertThrown(() -> cal.findPossibleAppointment(startTime, endTime, 60, false));
	}
	
	@Test
	public void findPossibleAppointment_CalendarID101_findPossibleAppointment_IfBranch(){
		Calendar cal = CalendarDao.getInstance().findById(101); 
		LocalDateTime startTime = LocalDateTime.of(2015, 4, 27, 10, 30);
		LocalDateTime endTime = LocalDateTime.now().plusMinutes(30);
		LocalDateTime time = null;
		assertThrown(() -> cal.findPossibleAppointment(startTime, endTime, 30, false));
	}
	
	@Test
	public void findTimeInWorkingHours_getException(){
		Calendar cal = CalendarDao.getInstance().findById(1);
		LocalDateTime ldt = LocalDateTime.of(2015, 6, 10, 18, 00);
		WorkingHours wh = cal.getWorkingHoursOfWeekDay(ldt.getDayOfWeek());		
		assertThrown(() -> cal.findTimeInWorkingHours(wh, ldt, false));
	}
	
	@Test
	public void findTimeInWorkingHours_getException_isEnd(){
		Calendar cal = CalendarDao.getInstance().findById(1);
		LocalDateTime ldt = LocalDateTime.of(2015, 6, 10, 7, 0);
		WorkingHours wh = cal.getWorkingHoursOfWeekDay(ldt.getDayOfWeek());		
		assertThrown(() -> cal.findTimeInWorkingHours(wh, ldt, true));
	}
	
	@Test
	public void findTimeInWorkingHours_isEnd(){
		Calendar cal = CalendarDao.getInstance().findById(1);
		LocalDateTime ldt = LocalDateTime.of(2015, 6, 10, 18, 00);
		LocalDateTime ldt2 = LocalDateTime.of(2015, 6, 10, 17, 00);
		WorkingHours wh = cal.getWorkingHoursOfWeekDay(ldt.getDayOfWeek());		
		try {
			assertEquals(ldt2, cal.findTimeInWorkingHours(wh, ldt, true));
		} catch (InvalidInputException e) {
		}
	}
	
	@Test
	public void findTimeInWorkingHours(){
		Calendar cal = CalendarDao.getInstance().findById(1);
		LocalDateTime ldt = LocalDateTime.of(2015, 6, 10, 7, 00);
		LocalDateTime ldt2 = LocalDateTime.of(2015, 6, 10, 8, 00);
		WorkingHours wh = cal.getWorkingHoursOfWeekDay(ldt.getDayOfWeek());		
		try {
			assertEquals(ldt2, cal.findTimeInWorkingHours(wh, ldt, false));
		} catch (InvalidInputException e) {
		}
	}
	
	@Test
	public void findTimeInWorkingHours_onlyFirstIf(){
		Calendar cal = CalendarDao.getInstance().findById(1);
		LocalDateTime ldt = LocalDateTime.of(2015, 6, 10, 8, 00);
		LocalDateTime ldt2 = LocalDateTime.of(2015, 6, 10, 8, 00);
		WorkingHours wh = cal.getWorkingHoursOfWeekDay(ldt.getDayOfWeek());		
		try {
			assertEquals(ldt2, cal.findTimeInWorkingHours(wh, ldt, false));
		} catch (InvalidInputException e) {
		}
	}
	
	@Test
	public void findTimeInWorkingHours_midlePart(){
		Calendar cal = CalendarDao.getInstance().findById(1);
		LocalDateTime ldt = LocalDateTime.of(2015, 6, 10, 12, 30);
		LocalDateTime ldt2 = LocalDateTime.of(2015, 6, 10, 13, 30);
		WorkingHours wh = cal.getWorkingHoursOfWeekDay(ldt.getDayOfWeek());		
		try {
			assertEquals(ldt2, cal.findTimeInWorkingHours(wh, ldt, false));
		} catch (InvalidInputException e) {
		}
	}
	
	@Test
	public void findTimeInWorkingHours_midlePart_isEnd(){
		Calendar cal = CalendarDao.getInstance().findById(1);
		LocalDateTime ldt = LocalDateTime.of(2015, 6, 10, 12, 30);
		LocalDateTime ldt2 = LocalDateTime.of(2015, 6, 10, 12, 00);
		WorkingHours wh = cal.getWorkingHoursOfWeekDay(ldt.getDayOfWeek());		
		try {
			assertEquals(ldt2, cal.findTimeInWorkingHours(wh, ldt, true));
		} catch (InvalidInputException e) {
		}
	}
	
}
