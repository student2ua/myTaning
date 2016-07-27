package com.tor.swing.text;

import javax.swing.text.Element;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;

/**
 * User: tor
 * Date: 27.07.16
 * Time: 16:10
 * To change this template use File | Settings | File Templates.
 */
public class XmlViewFactory implements ViewFactory {
    /**
     * @see javax.swing.text.ViewFactory#create(javax.swing.text.Element)
     */
    @Override
    public View create(Element elem) {
        return new XmlView(elem);
    }
}
