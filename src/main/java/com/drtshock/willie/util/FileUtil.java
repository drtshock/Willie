package com.drtshock.willie.util;

import com.drtshock.willie.Willie;

import java.io.File;
import java.net.URISyntaxException;

public class FileUtil {
    public static File getWillieDirectory() throws URISyntaxException {
        return new File(Willie.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile();
    }
}
