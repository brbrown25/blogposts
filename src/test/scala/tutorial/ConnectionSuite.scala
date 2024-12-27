package io.ssc.tutorial

import cats.effect.IO
import doobie.LogHandler
import doobie.util.log.LogEvent

class ConnectionSuite extends DatabaseBaseSuite {
  def logHandler(clazz: String): LogHandler[IO] = new LogHandler[IO] {
    def run(logEvent: LogEvent): IO[Unit] = {
      IO.println(s"  [${clazz}] -") >> IO.println(s"\t${logEvent.sql}")
    }
  }
  test("basic db connection") {
    withContainers { pgContainer =>
      assert(pgContainer.jdbcUrl.nonEmpty)
    }
  }
  test("the answer to the universe") {
    withContainers { pgContainer =>
      val xa = DbUtils.createTransactor(pgContainer.jdbcUrl, pgContainer.username, pgContainer.password, Some(logHandler(this.getClass.getSimpleName)))
      for {
        res <- Queries.answerToTheUniverse(xa)
      } yield assertEquals(res, 42)
    }
  }
}
