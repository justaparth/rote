package models

import org.joda.time.DateTime

case class Card(
    id: Long,
    japanese: String,
    furigana: String,
    english: Seq[String],
    notes: String,
    createdAt: DateTime,
    updatedAt: DateTime
)
