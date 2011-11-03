package com.tor.pattens.decorator.SampleFlowchartsEditor.blockImpl;

import org.apache.log4j.Logger;
import com.tor.pattens.decorator.SampleFlowchartsEditor.AbstractBlock;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 04.03.2010
 * Time: 13:37:49
 * Терминальный блок (начало/конец, вход/выход)
 */
public class TerminatorBlock implements AbstractBlock {
    private static final Logger log = Logger.getLogger(TerminatorBlock.class);

    public void draw() {
        System.out.println("TerminatorBlock.draw");
    }
}
