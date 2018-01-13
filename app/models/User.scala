package models

import java.sql.Timestamp

case class User(
    id: Long,
    firstName: String,
    lastName: String,
    email: String,
    createdAt: Timestamp,
    updatedAt: Timestamp
)