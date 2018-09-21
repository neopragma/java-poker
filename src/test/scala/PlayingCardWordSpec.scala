package scala

import com.neopragma.poker.{PlayingCard, Rank, Suit}
import org.junit.runner.RunWith
import org.scalatest.{FunSpec, WordSpec}

@RunWith(classOf[org.scalatest.junit.JUnitRunner])
class PlayingCardWordSpec extends WordSpec {

  "An Ace of Spades" when {
    "instantiated" should {
      "know it is not a one-eyed face card" in {
        val card = new PlayingCard(Suit.SPADES, Rank.ACE)
        assert(card.isOneEyed() == false,
          "(Ace should know it is not a one-eyed card)")
      }
    }
  }

  "A Jack of Hearts" when {
    "instantiated" should {
      "know it is a one-eyed face card" in {
        val card = new PlayingCard(Suit.HEARTS, Rank.JACK)
        assert(card.isOneEyed() == true,
          "(Jack of Hearts should know it is a one-eyed card)")
      }
    }
  }
}
