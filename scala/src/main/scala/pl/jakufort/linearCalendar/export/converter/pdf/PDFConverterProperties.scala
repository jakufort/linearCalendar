package pl.jakufort.linearCalendar.export.converter.pdf

import java.time.format.TextStyle
import java.util.Locale

import pl.jakufort.linearCalendar.export.converter.ConverterProperties

final case class PDFConverterProperties(cellHeight: Float,
                                        locale: Locale,
                                        daysStyle: TextStyle,
                                        monthsStyle: TextStyle)
  extends ConverterProperties {}
