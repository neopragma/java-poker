package scala

import com.neopragma.helpers.MessageId
import com.neopragma.poker._
import com.neopragma.preconditions.Precondition
import org.junit.runner.RunWith
import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.{Matchers, PropSpec}

import scala.collection.JavaConverters._
import scala.io.Source

@RunWith(classOf[org.scalatest.junit.JUnitRunner])
class PlayingCardPropSpecFromCsv extends PropSpec with TableDrivenPropertyChecks with Matchers {

  property("The stronger hand beats the weaker hand") {
    val game = new FiveCardStudGame
    var header = true
    for (line <- Source.fromFile("test-data.csv").getLines) {
      if (header) {
        header = false
      } else {
        val params = line.split(",")

        val cards1 = new java.util.ArrayList[Card]
        for (abbrev <- params.slice(2, 7)) {
          cards1.add(new PlayingCard(suit(abbrev), rank(abbrev)))
        }
        val hand1 = new Hand(cards1)

        val cards2 = new java.util.ArrayList[Card]
        for (abbrev <- params.slice(7, 12)) {
          cards2.add(new PlayingCard(suit(abbrev), rank(abbrev)))
        }
        val hand2 = new Hand(cards2)

        assert(hand1.beats(hand2, game) == Result.valueOf(params(12)),
          message(params(1), hand1, hand2))
      }

    }

    def message(description: String, hands: Hand*): Unit
    = {
      val msg = new StringBuilder
      msg.append(description)
      msg.append("\n")
      msg.append("Hand:\t")

      hands.foreach(hand => {
        val cards: scala.collection.mutable.Buffer[Card] = hand.show.asScala
        cards.foreach(card => {
          msg.append(card.rank)
          msg.append(" of ")
          msg.append(card.suit)
          msg.append("\n")
        })
      })
      msg.toString
    }


    def makeHand(cardAbbrev: String*): Hand
    = {
      Precondition.assertThat(cardAbbrev.length == 5, new MessageId("M998"))
      val cards: java.util.List[Card] = new java.util.ArrayList[Card]
      for (abbrev <- cardAbbrev) {
        cards.add(new PlayingCard(suit(abbrev), rank(abbrev)))
      }
      new Hand(cards)
    }

    def suit(abbrev: String): Suit
    = {
      val suitCode: Char = abbrev.charAt(abbrev.length - 1)
      suitCode match {
        case 'S' => Suit.SPADES
        case 'H' => Suit.HEARTS
        case 'D' => Suit.DIAMONDS
        case 'C' => Suit.CLUBS
        case _ => Suit.NONE
      }
    }

    def rank(abbrev: String): Rank
    = {
      val rankCode: Char = abbrev.charAt(0)
      rankCode match {
        case 'A' => Rank.ACE
        case 'K' => Rank.KING
        case 'Q' => Rank.QUEEN
        case 'J' => Rank.JACK
        case '1' => Rank.TEN
        case '2' => Rank.TWO
        case '3' => Rank.THREE
        case '4' => Rank.FOUR
        case '5' => Rank.FIVE
        case '6' => Rank.SIX
        case '7' => Rank.SEVEN
        case '8' => Rank.EIGHT
        case '9' => Rank.NINE
        case _ => Rank.NONE
      }
    }
  }
}

