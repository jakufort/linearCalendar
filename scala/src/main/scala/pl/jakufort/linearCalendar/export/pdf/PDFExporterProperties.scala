package pl.jakufort.linearCalendar.export.pdf

import pl.jakufort.linearCalendar.export.ExporterProperties
import pl.jakufort.linearCalendar.export.converter.pdf.PDFConverterProperties

case class PDFExporterProperties(
                                  pageSize: String,
                                  converterProperties: PDFConverterProperties
                                )
  extends ExporterProperties[PDFConverterProperties] {}
