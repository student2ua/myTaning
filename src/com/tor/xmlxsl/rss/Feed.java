package com.tor.xmlxsl.rss;

import java.util.ArrayList;
import java.util.List;

/**
 * User: tor
 * Date: 11.10.17
 * Time: 16:29
 * rss feed
 */
public class Feed {
    String title,link,description,language,copyright, pubDate;
    List<FeedMsg> entries =new ArrayList<FeedMsg>();

    public Feed(String title, String link, String description, String language, String copyright, String pubDate) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.language = language;
        this.copyright = copyright;
        this.pubDate = pubDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public List<FeedMsg> getEntries() {
        return entries;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Feed{");
        sb.append("title='").append(title).append('\'');
        sb.append(", link='").append(link).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", language='").append(language).append('\'');
        sb.append(", copyright='").append(copyright).append('\'');
        sb.append(", pubDate='").append(pubDate).append('\'');
        sb.append(", entries.size=").append(entries.size());
        sb.append('}');
        return sb.toString();
    }
}
