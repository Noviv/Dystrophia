package org.noviv.dystcore.accessories.utilities;

import java.io.File;

public class DystIO {

    public static File ioResourceToFile(String resource) {
        return new File(Thread.currentThread().getContextClassLoader().getResource(resource).getFile());
    }
}
