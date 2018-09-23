package pl.jakufort.linearCalendar

import java.io.{File, FileOutputStream}
import java.time.format.TextStyle
import java.util.Locale

import com.itextpdf.kernel.geom.PageSize
import pl.jakufort.linearCalendar.calendar.{CalendarProperties, LinearCalendar}
import pl.jakufort.linearCalendar.export.converter.pdf.PDFConverterProperties
import pl.jakufort.linearCalendar.export.pdf.{PDFCalendarExporter, PDFExporterProperties}

object Main {

  def main(args: Array[String]): Unit = {
    val calendar = LinearCalendar.generate(new CalendarProperties(year = 2018))
    PDFCalendarExporter.export(
      calendar = calendar,
      outputStream = new FileOutputStream(new File("calendar.pdf")),
      properties =
        new PDFExporterProperties(
          pageSize = PageSize.A4,
          converterProperties = new PDFConverterProperties(
            cellHeight = 50,
            locale = Locale.UK,
            daysStyle = TextStyle.SHORT,
            monthsStyle = TextStyle.SHORT
          )
        )
    )
  }
}
