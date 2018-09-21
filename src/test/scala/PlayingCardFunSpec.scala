package scala

import com.neopragma.poker.{PlayingCard, Rank, Suit}
import org.junit.runner.RunWith
import org.scalatest.FunSpec

@RunWith(classOf[org.scalatest.junit.JUnitRunner])
class PlayingCardFunSpec extends FunSpec {

  describe("One-eyed face cards:") {
    describe("Ace of Spades") {
      it("knows it isn't a one-eyed face card") {
        val card = new PlayingCard(Suit.SPADES, Rank.ACE)
        assert(card.isOneEyed() == false,
          "(Ace should know it is not a one-eyed card)")
      }
    }
    describe("Jack of Hearts") {
      it("knows it is a one-eyed face card") {
        val card = new PlayingCard(Suit.HEARTS, Rank.JACK)
        assert(card.isOneEyed() == true,
          "(Jack should know it is a one-eyed face card)")
      }
    }
  }
}
