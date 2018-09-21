package scala

import com.neopragma.poker.{PlayingCard, Rank, Suit}
import org.junit.runner.RunWith
import org.scalatest.{FlatSpec, FunSuite}

@RunWith(classOf[org.scalatest.junit.JUnitRunner])
class PlayingCardFunSuite extends FunSuite {

  test("An Ace of Spades should know it is not a one-eyed face card") {
    val card = new PlayingCard(Suit.SPADES, Rank.ACE)
    assert(card.isOneEyed() == false,
      "(Ace should know it is not a one-eyed card)")
  }

  test("A Jack of Hearts should know it is a one-eyed face card") {
    val card = new PlayingCard(Suit.HEARTS, Rank.JACK)
    assert(card.isOneEyed() == true,
      "(Jack of Hearts should know it is a one-eyed card)")
  }
}
