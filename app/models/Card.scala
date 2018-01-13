package models

import java.sql.Timestamp

case class Card(
    id: Long,
    japanese: String,
    furigana: String,
    english: Seq[String],
    notes: String,
    createdAt: Timestamp,
    updatedAt: Timestamp
)
