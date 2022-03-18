package com.example.l08t2;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.Context;
import java.io.OutputStreamWriter;
import java.io.IOException;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    TextView text;
    TextView pricetext;
    Spinner spin;
    private int bottles;
    private ArrayList<Bottle> bottle_array;
    private float money;
    private SeekBar sb;
    int pullonvalinta;
    Context context = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        text = (TextView) findViewById(R.id.editText);
        pricetext = (TextView) findViewById(R.id.pricetextt);
        sb = (SeekBar) findViewById(R.id.seekBar);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar sb, int progress, boolean fromUser) {
                pricetext.setText("Click 'ADD MONEY' to add: " + String.valueOf(progress) + "€");
            }
            @Override
            public void onStartTrackingTouch(SeekBar sb) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar sb) {
            }
        });
        bottles = 0;
        money = 0;
        bottle_array  = new ArrayList<Bottle>();
        bottle_array.add(new Bottle("Pepsi Max", "Pepsi", 0.33, 1.80, 0.5, 1));
        bottle_array.add(new Bottle("Pepsi Max", "Pepsi", 0.33, 2.2, 1.5, 1));
        bottle_array.add(new Bottle("Coca-Cola Zero", "Coca-Cola", 0.33, 2.0, 0.5, 1));
        bottle_array.add(new Bottle("Coca-Cola Zero", "Coca-Cola", 0.33, 2.5, 1.5, 1));
        bottle_array.add(new Bottle("Fanta Zero", "Fanta", 0.33, 1.95, 0.5, 1));

        for (int i=0; i<bottle_array.size(); i++) {
            bottles += bottle_array.get(i).getAmount();
        }

        spin = findViewById(R.id.spinner);
        ArrayAdapter<Bottle> arrayAdapter = new ArrayAdapter<Bottle>(this, android.R.layout.simple_spinner_item, bottle_array);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(arrayAdapter);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                pullonvalinta = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void addMoney(View v) {
        int number;
        number = sb.getProgress();
        money += number;
        sb.setProgress(0);
        text.setText("Klink! Added more money!");
    }

    public void buyBottle(View v) {

        if(bottles == 0) {
            text.setText("No more bottles available");
        }
        else {
            if (bottle_array.get(pullonvalinta).getAmount() == 0) {
                text.setText("Not available.");
            }
            else {

                if (money < bottle_array.get(pullonvalinta).getPrice()) {
                    text.setText("Add money first!");
                } else {
                    money -= bottle_array.get(pullonvalinta).getPrice();
                    text.setText("KACHUNK! " + bottle_array.get(pullonvalinta).getName() + " " + bottle_array.get(pullonvalinta).getSize() + " came out of the dispenser!");
                    try {
                        OutputStreamWriter ows = new OutputStreamWriter(context.openFileOutput("newFile.txt", context.MODE_PRIVATE));
                        ows.write("***THIS IS RECEIPT OF YOUR PURCHASE***\n\nYou bought: " + bottle_array.get(pullonvalinta).getName() + ", size: " + String.valueOf(bottle_array.get(pullonvalinta).getSize()) + ", price: " + String.valueOf(bottle_array.get(pullonvalinta).getPrice() + " €"));
                        ows.close();

                    } catch (IOException e) {
                        Log.e("IOException", "Virhe syötteessä");
                    }
                    bottle_array.get(pullonvalinta).setAmount(bottle_array.get(pullonvalinta).getAmount() - 1);
                    bottles -= 1;
                }
            }
        }
    }

    public void returnMoney(View v) {
        String raha = String.format("%.2f", money);
        text.setText("Klink klink. Money came out! You got "+ raha +"€ back");
        money = 0;
    }

}