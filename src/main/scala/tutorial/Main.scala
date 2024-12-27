package io.ssc.tutorial

import cats.effect._
import doobie._

object Main extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
//    val foods = List(
//      Food(20, "cheddar cheese", true, 113),
//      Food(30, "Big Mac", false, 1120)
//    )
//
//    val xa: Transactor[IO] = DbUtils.createTransactor("jdbc:postgresql://localhost:5432/test", "postgres", "postgres")
    IO.println("there is no main for this :)") >> IO(ExitCode.Success)
  }
}

