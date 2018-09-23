package pl.jakufort.linearCalendar.export.pdf

import com.itextpdf.kernel.geom.PageSize
import pl.jakufort.linearCalendar.export.ExporterProperties
import pl.jakufort.linearCalendar.export.converter.pdf.PDFConverterProperties

case class PDFExporterProperties(
                                  pageSize: PageSize,
                                  converterProperties: PDFConverterProperties
                                )
  extends ExporterProperties[PDFConverterProperties] {}
