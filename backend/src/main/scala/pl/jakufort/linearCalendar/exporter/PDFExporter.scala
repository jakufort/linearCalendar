package pl.jakufort.linearCalendar.exporter
import java.io.{File, FileOutputStream}
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.Locale

import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf._
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.{Cell, IBlockElement, Paragraph, Table}
import com.itextpdf.layout.property.TextAlignment
import pl.jakufort.linearCalendar.generator.{CalendarDay, CalendarRow, LinearCalendar}
import pl.jakufort.linearCalendar.properties.ExporterProperties

object PDFExporter {

  private val NUMBER_OF_WEEKS = 6

  def export(calendar: LinearCalendar, config: ExporterProperties): Unit = {
    val calendarTable: Table = generateCalendar(calendar, config)
    generatePdf(calendarTable, config.file)
  }

  private def generateCalendar(calendar: LinearCalendar, config: ExporterProperties): Table = {
    val table = new Table(NUMBER_OF_WEEKS * 7 + 1)
    table.setFontSize(7)
    addDaysHeader(table)
    calendar.months.foreach(row => {
      addMonthRow(table, row)
    })
    table
  }

  private def addDaysHeader(table: Table): Unit = {
    new Cell(1, 2).add(new Paragraph(""))
    table.addCell("")
    for (_ <- 1 to NUMBER_OF_WEEKS) {
      for (day <- DayOfWeek.values()) {
        table.addCell(day.getDisplayName(TextStyle.SHORT, Locale.UK))
      }
    }
  }

  private def addMonthRow(table: Table, row: CalendarRow): Unit = {
    addCell(table, row.month.getDisplayName(TextStyle.SHORT, Locale.UK))
    var counter = 1
    row.days.foreach(day => {
      counter += 1
      if (day.dayInMonth.isDefined) addCell(table, day.dayInMonth.get.toString) else addCell(table, "")
    })
    for (i <- counter to NUMBER_OF_WEEKS*7) {
      addCell(table, "")
    }
  }

  private def generatePdf(table: Table, file: File): Unit = {
    val outputStream: FileOutputStream = new FileOutputStream(file)
    val writerProperties = new WriterProperties()
    //Add metadata
    writerProperties.addXmpMetadata()

    val pdfDocument = new PdfDocument(new PdfWriter(outputStream, writerProperties))
    val document = new Document(pdfDocument, PageSize.A4.rotate())

    document.add(table)

    document.close()
  }

  private def addCell(table: Table, text: String): Unit = {
    table.addCell(new Cell(1, 1).setTextAlignment(TextAlignment.CENTER).add(new Paragraph(text)))
  }

}
