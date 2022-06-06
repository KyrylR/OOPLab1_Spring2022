package ua.univ.utils;

import java.net.URI;
import java.net.URISyntaxException;

public class ServletUtils {
    private ServletUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static int getURIId(String uriStr) throws URISyntaxException {
        URI uri = new URI(uriStr);
        String[] segments = uri.getPath().split("/");
        int id = -1;
        // TODO: fix problem with larger requests
        if (segments.length > 4) throw new URISyntaxException(uriStr, "The path to the uri is too long");
        else if (segments.length == 4) {
            String idStr = segments[segments.length - 1];
            id = Integer.parseInt(idStr);
        }
        return id;
    }
}
