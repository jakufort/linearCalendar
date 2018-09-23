package pl.jakufort.linearCalendar.export.converter

import pl.jakufort.linearCalendar.calendar.LinearCalendar

trait CalendarConverter[T, U <: ConverterProperties] {
  def convert(calendar: LinearCalendar, properties: U): T
}
