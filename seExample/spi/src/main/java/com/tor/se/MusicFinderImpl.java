package com.tor.se;

import java.util.Collections;
import java.util.List;

/**
 * User: tor
 * Date: 05.09.16
 * Time: 21:20
 * https://habrahabr.ru/post/118488/
 */
public class MusicFinderImpl implements IMusicFinder {
    @Override
    public List<String> getMusic() {
        return Collections.singletonList("simple");
    }
}
