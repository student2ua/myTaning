package com.tor.se;

import java.util.*;

/**
 * User: tor
 * Date: 05.09.16
 * Time: 21:21
 */
public class ReportRenderer {
    private static final ServiceLoader<IMusicFinder> SERVICE_LOADER = ServiceLoader.load(IMusicFinder.class);

    public void generateReport() {
        final List<String> music = findMusic();
        for (String composition : music) {
            System.out.println(composition);
        }
    }

    public List<String> findMusic() {
        final List<String> music = new ArrayList<String>();
        synchronized (SERVICE_LOADER) {
            for (IMusicFinder finder : SERVICE_LOADER) {
                music.addAll(finder.getMusic());
            }
        }
        Collections.sort(music);
        return music;
    }

    public static ReportRenderer getInstance() {
        final Iterator<ReportRenderer> providers = ServiceLoader.load(ReportRenderer.class).iterator();
        if (providers.hasNext()) {
            return providers.next();
        }
        return new ReportRenderer();
    }

    public static void main(String[] args) {
        final ReportRenderer renderer = ReportRenderer.getInstance();
        renderer.generateReport();
    }
}
