package pl.jakufort.linearCalendar.export.pdf

import java.time.format.TextStyle
import java.util.Locale

import org.scalatest.FunSpec
import pl.jakufort.linearCalendar.calendar.{CalendarProperties, LinearCalendar}
import pl.jakufort.linearCalendar.export.converter.pdf.PDFConverterProperties

class PDFCalendarConverterTest extends FunSpec {

  describe(".convert") {
    import pl.jakufort.linearCalendar.export.converter.pdf.PDFCalendarConverter.convert

    val subject = convert(
      calendar = LinearCalendar.generate(CalendarProperties(2018)),
      properties = PDFConverterProperties(
        cellHeight = 50,
        locale = Locale.UK,
        daysStyle = TextStyle.SHORT,
        monthsStyle = TextStyle.SHORT
      )
    )

    it("table have correct number of columns and rows") {
      val expectedNumberOfWeeks = 6
      //expectedNumberOfWeeks * numberOfDaysInWeek + additionalColumnForMonthName
      assert(subject.getNumberOfColumns == expectedNumberOfWeeks * 7 + 1)
      //number of months + additional row for day names
      assert(subject.getNumberOfRows == 13)
    }

  }

}
