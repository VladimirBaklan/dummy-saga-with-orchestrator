package com.vladimirbaklan

import scala.util.{Failure, Success, Try}

package object common {
  type ServiceResult[A] = Either[ServiceError, A]

  object ServiceResult {
    val Done = Right(())

    def fromOption[A](option: Option[A], error: Option[ServiceError] = None): ServiceResult[A] = option match {
      case Some(value) => Right(value)
      case None => Left(error.getOrElse(NotFoundError()))
    }

    def fromTry[A](value: Try[A], error: Option[ServiceError] = None): ServiceResult[A] = value match {
      case Success(value) => Right(value)
      case Failure(exception) => Left(error.getOrElse(NotFoundError()))
    }
  }

  case class NotFoundError(message: String = "Element Not Found") extends ServiceError

  // TODO: Error code?
  sealed trait ServiceError {
    def message: String
  }

}
