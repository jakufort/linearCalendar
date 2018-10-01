module Generator.Calendar exposing (generate, LinearCalendar)

import List exposing (map)
import Generator.Properties exposing (CalendarProperties)
import Date
import Time
import Basics
import Dict

type alias LinearCalendar = {
    months: List CalendarMonth
  }

type alias CalendarMonth = {
    displayName: String,
    days: List (Maybe Int)
  }

generate : CalendarProperties -> LinearCalendar
generate properties = LinearCalendar (generateMonths properties.year)

generateMonths : Int -> List CalendarMonth
generateMonths year =
  Date.range Date.Month 1 (firstMonthOfYear year) (firstMonthOfYear (year+1))
  |> map (\date -> CalendarMonth (Date.format "MMMM" date) (generateDays date))

firstMonthOfYear : Int -> Date.Date
firstMonthOfYear year = Date.fromCalendarDate year Time.Jan 1

--assuming that date is first day of month
generateDays : Date.Date -> List (Maybe Int)
generateDays month =
  let
    monthFirstDay = Date.weekdayNumber month
    monday = Date.weekdayToNumber Time.Mon
    emptyStartDaysLimit = monthFirstDay - monday
    emptyEndDaysLimit = emptyStartDaysLimit + daysInMonth (Date.year month) (Date.month month)
  in
    List.repeat numberOfWeeks [1, 2, 3, 4, 5, 6, 7]
    |> map (getDay emptyStartDaysLimit emptyEndDaysLimit)

getDay : Int -> Int -> Int -> Maybe Int
getDay startLimit endLimit day =
    if day > startLimit && day <= endLimit then
      Just (day - startLimit)
    else
      Nothing

numberOfWeeks : Int
numberOfWeeks = 6

daysInMonth : Int -> Date.Month -> Int
daysInMonth y m =
    case m of
        Time.Jan ->
            31
        Time.Feb ->
            if isLeapYear y then
                29
            else
                28
        Time.Mar ->
            31
        Time.Apr ->
            30
        Time.May ->
            31
        Time.Jun ->
            30
        Time.Jul ->
            31
        Time.Aug ->
            31
        Time.Sep ->
            30
        Time.Oct ->
            31
        Time.Nov ->
            30
        Time.Dec ->
            31

isLeapYear : Int -> Bool
isLeapYear y =
    modBy 4 y == 0 && modBy 100 y /= 0 || modBy 400 y == 0