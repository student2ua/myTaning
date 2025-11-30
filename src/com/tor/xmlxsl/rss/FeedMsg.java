package com.tor.xmlxsl.rss;

/**
 * User: tor
 * Date: 11.10.17
 * Time: 16:31
 */
public class FeedMsg {
    String title,description, link, author, guid;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FeedMsg{");
        sb.append("title='").append(title).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", link='").append(link).append('\'');
        sb.append(", author='").append(author).append('\'');
        sb.append(", guid='").append(guid).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
