package pl.jakufort.linearCalendar

import java.io.{File, FileOutputStream}

import pl.jakufort.linearCalendar.calendar.{CalendarProperties, LinearCalendar}
import pl.jakufort.linearCalendar.formats.pdf.PDFCalendarExporter

object Main {

  def main(args: Array[String]): Unit = {
    val calendar = LinearCalendar.generate(new CalendarProperties(year = 2018))
    PDFCalendarExporter.export(calendar, new FileOutputStream(new File("calendar.pdf")))
  }
}
