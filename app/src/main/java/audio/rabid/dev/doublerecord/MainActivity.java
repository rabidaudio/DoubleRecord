package audio.rabid.dev.doublerecord;

import android.app.IntentService;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.videoRecordView) VideoRecordView videoRecordView;
    @Bind(R.id.start) Button recordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        Intent i = new Intent(this, RecordService.class);
//        i.setAction(RecordService.RECORD_ACTION);
//        startService(i);

    }

    @OnClick(R.id.start)
    public void toggleRecording(){
        if(videoRecordView.isRecording()){
            videoRecordView.stopRecording();
            recordButton.setVisibility(View.INVISIBLE);
            recordButton.setOnClickListener(null);
        }else{
            recordButton.setText("stop");
            videoRecordView.startRecording();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        videoRecordView.destroy();
    }
}
