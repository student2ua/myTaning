package com.tor.pattens.decorator.SampleFlowchartsEditor.blockImpl;

import org.apache.log4j.Logger;
import com.tor.pattens.decorator.SampleFlowchartsEditor.AbstractBlock;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 04.03.2010
 * Time: 13:41:04
 * Блок - процесс (один или несколько операторов)
 */
public class ProcessBlock implements AbstractBlock {
    private static final Logger log = Logger.getLogger(ProcessBlock.class);

    public void draw() {
        System.out.println("ProcessBlock.draw");
    }
}
