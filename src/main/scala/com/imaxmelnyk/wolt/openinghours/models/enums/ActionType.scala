package com.imaxmelnyk.wolt.openinghours.models.enums

import io.circe.Decoder

sealed trait ActionType

object ActionType {
  implicit val decoder: Decoder[ActionType] = Decoder.decodeString.emap {
    case "open" => Right(Open)
    case "close" => Right(Close)
    case _ => Left("invalid action type")
  }
}

case object Open extends ActionType
case object Close extends ActionType
