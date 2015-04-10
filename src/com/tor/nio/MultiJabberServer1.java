package com.tor.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * Сервер принимает соединения не блокирующим способом. Когда соединение
 * установлено, создается сокет, который регистрируется с селектором для
 * чтения/записи. Чтение/запись выполняется над этим сокетом, когда селектор
 * разблокируется. Эта программа работает точно так же, как и MultiJabberServer.
 */
public class MultiJabberServer1 {
    public static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        // Канал будет читать данные в ByteBuffer, посылаемые
        // методом PrintWriter.println(). Декодирование этого потока
        // байт требует кодовой страницы для кодировки по умолчанию.
        String encoding = System.getProperty("file.encoding");
        // Инициализируем здесь, так как мы не хотим создавать новый
        // экземпляр кодировки каждый раз, когда это необходимо
        // Charset cs = Charset.forName(
        // System.getProperty("file.encoding"));
        Charset cs = Charset.forName(encoding);
        ByteBuffer buffer = ByteBuffer.allocate(16);
        SocketChannel ch = null;
        ServerSocketChannel ssc = ServerSocketChannel.open();
        Selector sel = Selector.open();
        try {
            ssc.configureBlocking(false);
            // Локальныйы адрес, на котором он будет слушать соединения
            // Примечание: Socket.getChannel() возвращает null, если с ним не
            // ассоциирован канал, как показано ниже.
            // т.е выражение (ssc.socket().getChannel() != null) справедливо
            ssc.socket().bind(new InetSocketAddress(PORT));
            // Канал заинтересован в событиях OP_ACCEPT
            SelectionKey key = ssc.register(sel, SelectionKey.OP_ACCEPT);
            System.out.println("Server on port: " + PORT);
            while (true) {
                sel.select();
                Iterator it = sel.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey skey = (SelectionKey) it.next();
                    it.remove();
                    if (skey.isAcceptable()) {
                        ch = ssc.accept();
                        System.out.println("Accepted connection from:"
                                + ch.socket());
                        ch.configureBlocking(false);
                        ch.register(sel, SelectionKey.OP_READ);
                    } else {
                        // Обратите внимание, что не выполняется проверка, если
                        // в канал
                        // можно писать или читать - для упрощения.
                        ch = (SocketChannel) skey.channel();
                        ch.read(buffer);
                        CharBuffer cb = cs.decode((ByteBuffer) buffer.flip());
                        String response = cb.toString();
                        System.out.print("Echoing : " + response);
                        ch.write((ByteBuffer) buffer.rewind());
                        if (response.indexOf("END") != -1)
                            ch.close();
                        buffer.clear();
                    }
                }
            }
        }
        finally {
            if (ch != null)
                ch.close();
            ssc.close();
            sel.close();
        }
    }
}