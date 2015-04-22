package com.tor.util.concurrent.exchanger;

import java.util.concurrent.Exchanger;

/**
 * User: tor
 * Date: 22.04.15
 * Time: 16:16
 * http://tutorials.jenkov.com/java-util-concurrent/exchanger.html
 * <p> основное предназначение данного класса — это обмен объектами между двумя потоками. При этом, также
 * поддерживаются null значения, что позволяет использовать данный класс для передачи только одного объекта или же просто
 * как синхронизатор двух потоков. Первый поток, который вызывает метод exchange(...) заблокируется до тех пор, пока тот
 * же метод не вызовет второй поток. Как только это произойдет, потоки обменяются значениями и продолжат свою работу.</p>
 */
public class ExchangerRunnableMain {
    public static void main(String[] args) {
        Exchanger exchanger = new Exchanger();
        ExchangerRunnable er1 = new ExchangerRunnable(exchanger, "A");  //Producer
        ExchangerRunnable er2 = new ExchangerRunnable(exchanger, "B");  //Consumer
        new Thread(er1).start();
        new Thread(er2).start();
    }

}
