package pl.jakufort.linearCalendar.calendar

import java.time.{DayOfWeek, YearMonth}

object CalendarRow {
  private val NUMBER_OF_WEEKS: Int = 6
  val NUMBER_OF_DAYS: Int = NUMBER_OF_WEEKS * 7

  def generate(month: YearMonth): CalendarRow = {
    val emptyStartDaysLimit = month.atDay(1).getDayOfWeek.getValue - DayOfWeek.MONDAY.getValue
    val emptyEndDaysLimit = emptyStartDaysLimit + month.lengthOfMonth
    val days = (1 to NUMBER_OF_DAYS)
      .map {
        case i if i > emptyStartDaysLimit && i <= emptyEndDaysLimit => CalendarDay.of(i - emptyStartDaysLimit)
        case _ => CalendarDay.empty
      }
    new CalendarRow(month, days)
  }

}

final class CalendarRow(val month: YearMonth, val days: Seq[CalendarDay]) {}




