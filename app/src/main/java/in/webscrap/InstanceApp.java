package in.webscrap;

import android.app.Application;

/**
 * Created by Yash Dani on 4/2/2017.
 */

public class InstanceApp extends Application {

    private static InstanceApp instanceApp;

    public void onCreate() {
        super.onCreate();

        instanceApp = this;
    }

    public static synchronized InstanceApp getInstanceApp() {
        return instanceApp;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

}
