package scala

import com.neopragma.poker.{PlayingCard, Rank, Suit}
import org.junit.runner.RunWith
import org.scalatest.FlatSpec

@RunWith(classOf[org.scalatest.junit.JUnitRunner])
class PlayingCardFlatSpec extends FlatSpec {

  behavior of "a PlayingCard"

  it should "know it is not a one-eyed face card" in {
    val card = new PlayingCard(Suit.SPADES, Rank.ACE)
    assert(card.isOneEyed() == false,
      "(Ace should know it is not a one-eyed card)")
  }

  it should "know it is a one-eyed face card" in {
    val card = new PlayingCard(Suit.HEARTS, Rank.JACK)
    assert(card.isOneEyed() == true,
      "(Jack of Hearts should know it is a one-eyed card)")
  }

}
