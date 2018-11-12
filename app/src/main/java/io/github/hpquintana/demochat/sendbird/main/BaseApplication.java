package io.github.hpquintana.demochat.sendbird.main;


import android.app.Application;
import com.sendbird.android.SendBird;
import io.github.hpquintana.demochat.sendbird.utils.PreferenceUtils;

public class BaseApplication extends Application {

    public static final String VERSION = "3.0.40";
    private static final String APP_ID = "A6805046-D030-4FAC-9BC4-EDE33BADECD6"; // US-1 Demo

    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceUtils.init(getApplicationContext());

        SendBird.init(APP_ID, getApplicationContext());
    }
}
