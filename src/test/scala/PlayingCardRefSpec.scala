import com.neopragma.poker.{PlayingCard, Rank, Suit}
import org.junit.runner.RunWith
import org.scalatest.refspec.RefSpec

@RunWith(classOf[org.scalatest.junit.JUnitRunner])
class PlayingCardRefSpec extends RefSpec {

  object `A Jack of Hearts` {
    object `when instantiated` {
      def `should know it is a one-eyed face card`: Unit = {
        val jackOfHearts = new PlayingCard(Suit.HEARTS, Rank.JACK)
        assert(jackOfHearts.isOneEyed)
      }
    }
  }

  object `An Ace of Spades` {
    object `when instantiated` {
      def `should know it is not a one-eyed face card`: Unit = {
        val aceOfSpades = new PlayingCard(Suit.SPADES, Rank.ACE)
        assert(aceOfSpades.isOneEyed == false)
      }
    }
  }
}
