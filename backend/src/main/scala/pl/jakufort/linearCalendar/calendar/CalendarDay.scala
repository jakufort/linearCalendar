package pl.jakufort.linearCalendar.calendar

object CalendarDay {
  val empty: CalendarDay = new CalendarDay(Option.empty)
  def of(dayNumber: Int) = new CalendarDay(Option.apply(dayNumber))
}

final case class CalendarDay(dayInMonth: Option[Int]) {}
