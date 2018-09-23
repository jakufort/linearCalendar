package pl.jakufort.linearCalendar.export

import java.io.OutputStream

import pl.jakufort.linearCalendar.calendar.LinearCalendar
import pl.jakufort.linearCalendar.export.converter.ConverterProperties

trait CalendarExporter[U <: ConverterProperties, T <: ExporterProperties[U]] {
  def export(calendar: LinearCalendar, outputStream: OutputStream, properties: T): Unit
}
