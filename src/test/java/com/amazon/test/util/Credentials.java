package com.amazon.test.util;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * Class providing utility method related to user credentials.
 */
public class Credentials {
    /**
     * Decodes a Base64 encoded string.
     *
     * @param encoded base64 encoded string
     * @return decoded string
     */
    public static String decode(String encoded) {
        return new String(Base64.decode(encoded));
    }
}
