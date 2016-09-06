package com.tor.se;

import java.util.Collections;
import java.util.List;

/**
 * User: tor
 * Date: 05.09.16
 * Time: 21:53
 * To change this template use File | Settings | File Templates.
 */
public class ExtendedMusicFinder implements IMusicFinder {
    @Override
    public List<String> getMusic() {
        return Collections.singletonList("From Extended");
    }
}
