package pl.jakufort.linearCalendar.formats

import java.io.OutputStream

import pl.jakufort.linearCalendar.calendar.LinearCalendar

trait CalendarExporter {
  def export(calendar: LinearCalendar, outputStream: OutputStream): Unit
}
