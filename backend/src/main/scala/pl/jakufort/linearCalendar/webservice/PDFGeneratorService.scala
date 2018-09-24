package pl.jakufort.linearCalendar.webservice

import java.time.format.TextStyle
import java.util.Locale

import akka.util.ByteString
import com.itextpdf.kernel.geom.PageSize
import pl.jakufort.linearCalendar.calendar.{CalendarProperties, LinearCalendar}
import pl.jakufort.linearCalendar.export.converter.pdf.PDFConverterProperties
import pl.jakufort.linearCalendar.export.pdf.{PDFCalendarExporter, PDFExporterProperties}

object PDFGeneratorService {

  def generate(config: ServiceProperties): ByteString = {
    val builder = ByteString.createBuilder
//    val calendar = LinearCalendar.generate(CalendarProperties(year = 2018))
    val calendar = LinearCalendar.generate(config.calendarProperties)
    PDFCalendarExporter.export(
      calendar = calendar,
      outputStream = builder.asOutputStream,
      properties = config.pdfExporterProperties
//      properties =
//        PDFExporterProperties(
//          pageSize = PageSize.A4,
//          converterProperties = PDFConverterProperties(
//            cellHeight = 25,
//            locale = Locale.UK,
//            daysStyle = TextStyle.SHORT_STANDALONE,
//            monthsStyle = TextStyle.SHORT
//          )
//        )
    )
    builder.result()
  }

}
