package com.imaxmelnyk.wolt.openinghours.models.enums

import io.circe.KeyDecoder

sealed abstract class Weekday(protected val printName: String,
                              protected val order: Int) extends Ordered[Weekday] {
  override def compare(that: Weekday): Int = order - that.order
  override def toString: String = printName
}

object Weekday {
  implicit val keyDecoder: KeyDecoder[Weekday] = {
    case "monday" => Some(Monday)
    case "tuesday" => Some(Tuesday)
    case "wednesday" => Some(Wednesday)
    case "thursday" => Some(Thursday)
    case "friday" => Some(Friday)
    case "saturday" => Some(Saturday)
    case "sunday" => Some(Sunday)
    case _ => Option.empty[Weekday]
  }
}

case object Monday extends Weekday("Monday", 0)
case object Tuesday extends Weekday("Tuesday", 1)
case object Wednesday extends Weekday("Wednesday", 2)
case object Thursday extends Weekday("Thursday", 3)
case object Friday extends Weekday("Friday", 4)
case object Saturday extends Weekday("Saturday", 5)
case object Sunday extends Weekday("Sunday", 6)
