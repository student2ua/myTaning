package obuchenie.xmlmenu.exemple;
// TestXMLMenuLoader.java
// �������� �������� ������� ���� �� ����� XML


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class TestXMLMenuLoader extends JFrame {
    private static final String MENU_XML = "obuchenie\\menu.xml";
    private File file = new File(this.getClass().getClassLoader().getResource("").getFile() + MENU_XML);

    public TestXMLMenuLoader() {
        super("TestXMLMenuLoader");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // ��������� ���� XML � ��������� ����
        try {
            InputStream stream =
                    new FileInputStream(file);
            // ��������� ����
            XMLMenuLoader loader =
                    new XMLMenuLoader(stream);
            loader.parse();
            // ������������� ������ ����
            setJMenuBar(loader.getMenuBar("mainMenu"));
            // ������� ������������� ���������
            loader.addActionListener("exit",
                    new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            System.exit(0);
                        }
                    });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // ������� ���� �� �����
        setSize(300, 200);
        setVisible(true);
    }

    public static void main(String[] args) {
        new TestXMLMenuLoader();
    }
}