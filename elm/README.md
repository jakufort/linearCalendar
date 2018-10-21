# Linear Calendar in elm

## Why?

I wanted to finally dabble in elm, and, after writing "backend" in scala, I realized that I can write this generated in javascript, so that it could be served directly from github for others to use.

## Used libraries
- pdfmake

## Usage

`index.html` expects two javascript files
- `elm.min.js` - can be created using two commands
    -  `elm make src/LinearCalendar.elm --optimize --output=js/elm.js`
    -  `uglifyjs js/elm.js --compress 'pure_funcs="F2,F3,F4,F5,F6,F7,F8,F9,A2,A3,A4,A5,A6,A7,A8,A9",pure_getters,keep_fargs=false,unsafe_comps,unsafe' | uglifyjs --mangle --output=js/elm.min.js`
    - (more on optimizing elm can be found here: https://elm-lang.org/0.19.0/optimize)
- `pdfExporter.js` - already inside repository 

## License
AGPLv3