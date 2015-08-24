package audio.rabid.dev.doublerecord;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by charles on 8/21/15.
 *
 * @see android.media.projection.MediaProjection
 * @see <a href="http://stackoverflow.com/a/24280494">another way</a>
 * @see <a href="https://code.google.com/p/android-screenshot-library/wiki/DeveloperGuide">Another other way</a>
 */
public class RecordService extends IntentService {

    private static final String TAG = RecordService.class.getSimpleName();

    public static final String RECORD_ACTION = RecordService.class.getCanonicalName()+".RECORD_ACTION";

    WindowManager windowManager;
    Handler mainHandler;

    public RecordService() {
        super(TAG);
    }

    @Override
    public void onCreate(){
        super.onCreate();

        mainHandler = new Handler(Looper.getMainLooper());
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();

//        Toast.makeText(this, action, Toast.LENGTH_LONG).show();

        //TODO

        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                createView();
            }
        });
    }

    private void createView(){
        TextView t = new TextView(this);
        t.setText("Hello World!");
        ViewGroup.LayoutParams lp = new WindowManager.LayoutParams(
        WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, PixelFormat.OPAQUE);

        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                windowManager.removeView(v);
            }
        });

        windowManager.addView(t, lp);
    }
}
