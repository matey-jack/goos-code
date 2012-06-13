package test.auctionsniper;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mockito;

import auctionsniper.Auction;
import auctionsniper.AuctionHouse;
import auctionsniper.AuctionSniper;
import auctionsniper.SniperCollector;
import auctionsniper.SniperLauncher;
import auctionsniper.UserRequestListener.Item;

// http://stackoverflow.com/questions/6565971/does-mockito-have-an-equivalent-idiom-to-jmocks-states
// No, but in many cases, InOrder does the right job!
public class SniperLauncherTest {
  private final Auction auction = mock(Auction.class);
  private final AuctionHouse auctionHouse = mock(AuctionHouse.class);
  private final SniperCollector sniperCollector = mock(SniperCollector.class);
  private final SniperLauncher launcher = new SniperLauncher(auctionHouse, sniperCollector);
  
  @Test public void
  addsNewSniperToCollectorAndThenJoinsAuction() {
	// Given
    final Item item = new Item("item 123", 456);
    when(auctionHouse.auctionFor(item)).thenReturn(auction);
    
    // When
    launcher.joinAuction(item);
 
    // Then
    final InOrder inOrder = Mockito.inOrder(auction);
    inOrder.verify(auction).addAuctionEventListener(argThat(sniperForItem(item))); 
    inOrder.verify(auction).join();

    final InOrder inOrder2 = Mockito.inOrder(auction, sniperCollector);
    inOrder2.verify(sniperCollector).addSniper(argThat(sniperForItem(item))); 
    inOrder2.verify(auction).join();
  }

  protected Matcher<AuctionSniper>sniperForItem(Item item) {
    return new FeatureMatcher<AuctionSniper, String>(equalTo(item.identifier), "sniper with item id", "item") {
      @Override protected String featureValueOf(AuctionSniper actual) {
        return actual.getSnapshot().itemId;
      }
    };
  }
}
