var pdfExporter = function() {
  var tmp = {};

  tmp.subscribeToElm = function(app) {
    app.ports.exportCalendar.subscribe(generatePdf);
  }

  var generatePdf = function(data) {
    var definition = tableDefinition;
    definition.content = [
      {
        table: {
          headerRows: 1,
//          widths: [ '*', 'auto', 100, '*' ],
          body: [
            getHeader(data),
          ].concat(generateCalendar(data))
        }
      }
    ];

    pdfMake.createPdf(definition).download('calendar.pdf');
//    pdfMake.createPdf(definition).open();
  }

  var tableDefinition = {
    pageSize: 'A4',
    pageOrientation: 'landscape'
  }

  var getHeader = function(data) {
    return data.calendar.header;
  }

  var generateCalendar = function(data) {
    return data.calendar.months.map(month => [month.name].concat(daysArray(month.days)))
  }

  var daysArray = function(days) {
    return days.map(x => x === null ? "" : x.toString());
  }

  return tmp;
}();