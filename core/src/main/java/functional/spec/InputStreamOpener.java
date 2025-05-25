package functional.spec;

import java.io.IOException;
import java.io.InputStream;

@FunctionalInterface
public interface InputStreamOpener {
    InputStream getStream(String path) throws IOException;
}
