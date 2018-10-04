module Exporters.HtmlExporter exposing (exportCalendar)

import Exporters.Properties exposing (HTMLExportProperties)
import Generator.Calendar exposing (LinearCalendar, numberOfWeeks, CalendarMonth)
import Html exposing (Html, table, tr, td, text)

exportCalendar : HTMLExportProperties -> LinearCalendar -> Html msg
exportCalendar _ calendar = table [] ([
    (daysHeader calendar)
  ] ++ List.map (monthRow) calendar.months)

daysHeader : LinearCalendar -> Html msg
daysHeader calendar = tr [] (List.map (\x -> td [] [text x]) (calendar.header))

monthRow : CalendarMonth -> Html msg
monthRow month = tr [] ([td [] [text month.displayName]] ++ (List.map (dayCell) month.days))

dayCell : Maybe Int -> Html msg
dayCell day = case day of
    Nothing -> td [] []
    Just n -> td [] [text (String.fromInt(n))]