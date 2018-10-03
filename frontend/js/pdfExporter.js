var pdfExporter = function() {
  var tmp = {};

  tmp.subscribeToElm = function(app) {
    app.ports.exportCalendar.subscribe(function(data) {
      console.log(data);
    });
  }

  return tmp;
}();