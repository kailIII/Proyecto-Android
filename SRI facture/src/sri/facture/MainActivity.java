package sri.facture;

import com.sri.facture.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.widget.ProgressBar;

public class MainActivity extends Activity {
	ProgressBar myProgressBar;
	int myProgress = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        myProgressBar=(ProgressBar)findViewById(R.id.progressBar1);
        new Thread(myThread).start();
        
    }    
    
    private Runnable myThread = new Runnable(){
        
        public void run() {
            //don't hard code things, use a constant for max progress value
            while ( myProgress<100 ){
                try{
                    myHandle.sendMessage(myHandle.obtainMessage());
                    //same
                    Thread.sleep(30);
                } catch(Exception ex){
                  //never live an empty catch statement, you are missing exceptions and
                  //can't correct them
                  Log.e( "MyCurrentClass", "Error during async data processing",ex );
                }//catch
            }//while
            //start new activity here
            startActivity(new Intent(MainActivity.this, Login.class));
        }//met

        Handler myHandle = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                myProgress++;
                myProgressBar.setProgress(myProgress);
            }
        };
    };
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}