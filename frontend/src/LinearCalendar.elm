import Browser
import Html exposing (Html, Attribute, div, input, text, button)
import Html.Attributes exposing (..)
import Html.Events exposing (onClick, onInput)
import Generator.Properties exposing (CalendarProperties)
import Exporters.HtmlExporter exposing (exportCalendar)
import Exporters.Properties exposing (ExportProperties(..))
import Generator.Calendar exposing (generate)

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

type Msg = ChangeYear String | GenerateCalendar

update : Msg -> Model -> Model
update msg model =
    case msg of
        ChangeYear newYear -> { model | year = newYear }
        GenerateCalendar -> { model | generatedTable = exportCalendar HTMLExportProperties (generate (CalendarProperties (getYear(model)))) }

getYear : Model -> Int
getYear model = Maybe.withDefault 2018 (String.toInt(model.year))

view : Model -> Html Msg
view model =
    div []
      [ input [ value model.year, onInput ChangeYear ] []
      , button [ onClick GenerateCalendar ] [ text "Generate Calendar" ]
      , div [] [ model.generatedTable ]
      ]
