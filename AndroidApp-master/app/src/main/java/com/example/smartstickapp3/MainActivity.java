package com.example.smartstickapp3;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    BluetoothDevice remoteDevice;
    BluetoothServerSocket nmserver;
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


    Toolbar toolbar;
    private CardView addCard, showCard, searchCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myBluetooth = BluetoothAdapter.getDefaultAdapter();
        Intent newInt = getIntent();
        address = newInt.getStringExtra(BluetoothScreen.EXTRA_ADRESS);

        setUpToolBar();
        setUpDrawer();
        setUpCardView();

        new BTbaglan().execute();
    }

    private void Disconnect() {
        if (btSocket != null) {
            try {
                btSocket.close();
            } catch (IOException e) {
                //msg("Error");
            }
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Disconnect();
    }

    private class BTbaglan extends AsyncTask<Void, Void, Void> {
        private boolean ConnectSuccess = true;

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(MainActivity.this, "Bağlanıyor...", "Lütfen Bekleyin");
        }

        @Override
        protected Void doInBackground(Void... devices) {
            try {
                if (btSocket == null || !isBtConnected) {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice cihaz = myBluetooth.getRemoteDevice(address);
                    btSocket = cihaz.createInsecureRfcommSocketToServiceRecord(myUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();
                }
            } catch (IOException e) {
                ConnectSuccess = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (!ConnectSuccess) {
                //msg("Baglantı hatası, lütfen tekrar deneyin");
                Toast.makeText(getApplicationContext(), "Bağlantı Hatası Tekrar Deneyin", Toast.LENGTH_SHORT).show();
                //finish();
                // şimdilik bu kısmı yoruma aldım yoksa uygulama sonlanıyor diğer ekranları yapamıyorum.
            } else {
                //   msg("Baglantı Basarılı");
                Toast.makeText(getApplicationContext(), "Bağlantı Başarılı", Toast.LENGTH_SHORT).show();

                isBtConnected = true;
            }
            progress.dismiss();
        }
    }

    private void setUpCardView() {
        addCard = findViewById(R.id.add_box);
        showCard = findViewById(R.id.show_box);
        searchCard = findViewById(R.id.search_box);

        addCard.setOnClickListener(this);
        showCard.setOnClickListener(this);
        searchCard.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.add_box:
                i = new Intent(this, AddActivity.class);
                Toast.makeText(this, "Bitki Ekle", Toast.LENGTH_SHORT).show();
                startActivity(i);
                break;
            case R.id.show_box:
                i = new Intent(this, ShowActivity.class);
                Toast.makeText(this, "Bitki Göster", Toast.LENGTH_SHORT).show();
                startActivity(i);
                break;
            case R.id.search_box:
                i = new Intent(this, SearchActivity.class);
                Toast.makeText(this, "Bitki Ara", Toast.LENGTH_SHORT).show();
                startActivity(i);
                break;
            default:
                break;
        }
    }

    private void setUpDrawer() {

        NavigationDrawerFragment navFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.fragment);
        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        navFragment.setUpNavigationDrawer(drawerLayout, toolbar);
    }

    private void setUpToolBar() {

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Smart Stick App");
        toolbar.setSubtitle("Hoş Geldiniz");

        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                String mesaj = " ";
                switch (item.getItemId()) {
                    case R.id.bluetooth:
                        mesaj = bluetoothDurumuYaz();
                        Toast.makeText(MainActivity.this, mesaj, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.hakkinda:
                        mesaj = "HAKKINDA";
                        Toast.makeText(MainActivity.this, mesaj, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this,HakkindaActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.logout:
                        uygulamadanCikisYap();
                        break;
                }
                return true;
            }
        });
    }

    public String bluetoothDurumuYaz(){
        if (!myBluetooth.isEnabled()) {
            return "Kapalı";
        }
        else{
            return "Açık";
        }
    }

    public void uygulamadanCikisYap() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        //builder.setTitle(R.string.app_name);
        builder.setTitle("Uyarı");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Çıkış yapmak istiyor musunuz?")
                .setCancelable(false)
                .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
