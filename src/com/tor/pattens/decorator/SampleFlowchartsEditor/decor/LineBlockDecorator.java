package com.tor.pattens.decorator.SampleFlowchartsEditor.decor;

import com.tor.pattens.decorator.SampleFlowchartsEditor.AbstractBlock;
import com.tor.pattens.decorator.SampleFlowchartsEditor.AbstractBlockDecorator;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 04.03.2010
 * Time: 13:47:07
 * To change this template use File | Settings | File Templates.
 */
public class LineBlockDecorator extends AbstractBlockDecorator {
    private static final Logger log = Logger.getLogger(LineBlockDecorator.class);
    String label = "LineBlockkDecorator.drawLine";

    public LineBlockDecorator(AbstractBlock abstractBlock) {
        super(abstractBlock);
    }

    public void draw() {
        super.draw();
        drawLine();
    }

    private void drawLine() {
        System.out.println(label);
    }
}
