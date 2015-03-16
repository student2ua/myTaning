package com.tor.sun.misc;

import sun.misc.Signal;
import sun.misc.SignalHandler;

import java.util.concurrent.locks.Lock;

/**
 * User: tor
 * Date: 16.03.15
 * Time: 12:51
 * http://habrahabr.ru/post/78035/
 * Передо мной частенько вставала задача написать какой-нибудь Java-сервис. В качестве ОС мы используем по большей части
 * линукс, так что удобнее всего управляться с такими сервисами — работать с ними как с демонами. То есть, запускаем:
 * start-stop-daemon --start --make-pidfile --pidfile /var/run/myservice.pid --exec /usr/bin/java — -jar myservice.jar
 *
 * , и останавливаем:
 * start-stop-daemon --stop --quiet --oknodo --pidfile /var/run/myservice.pid
 *
 * Команда --stop посылает JVM сигнал SIGTERM и сервис останавливается. Все как бы неплохо, JVM завершается в штатном
 * порядке, если только вам не нужно выполнить по завершении работы сервиса какое-либо действие. Например явно освободить
 * ресурс, или написать что-нибудь приятное в stdout.
 */
public class DiagnosticSignalHandlerApp {
    // какой нибуть важный ресурс
    private static Lock resource;

    public static void main(String[] args) {
        SignalHandler signalHandler = new SignalHandler() {
            @Override
            public void handle(Signal sig) {
                resource.unlock();
            }
        };
        DiagnosticSignalHandler.install("TERM", signalHandler);
        DiagnosticSignalHandler.install("INT", signalHandler);
        DiagnosticSignalHandler.install("ABRT", signalHandler);
        resource.lock();
    }
}
