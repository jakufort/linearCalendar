package pl.jakufort.linearCalendar.export

import pl.jakufort.linearCalendar.export.converter.ConverterProperties

trait ExporterProperties[T <: ConverterProperties] {
  def converterProperties: T
}
