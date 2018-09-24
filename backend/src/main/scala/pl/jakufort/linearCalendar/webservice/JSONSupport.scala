package pl.jakufort.linearCalendar.webservice

import java.time.format.TextStyle
import java.util.Locale

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import pl.jakufort.linearCalendar.calendar.CalendarProperties
import pl.jakufort.linearCalendar.export.converter.pdf.PDFConverterProperties
import pl.jakufort.linearCalendar.export.pdf.PDFExporterProperties
import spray.json._

trait JSONSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit object LocaleFormat extends JsonFormat[Locale] {
    def write(obj: Locale) = JsString(obj.toString)
    def read(json: JsValue) : Locale = json match {
      case JsString(langString) => new Locale(langString)
      case _ => deserializationError("Locale Language String Expected")
    }
  }
  implicit object TextStyleFormat extends JsonFormat[TextStyle] {
    override def write(obj: TextStyle): JsValue = JsString(obj.toString)
    override def read(json: JsValue): TextStyle = json match {
      case JsString(textStyle) => TextStyle.valueOf(textStyle)
      case _ => deserializationError("Locale Language String Expected")
    }
  }
  implicit val calendarPropertiesFormat: RootJsonFormat[CalendarProperties] = jsonFormat1(CalendarProperties)
  implicit val pdfConverterProperties: RootJsonFormat[PDFConverterProperties] = jsonFormat4(PDFConverterProperties)
  implicit val pdfExporterProperties: RootJsonFormat[PDFExporterProperties] = jsonFormat2(PDFExporterProperties)
  implicit val serviceProperties: RootJsonFormat[ServiceProperties] = jsonFormat2(ServiceProperties)
}
