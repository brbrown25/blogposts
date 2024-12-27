package io.ssc.tutorial

import cats.effect.IO
import doobie.LogHandler
import doobie.util.log.LogEvent

class PersonSuite extends DatabaseBaseSuite {
  def logHandler(clazz: String): LogHandler[IO] = new LogHandler[IO] {
    def run(logEvent: LogEvent): IO[Unit] = {
      IO.println(s"  [${clazz}] -") >> IO.println(s"\t${logEvent.sql}")
    }
  }

  test("insert person") {
    withContainers { pgContainer =>
      implicit val xa = DbUtils.createTransactor(pgContainer.jdbcUrl, pgContainer.username, pgContainer.password, Some(logHandler(this.getClass.getSimpleName)))
      for {
        res <- Person.insertPerson("Bobby", Some(80))
      } yield assertEquals(res.name, "Bobby")
    }
  }
  test("find person") {
    withContainers { pgContainer =>
      implicit val xa = DbUtils.createTransactor(pgContainer.jdbcUrl, pgContainer.username, pgContainer.password, Some(logHandler(this.getClass.getSimpleName)))
      for {
        res <- Person.findPerson("Bobby")
      } yield assertEquals(res.map(_.name), Some("Bobby"))
    }
  }
  test("find person from initialization") {
    withContainers { pgContainer =>
      implicit val xa = DbUtils.createTransactor(pgContainer.jdbcUrl, pgContainer.username, pgContainer.password, Some(logHandler(this.getClass.getSimpleName)))
      for {
        res <- Person.findPerson("Alice")
      } yield assertEquals(res.map(_.name), Some("Alice"))
    }
  }
  test("update person") {
    withContainers { pgContainer =>
      implicit val xa = DbUtils.createTransactor(pgContainer.jdbcUrl, pgContainer.username, pgContainer.password, Some(logHandler(this.getClass.getSimpleName)))
      for {
        res <- Person.updatePerson(Person(10, "Alice", Some(20)))
      } yield assertEquals(res, 1)
    }
  }
}
