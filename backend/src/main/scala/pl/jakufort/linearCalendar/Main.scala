package pl.jakufort.linearCalendar

import java.io.{File, FileOutputStream}
import java.time.format.TextStyle
import java.util.Locale

import pl.jakufort.linearCalendar.calendar.{CalendarProperties, LinearCalendar}
import pl.jakufort.linearCalendar.export.converter.pdf.PDFConverterProperties
import pl.jakufort.linearCalendar.export.pdf.{PDFCalendarExporter, PDFExporterProperties}

object Main {

  def main(args: Array[String]): Unit = {
    val calendar = LinearCalendar.generate(CalendarProperties(year = 2018))
    PDFCalendarExporter.export(
      calendar = calendar,
      outputStream = new FileOutputStream(new File("calendar.pdf")),
      properties =
        PDFExporterProperties(
          pageSize = "A4",
          converterProperties = PDFConverterProperties(
            cellHeight = 25,
            locale = Locale.UK,
            daysStyle = TextStyle.SHORT_STANDALONE,
            monthsStyle = TextStyle.SHORT
          )
        )
    )
  }
}
