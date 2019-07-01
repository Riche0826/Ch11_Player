package flag.com.tw.ch11_player;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Uri uri;
    TextView txvName, txvUri;
    boolean isVideo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        txvName = (TextView) findViewById(R.id.txvName);
        txvUri = (TextView) findViewById(R.id.txvUri);

        uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.welcome);

        txvName.setText("welcome.mp3");
        txvUri.setText("程式內的歌曲：" + uri.toString());

    }

    public void onPick(View v){
        Intent it = new Intent(Intent.ACTION_GET_CONTENT);

        if(v.getId() == R.id.btnPickAudio){
            it.setType("audio/*");
            startActivityForResult(it, 100);
        }else {
            it.setType("video/*");
            startActivityForResult(it, 101);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){
            isVideo = (requestCode == 101);

            uri = data.getData();
            txvName.setText(getFilename(uri));
            txvUri.setText("檔案 URI:" + uri.toString());
        }
    }

    String getFilename(Uri uri) {
        String fileName = null;
        String[] colName = {MediaStore.MediaColumns.DISPLAY_NAME};
        Cursor cursor = getContentResolver().query(uri, colName, null, null, null);

        cursor.moveToFirst();
        fileName = cursor.getString(0);
        cursor.close();
        return fileName;
    }
}
