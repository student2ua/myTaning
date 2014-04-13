package com.tor.mock_test;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
//import org.jmock.cglib.MockObjectTestCase;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 02.02.2010
 * Time: 19:37:29
 */
public class TestMockClass2 extends MockObjectTestCase {

    public static interface IListener {
        void eventOut(String arg);
    }

    public static class TestedClass {
        IListener listener;

        public TestedClass(IListener listener) {
            this.listener = listener;
        }

        public void eventIn(String arg) {
            listener.eventOut(arg);
        }
    }

    TestedClass testedClass = null;
    Mock mock = new Mock(IListener.class);

    public void test1() {
        final IListener iListener = (IListener) mock.proxy();
        testedClass = new TestedClass(iListener);

        /**in 2 version
         *  checking(new Expectations() {{
         oneOf(ltr).eventOut("testtest");
         }});
         testedClass.eventIn("test");
         mockery.assertIsSatisfied();*/

    }

    public void test2() {
      /** in 2 version
       *  final IListener ltr = mockery.mock(IListener.class);
        tc = new TestedClass(ltr);
        mockery.checking(new Expectations() {
            {
                oneOf(ltr).eventOut(with(containsString("test")));
            }
        });
        tc.eventIn("test");
        mockery.assertIsSatisfied();*/
    }
}
