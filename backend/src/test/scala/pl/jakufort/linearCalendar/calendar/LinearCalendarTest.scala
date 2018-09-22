package pl.jakufort.linearCalendar.calendar

import java.time.YearMonth

import org.scalatest.FunSpec

class LinearCalendarTest extends FunSpec {

  describe(".generate") {
    import pl.jakufort.linearCalendar.calendar.LinearCalendar.generate

    val year = 2018
    val subject = generate(new CalendarProperties(year))

    it("generates calendar for whole year") {
      assert(subject.months.size == 12)
    }

    it("generates calendar from january to december") {
      val expectedMonths = (1 to 12).map(x => YearMonth.of(year, x))
      assert(subject.months.map(month => month.month) == expectedMonths)
    }

    it("generates constant number of days") {
      val expectedNumberOfWeeks = 6
      assert(subject.months.head.days.size == expectedNumberOfWeeks * 7)
    }
  }

}
