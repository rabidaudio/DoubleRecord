package audio.rabid.dev.doublerecord;

import android.content.Context;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

/**
 * Created by charles on 8/24/15.
 */
public class VideoRecordView extends SurfaceView implements SurfaceHolder.Callback,
        View.OnClickListener, Camera.PreviewCallback {

    private static final String TAG = VideoRecordView.class.getSimpleName();

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
