package scala

import com.neopragma.poker.{PlayingCard, Rank, Suit}
import org.junit.runner.RunWith
import org.scalatest.prop.{TableDrivenPropertyChecks}
import org.scalatest.{Matchers, PropSpec}

@RunWith(classOf[org.scalatest.junit.JUnitRunner])
class PlayingCardPropSpec extends PropSpec with TableDrivenPropertyChecks with Matchers {

  val examples =
    Table(
      ("card", "result"),
      (new PlayingCard(Suit.SPADES, Rank.ACE), false),
      (new PlayingCard(Suit.HEARTS, Rank.JACK), true)
    )

  property("a PlayingCard knows whether it is a one-eyed face card") {
    forAll(examples) { (card: PlayingCard, result: Boolean) =>
      assert(card.isOneEyed() == result,
        "(" + card.rank() + " of " + card.suit() + " doesn't know if it's one-eyed or not)"
      )
    }
  }

}
