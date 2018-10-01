module Exporters.HtmlExporter exposing (..)

import Exporters.Properties exposing (ExportProperties)
import Generator.Calendar exposing (LinearCalendar)
import Html exposing (Html, table, tr, td)

exportCalendar : ExportProperties -> LinearCalendar -> Html msg
exportCalendar properties calendar = table [] []