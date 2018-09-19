package pl.jakufort.linearCalendar.generator

import java.time.{DayOfWeek, LocalDate, Month}

import pl.jakufort.linearCalendar.properties.CalendarProperties

import scala.collection.mutable

object LinearCalendar {
  def generate(calendarProperties: CalendarProperties): LinearCalendar = {
    val months = mutable.MutableList[CalendarRow]()
    for (monthNumber <- 1 to 12) {
      val month = LocalDate.of(calendarProperties.year, monthNumber, 1)
      months.+=(generateRow(month, calendarProperties.specialDates))
    }
    new LinearCalendar(months.toList)
  }

  private def generateRow(monthStart: LocalDate, specialDates: Set[LocalDate]): CalendarRow = {
    val days = mutable.MutableList[CalendarDay]()
    for (_ <- 1 to (monthStart.getDayOfWeek.getValue - DayOfWeek.MONDAY.getValue)) {
      days.+=(new CalendarDay(false, Option.empty))
    }
    for (dayNumber <- 1 to monthStart.lengthOfMonth()) {
      val day = LocalDate.of(monthStart.getYear, monthStart.getMonth, dayNumber)
      days.+=(new CalendarDay(specialDates.contains(day), Option.apply(day.getDayOfMonth)))
    }
    new CalendarRow(monthStart.getMonth, days.toList)
  }
}

class CalendarDay(val special: Boolean, val dayInMonth: Option[Int]) {
}

class CalendarRow(val month: Month, val days: List[CalendarDay]) {
}

class LinearCalendar private (val months: List[CalendarRow]) {

}
