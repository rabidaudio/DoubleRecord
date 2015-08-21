package audio.rabid.dev.doublerecord;

import android.app.ActionBar;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

/**
 * Created by charles on 8/21/15.
 *
 * @see android.media.projection.MediaProjection
 * @see <a href="http://stackoverflow.com/a/24280494">another way</a>
 * @see <a href="https://code.google.com/p/android-screenshot-library/wiki/DeveloperGuide>Another other way</a>
 */
public class RecordService extends IntentService {

    private static final String TAG = RecordService.class.getSimpleName();

    public static final String RECORD_ACTION = RecordService.class.getCanonicalName()+".RECORD_ACTION";

    WindowManager windowManager;

    public RecordService() {
        super(TAG);

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        TextView t = new TextView(this);
        t.setText("Hello World!");
        t.setWidth(100);
        t.setHeight(50);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(this, null);
        windowManager.addView(t, lp);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();

        //TODO
    }


    class VideoRecordView extends SurfaceView implements SurfaceHolder.Callback, View.OnClickListener, Camera.PreviewCallback {

        Camera camera;

        MediaRecorder cameraRecorder = new MediaRecorder();

        public VideoRecordView(Context context) {
            super(context);

            getHolder().addCallback(this);

            camera = Camera.open();

            if(camera == null){
                Toast.makeText(context, "Camera unavailable", Toast.LENGTH_LONG).show();
                return;
            }
        }

        public void destroy(){
            if(camera != null) camera.release();
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            Log.d(TAG, "Surface created");

            try {
                camera.setOneShotPreviewCallback(this);
                camera.setPreviewDisplay(getHolder());
                camera.startPreview();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            Log.d(TAG, "Surface Changed");
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            Log.d(TAG, "Surface destroyed");
        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            //preview started, start recording

            cameraRecorder.setCamera(camera);
            cameraRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
            cameraRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
            cameraRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));
            cameraRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            cameraRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
            cameraRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP); //H264?

            File cameraRecordingFile = new File(Environment.getDataDirectory(), String.valueOf(System.currentTimeMillis()));
            cameraRecorder.setOutputFile(cameraRecordingFile.getAbsolutePath());
//            cameraRecorder.prepare();

        }
    }
}
