import Browser
import Html exposing (Html, Attribute, div, input, text, button)
import Html.Attributes exposing (..)
import Html.Events exposing (onClick, onInput)
import Generator.Properties exposing (CalendarProperties)
import Exporters.HtmlExporter
import Exporters.Properties exposing (ExportProperties(..))
import Generator.Calendar exposing (generate)
import Exporters.PdfExporter

main = Browser.sandbox { init = init, update = update, view = view }

type alias Model = {
    year: String,
    generatedTable: Html Msg
  }

init : Model
init = {
  year = "2018",
  generatedTable = div [] []
  }

type Msg = ChangeYear String | GenerateHtmlCalendar | GeneratePdfCalendar

update : Msg -> Model -> Model
update msg model =
    case msg of
        ChangeYear newYear -> { model | year = newYear }
        GenerateHtmlCalendar -> { model | generatedTable = Exporters.HtmlExporter.exportCalendar HTMLExportProperties (generateCalendar model) }
        GeneratePdfCalendar -> let a = Exporters.PdfExporter.exportCalendar HTMLExportProperties (generateCalendar model) in model

generateCalendar : Model -> Generator.Calendar.LinearCalendar
generateCalendar model = generate (CalendarProperties (getYear(model)))

getYear : Model -> Int
getYear model = Maybe.withDefault 2018 (String.toInt(model.year))

view : Model -> Html Msg
view model =
    div []
      [ input [ value model.year, onInput ChangeYear ] []
      , button [ onClick GenerateHtmlCalendar ] [ text "Generate HTML Calendar" ]
      , button [ onClick GeneratePdfCalendar ] [ text "Generate PDF Calendar" ]
      , div [] [ model.generatedTable ]
      ]
