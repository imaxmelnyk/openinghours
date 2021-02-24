package com.imaxmelnyk.wolt.openinghours.models

import com.imaxmelnyk.wolt.openinghours.models.enums.ActionType
import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

case class Action(`type`: ActionType,
                  value: Int)

object Action {
  implicit val decoder: Decoder[Action] = deriveDecoder[Action]
  implicit val ordering: Ordering[Action] = Ordering.by(_.value)

  def actionValueToStringTime(value: Int): String = {
    val hours: Int = value / 3600 % 24
    val minutes: Int = value / 60 % 60
    val seconds: Int = value % 60

    val (formattedHours, dayPart): (Int, String) = hours match {
      case 0 => (12, "AM")
      case 12 => (12, "PM")
      case h if h < 12 => (h, "AM")
      case h if h >= 12 => (h - 12, "PM")
    }

    (formattedHours, minutes, seconds) match {
      case (h, m, s) if s != 0 => s"$h.$m:$s $dayPart"
      case (h, m, _) if m != 0 => s"$h.$m $dayPart"
      case (h, _, _) => s"$h $dayPart"
    }
  }
}
