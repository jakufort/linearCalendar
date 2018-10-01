module Exporters.Properties exposing (ExportProperties(..))

import Generator.Properties exposing (CalendarProperties)

type ExportProperties =
  PDFExportProperties {
    cellHeight: Int,
    pageSize: PageSize
  }
  | HTMLExportProperties

type alias PageSize = String

