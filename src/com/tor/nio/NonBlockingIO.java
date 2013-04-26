package com.tor.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * ����: �������� ��� ������������ ��������. ��� ������/������, ������
 * ������������ ���������� � ���������� ��������.
 * <p/>
 * ��������: -> ������� ��������. -> ������� ����� -> ��������� �����,
 * ��������������� � �������, � <���������� ������> -> ������������� �����, ���
 * �� ����������� -> ������������ ����� � ���������. -> �������� ����� select( ),
 * ����� �� ���������� ���������� �� ��� ���, ���� ����� �� ����� �����. (���
 * ��� �������������� ������� select(long timeout) -> �������� ��������� ������,
 * ����������� � �������� ������ ��� ������, �������� ������� ������� � ���,
 * ����� ��� ���������������� � ������� ���������. -> ���������� �����. -> ���
 * ������� ����� ���������, ��� ��������������� ����� ����� � ������, � �������
 * �� �������������. -> ���� �� �����, �������� ��������� � ����������.
 * <p/>
 * ����������: -> ��������� ���������� MultiJabberServer �� ��������� ������. ��
 * ���������� ��� � ������������ � ��������� MultiJabberServer -> �� ����� �����
 * �������� ���������� � MultiJabberServer, �� ��� ���������� ��������.
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
            // ����� ������������� � ���������� ������/������/����������
            ch.register(sel, SelectionKey.OP_READ | SelectionKey.OP_WRITE
                    | SelectionKey.OP_CONNECT);
            // ������������, ����� ������ � ������/������/����������
            sel.select();
            // �����, ����������� � �������� ������, ����� �������������
            // � ������, ������� ����� ���� ���������in can be
            // ��� ������������.
            Iterator it = sel.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey key = (SelectionKey) it.next();
                it.remove();
                // ���� ��������� � ������ ����� ����� � ����������?
                // if((key.readyOps() & SelectionKey.OP_CONNECT) != 0) {
                if (key.isConnectable()) {
                    InetAddress ad = InetAddress.getLocalHost();
                    System.out.println("Connect will not block");
                    // �� ������ ��������� ������������ ��������,
                    // ����� ���������, ��� �� ��������. ���� �� �������������
                    // ����� ����� ��������� ��� ����������, �����
                    // ��� �������, � �������� �� �������� ������������
                    // ������� �� ��������� finishConnect(), ������� ���������
                    // �������� ����������.
                    if (!ch.connect(new InetSocketAddress(ad, sPort)))
                        ch.finishConnect();
                }
                // ���� �����, ��������� � ������, ����� � ������?
                // if((key.readyOps() & SelectionKey.OP_READ) != 0)
                if (key.isReadable())
                    System.out.println("Read will not block");
                // ����� �� �����, ��������� � ������, � ������?
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