package pl.jakufort.linearCalendar.calendar

import java.time.{DayOfWeek, YearMonth}

object LinearCalendar {
  def generate(calendarProperties: CalendarProperties): LinearCalendar = {
    val months = (1 to 12)
      .map(i => YearMonth.of(calendarProperties.year, i))
      .map(yearMonth => CalendarRow.generate(yearMonth))
    new LinearCalendar(months)
  }
}

final class LinearCalendar private (val months: Seq[CalendarRow]) {}

final case class CalendarDay(dayInMonth: Option[Int]) {}

object CalendarRow {
  val NUMBER_OF_WEEKS: Int = 6
  val NUMBER_OF_DAYS: Int = NUMBER_OF_WEEKS * 7

  def generate(month: YearMonth): CalendarRow = {
    val leftEmptyDays = (1 to startOfMonthDayNumber(month)).map(_ => CalendarDay.empty)
    val rightEmptyDays = (month.atEndOfMonth().getDayOfMonth + 1 to (NUMBER_OF_DAYS - leftEmptyDays.size)).map(_ => CalendarDay.empty)
    val realDays = (1 to month.lengthOfMonth()).map(i => new CalendarDay(Option.apply(i)))
    new CalendarRow(month, leftEmptyDays ++ realDays ++ rightEmptyDays)
  }

  private def startOfMonthDayNumber(month: YearMonth): Int = month.atDay(1).getDayOfWeek.getValue - DayOfWeek.MONDAY.getValue
}

object CalendarDay {
  val empty: CalendarDay = new CalendarDay(Option.empty)
}

final class CalendarRow(val month: YearMonth, val days: Seq[CalendarDay]) {}