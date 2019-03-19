import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

public class NotificationUtils extends ContextWrapper {
    private NotificationManager manager;
    public static final String ANDROID_CHANNEL_ID = "com.pacayasID.ANDROID";
    public static final String ANDROID_CHANNEL_NAME = "com.pacayasName.ANDROID";

    public NotificationUtils(Context base) {
        super(base);
        createChannels();
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannels() {
        NotificationChannel notificationChannel = new NotificationChannel(ANDROID_CHANNEL_ID,
                ANDROID_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
    }
}
