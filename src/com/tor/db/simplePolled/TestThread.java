package com.tor.db.simplePolled;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 22.09.13
 * Time: 12:14
 * To change this template use File | Settings | File Templates.
 */
class TestThread extends Thread {
    private DBPool pool;
    private long workTime = 0;
    private long foundStr = 0;

    TestThread(DBPool pool) {
        this.pool = pool;
    }

    @Override
    public void run() {
        workTime = System.currentTimeMillis(); // Засекаем время
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        Random rnd = new Random();// будем использовать как первичный ключ в запросе
        for (int i = 0; i < 100; i++) {
            try {
                con = pool.getConnection();// получем соединение к БД
                // отправляем запрос на парсинг и построение плана выполнения
                st = con.prepareStatement("SELECT a.*  FROM test.test_table a WHERE id =?");
                st.setObject(1, rnd.nextInt(10));
                rs = st.executeQuery();// выполняем запрос
                if (rs.next()) {
                    String tmp = (rs.getString(2));   // обрабатываем результат
                    if (tmp != null) {
                        foundStr++;
                    }
                }
            } catch (SQLException ex) {
                //ошибка при выполнении, выводим в консоль
                System.out.println("Pool " + pool + " exeption " + ex);
            } finally {
                // и в конце, аккуратно закрываем все использованные нами объекты
                try {
                    if (rs != null)
                        rs.close();
                } catch (SQLException ignore) {
                }
                try {
                    if (st != null)
                        st.close();
                } catch (SQLException ignore) {
                }
                try {
                    if (con != null)
                        pool.putConnection(con); // кладем соединение обратно в пул
                } catch (SQLException ignore) {
                }
            }
        }
        workTime = System.currentTimeMillis() - workTime; // получаем потраченное время
    }

}


