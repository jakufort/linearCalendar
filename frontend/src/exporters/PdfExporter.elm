module Exporters.PdfExporter exposing (..)

import Exporters.Properties exposing (ExportProperties)
import Generator.Calendar exposing (LinearCalendar)

exportCalendar : ExportProperties -> LinearCalendar -> String
exportCalendar properties calendar = ""