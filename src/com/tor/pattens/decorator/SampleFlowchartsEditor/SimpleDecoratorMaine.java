package com.tor.pattens.decorator.SampleFlowchartsEditor;

import com.tor.pattens.decorator.SampleFlowchartsEditor.blockImpl.ProcessBlock;
import com.tor.pattens.decorator.SampleFlowchartsEditor.blockImpl.TerminatorBlock;
import com.tor.pattens.decorator.SampleFlowchartsEditor.decor.BorderBlockDecorator;
import com.tor.pattens.decorator.SampleFlowchartsEditor.decor.LineBlockDecorator;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 04.03.2010
 * Time: 13:57:12
 * To change this template use File | Settings | File Templates.
 */
public class SimpleDecoratorMaine {
    private static final Logger log = Logger.getLogger(SimpleDecoratorMaine.class);

    public static void main(String[] args) {
        TerminatorBlock terminatorBlock = new TerminatorBlock();
        terminatorBlock.draw();
        System.out.println("----------------------------------");
        ProcessBlock processBlock = new ProcessBlock();
        processBlock.draw();
        System.out.println("----------------------------------");

        LineBlockDecorator lineBlockDecorator = new LineBlockDecorator(processBlock);
        BorderBlockDecorator borderBlockDecorator = new BorderBlockDecorator(lineBlockDecorator);
        lineBlockDecorator.draw();
        System.out.println("----------------------------------");
        borderBlockDecorator.draw();
        System.out.println("----------------------------------");

        BorderBlockDecorator borderBlockDecorator2 = new BorderBlockDecorator(terminatorBlock);
        borderBlockDecorator2.draw();


    }
}
