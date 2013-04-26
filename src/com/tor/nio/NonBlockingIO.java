package com.tor.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Цель: Показать как использовать селектор. Нет чтения/записи, просто
 * показывается готовность к совершению операции.
 * <p/>
 * Алгоритм: -> Создаем селектор. -> Создаем канал -> Связываем сокет,
 * ассоциированный с каналом, с <клиентским портом> -> Конфигурируем канал, как
 * не блокирующий -> Регестрируем канал в селекторе. -> Вызываем метод select( ),
 * чтобы он блокировал выполнение до тех пор, пока канал не будет готов. (как
 * это предполагается методом select(long timeout) -> Получаем множество ключей,
 * относящихся к готовому каналу для работы, основной интерес состоит в том,
 * когда они зарегестрированя с помощью селектора. -> Перебираем ключи. -> Для
 * каждого ключа проверяем, что соответствующий канал готов к работе, в которой
 * он заинтересован. -> Если он готов, печатаем сообщение о готовности.
 * <p/>
 * Примечание: -> Необходим запущенный MultiJabberServer на локальной машине. Вы
 * запускаете его и соединяетесь с локальным MultiJabberServer -> Он может стать
 * причиной исключения в MultiJabberServer, но это исключение ожидаемо.
 */
public class NonBlockingIO {
    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Usage: java <client port> <local server port>");
            System.exit(1);
        }
        int cPort = Integer.parseInt(args[0]);
        int sPort = Integer.parseInt(args[1]);
        SocketChannel ch = SocketChannel.open();
        Selector sel = Selector.open();
        try {
            ch.socket().bind(new InetSocketAddress(cPort));
            ch.configureBlocking(false);
            // Канал заинтересован в выполнении чтения/записи/соединении
            ch.register(sel, SelectionKey.OP_READ | SelectionKey.OP_WRITE
                    | SelectionKey.OP_CONNECT);
            // Разблокируем, когда готовы к чтению/записи/соединению
            sel.select();
            // Ключи, относящиеся к готовому каналу, канал заинтересован
            // в работе, которая может быть выполненаin can be
            // без блокирования.
            Iterator it = sel.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey key = (SelectionKey) it.next();
                it.remove();
                // Если связанный с ключом канал готов к соединению?
                // if((key.readyOps() & SelectionKey.OP_CONNECT) != 0) {
                if (key.isConnectable()) {
                    InetAddress ad = InetAddress.getLocalHost();
                    System.out.println("Connect will not block");
                    // Вы должны проверить возвращаемое значение,
                    // чтобы убедиться, что он соединен. Этот не блокированный
                    // вызов может вернуться без соединения, когда
                    // нет сервера, к которому вы пробуете подключиться
                    // Поэтому вы вызываете finishConnect(), который завершает
                    // операцию соединения.
                    if (!ch.connect(new InetSocketAddress(ad, sPort)))
                        ch.finishConnect();
                }
                // Если канал, связанный с ключом, готов к чтению?
                // if((key.readyOps() & SelectionKey.OP_READ) != 0)
                if (key.isReadable())
                    System.out.println("Read will not block");
                // Готов ли канал, связанный с ключом, к записи?
                // if((key.readyOps() & SelectionKey.OP_WRITE) != 0)
                if (key.isWritable())
                    System.out.println("Write will not block");
            }
        }
        finally {
            ch.close();
            sel.close();
        }
    }
}