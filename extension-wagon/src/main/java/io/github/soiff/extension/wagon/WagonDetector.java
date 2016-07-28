package io.github.soiff.extension.wagon;

import java.io.File;
import java.io.IOException;

/**
 * Created by zhangh on 16-7-28.
 *
 * @author : zhangh
 * @version : 1.0.0
 * @since : 1.8
 */
public class WagonDetector {

    static final String EXTERNAL_LOADER;
    static final String AXEL_STATE_EXTENSION = ".st";

    static {
        final String keyDownloaderPath = "external.loader";
        EXTERNAL_LOADER = ( null == System.getProperty ( keyDownloaderPath )
            ? System.getenv ().get ( keyDownloaderPath ) : System.getProperty ( keyDownloaderPath ) );
    }

    public static String getAxelPath (File output, String url )
    {
        return output.getParent() + File.separatorChar + url.substring( url.lastIndexOf( "/" ) + 1 );
    }

    public static File getAxelStateFile ( File output, String url )
    {
        return new File( getAxelPath( output, url ) + File.separatorChar + AXEL_STATE_EXTENSION );
    }

    public static File getAxelFile( File output, String url )
    {
        return new File( getAxelPath( output, url ) );
    }

    protected void getTransfer( Resource resource, String url, File destination )
        throws TransferFailedException
    {
        // ensure that the destination is created only when we are ready to transfer
        fireTransferDebug( "attempting to create parent directories for destination: " + destination.getName() );
        createParentDirectories( destination );

        fireGetStarted( resource, destination );

        if ( null == EXTERNAL_LOADER || EXTERNAL_LOADER.length() <= 0 )
        {
            throw new RuntimeException( "Invalid external format" );
        }

        try
        {
            transfer ( resource, url, destination, TransferEvent.REQUEST_GET );
        }
        catch ( final IOException e )
        {
            if ( destination.exists() )
            {
                boolean deleted = destination.delete();

                if ( !deleted )
                {
                    destination.deleteOnExit();
                }
            }

            fireTransferError( resource, e, TransferEvent.REQUEST_GET );

            String msg = "GET request of: " + resource.getName() + " from " + repository.getName() + " failed";

            throw new TransferFailedException( msg, e );
        }

        fireGetCompleted( resource, destination );
    }
}
