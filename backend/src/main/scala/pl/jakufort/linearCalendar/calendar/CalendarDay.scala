package pl.jakufort.linearCalendar.calendar

final case class CalendarDay(dayInMonth: Option[Int]) {}

object CalendarDay {
  val empty: CalendarDay = new CalendarDay(Option.empty)
}