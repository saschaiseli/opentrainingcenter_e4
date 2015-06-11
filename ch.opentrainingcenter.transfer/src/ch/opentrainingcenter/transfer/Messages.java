package ch.opentrainingcenter.transfer;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
    private static final String BUNDLE_NAME = "ch.opentrainingcenter.transfer.messages"; //$NON-NLS-1$
    public static String CommonTransferFactory_0;
    public static String Sport_BIKE;
    public static String Sport_Joggen;
    public static String Sport_OTHER;
    public static String Sport_RUNNING;
    public static String Sport_unbekannt;
    public static String Sport_Velo;
    static {
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
