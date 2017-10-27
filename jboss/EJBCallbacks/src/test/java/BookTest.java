import com.tor.entity.Book;
import com.tor.entity.Publisher;
import com.tor.stateless.LibraryPersistentBeanRemote;
import com.tor.timer.TimerSessionBeanRemote;
import org.junit.Before;
import org.junit.Test;

import javax.jms.*;
import javax.naming.InitialContext;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * User: tor
 * Date: 04.07.14
 * Time: 14:09
 * To change this template use File | Settings | File Templates.
 */
public class BookTest {
    InitialContext context;
    private BufferedReader brConsoleReader;

    @Before
    public void setUp() throws Exception {
        Properties properties = new Properties();
        properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("jndi.properties"));
//        properties.load(this.getClass().getResourceAsStream("jndi.properties"));
        context = new InitialContext(properties);
        brConsoleReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Test
    public void testMessageEJB() throws Exception {
        try {
            Queue queue = (Queue) context.lookup("/queue/BookQueue");
            QueueConnectionFactory factory = (QueueConnectionFactory) context.lookup("ConnectionFactory");
            QueueConnection connection = factory.createQueueConnection();
            QueueSession session = connection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
            QueueSender sendler = session.createSender(queue);

            int choice = 1;
            while (choice != 2) {
                String bookName;
                String str = brConsoleReader.readLine();
                choice = Integer.parseInt(str);
                if (choice == 1) {
                    System.out.printf("new Name:");
                    bookName = brConsoleReader.readLine();
                    System.out.print("Enter publisher name: ");
                    String publisherName = brConsoleReader.readLine();
                    System.out.print("Enter publisher address: ");
                    String publisherAddress = brConsoleReader.readLine();
                    Book book = new Book();
                    book.setName(bookName);
                    book.setPublisher
                            (new Publisher(publisherName, publisherAddress));

                    ObjectMessage objectMessage = session.createObjectMessage(book);
                    sendler.send(objectMessage);
                } else if (choice == 2) break;
            }
            LibraryPersistentBeanRemote libraryBean = (LibraryPersistentBeanRemote) context.lookup("LibraryPersistentBean/remote");
            List<Book> bookList = libraryBean.getBooks();
            System.out.println("bookList = " + bookList);
        } finally {
            if (brConsoleReader != null) brConsoleReader.close();
        }
    }

    @Test
    public void testEJB() throws Exception {
        try {
            LibraryPersistentBeanRemote libraryBean =
                    (LibraryPersistentBeanRemote)
                            context.lookup("LibraryPersistentBean/remote");

            int choice = 1;
            while (choice != 2) {
                String bookName;
                String str = brConsoleReader.readLine();
                choice = Integer.parseInt(str);
                if (choice == 1) {
                    System.out.printf("new Name:");
                    bookName = brConsoleReader.readLine();
                    System.out.print("Enter publisher name: ");
                    String publisherName = brConsoleReader.readLine();
                    System.out.print("Enter publisher address: ");
                    String publisherAddress = brConsoleReader.readLine();
                    Book book = new Book();
                    book.setName(bookName);
                    book.setPublisher
                            (new Publisher(publisherName, publisherAddress));

                    libraryBean.addBook(book);
                } else if (choice == 2) break;
            }
            List<Book> bookList = libraryBean.getBooks();
            System.out.println("bookList = " + bookList);
        } finally {
            if (brConsoleReader != null) brConsoleReader.close();
        }
    }

    @Test
    public void testTimerService() throws Exception {
        TimerSessionBeanRemote bean = (TimerSessionBeanRemote) context.lookup("TimerSessionBean/remote");
        System.out.println(new Date().toString() + " timer created");
        bean.createTimer(2000);
        //EJB Container calls the timeoutHandler method after 2 seconds
    }
}
