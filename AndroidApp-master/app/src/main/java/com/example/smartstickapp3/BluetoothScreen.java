package com.example.smartstickapp3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class BluetoothScreen extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    BluetoothAdapter myBluetooth;
    private Set<BluetoothDevice> pairedDevices;
    Button toggle_button, pair_button;
    ListView pairedList;
    public static String EXTRA_ADRESS = "device_address";
    //ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_screen);

        myBluetooth = BluetoothAdapter.getDefaultAdapter();//bulunulan cihazın bluetooth özelliğini olup olmadığını kontrol eder.
        toggle_button = (Button) findViewById(R.id.btnBluetooth);
        pair_button = (Button) findViewById(R.id.btnCihazListele);
        pairedList = (ListView) findViewById(R.id.lvCihazListele);

        toggle_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleBluetooth();
            }
        });
        pair_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listDevice();
            }
        });
    }

    private void toggleBluetooth() {
        if (myBluetooth == null) {
            Toast.makeText(getApplicationContext(), "Bluetooth cihaz yok!", Toast.LENGTH_SHORT).show();
        }
        if (!myBluetooth.isEnabled()) {
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBTIntent);
        }
        if (myBluetooth.isEnabled()) {
            myBluetooth.disable();
        }
    }

    private void listDevice() {

        pairedDevices = myBluetooth.getBondedDevices();
        ArrayList list = new ArrayList();

        if (pairedDevices.size() > 0) {
            for (BluetoothDevice bt : pairedDevices) {
                list.add(bt.getName() + "\n" + bt.getAddress());
            }
        } else {
            Toast.makeText(getApplicationContext(), "Eşleşmiş Cihaz Yok!", Toast.LENGTH_SHORT).show();
        }
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, list);

        pairedList.setAdapter(adapter);
        pairedList.setOnItemClickListener(selectDevice);
    }

    public AdapterView.OnItemClickListener selectDevice = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String info = ((TextView) view).getText().toString();
            String address = info.substring(info.length() - 17);

            Intent comIntent = new Intent(BluetoothScreen.this, MainActivity.class);
            comIntent.putExtra(EXTRA_ADRESS, address);
            startActivity(comIntent);
            // finish ile bluetooth eşleştirme aktivitesini bitirmezsek geri bu aktiviteye gelebilir.
            finish();
        }
    };

    @Override
    public void onBackStackChanged() {

    }
}
