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
import Bootstrap.Form as Form
import Bootstrap.Form.Input as Input
import Bootstrap.Form.Select as Select
import Bootstrap.Button as Button
import Bootstrap.Navbar as Navbar
import Bootstrap.Card as Card
import Bootstrap.Card.Block as Block

main = Browser.element
                   { init = init
                   , update = update
                   , subscriptions = subscriptions
                   , view = view
                   }

type alias Model = {
    year: String,
    cellHeight : String,
    pageSize : String,
    generateHtml: Bool,
    tabState: Tab.State,
    navbarState: Navbar.State
  }

init : () -> (Model, Cmd Msg)
init _ =
    let
        (navbarState, navbarCmd) = Navbar.initialState NavbarMsg
    in
    ({
    year = "2018",
    cellHeight = "10",
    pageSize = "A4",
    generateHtml = False,
    tabState = Tab.initialState,
    navbarState = navbarState
    }, navbarCmd)

type Msg = ChangeYear String
  | GenerateHtmlCalendar
  | GeneratePdfCalendar
  | ChangePageSize String
  | ChangeCellHeight String
  | ChangeTab Tab.State
  | NavbarMsg Navbar.State
  | ClearHtmlCalendar

update : Msg -> Model -> (Model, Cmd Msg)
update msg model =
    case msg of
        ChangeYear newYear -> ({ model | year = newYear }, Cmd.none)
        GenerateHtmlCalendar -> ({ model | generateHtml = True }, Cmd.none)
        GeneratePdfCalendar -> (model, Exporters.PdfExporter.exportCalendar (Exporters.PdfExporter.createModel (PDFExportProperties (getOrDefault (model.cellHeight) 10) (model.pageSize)) (generateCalendar model)))
        ChangePageSize newPageSize -> ({ model | pageSize = newPageSize }, Cmd.none)
        ChangeCellHeight newCellHeight -> ({ model | cellHeight = newCellHeight }, Cmd.none)
        ChangeTab state -> ({ model | tabState = state }, Cmd.none)
        NavbarMsg state -> ({ model | navbarState = state }, Cmd.none)
        ClearHtmlCalendar -> ({ model | generateHtml = False }, Cmd.none)

getOrDefault : String -> Int -> Int
getOrDefault str def = Maybe.withDefault def (String.toInt(str))

subscriptions : Model -> Sub Msg
subscriptions model =
    Sub.batch [
      Navbar.subscriptions model.navbarState NavbarMsg,
      Tab.subscriptions model.tabState ChangeTab
    ]


view : Model -> Html Msg
view model = Grid.container []
  [
  -- TODO: remove
    CDN.stylesheet,
    navbar model,
    Card.config []
      |> Card.block []
          [ Block.titleH4 [] [ text "Calendar Generator" ]
          , Block.custom <|
              yearInput
          , Block.custom <|
              tabs model
          ]
      |> Card.view
  ]

navbar : Model -> Html Msg
navbar model = Navbar.config NavbarMsg
  |> Navbar.withAnimation
  |> Navbar.attrs [Spacing.mb4]
  |> Navbar.brand [ href "#"] [ text "Linear Calendar in Elm"]
  |> Navbar.items
     [ Navbar.itemLink [href "#"] [ text "Source Code"] ]
  |> Navbar.view model.navbarState

tabs : Model -> Html Msg
tabs model = Tab.config ChangeTab
  |> Tab.pills
  |> Tab.useHash True
  |> Tab.items [
     Tab.item {
       id = "html-tab",
       link = Tab.link [] [ text "HTML" ],
       pane = htmlPane model
     }, Tab.item {
       id = "pdf-tab",
       link = Tab.link [] [ text "PDF" ],
       pane = pdfPane model
     }
   ]
  |> Tab.view model.tabState

htmlPane : Model -> Tab.Pane Msg
htmlPane model = Tab.pane
  [ Spacing.mt3 ]
  [
    Form.form [] [
      Button.button [ Button.primary, Button.onClick GenerateHtmlCalendar] [ text "Generate" ],
      Button.button [ Button.secondary, Button.attrs [ Spacing.ml1 ], Button.onClick ClearHtmlCalendar ] [ text "Clear" ],
      case model.generateHtml of
          True -> Exporters.HtmlExporter.exportCalendar HTMLExportProperties (generateCalendar model)
          False -> div [] []
    ]
  ]

pdfPane : Model -> Tab.Pane Msg
pdfPane model = Tab.pane
  [ Spacing.mt3 ]
  [
    Form.form [] [
      Form.group [] [
        Form.label [for "page-size-select"] [ text "Page Size"],
        Select.select [Select.id "page-size-select", Select.onChange ChangePageSize] (List.map (\x -> Select.item [value x] [text x]) ["A1", "A2", "A3", "A4", "A5"])
      ],
      Form.group [] [
        Input.number [Input.id "cell-height-input", Input.onInput ChangeCellHeight, Input.placeholder "Cell Height"]
      ],
      Button.button [Button.primary, Button.onClick GeneratePdfCalendar] [ text "Generate" ]
    ]
  ]

yearInput : Html Msg
yearInput = Form.group [] [
    Input.number [Input.id "calendar-year", Input.onInput ChangeYear, Input.placeholder "Year"]
  ]

generateCalendar : Model -> Generator.Calendar.LinearCalendar
generateCalendar model = generate (CalendarProperties (getOrDefault (model.year) 2018))