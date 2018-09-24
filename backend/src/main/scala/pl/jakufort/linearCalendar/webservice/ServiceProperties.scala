package pl.jakufort.linearCalendar.webservice

import pl.jakufort.linearCalendar.calendar.CalendarProperties
import pl.jakufort.linearCalendar.export.pdf.PDFExporterProperties

final case class ServiceProperties(calendarProperties: CalendarProperties, pdfExporterProperties: PDFExporterProperties) {}
