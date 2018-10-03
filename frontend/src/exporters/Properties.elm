module Exporters.Properties exposing (PDFExportProperties, HTMLExportProperties)

import Generator.Properties exposing (CalendarProperties)

type alias PDFExportProperties = {
    cellHeight: Int,
    pageSize: PageSize
  }

type alias PageSize = String

type alias HTMLExportProperties = {}



