package com.tor.web.asyncServlet;

import javax.servlet.AsyncContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@WebListener
public class BidPushService implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        final Map<String, List<AsyncContext>> aucWatchers = new HashMap<String, List<AsyncContext>>();
        sce.getServletContext().setAttribute("aucWatchers", aucWatchers);
        // store new bids not published yet
        Queue<Bid> aucBids = new ConcurrentLinkedQueue<Bid>();
        sce.getServletContext().setAttribute("aucBids", aucBids);

        Executor bidExecutor = Executors.newCachedThreadPool();
        final Executor watcherExecutor = Executors.newCachedThreadPool();
        while(true)
        {
            if(!aucBids.isEmpty()) // There are unpublished new bid events.
            {
                final Bid bid = aucBids.poll();
                bidExecutor.execute(new Runnable(){
                    public void run() {
                        List<AsyncContext> watchers = aucWatchers.get(bid.getAuctionId());
                        for(final AsyncContext aCtx : watchers)
                        {
                            watcherExecutor.execute(new Runnable(){
                                public void run() {
                                    // publish a new bid event to a watcher
                                    try {
                                        aCtx.getResponse().getWriter().print("A new bid on the item was placed. The current price ..., next bid price is ...");
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                };
                            });
                        }
                    }
                });
            }
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
    }

    public class Bid {
        private String auctionId;

        private String getAuctionId() {
            return auctionId;
        }

        private void setAuctionId(String auctionId) {
            this.auctionId = auctionId;
        }
    }
}
