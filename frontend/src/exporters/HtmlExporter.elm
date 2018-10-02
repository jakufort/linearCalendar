module Exporters.HtmlExporter exposing (..)

import Exporters.Properties exposing (ExportProperties)
import Generator.Calendar exposing (LinearCalendar, numberOfWeeks, CalendarMonth)
import Html exposing (Html, table, tr, td, text)

exportCalendar : ExportProperties -> LinearCalendar -> Html msg
exportCalendar _ calendar = table [] ([
    daysHeader
  ] ++ List.map (monthRow) calendar.months)

daysHeader : Html msg
daysHeader = tr [] ([td [] []] ++ (List.map (\x -> td [] [text x]) (List.foldr (++) [] (List.repeat numberOfWeeks daysInWeek))))

daysInWeek : List String
daysInWeek = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"]

monthRow : CalendarMonth -> Html msg
monthRow month = tr [] ([td [] [text month.displayName]] ++ (List.map (dayCell) month.days))

dayCell : Maybe Int -> Html msg
dayCell day = case day of
    Nothing -> td [] []
    Just n -> td [] [text (String.fromInt(n))]