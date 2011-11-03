package com.tor.pattens.decorator.SampleFlowchartsEditor;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 04.03.2010
 * Time: 13:43:58
 * ���������� ��������� ������
 */
public abstract class AbstractBlockDecorator implements AbstractBlock {
    private AbstractBlock abstractBlock;

    public AbstractBlockDecorator(AbstractBlock abstractBlock) {
        this.abstractBlock = abstractBlock;
    }

    public void draw() {
        abstractBlock.draw();
    }
}
