package pl.jakufort.linearCalendar.export.converter.pdf

import java.time.DayOfWeek

import com.itextpdf.layout.element.{Cell, Paragraph, Table}
import com.itextpdf.layout.property.TextAlignment
import pl.jakufort.linearCalendar.calendar.{CalendarDay, CalendarRow, LinearCalendar}
import pl.jakufort.linearCalendar.export.converter.CalendarConverter

object PDFCalendarConverter extends CalendarConverter[Table, PDFConverterProperties] {

  override def convert(calendar: LinearCalendar, properties: PDFConverterProperties): Table = {
    val table = new Table(CalendarRow.NUMBER_OF_DAYS + 1)
    addDaysHeader(table, properties)
    calendar.months.foreach(addMonthRow(table, properties, _))
    table
  }

  private def addDaysHeader(table: Table, properties: PDFConverterProperties): Unit = {
    new Cell(1, 2).add(new Paragraph(""))
    addEmptyCell(table)
    (0 until CalendarRow.NUMBER_OF_DAYS)
      .map(i => DayOfWeek.of((i % 7) + 1))
      .foreach(day => table.addCell(day.getDisplayName(properties.daysStyle, properties.locale)))
  }

  private def addMonthRow(table: Table, properties: PDFConverterProperties, row: CalendarRow): Unit = {
    addCell(table, row.month.getMonth.getDisplayName(properties.monthsStyle, properties.locale))
    row.days.foreach(addDay(table, properties, _))
  }

  private def addDay(table: Table, properties: PDFConverterProperties, calendarDay: CalendarDay): Unit = calendarDay match {
    case CalendarDay(None) => addEmptyCell(table)
    case CalendarDay(Some(dayInMonth)) => addCell(table, dayInMonth.toString, properties.cellHeight)
  }

  private def addCell(table: Table, text: String, height: Float = 25): Unit = {
    table
      .addCell(new Cell(1, 1)
        .setTextAlignment(TextAlignment.CENTER)
        .setHeight(height)
        .add(new Paragraph(text)))
  }

  private def addEmptyCell(table: Table):Unit = addCell(table, "")

}
