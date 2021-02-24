package com.imaxmelnyk.wolt.openinghours.models

import com.imaxmelnyk.wolt.openinghours.exceptions.InvalidFormatException
import com.imaxmelnyk.wolt.openinghours.models.OpeningHours._
import com.imaxmelnyk.wolt.openinghours.models.enums.{Close, Monday, Open, Weekday}
import com.imaxmelnyk.wolt.openinghours.models.Action.actionValueToStringTime

case class OpeningHours(protected val schedule: Schedule) {
  import OpeningHours._

  override def toString: String = {
    schedule
      .toSeq.sorted
      .map({ case (weekday, actions) => (weekday, actions.sorted) })
      .foldLeft((Vector.empty[String], false)) {
        // when we have over the week opening hours (from sunday to monday) we put correction at the beginning
        case ((_, false), (Monday, Action(Close, closeTimeValue) +: actions)) =>
          val (mondayString, isOverNight): (String, Boolean) = processSimpleWeekDay(Monday, actions)
          (Vector(actionValueToStringTime(closeTimeValue), mondayString), isOverNight)

        // usual simple opening hours without overnight
        case ((acc, false), (weekday, actions)) =>
          val (weekdayString, isOverNight): (String, Boolean) = processSimpleWeekDay(weekday, actions)
          (acc :+ weekdayString, isOverNight)

        // handling overnight
        case ((acc :+ toCorrect, true), (weekday, Action(Close, closeTimeValue) +: actions)) =>
          val (weekdayString, isOverNight): (String, Boolean) = processSimpleWeekDay(weekday, actions)
          val overNightCorrection: String = toCorrect + actionValueToStringTime(closeTimeValue)

          (acc :++ Vector(overNightCorrection, weekdayString), isOverNight)

        case _ =>
          throw InvalidFormatException
      } match {
      // handling over the week opening hours
      case (correction +: schedule :+ toCorrect, true) =>
        (schedule :+ toCorrect + correction).mkString("\n")
      case (schedule, false) =>
        schedule.mkString("\n")
      case _ =>
        throw InvalidFormatException
    }
  }

  // here we assume that the actions always start with "Open" or empty
  private def processSimpleWeekDay(weekday: Weekday,
                                   actions: Seq[Action]): (String, Boolean) = {
    if (actions.isEmpty) (s"$weekday: Closed", false)
    else {
      val (periods, isOverNight): (Seq[String], Boolean) =  actions.grouped(2).foldLeft((Seq.empty[String], false)) {
        // usual situation with open and close pair
        case ((acc, _), Seq(Action(Open, openTimeValue), Action(Close, closeTimeValue))) =>
          (acc :+ s"${actionValueToStringTime(openTimeValue)} - ${actionValueToStringTime(closeTimeValue)}" , false)

        // situation with coming overnight opening hours
        case ((acc, _), Seq(Action(Open, openTimeValue))) =>
          (acc :+ s"${actionValueToStringTime(openTimeValue)} - " , true)

        case _ =>
          throw InvalidFormatException
      }

      (s"$weekday: ${periods.mkString(", ")}", isOverNight)
    }
  }
}

object OpeningHours {
  implicit val scheduleOrdering: Ordering[(Weekday, Seq[Action])] = Ordering.by(_._1)

  type Schedule = Map[Weekday, Seq[Action]]
}
