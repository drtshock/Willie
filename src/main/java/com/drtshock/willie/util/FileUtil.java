package com.drtshock.willie.util;

import com.drtshock.willie.Willie;

import java.io.File;
import java.net.URISyntaxException;

/**
 * Useful methods to prevent code repetition with IO.
 */
public class FileUtil {
    /**
     * Gets the directory that the Willie JAR archive is in.
     *
     * @return the directory as a File object
     * @throws URISyntaxException if the JAR archive is at an invalid path
     */
    public static File getWillieDirectory() throws URISyntaxException {
        return new File(Willie.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile();
    }
}
