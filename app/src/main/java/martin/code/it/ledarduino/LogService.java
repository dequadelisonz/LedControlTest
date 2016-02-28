package martin.code.it.ledarduino;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;


public class LogService extends IntentService {

    int com = 0;
    InputStream mmInputStream;
    Thread workerThread;
    byte[] readBuffer;
    int readBufferPosition;
    volatile boolean stopWorker;

    public LogService() {
        super("LogService");
    }

    public void onCreate() {
        super.onCreate();
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onHandleIntent(Intent i) {

        final int SIMPLE_NOTIFICATION_ID = 1;
        Log.d("IService", "onHandleIntent");
        android.os.Debug.waitForDebugger(); //________utilizzare solo per debug, altrimenti l'app si blocca!

        NotificationManager mNotificationManager;
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(
                LogService.this);
        notificationBuilder.setContentTitle("Titolo della mia notifica");
        notificationBuilder.setContentText("Testo della mia notifica");
        notificationBuilder.setTicker("Questo Ã¨ il tickerText");
        notificationBuilder.setWhen(System.currentTimeMillis());
        notificationBuilder.setSmallIcon(R.drawable.ic_stat_icon);
        notificationBuilder.setDefaults(Notification.DEFAULT_SOUND
                | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);


        try {

            mmInputStream = LedControl.btSocket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        readBuffer = new byte[1024];
        int bytes;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                int bytesAvailable = mmInputStream.available();
                if (bytesAvailable > 0) {
                    bytes = mmInputStream.read(readBuffer, 0, bytesAvailable);

                    Log.d("service thread", String.valueOf(readBuffer[0]));
                    com = 1;
                    handler.post(new Runnable() {
                        public void run() {
                            String r = String.valueOf('r');
                            LedControl.snapText.setText(String.valueOf(readBuffer[0]));
                            //if (r == LedControl.snapText.getText().toString())

                        }
                    });

                }
            } catch (IOException e) {

            }

            //beginListenForData();
            if (com == 1) {
                mNotificationManager.notify(SIMPLE_NOTIFICATION_ID,
                        notificationBuilder.build());
                com=0;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }

    @Override
    public void onDestroy() {
        Log.i("PROVA SERVICE", "Distruzione Service");
    }


    final Handler handler = new Handler();

    void beginListenForData() {
        final Handler handler = new Handler();
        final byte delimiter = 0x10; //This is the ASCII code for a newline character

        stopWorker = false;
        readBufferPosition = 0;

        workerThread = new Thread(new Runnable() {
            public void run() {
                Thread.currentThread().setName("workerThread");
                android.os.Debug.waitForDebugger(); //________utilizzare solo per debug, altrimenti l'app si blocca!
                readBuffer = new byte[1024];
                int bytes;
                while (!Thread.currentThread().isInterrupted() && !stopWorker) {
                    try {
                        int bytesAvailable = mmInputStream.available();
                        if (bytesAvailable > 0) {
                            bytes = mmInputStream.read(readBuffer, 0, readBuffer.length);
                            for (int i = 0; i < readBuffer.length; i++) {
                                Log.d("service thread", String.valueOf(readBuffer[i]));
                            }

                        /*
                            byte[] packetBytes = new byte[bytesAvailable];
                            mmInputStream.read(packetBytes);
                            for (int i = 0; i < bytesAvailable; i++) {
                                byte b = packetBytes[i];
                                if (b == delimiter) {
                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                    final String data = new String(encodedBytes, "US-ASCII");
                                    readBufferPosition = 0;

                                    handler.post(new Runnable() {
                                        public void run() {
                                            String r= String.valueOf('r');
                                            LedControl.snapText.setText(data);
                                            if(r== LedControl.snapText.getText().toString())
                                                com=1;
                                        }
                                    });
                                } else {
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        */
                        }
                    } catch (IOException ex) {
                        stopWorker = true;
                    }
                }
            }
        });

        workerThread.start();
    }

    ;


}
