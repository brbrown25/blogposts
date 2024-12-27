package io.ssc.tutorial

import cats.effect.IO
import doobie.Transactor
import doobie.implicits._

final case class Food(id: Long, name: String, isVegetarian: Boolean, caloriesPerServing: Int)

object Food {
  /**
  * Since isGlutenFree is not a field in the case class, we will get the following exception
  * when we run the FoodSuite.
  *
  * ==> X io.ssc.tutorial.FoodSuite.find food  0.035s org.postgresql.util.PSQLException: ERROR: column "is_gluten_free" does not exist
  *  Position: 55
  **/
  def findFood(n: String)(implicit xa: Transactor[IO]): IO[Option[Food]] = {
    val q = sql"select id, name, is_vegetarian, calories_per_serving, is_gluten_free from food where name = $n".query[Food].option
    q.transact(xa)
  }
}