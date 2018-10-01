import Browser
import Html exposing (Html, Attribute, div, input, text, button)
import Html.Attributes exposing (..)
import Html.Events exposing (onClick, onInput)
import Generator.Properties exposing (CalendarProperties)
import Exporters.HtmlExporter exposing (exportCalendar)
import Exporters.Properties exposing (ExportProperties(..))
import Generator.Calendar exposing (generate)

main = Browser.sandbox { init = init }

type alias Model = {
    year: Int,
    generatedTable: Html msg
  }

init : Model
init = { year = 2018 }

type Msg = ChangeYear year | GenerateCalendar

update : Msg -> Model -> Model
update msg model =
    case msg of
        ChangeYear newYear -> { model | year = newYear }
        GenerateCalendar -> { model | generatedTable = exportCalendar HTMLExportProperties (generate (CalendarProperties model.year)) }

view : Model -> Html Msg
view model =
    div []
      [ input [ value model.year, onInput ChangeYear ] []
      , button [ onClick GenerateCalendar ] [ text "Generate Calendar" ]
      , div [] [ model.generatedTable ]
      ]
