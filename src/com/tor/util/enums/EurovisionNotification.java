package com.tor.util.enums;

/** The policy on how to notify the user of any Eurovision song contest event */
public enum EurovisionNotification {

 /** I love Eurovision, don't want to miss it, never! */
 ALWAYS() {
  @Override
  public boolean mustNotify(String eventCity, String userCity) {
   return true;
  }
 },

 /**
  * I only want to know about Eurovision if it takes place in my city, so
  * that I can take holidays elsewhere at the same time
  */
 ONLY_IF_IN_MY_CITY() {
  // a case of flyweight pattern since we pass all the extrinsi data as
  // arguments instead of storing them as member data
  @Override
  public boolean mustNotify(String eventCity, String userCity) {
   return eventCity.equalsIgnoreCase(userCity);
  }
 },

 /** I don't care, I don't want to know */
 NEVER() {
  @Override
  public boolean mustNotify(String eventCity, String userCity) {
   return false;
  }
 };

 // no default behavior
 public abstract boolean mustNotify(String eventCity, String userCity);

}
