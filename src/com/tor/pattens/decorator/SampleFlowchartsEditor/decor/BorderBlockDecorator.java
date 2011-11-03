package com.tor.pattens.decorator.SampleFlowchartsEditor.decor;

import com.tor.pattens.decorator.SampleFlowchartsEditor.AbstractBlockDecorator;
import com.tor.pattens.decorator.SampleFlowchartsEditor.AbstractBlock;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 04.03.2010
 * Time: 13:51:35
 * To change this template use File | Settings | File Templates.
 */
public class BorderBlockDecorator extends AbstractBlockDecorator {
    private static final Logger log = Logger.getLogger(BorderBlockDecorator.class);
    private int borderSize = 2;

    public BorderBlockDecorator(AbstractBlock abstractBlock) {
        super(abstractBlock);
    }

    public void draw() {
        super.draw();
        drawBorder();
    }

    private void drawBorder() {
        System.out.println("BorderBlockDecorator.drawBorder size=" + borderSize);
    }
}
