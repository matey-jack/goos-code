package test.auctionsniper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import auctionsniper.SniperState;
import auctionsniper.util.Defect;

public class SniperStateTests {

  @Test public void
  isWonWhenAuctionClosesWhileWinning() {
    assertEquals(SniperState.LOST, SniperState.JOINING.whenAuctionClosed());
    assertEquals(SniperState.LOST, SniperState.BIDDING.whenAuctionClosed());
    assertEquals(SniperState.WON, SniperState.WINNING.whenAuctionClosed());
  }
  
  // RW: Defects are always unexpected and should not be tested for!
  //     Instead we have to make sure that callers respect the precondition.
  //     But precondition checking plus simple coverage tests will deal with that automatically!
  @Test(expected=Defect.class) public void
  defectIfAuctionClosesWhenWon() {
    SniperState.WON.whenAuctionClosed();
  }

  @Test(expected=Defect.class) public void
  defectIfAuctionClosesWhenLost() {
    SniperState.LOST.whenAuctionClosed();
  }

}
