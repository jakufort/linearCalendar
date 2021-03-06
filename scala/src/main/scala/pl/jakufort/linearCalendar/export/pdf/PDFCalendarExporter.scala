package pl.jakufort.linearCalendar.export.pdf

import java.io.OutputStream

import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf._
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Table
import pl.jakufort.linearCalendar.calendar.LinearCalendar
import pl.jakufort.linearCalendar.export.CalendarExporter
import pl.jakufort.linearCalendar.export.converter.pdf.{PDFCalendarConverter, PDFConverterProperties}

object PDFCalendarExporter extends CalendarExporter[PDFConverterProperties, PDFExporterProperties] {

  override def export(calendar: LinearCalendar, outputStream: OutputStream, properties: PDFExporterProperties): Unit = {
    val calendarTable: Table = PDFCalendarConverter.convert(calendar, properties.converterProperties)
    val pdfDocument = generatePdfDocument(outputStream)
    val document = createDocument(calendarTable, pdfDocument, properties)
    document.close()
  }

  private def generatePdfDocument(outputStream: OutputStream):PdfDocument = {
    val writerProperties = new WriterProperties()
    writerProperties.addXmpMetadata()
    new PdfDocument(new PdfWriter(outputStream, writerProperties))
  }


  private def createDocument(calendarTable: Table, pdfDocument: PdfDocument, properties: PDFExporterProperties):Document = {
    val document = new Document(pdfDocument, getPageSize(properties.pageSize).rotate())
    document.add(calendarTable)
    document
  }

  private def getPageSize(pageSize: String): PageSize = pageSize match {
    case "A0" => PageSize.A0
    case "A1" => PageSize.A1
    case "A2" => PageSize.A2
    case _ => PageSize.A4
  }


}
