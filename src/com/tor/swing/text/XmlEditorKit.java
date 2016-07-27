package com.tor.swing.text;

import javax.swing.text.StyledEditorKit;
import javax.swing.text.ViewFactory;

/**
 * User: tor
 * Date: 27.07.16
 * Time: 16:08
 */
public class XmlEditorKit extends StyledEditorKit {
    private ViewFactory factory;

    public XmlEditorKit() {
        factory = new XmlViewFactory();
    }

    @Override
    public ViewFactory getViewFactory() {
        return factory;
    }

    @Override
    public String getContentType() {
        return "text/xml";
    }
}
