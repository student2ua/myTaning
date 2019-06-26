package com.tor.web.servletDAO;

/**
 * User: Admin
 * Date: 27.12.17
 * Time: 19:41
 * CREATE TABLE COUNTRY
 (
 COUNTRYID NUMBER(38,0) PRIMARY KEY NOT NULL,
 COUNTRYNAME VARCHAR2(100),
 ISCHECK INT DEFAULT 0 NOT NULL,
 SORTLEVEL INT DEFAULT 0,
 CAPITAL VARCHAR2(100),
 SHORTNAME VARCHAR2(10),
 CODE_ISO INT
 );
 */
public class Country {
    private Integer countryId;
    private String countryName;
    private Boolean isCheck;
    private Integer sortLevel;
    private String capital;
    private String shortName;
    private Integer codeIso;

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Boolean getCheck() {
        return isCheck;
    }

    public void setCheck(Boolean check) {
        isCheck = check;
    }

    public Integer getSortLevel() {
        return sortLevel;
    }

    public void setSortLevel(Integer sortLevel) {
        this.sortLevel = sortLevel;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Integer getCodeIso() {
        return codeIso;
    }

    public void setCodeIso(Integer codeIso) {
        this.codeIso = codeIso;
    }
}
