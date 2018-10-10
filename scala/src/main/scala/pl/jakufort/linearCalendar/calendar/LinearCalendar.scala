package pl.jakufort.linearCalendar.calendar

import java.time.YearMonth

object LinearCalendar {
  def generate(calendarProperties: CalendarProperties): LinearCalendar = {
    val months = (1 to 12)
      .map(YearMonth.of(calendarProperties.year, _))
      .map(CalendarRow.generate)
    new LinearCalendar(months)
  }
}

final class LinearCalendar private (val months: Seq[CalendarRow]) {}
