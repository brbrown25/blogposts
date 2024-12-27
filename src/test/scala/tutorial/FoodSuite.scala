package io.ssc
package tutorial

import cats.effect.IO
import doobie.LogHandler
import doobie.util.log.LogEvent

class FoodSuite extends DatabaseBaseSuite {
  def logHandler(clazz: String): LogHandler[IO] = new LogHandler[IO] {
    def run(logEvent: LogEvent): IO[Unit] = {
      IO.println(s"  [${clazz}] -") >> IO.println(s"\t${logEvent.sql}")
    }
  }

  test("find food") {
    withContainers { pgContainer =>
      implicit val xa = DbUtils.createTransactor(pgContainer.jdbcUrl, pgContainer.username, pgContainer.password, Some(logHandler(this.getClass.getSimpleName)))
      for {
        res <- Food.findFood("pizza")
      } yield assertEquals(res.map(_.name), Some("pizza"))
    }
  }
}
