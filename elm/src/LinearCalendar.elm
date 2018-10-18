import Browser
import Html exposing (..)
import Html.Attributes exposing (..)
import Html.Events exposing (onClick, onInput)
import Generator.Properties exposing (CalendarProperties)
import Exporters.HtmlExporter
import Exporters.Properties exposing (PDFExportProperties, HTMLExportProperties)
import Generator.Calendar exposing (generate)
import Exporters.PdfExporter
import Bootstrap.Grid as Grid
import Bootstrap.Tab as Tab
import Bootstrap.CDN as CDN
import Bootstrap.Utilities.Spacing as Spacing

main = Browser.element
                   { init = init
                   , update = update
                   , subscriptions = subscriptions
                   , view = view
                   }

type alias Model = {
    year: String,
    panel: Panel,
    tabState: Tab.State
  }

type alias PDFPanel = {
    cellHeight : String,
    pageSize : String
  }

type alias HTMLPanel = {
    shouldBeGenerated: Bool
  }

type Panel = PDF PDFPanel | HTML HTMLPanel

init : () -> (Model, Cmd Msg)
init _ = ({
  year = "2018",
  panel = HTML { shouldBeGenerated = False },
  tabState = Tab.initialState
  }, Cmd.none)

type Msg = ChangeYear String
  | GenerateHtmlCalendar
  | GeneratePdfCalendar
  | ChangePanel Panel
  | ChangePageSize String
  | ChangeCellHeight String
  | ChangeTab Tab.State

update : Msg -> Model -> (Model, Cmd Msg)
update msg model =
    case msg of
        ChangeYear newYear -> ({ model | year = newYear }, Cmd.none)
        GenerateHtmlCalendar -> ({ model | panel = setShouldGenerate (model.panel) }, Cmd.none)
        GeneratePdfCalendar -> (model, Exporters.PdfExporter.exportCalendar (Exporters.PdfExporter.createModel (PDFExportProperties (getOrDefault (getCellHeight (model.panel)) 10) (getPageSize (model.panel))) (generateCalendar model)))
        ChangePanel newPanel -> ({ model | panel = newPanel }, Cmd.none)
        ChangePageSize newPageSize -> ({ model | panel = setPageSize (model.panel) newPageSize }, Cmd.none)
        ChangeCellHeight newCellHeight -> ({ model | panel = setCellHeight (model.panel) newCellHeight }, Cmd.none)
        ChangeTab state -> ({ model | tabState = state }, Cmd.none)

setShouldGenerate : Panel -> Panel
setShouldGenerate panel = case panel of
    PDF _ -> panel
    HTML values -> HTML ({ values | shouldBeGenerated = True })

setPageSize : Panel -> String -> Panel
setPageSize panel pageSize = case panel of
    PDF vals -> PDF ({ vals | pageSize = pageSize })
    HTML _ -> panel

setCellHeight : Panel -> String -> Panel
setCellHeight panel cellHeight = case panel of
    PDF vals -> PDF ({ vals | cellHeight = cellHeight })
    HTML _ -> panel

getCellHeight : Panel -> String
getCellHeight panel = case panel of
    HTML _ -> ""
    PDF vals -> vals.cellHeight

getPageSize : Panel -> String
getPageSize panel = case panel of
    HTML _ -> ""
    PDF vals -> vals.pageSize

getOrDefault : String -> Int -> Int
getOrDefault str def = Maybe.withDefault def (String.toInt(str))

subscriptions : Model -> Sub Msg
subscriptions model =
  Sub.none

view : Model -> Html Msg
view model = Grid.container []
  [
    -- TODO: remove
    CDN.stylesheet,
    Tab.config ChangeTab
      |> Tab.items [
          Tab.item {
            id = "html-tab",
            link = Tab.link [] [ text "Generate HTML" ],
            pane = htmlPane model
          }, Tab.item {
            id = "pdf-tab",
            link = Tab.link [] [ text "Generate PDF" ],
            pane = pdfPane model
          }
        ]
      |> Tab.view model.tabState
  ]

--view : Model -> Html Msg
--view model =
--    div []
--    [
--      calendarPanel model
--      , exportTabs model
--    ]

htmlPane : Model -> Tab.Pane Msg
htmlPane model = Tab.pane
  [ Spacing.mt3 ]
  [
    h4 [] [ text "HTML PANE" ]
  ]

pdfPane : Model -> Tab.Pane Msg
pdfPane model = Tab.pane
  [ Spacing.mt3 ]
  [ pdfPanel ]

pdfPanel : Html Msg
pdfPanel = div [ id "pdf-panel", class "tab-content" ] [
    div [] [
        label [ for "page-size-select" ] [ text "Page Size: " ]
        , select [ id "page-size-select", onInput ChangePageSize ] (List.map (\x -> option [ value x ] [ text x ]) ["A1", "A2", "A3", "A4", "A5"])
      ]
    , div [] [
          label [ for "cell-height-input"] [ text "Cell Height: " ]
          , input [ id "cell-height-input", onInput ChangeCellHeight ] []
        ]
    , button [ onClick GeneratePdfCalendar ] [ text "Generate PDF Calendar" ]
  ]


--calendarPanel : Model -> Html Msg
--calendarPanel model = div [] [
--    label [ for "calendar-year" ] [ text "Year: "]
--    , input [ value model.year, onInput ChangeYear ] []
--  ]

--exportTabs : Model -> Html Msg
--exportTabs model = div [] [
--    button [ onClick (ChangePanel (HTML { shouldBeGenerated = False })) ] [ text "HTML" ]
--    , button [ onClick (ChangePanel (PDF { pageSize = "A4", cellHeight = "10" })) ] [ text "PDF" ]
--    , case model.panel of
--        HTML fields -> htmlPanel model fields.shouldBeGenerated
--        PDF _ -> pdfPanel
--  ]

--htmlPanel : Model -> Bool -> Html Msg
--htmlPanel model shouldGenerate = div [ id "html-panel", class "tab-content" ] [
--    button [ onClick GenerateHtmlCalendar ] [ text "Generate HTML Calendar" ]
--    , case shouldGenerate of
--        True -> Exporters.HtmlExporter.exportCalendar HTMLExportProperties (generateCalendar model)
--        False -> div [] []
--  ]

generateCalendar : Model -> Generator.Calendar.LinearCalendar
generateCalendar model = generate (CalendarProperties (getOrDefault (model.year) 2018))