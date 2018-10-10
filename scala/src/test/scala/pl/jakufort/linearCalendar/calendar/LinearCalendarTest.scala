package pl.jakufort.linearCalendar.calendar

import java.time.{Month, YearMonth}

import org.scalatest.FunSpec

class LinearCalendarTest extends FunSpec {

  describe(".generate") {
    import LinearCalendar.generate

    val year = 2018
    val subject = generate(new CalendarProperties(year))

    it("generates calendar for whole year") {
      assert(subject.months.size == 12)
    }

    it("generates calendar from january to december") {
      val expectedMonths = (1 to 12).map(YearMonth.of(year, _))
      assert(subject.months.map(month => month.month) == expectedMonths)
    }

    it("have expected number of real days in row") {
      val expectedDays = Month.values().map(YearMonth.of(year, _).lengthOfMonth).toSeq
      assert(subject.months.map(_.days.count(_.dayInMonth.isDefined)) == expectedDays)
    }

  }

}
