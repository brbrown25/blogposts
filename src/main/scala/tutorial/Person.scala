package io.ssc.tutorial

import cats.effect.IO
import doobie.Transactor
import doobie.implicits._


final case class Person(id: Long, name: String, age: Option[Long])

object Person {
  def findPerson(n: String)(implicit xa: Transactor[IO]): IO[Option[Person]] = {
    val q = sql"select id, name, age from person where name = $n".query[Person].option
    q.transact(xa)
  }

  def updatePerson(p: Person)(implicit xa: Transactor[IO]): IO[Int] = {
    val q = sql"update person set age = ${p.age} where name = ${p.name}".update.run
    q.transact(xa)
  }

  def insertPerson(name: String, age: Option[Short])(implicit xa: Transactor[IO]): IO[Person] = {
    val q = for {
      _ <- sql"insert into person (name, age) values ($name, $age)".update.run
      id <- sql"select lastval()".query[Long].unique
      p <- sql"select id, name, age from person where id = $id".query[Person].unique
    } yield p
    q.transact(xa)
  }
}