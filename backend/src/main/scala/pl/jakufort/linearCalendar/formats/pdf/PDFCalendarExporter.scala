package pl.jakufort.linearCalendar.formats.pdf

import java.io.OutputStream

import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf._
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Table
import pl.jakufort.linearCalendar.calendar.LinearCalendar
import pl.jakufort.linearCalendar.formats.CalendarExporter

object PDFCalendarExporter extends CalendarExporter {

  override def export(calendar: LinearCalendar, outputStream: OutputStream): Unit = {
    val calendarTable: Table = PDFCalendarConverter.convert(calendar)
    val pdfDocument = generatePdfDocument(outputStream)
    val document = createDocument(calendarTable, pdfDocument)
    document.close()
  }

  private def generatePdfDocument(outputStream: OutputStream):PdfDocument = {
    val writerProperties = new WriterProperties()
    writerProperties.addXmpMetadata()
    new PdfDocument(new PdfWriter(outputStream, writerProperties))
  }


  private def createDocument(calendarTable: Table, pdfDocument: PdfDocument):Document = {
    val document = new Document(pdfDocument, PageSize.A4.rotate())
    document.add(calendarTable)
    document
  }


}
