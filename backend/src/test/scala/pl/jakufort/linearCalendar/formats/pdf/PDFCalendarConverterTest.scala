package pl.jakufort.linearCalendar.formats.pdf

import org.scalatest.FunSpec
import pl.jakufort.linearCalendar.calendar.{CalendarProperties, LinearCalendar}

class PDFCalendarConverterTest extends FunSpec {

  describe(".convert") {
    import pl.jakufort.linearCalendar.formats.pdf.PDFCalendarConverter.convert

    val subject = convert(LinearCalendar.generate(new CalendarProperties(2018)))

    it("table have correct number of columns and rows") {
      val expectedNumberOfWeeks = 6
      //expectedNumberOfWeeks * numberOfDaysInWeek + additionalColumnForMonthName
      assert(subject.getNumberOfColumns == expectedNumberOfWeeks * 7 + 1)
      //number of months + additional row for day names
      assert(subject.getNumberOfRows == 13)
    }

  }

}
