import Browser
import Html exposing (Html, Attribute, div, input, text, button, label)
import Html.Attributes exposing (..)
import Html.Events exposing (onClick, onInput)
import Generator.Properties exposing (CalendarProperties)
import Exporters.HtmlExporter
import Exporters.Properties exposing (PDFExportProperties, HTMLExportProperties)
import Generator.Calendar exposing (generate)
import Exporters.PdfExporter

main = Browser.element
                   { init = init
                   , update = update
                   , subscriptions = subscriptions
                   , view = view
                   }

type alias Model = {
    year: String,
    generatedTable: Html Msg,
    panelContent: Html Msg
  }

init : () -> (Model, Cmd Msg)
init _ = ({
  year = "2018",
  generatedTable = div [] [],
  panelContent = htmlPanel
  }, Cmd.none)

type Msg = ChangeYear String
  | GenerateHtmlCalendar
  | GeneratePdfCalendar
  | ChangePanel (Html Msg)

update : Msg -> Model -> (Model, Cmd Msg)
update msg model =
    case msg of
        ChangeYear newYear -> ({ model | year = newYear }, Cmd.none)
        GenerateHtmlCalendar -> ({ model | generatedTable = Exporters.HtmlExporter.exportCalendar HTMLExportProperties (generateCalendar model) }, Cmd.none)
        GeneratePdfCalendar -> (model, Exporters.PdfExporter.exportCalendar (Exporters.PdfExporter.createModel (PDFExportProperties 10 "") (generateCalendar model)))
        ChangePanel content -> ({ model | panelContent = content }, Cmd.none)

generateCalendar : Model -> Generator.Calendar.LinearCalendar
generateCalendar model = generate (CalendarProperties (getYear(model)))

getYear : Model -> Int
getYear model = Maybe.withDefault 2018 (String.toInt(model.year))

subscriptions : Model -> Sub Msg
subscriptions model =
  Sub.none

view : Model -> Html Msg
view model =
    div []
    [
      calendarPanel model
      , exportTypesPanel model
      , div [] [ model.panelContent ]
      , div [] [ model.generatedTable ]
    ]

calendarPanel : Model -> Html Msg
calendarPanel model = div [] [
    label [ for "calendar-year" ] [ text "Year: "]
    , input [ value model.year, onInput ChangeYear ] []
  ]

exportTypesPanel : Model -> Html Msg
exportTypesPanel model = div [] [
    button [ onClick (ChangePanel htmlPanel) ] [ text "HTML" ]
    , button [ onClick (ChangePanel pdfPanel) ] [ text "PDF" ]
  ]

htmlPanel : Html Msg
htmlPanel = div [ id "html-panel", class "tab-content" ] [
    button [ onClick GenerateHtmlCalendar ] [ text "Generate HTML Calendar" ]
  ]

pdfPanel : Html Msg
pdfPanel = div [ id "pdf-panel", class "tab-content" ] [
    button [ onClick GeneratePdfCalendar ] [ text "Generate PDF Calendar" ]
  ]