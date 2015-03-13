package ch.fhnw.ht.eit.pro2.team3.monkeypid;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;

/**
 * @author Alex Murray
 */
public class Assets {
    private static Assets instance = null;
    private static String RESOURCE_PREFIX = "/ch/fhnw/ht/eit/pro2/team3/monkeypid/";

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
        return getClass().getResource(RESOURCE_PREFIX + relativePath);
    }
}
