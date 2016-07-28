package io.github.soiff.extension.aether;

/**
 * Created by zhangh on 16-7-28.
 *
 * @author : zhangh
 * @version : 1.0.0
 * @since : 1.8
 */
public class AetherDetector {

    public static final String PROP_EXTERNAL_LOADER = "external.loader";

    private static final boolean CONFIG_SKIP_CHECKSUMS;

    static {
        CONFIG_SKIP_CHECKSUMS = ( System.getenv().containsKey( PROP_EXTERNAL_LOADER )
            ? ( null == System.getenv().get( PROP_EXTERNAL_LOADER ) ? true
            : Boolean.valueOf(System.getenv().get( PROP_EXTERNAL_LOADER )))
            : ( System.getProperties().containsKey( PROP_EXTERNAL_LOADER )
            ? ( null == System.getProperties().get( PROP_EXTERNAL_LOADER ) ? Boolean.TRUE
            : Boolean.valueOf(System.getProperties().getProperty( PROP_EXTERNAL_LOADER )))
            : false ));
    }

    public static boolean shouldSkipChecksums() {
        return CONFIG_SKIP_CHECKSUMS;
    }
}
