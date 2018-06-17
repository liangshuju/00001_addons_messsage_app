package angfly.anos.ask.app.addons.app_addons_00001_message;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
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

    private Handler myHandler = null;

    private int messageCounts = 0;

    private HandlerThread myHandlerThread3 = null;

    private Handler myHandler3 = null;

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

        private Looper mLooper;

        @Override
        public void run() {
            super.run();
            Looper.prepare();

            synchronized (this) {
                mLooper = Looper.myLooper();
                notifyAll();
            }

            Looper.loop();
        }

        public Looper getLooper() {

            if (!isAlive()) {
                return null;
            }

            // if the thread has been started, wait until the looper has been created.
            synchronized (this) {
                while(isAlive() && mLooper == null) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            return mLooper;
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

                myHandler3.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Log.i(TAG, "myHandler3 get message : messageCounts = " + messageCounts);
                        messageCounts ++;
                    }
                });

                Log.i(TAG, "Send Message : counts = " + buttonCounts);
                Message msg = new Message();
                myHandler.sendMessage(msg);
                buttonCounts ++;
            }
        });

        myThread = new Thread(new MyRunnable(), "MyThread Test");
        myThread.start();

        myThread2 = new MyThread();
        myThread2.start();

        myHandler = new Handler(myThread2.getLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Log.i(TAG, "myHandler handleMessage : messageCounts = " + messageCounts);
                messageCounts ++;
                return false;
            }
        });

        myHandlerThread3 = new HandlerThread("MessageTestHandlerThread3");
        myHandlerThread3.start();

        myHandler3 = new Handler(myHandlerThread3.getLooper());
    }

}


