package pl.jakufort.linearCalendar.formats

import pl.jakufort.linearCalendar.calendar.LinearCalendar

trait CalendarConverter[T] {
  def convert(calendar: LinearCalendar): T
}
