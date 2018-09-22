package pl.jakufort.linearCalendar.calendar

import java.time.YearMonth

object LinearCalendar {
  def generate(calendarProperties: CalendarProperties): LinearCalendar = {
    val months = (1 to 12)
      .map(i => YearMonth.of(calendarProperties.year, i))
      .map(yearMonth => CalendarRow.generate(yearMonth))
    new LinearCalendar(months)
  }
}

final class LinearCalendar private (val months: Seq[CalendarRow]) {}
