package models

import org.joda.time.DateTime

case class Users(
  id: String,
  name: String,
  email: String,
  password: String,
  createdTime: Option[DateTime],
  lastUpdatedTime: Option[DateTime]
)
