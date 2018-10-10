port module Exporters.PdfExporter exposing (exportCalendar, createModel)

import Exporters.Properties exposing (PDFExportProperties)
import Generator.Calendar exposing (LinearCalendar, CalendarMonth)
import Json.Encode as E

type alias Model = E.Value

port exportCalendar : Model -> Cmd msg

createModel : PDFExportProperties -> LinearCalendar -> Model
createModel properties calendar = E.object [
    ("properties", encodeProperties properties),
    ("calendar", encodeCalendar calendar)
  ]

encodeProperties : PDFExportProperties -> E.Value
encodeProperties properties = E.object [
    ("cellHeight", E.int (properties.cellHeight)),
    ("pageSize", E.string (properties.pageSize))
  ]

encodeCalendar : LinearCalendar -> E.Value
encodeCalendar calendar = E.object [
    ("months", E.list encodeMonth (calendar.months)),
    ("header", E.list (E.string) (calendar.header))
  ]

encodeMonth : CalendarMonth -> E.Value
encodeMonth month = E.object [
    ("name", E.string (month.displayName)),
    ("days", E.list encodeDay (month.days))
  ]

encodeDay : Maybe Int -> E.Value
encodeDay day = case day of
    Nothing -> E.null
    Just n -> E.int n