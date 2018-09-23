package pl.jakufort.linearCalendar.export

import pl.jakufort.linearCalendar.export.converter.ConverterProperties

class ExporterProperties[T <: ConverterProperties] (val converterProperties: T) {}
