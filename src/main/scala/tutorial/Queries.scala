package io.ssc.tutorial

import cats.effect.IO
import doobie.implicits._
import doobie.{ConnectionIO, Transactor}

object Queries {
  def answerToTheUniverse(xa: Transactor[IO]): IO[Int] = {
    val q: ConnectionIO[Int] = sql"select 42".query[Int].unique
    q.transact(xa)
  }
}
