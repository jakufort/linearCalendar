package pl.jakufort.linearCalendar.calendar

import java.time.{Month, YearMonth}

import org.scalatest.FunSpec

class CalendarRowTest extends FunSpec {

  describe(".generate") {
    import CalendarRow.generate
    val month = Month.JANUARY
    val year = 2018
    val subject = generate(YearMonth.of(year, month))

    it("generates constant number of days") {
      val expectedNumberOfWeeks = 6
      assert(subject.days.size == expectedNumberOfWeeks * 7)
    }

    it("generates correct margin before and after month days") {
      assert(subject.days.head.dayInMonth.isDefined)
      assert(subject.days.drop(month.length(false)).forall(_.dayInMonth.isEmpty))
    }
  }
}
