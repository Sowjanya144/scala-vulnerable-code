package dao.component

import models.Users
import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat
import play.api.db.slick.HasDatabaseConfigProvider
import slick.ast.BaseTypedType
import slick.jdbc.JdbcType
import slick.lifted.ProvenShape
import slick.driver.JdbcProfile

trait UsersComponent { self: HasDatabaseConfigProvider[JdbcProfile] =>
  import driver.api._

  // Define implicit conversions for org.joda.time.DateTime
  implicit val jodaDateTimeMapper: JdbcType[DateTime] with BaseTypedType[DateTime] = {
    val format = ISODateTimeFormat.dateTime()
    MappedColumnType.base[DateTime, java.sql.Timestamp](
      dt => new java.sql.Timestamp(dt.getMillis),
      ts => new DateTime(ts.getTime)
    )
  }

  class UsersTable(tag: Tag) extends Table[Users](tag, "users") {
    def id = column[String]("id", O.PrimaryKey)
    def name = column[String]("name")
    def email = column[String]("email")
    def password = column[String]("password")
    def createdTime = column[Option[DateTime]]("created_ts")
    def lastUpdatedTime = column[Option[DateTime]]("last_updated_ts")

    // Projection
    def * : ProvenShape[Users] =
      (id, name, email, password, createdTime, lastUpdatedTime) <> ((Users.apply _).tupled, Users.unapply)
  }
}
