package ch.fhnw.ht.eit.pro2.team3.monkeypid;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @author Alex Murray
 */
public class Assets {
    private static Assets instance = null;
    private static String RESOURCE_PREFIX =
            pathToPlatform("../resources/ch/fhnw/ht/eit/pro2/team3/monkeypid/");

    private static String pathToPlatform(String path) {
        return String.join(File.separator, path.split("/"));
    }

    protected Assets() {
        // Singleton
    }

    public static Assets get() {
        if(instance == null) {
            instance = new Assets();
        }
        return instance;
    }

    public URL getResourceURL(String relativePath) throws IOException {
        String path = RESOURCE_PREFIX +
                File.separator +
                pathToPlatform(relativePath);
        return Assets.class.getResource(path);
    }
}
