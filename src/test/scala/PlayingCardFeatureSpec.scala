import com.neopragma.poker.{PlayingCard, Rank, Suit}
import org.junit.runner.RunWith
import org.scalatest.{FeatureSpec, GivenWhenThen}

@RunWith(classOf[org.scalatest.junit.JUnitRunner])
class PlayingCardFeatureSpec extends FeatureSpec with GivenWhenThen {

  feature("Playing card knows whether it's a one-eyed face card") {
    scenario("A one-eyed face card identifies itself as such") {

      Given("The Jack of Hearts")
      val jackOfHearts = new PlayingCard(Suit.HEARTS, Rank.JACK)

      When("we ask the card if it is one-eyed")
      val oneEyed = jackOfHearts.isOneEyed

      Then("it says it is one-eyed")
      assert(oneEyed == true)
    }
    scenario("A regular card knows it isn't one-eyed") {

      Given("The Ace of Spades")
      val aceOfSpades = new PlayingCard(Suit.SPADES, Rank.ACE)

      When("we ask the card if it is one-eyed")
      val oneEyed = aceOfSpades.isOneEyed

      Then("it says it is not one-eyed")
      assert(oneEyed == false)
    }
  }
}
