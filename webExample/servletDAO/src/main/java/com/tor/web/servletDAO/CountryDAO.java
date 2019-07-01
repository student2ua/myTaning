package com.tor.web.servletDAO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: tor
 * Date: 27.12.17
 * Time: 19:42
 */
public class CountryDAO implements DAO<Country, Integer> {

    private DataSource ds;

    public CountryDAO(DataSource dataSource) {
        this.ds = dataSource;
    }

    public List<Country> findAll() throws SQLException {
        List<Country> countries = new ArrayList<Country>();
        Connection connection = null;
        try {
            connection = ds.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT COUNTRYID, COUNTRYNAME, ISCHECK, SORTLEVEL,CAPITAL,SHORTNAME, CODE_ISO FROM DCT_COMMON.COUNTRY");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Country country = new Country();
                country.setCountryId(resultSet.getInt("COUNTRYID"));
                country.setCountryName(resultSet.getString("COUNTRYNAME"));
                country.setCheck(resultSet.getBoolean("ISCHECK"));
                country.setSortLevel(resultSet.getInt("SORTLEVEL"));
                country.setCapital(resultSet.getString("CAPITAL"));
                country.setShortName(resultSet.getString("SHORTNAME"));
                country.setCodeIso(resultSet.getInt("CODE_ISO"));
                countries.add(country);
            }
        } finally {
            if (connection != null) connection.close();
        }
        return countries;
    }

    public Country find(Integer integer) throws SQLException {
        Connection connection = null;
        try {
            connection = ds.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT COUNTRYID, COUNTRYNAME, ISCHECK, SORTLEVEL,CAPITAL,SHORTNAME, CODE_ISO FROM DCT_COMMON.COUNTRY WHERE COUNTRYID = ?");
            statement.setInt(1, integer);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Country country = new Country();
                country.setCountryId(resultSet.getInt("COUNTRYID"));
                country.setCountryName(resultSet.getString("COUNTRYNAME"));
                country.setCheck(resultSet.getBoolean("ISCHECK"));
                country.setSortLevel(resultSet.getInt("SORTLEVEL"));
                country.setCapital(resultSet.getString("CAPITAL"));
                country.setShortName(resultSet.getString("SHORTNAME"));
                country.setCodeIso(resultSet.getInt("CODE_ISO"));
                return country;
            }
        } finally {
            if (connection != null) connection.close();
        }
        return null;
    }

    public boolean save(Country country) throws Exception {
        String sql = "INSERT INTO DCT_COMMON.COUNTRY (COUNTRYID, COUNTRYNAME, ISCHECK, SORTLEVEL, CAPITAL, SHORTNAME, CODE_ISO) VALUES " +
                "(DCT_COMMON.COUNTRY_SEQ.nextval, ?, ?, ?, ?, ?, ?)";
        boolean rez = false;
        Connection connection = null;
        try {
            connection = ds.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
//            statement.setInt(1, country.getCountryId());
            statement.setString(1, country.getCountryName());
            statement.setInt(2, country.getCheck() ? 1 : 0);
            statement.setInt(3, country.getSortLevel());
            statement.setString(4, country.getCapital());
            statement.setString(5, country.getShortName());
            statement.setInt(6, country.getCodeIso());

            System.err.println("country = " + country);

            int resultSet = statement.executeUpdate();
            rez = resultSet > 0;
        } finally {
            if (connection != null) connection.close();
        }
        return rez;
    }

    @Override
    public boolean update(Country country) throws Exception {
        String sql = "UPDATE DCT_COMMON.COUNTRY SET COUNTRYNAME =?, ISCHECK = ?, SORTLEVEL = ?, CAPITAL = ?, SHORTNAME = ?, CODE_ISO = ? WHERE COUNTRYID = ?";
        boolean rez = false;
        Connection connection = null;
        try {
            connection = ds.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, country.getCountryName());
            statement.setInt(2, country.getCheck() ? 1 : 0);
            statement.setInt(3, country.getSortLevel());
            statement.setString(4, country.getCapital());
            statement.setString(5, country.getShortName());
            statement.setInt(6, country.getCodeIso());
            statement.setInt(7, country.getCountryId());

            System.err.println("update.country = " + country);

            int resultSet = statement.executeUpdate();
            rez = resultSet > 0;
        } finally {
            if (connection != null) connection.close();
        }
        return rez;
    }

    @Override
    public boolean delete(Country country) throws Exception {
        return delete(country.getCountryId());
    }

    public boolean delete(Integer pk) throws Exception {
        Connection connection = null;
        try {
            connection = ds.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM  DCT_COMMON.COUNTRY WHERE COUNTRYID = ?");
            statement.setInt(1, pk);
            int resultSet = statement.executeUpdate();
            return resultSet > 0;
        } finally {
            if (connection != null) connection.close();
        }
    }
}
