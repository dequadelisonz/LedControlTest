package martin.code.it.ledarduino;

        import android.graphics.Color;
        import android.support.v7.app.ActionBarActivity;
        import android.os.Bundle;
        import android.view.KeyEvent;

        import android.bluetooth.BluetoothSocket;
        import android.content.Intent;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;
        import android.app.ProgressDialog;
        import android.bluetooth.BluetoothAdapter;
        import android.bluetooth.BluetoothDevice;
        import android.os.AsyncTask;

        import java.lang.String;

        import java.io.IOException;
        import java.util.UUID;


public class LedControl extends ActionBarActivity {

    Button Boncu,Boffcu,Bonba,Boffba,Bonca,Boffca,Bonst,Boffst,Bonsa,Boffsa,BAtt,BStt ,btnDis,Btla;
    String s;
    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    public static BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    public static TextView snapText;

    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent newint = getIntent();
        address = newint.getStringExtra(DeviceList.EXTRA_ADDRESS); //receive the address of the bluetooth device
        //view of the LedControl
        setContentView(R.layout.activity_led_control);

        //call the widgtes
        Boncu = (Button) findViewById(R.id.boncu);
        Boffcu = (Button) findViewById(R.id.boffcu);
        Bonba =(Button) findViewById(R.id.bonba);
        Boffba =(Button) findViewById(R.id.boffba);
        Bonca =(Button) findViewById(R.id.bonca);
        Boffca= (Button) findViewById(R.id.boffca);
        Bonst= (Button) findViewById(R.id.bonst);
        Boffst= (Button) findViewById(R.id.boffst);
        Bonsa=(Button) findViewById(R.id.bonsa);
        Boffsa=(Button) findViewById(R.id.boffsa);
        BStt=(Button) findViewById(R.id.BStt);
        BAtt=(Button) findViewById(R.id.BAtt);
        btnDis = (Button) findViewById(R.id.btnDis);
        Btla=(Button) findViewById(R.id.Btla);
        snapText = (TextView)findViewById(R.id.textView10);



        new ConnectBT().execute(); //Call the class to connect


        Boncu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s = String.valueOf('0');
                Boffcu.setTextColor(Color.argb(255, 0, 0, 0));
                Boncu.setTextColor(Color.argb(255,25,255, 0));
                send(s);
            }
        });
        Boffcu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s=String.valueOf('1');
                Boncu.setTextColor(Color.argb(255, 0, 0, 0));
                Boffcu.setTextColor(Color.argb(255, 255, 0, 0));
                send(s);
            }
        });

        Bonba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s = String.valueOf('2');
                Bonba.setTextColor(Color.argb(255, 25, 255, 0));
                Boffba.setTextColor(Color.argb(255, 0, 0, 0));
                send(s);}});
        Boffba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s=String.valueOf('3');
                Bonba.setTextColor(Color.argb(255, 0, 0, 0));
                Boffba.setTextColor(Color.argb(255, 255, 0, 0));
                send(s);}});

        Bonca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s=String.valueOf('a');
                Bonca.setTextColor(Color.argb(255, 25, 255, 0));
                Boffca.setTextColor(Color.argb(255, 0, 0, 0));
                send(s);}
        });
        Boffca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s=String.valueOf('4');
                Bonca.setTextColor(Color.argb(255, 0, 0, 0));
                Boffca.setTextColor(Color.argb(255, 255, 0, 0));
                send(s);}
        });

        Bonst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s=String.valueOf('5');
                Bonst.setTextColor(Color.argb(255, 25, 255, 0));
                Boffst.setTextColor(Color.argb(255, 0, 0, 0));
                send(s);}
        });
        Boffst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s = String.valueOf('6');
                Bonst.setTextColor(Color.argb(255, 0, 0, 0));
                Boffst.setTextColor(Color.argb(255, 255, 0, 0));
                send(s);}});

        Bonsa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s = String.valueOf('7');
                Bonsa.setTextColor(Color.argb(255, 25, 255, 0));
                Boffsa.setTextColor(Color.argb(255, 0, 0, 0));
                send(s);}});
        Boffsa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s = String.valueOf('8');
                Bonsa.setTextColor(Color.argb(255, 0, 0, 0));
                Boffsa.setTextColor(Color.argb(255, 255, 0, 0));
                send(s);}});

        BAtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s = String.valueOf('q');
                Bonsa.setTextColor(Color.argb(255, 25, 255, 0));
                Boffsa.setTextColor(Color.argb(255, 0, 0, 0));
                Bonst.setTextColor(Color.argb(255, 25, 255, 0));
                Boffst.setTextColor(Color.argb(255, 0, 0, 0));
                Bonca.setTextColor(Color.argb(255, 25, 255, 0));
                Boffca.setTextColor(Color.argb(255, 0, 0, 0));
                Boffcu.setTextColor(Color.argb(255, 0, 0, 0));
                Boncu.setTextColor(Color.argb(255,25,255, 0));
                Bonba.setTextColor(Color.argb(255, 25, 255, 0));
                Boffba.setTextColor(Color.argb(255, 0, 0, 0));
                send(s);}});
        BStt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s = String.valueOf('9');
                Bonsa.setTextColor(Color.argb(255, 0, 0, 0));
                Boffsa.setTextColor(Color.argb(255, 255, 0, 0));
                Bonst.setTextColor(Color.argb(255, 0, 0, 0));
                Boffst.setTextColor(Color.argb(255, 255, 0, 0));
                Bonca.setTextColor(Color.argb(255, 0, 0, 0));
                Boffca.setTextColor(Color.argb(255, 255, 0, 0));
                Bonba.setTextColor(Color.argb(255, 0, 0, 0));
                Boffba.setTextColor(Color.argb(255, 255, 0, 0));
                Boncu.setTextColor(Color.argb(255, 0, 0, 0));
                Boffcu.setTextColor(Color.argb(255, 255, 0, 0));
                send(s);}
        });
        btnDis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Disconnect(); //close connection
            }
        });

    }

    public void startService(View v) {
        startService(new Intent(this, LogService.class));
    }


    private void send(String s)
    {
        try
        {
            btSocket.getOutputStream().write(s.toString().getBytes());
        }
        catch (IOException e)
        {
            msg("Error");
        }

    }

    private void Disconnect()
    {
        if (btSocket!=null) //If the btSocket is busy
        {
            try
            {
                btSocket.close(); //close connection
            }
            catch (IOException e)
            { msg("Error");}
        }
        finish(); //return to the first layout

    }

    // fast way to call Toast
    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }



    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(LedControl.this, "Connessione in corso..", "Perfavore aspetta!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            android.os.Debug.waitForDebugger();//___________utilizzare solo per debug, altrimenti l'app si blocca!
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                msg("Connessione fallita. device non supportato");
                finish();
            }
            else
            {
                msg("Connesso.");
                String s=String.valueOf('9');
                send(s);
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ECLAIR
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            // Take care of calling this method on earlier versions of
            // the platform where it doesn't exist.
            onBackPressed();
        }

        return super.onKeyDown(keyCode, event);
    }
    @Override public void onBackPressed() {
        moveTaskToBack(true);
        return;
    }


}
