package io.ssc.tutorial

import cats.effect.IO
import doobie.util.log.LogEvent
import doobie.{LogHandler, Transactor}

object DbUtils {
  def createTransactor(url: String, username: String, password: String, logHandler: Option[LogHandler[IO]] = None): Transactor[IO] = {
    Transactor.fromDriverManager[IO](
      driver = "org.postgresql.Driver",
      url = url,
      user = username,
      password = password,
      logHandler = logHandler
    )
  }
}
