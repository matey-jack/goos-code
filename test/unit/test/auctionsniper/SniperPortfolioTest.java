package test.auctionsniper;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import auctionsniper.AuctionSniper;
import auctionsniper.SniperPortfolio;
import auctionsniper.SniperPortfolio.PortfolioListener;
import auctionsniper.UserRequestListener.Item;

public class SniperPortfolioTest {
  private final PortfolioListener listener = mock(PortfolioListener.class);
  private final SniperPortfolio portfolio = new SniperPortfolio();
  
  @Test public void
  notifiesListenersOfNewSnipers() {
    // Given
	final AuctionSniper sniper = new AuctionSniper(new Item("item id", 123), null);
    portfolio.addPortfolioListener(listener);
    
    // When
    portfolio.addSniper(sniper);
    
    // Then
    verify(listener).sniperAdded(sniper);
  }
}
