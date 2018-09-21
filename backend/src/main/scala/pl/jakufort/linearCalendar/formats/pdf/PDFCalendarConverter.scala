package pl.jakufort.linearCalendar.formats.pdf

import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.Locale

import com.itextpdf.layout.element.{Cell, Paragraph, Table}
import com.itextpdf.layout.property.TextAlignment
import pl.jakufort.linearCalendar.calendar.{CalendarDay, CalendarRow, LinearCalendar}
import pl.jakufort.linearCalendar.formats.CalendarConverter

object PDFCalendarConverter extends CalendarConverter[Table] {

  private val FONT_SIZE = 7

  override def convert(calendar: LinearCalendar): Table = {
    val table = new Table(CalendarRow.NUMBER_OF_DAYS + 1)
    table.setFontSize(FONT_SIZE)
    addDaysHeader(table)
    calendar.months.foreach(row => {
      addMonthRow(table, row)
    })
    table
  }

  private def addDaysHeader(table: Table): Unit = {
    new Cell(1, 2).add(new Paragraph(""))
    addEmptyCell(table)
    for (_ <- 1 to CalendarRow.NUMBER_OF_WEEKS) {
      for (day <- DayOfWeek.values()) {
        table.addCell(day.getDisplayName(TextStyle.SHORT, Locale.UK))
      }
    }
  }

  private def addMonthRow(table: Table, row: CalendarRow): Unit = {
    addCell(table, row.month.getMonth.getDisplayName(TextStyle.SHORT, Locale.UK))
    row.days.foreach(addDay(table, _))
  }

  private def addDay(table: Table, calendarDay: CalendarDay): Unit = calendarDay match {
    case CalendarDay(None) => addEmptyCell(table)
    case CalendarDay(Some(dayInMonth)) => addCell(table, dayInMonth.toString)
  }

  private def addCell(table: Table, text: String): Unit = {
    table.addCell(new Cell(1, 1).setTextAlignment(TextAlignment.CENTER).add(new Paragraph(text)))
  }

  private def addEmptyCell(table: Table):Unit = addCell(table, "")

}
