package models

import java.sql.Timestamp

case class Review(
    id: Long,
    card_id: Long,
    user_id: Long,
    english_response: String,
    japanese_response: String,
    english_correct: Boolean,
    japanese_correct: Boolean,
    createdAt: Timestamp
) {

  def isCorrect(): Boolean = {
    english_correct && japanese_correct
  }

}
