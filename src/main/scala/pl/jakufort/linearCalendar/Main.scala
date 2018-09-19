package pl.jakufort.linearCalendar

import java.io.File
import java.time.LocalDate

import pl.jakufort.linearCalendar.exporter.PDFExporter
import pl.jakufort.linearCalendar.generator.LinearCalendar
import pl.jakufort.linearCalendar.properties.{CalendarProperties, ExporterProperties}

object Main {

  def main(args: Array[String]): Unit = {
    val calendar = LinearCalendar.generate(new CalendarProperties(year = 2018, specialDates = Set.apply(LocalDate.now())))
    PDFExporter.export(calendar = calendar, config = new ExporterProperties(pages = 1, new File("calendar.pdf")))
  }
}
