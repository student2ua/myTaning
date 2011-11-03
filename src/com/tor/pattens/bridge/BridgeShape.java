package com.tor.pattens.bridge;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 01.03.2010
 * Time: 14:29:31
 * "Abstraction".
 */
public interface BridgeShape {

    public void draw();                             // low-level

    public void resizeByPercentage(double pct);     // high-level
}
