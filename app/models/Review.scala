package models

import org.joda.time.DateTime

case class Review(
    id: Long,
    card_id: Long,
    user_id: Long,
    english_response: String,
    japanese_response: String,
    english_correct: Boolean,
    japanese_correct: Boolean,
    createdAt: DateTime
) {

  def isCorrect(): Boolean = {
    english_correct && japanese_correct
  }

}
