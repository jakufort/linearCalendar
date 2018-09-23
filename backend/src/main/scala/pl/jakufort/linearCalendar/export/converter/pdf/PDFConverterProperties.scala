package pl.jakufort.linearCalendar.export.converter.pdf

import java.time.format.TextStyle
import java.util.Locale

import pl.jakufort.linearCalendar.export.converter.ConverterProperties

class PDFConverterProperties(val cellHeight: Float,
                             val locale: Locale,
                             val daysStyle: TextStyle,
                             val monthsStyle: TextStyle)
  extends ConverterProperties {}
