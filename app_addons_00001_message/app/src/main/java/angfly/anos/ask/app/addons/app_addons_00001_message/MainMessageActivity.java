package angfly.anos.ask.app.addons.app_addons_00001_message;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainMessageActivity extends AppCompatActivity {

    private final static String TAG = "Message_test";

    private Button sendMessageButton = null;

    private int buttonCounts = 0;

    private Thread myThread = null;

    private MyThread myThread2 = null;

    class MyRunnable implements Runnable {

        @Override
        public void run() {
            int counts = 0;
            for (;;) {
                Log.i(TAG, "MyThread : count = " + counts);

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                counts ++;
            }
        }
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            super.run();
            int counts = 0;
            for (;;) {
                Log.i(TAG, "MyThread2 : count = " + counts);

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                counts ++;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_message);

        sendMessageButton = findViewById(R.id.button_send_message);
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Send Message : counts = " + buttonCounts);
                buttonCounts ++;
            }
        });

        myThread = new Thread(new MyRunnable(), "MyThread Test");
        myThread.start();

        myThread2 = new MyThread();
        myThread2.start();
    }
}
