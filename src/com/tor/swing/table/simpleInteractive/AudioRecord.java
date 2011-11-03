package com.tor.swing.table.simpleInteractive;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 01.11.11
 * Time: 17:35
 */
public class AudioRecord {
     protected String title;
     protected String artist;
     protected String album;

     public AudioRecord() {
         title = "";
         artist = "";
         album = "";
     }

     public String getTitle() {
         return title;
     }

     public void setTitle(String title) {
         this.title = title;
     }

     public String getArtist() {
         return artist;
     }

     public void setArtist(String artist) {
         this.artist = artist;
     }

     public String getAlbum() {
         return album;
     }

     public void setAlbum(String album) {
         this.album = album;
     }
 }