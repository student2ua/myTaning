package com.tor.xmlxsl.rss;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * User: tor
 * Date: 11.10.17
 * Time: 16:34
 * http://www.vogella.com/tutorials/RSSFeed/article.html
 */
public class StaxFeedsParser {
    static final String TITLE = "title";
    static final String DESCRIPTION = "description";
    static final String CHANNEL = "channel";
    static final String LANGUAGE = "language";
    static final String COPYRIGHT = "copyright";
    static final String LINK = "link";
    static final String AUTHOR = "author";
    static final String ITEM = "item";
    static final String PUB_DATE = "pubDate";
    static final String GUID = "guid";
    private final URL url;

    public StaxFeedsParser(String urlStr) throws MalformedURLException {
        this.url = new URL(urlStr);
    }

    public Feed readFeed() {
        Feed feed = null;
        try {
            boolean isFeedHeader = true;
            String description = "";
            String title = "";
            String link = "";
            String language = "";
            String copyright = "";
            String author = "";
            String pubdate = "";
            String guid = "";

            // First create a new XMLInputFactory
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            // Setup a new eventReader
            InputStream in = read();
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            // read the XML document
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                if (event.isStartElement()) {
                    String localPart = event.asStartElement().getName().getLocalPart();

                    if (ITEM.equals(localPart)) {
                        if (isFeedHeader) {
                            isFeedHeader = false;
                            feed = new Feed(title, link, description, language,
                                    copyright, pubdate);
                        }
                        event = eventReader.nextEvent();

                    } else if (TITLE.equals(localPart)) {
                        title = getCharacterData(event, eventReader);

                    } else if (DESCRIPTION.equals(localPart)) {
                        description = getCharacterData(event, eventReader);

                    } else if (LINK.equals(localPart)) {
                        link = getCharacterData(event, eventReader);

                    } else if (GUID.equals(localPart)) {
                        guid = getCharacterData(event, eventReader);

                    } else if (LANGUAGE.equals(localPart)) {
                        language = getCharacterData(event, eventReader);

                    } else if (AUTHOR.equals(localPart)) {
                        author = getCharacterData(event, eventReader);

                    } else if (PUB_DATE.equals(localPart)) {
                        pubdate = getCharacterData(event, eventReader);

                    } else if (COPYRIGHT.equals(localPart)) {
                        copyright = getCharacterData(event, eventReader);

                    }
                } else if (event.isEndElement()) {
                    if (ITEM.equals(event.asEndElement().getName().getLocalPart())) {
                        FeedMsg message = new FeedMsg();
                        message.setAuthor(author);
                        message.setDescription(description);
                        message.setGuid(guid);
                        message.setLink(link);
                        message.setTitle(title);
                        feed.getEntries().add(message);
                        event = eventReader.nextEvent();
//                        continue;
                    }
                }
            }
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
        return feed;
    }

    private String getCharacterData(XMLEvent event, XMLEventReader eventReader)
            throws XMLStreamException {
        String result = "";
        event = eventReader.nextEvent();
        if (event instanceof Characters) {
            result = event.asCharacters().getData();
        }
        return result;
    }

    private InputStream read() {
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0 Safari/537.36");
            conn.setRequestProperty("Accept", "application/rss+xml, text/xml");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);

            int code = conn.getResponseCode();
            if (code == 302 || code == 301) {
                String location = conn.getHeaderField("Location");
                System.out.println("Redirects to: " + location);
            }
            if (code != HttpURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code: " + code);
            }

            return conn.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException("Failed to fetch RSS feed: " + url, e);
        }
    }

    public static void main(String[] args) throws MalformedURLException {
        StaxFeedsParser parser =
                new StaxFeedsParser("http://lwn.net/headlines/rss");
//                new StaxFeedsParser("https://nnmclub.to/forum/rss.php?f=447&t=1");
//        new StaxFeedsParser(new File("feed.xml"));   java 1.6 - TLS 1.0, need java 11
        Feed feed = parser.readFeed();
        System.out.println(feed);
        for (FeedMsg message : feed.getEntries()) {
            System.out.println(message);
        }
    }
}
