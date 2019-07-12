package com.tor.web.asyncServlet;

import javax.servlet.AsyncContext;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * User: tor
 * Date: 12.07.19
 * Time: 18:26
 * https://www.javaworld.com/article/2077995/java-concurrency-asynchronous-processing-support-in-servlet-3-0.html?page=2
 */
@WebServlet(asyncSupported = true)
public class AuctionServlet extends HttpServlet {
    // track bid prices
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        AsyncContext aCtx = request.startAsync(request, response);
        // This could be a cluser-wide cache.
        ServletContext appScope = request.getServletContext();
        Map<String, List<AsyncContext>> aucWatchers = (Map<String, List<AsyncContext>>) appScope.getAttribute("aucWatchers");
        List<AsyncContext> watchers = aucWatchers.get(request.getParameter("auctionId"));
//        @see BidPushService
        watchers.add(aCtx); // register a watcher
    }

    // place a bid
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        // run in a transactional context
        // save a new bid
        AsyncContext aCtx = request.startAsync(request, response);
        ServletContext appScope = request.getServletContext();
        Queue<BidPushService.Bid> aucBids = (Queue<BidPushService.Bid>) appScope.getAttribute("aucBids");
        aucBids.add((BidPushService.Bid) request.getAttribute("bid"));  // a new bid event is placed queued.
    }

}

