package io.ssc.tutorial

import cats.effect.IO
import com.dimafeng.testcontainers.PostgreSQLContainer
import com.dimafeng.testcontainers.munit.TestContainerForAll
import doobie.LogHandler
import doobie.implicits._
import doobie.util.log.LogEvent
import munit.CatsEffectSuite
import org.testcontainers.utility.DockerImageName

trait DatabaseBaseSuite extends CatsEffectSuite with TestContainerForAll {
  def printSqlLogHandler(clazz: String): LogHandler[IO] = new LogHandler[IO] {
    def run(logEvent: LogEvent): IO[Unit] = {
      IO {
        println(s"[${clazz}] - ${logEvent.sql}")
      }
    }
  }

  override def beforeAll(): Unit =
    super.beforeAll()

  override def afterAll(): Unit =
    super.afterAll()

  override val containerDef = PostgreSQLContainer.Def(dockerImageName = DockerImageName.parse("postgres:16.3"))

  override def afterContainersStart(container: Containers): Unit = {
    val clazz = this.getClass.getSimpleName
    println(s"[${clazz}] Setting up container ${container.containerId}")
    val xa = DbUtils.createTransactor(container.jdbcUrl, container.username, container.password, Some(printSqlLogHandler(clazz)))

    //create the tables we need
    val createFoodTbl = sql"create table food (id bigint generated by default as identity primary key, name varchar(255) not null unique , is_vegetarian boolean default false, calories_per_serving int)".update.run.transact(xa).unsafeRunSync()
    val createPersonTbl = sql"create table person (id bigint generated by default as identity primary key, name text not null, age int)".update.run.transact(xa).unsafeRunSync()

    //insert some sample data
    val f1 = sql"insert into food (name, is_vegetarian, calories_per_serving) values ('banana', true, 110)".update.run.transact(xa).unsafeRunSync()
    val f2 = sql"insert into food (name, is_vegetarian, calories_per_serving) values ('apples', true, 10)".update.run.transact(xa).unsafeRunSync()
    val f3 = sql"insert into food (name, is_vegetarian, calories_per_serving) values ('pizza', false, 210)".update.run.transact(xa).unsafeRunSync()
    val f4 = sql"insert into food (name, is_vegetarian, calories_per_serving) values ('pie', true, 110)".update.run.transact(xa).unsafeRunSync()

    val p1 = sql"insert into person (id, name, age) values (10, 'Alice', 15)".update.run.transact(xa).unsafeRunSync()
    val p2 = sql"insert into person (id, name, age) values (20, 'Jerry', 80)".update.run.transact(xa).unsafeRunSync()
    val p3 = sql"insert into person (id, name, age) values (30, 'Hunter', 100)".update.run.transact(xa).unsafeRunSync()

    super.afterContainersStart(container)
  }
}
