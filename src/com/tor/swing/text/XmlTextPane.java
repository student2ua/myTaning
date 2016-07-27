package com.tor.swing.text;

import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * User: tor
 * Date: 27.07.16
 * Time: 17:21
 * To change this template use File | Settings | File Templates.
 */
public class XmlTextPane extends JTextPane {
    private Logger logger = Logger.getLogger(XmlTextPane.class);

    public XmlTextPane() {
        this.setEditorKitForContentType("text/xml", new XmlEditorKit());
        this.setContentType("text/xml");
        addKeyListener(new IndentKeyListener());
    }

    private class IndentKeyListener implements KeyListener {
        private boolean enterFlag;
        private final Character NEW_LINE = '\n';

        public void keyPressed(KeyEvent event) {
            enterFlag = false;
            if ((event.getKeyCode() == KeyEvent.VK_ENTER)
                    && (event.getModifiers() == 0)) {
                if (getSelectionStart() == getSelectionEnd()) {
                    enterFlag = true;
                    event.consume();
                }
            }
        }

        public void keyReleased(KeyEvent event) {
            if ((event.getKeyCode() == KeyEvent.VK_ENTER)
                    && (event.getModifiers() == 0)) {
                if (enterFlag) {
                    event.consume();

                    int start, end;
                    String text = getText();

                    int caretPosition = getCaretPosition();
                    try {
                        if (text.charAt(caretPosition) == NEW_LINE) {
                            caretPosition--;
                        }
                    } catch (IndexOutOfBoundsException e) {
                    }

                    start = text.lastIndexOf(NEW_LINE, caretPosition) + 1;
                    end = start;
                    try {
                        if (text.charAt(start) != NEW_LINE) {
                            while ((end < text.length())
                                    && (Character
                                    .isWhitespace(text.charAt(end)))
                                    && (text.charAt(end) != NEW_LINE)) {
                                end++;
                            }
                            if (end > start) {
                                getDocument()
                                        .insertString(
                                                getCaretPosition(),
                                                NEW_LINE
                                                        + text.substring(start,
                                                        end), null);
                            } else {
                                getDocument().insertString(getCaretPosition(),
                                        NEW_LINE.toString(), null);
                            }
                        } else {
                            getDocument().insertString(getCaretPosition(),
                                    NEW_LINE.toString(), null);
                        }
                    } catch (IndexOutOfBoundsException e) {
                        try {
                            getDocument().insertString(getCaretPosition(),
                                    NEW_LINE.toString(), null);
                        } catch (BadLocationException e1) {
                            logger.warn(e1.toString(), e1);
                        }
                    } catch (BadLocationException e) {
                        logger.warn(e.toString(), e);
                    }
                }
            }
        }

        public void keyTyped(KeyEvent e) {
        }
    }
}
