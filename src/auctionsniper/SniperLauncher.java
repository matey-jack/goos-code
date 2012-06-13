package auctionsniper;


public class SniperLauncher implements UserRequestListener {
  private final AuctionHouse auctionHouse;
  private final SniperCollector collector;

  public SniperLauncher(AuctionHouse auctionHouse, SniperCollector collector) {
    this.auctionHouse = auctionHouse;
    this.collector = collector;
  }

  public void joinAuction(Item item) { 
    Auction auction = auctionHouse.auctionFor(item);
    AuctionSniper sniper = new AuctionSniper(item, auction); 
    // TODO:RW: how to check preconditions on run-order: first add, then join! 
    // (because `join´ sends commands which could already trigger replies that
    // would then be missed...)
    // This is an important test, because the order of things is so fragile here!!
    auction.addAuctionEventListener(sniper); 
    collector.addSniper(sniper); 
    auction.join(); 
  }
}