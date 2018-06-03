package com.swiftesther.cryptorates;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BitcoinRatesActivity extends AppCompatActivity {

    // global objects
    public int BTC;
    public int base;
    public String[] val;
    public String s;
    public Handler handler;

    private TextView textView;
    private EditText editText;
    private Spinner spinner;
    private Button convert;
    private SharedPreferences result;
    private String wordString;
    private Map<String, String> map;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitcoin_rates);
        spinner = (Spinner) findViewById(R.id.bitcoin_card_spinner);
        ArrayAdapter<CharSequence> spinnerItems = ArrayAdapter.createFromResource(this, R.array.value, android.R.layout.simple_spinner_item);
        spinnerItems.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(spinnerItems);

        convert = (Button) findViewById(R.id.bitcoin_convert_button);

        textView = (TextView) findViewById(R.id.bitcoin_rate_textView);
        editText = (EditText) findViewById(R.id.edit_text);
        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convert();
            }
        });
        getStoredValues();
        Toast.makeText(getApplicationContext(), wordString, Toast.LENGTH_SHORT).show();
    }

    private void convert() {
        String key = getKey(spinner.getSelectedItemPosition());
        String value = map.get(key);
        Double valueInt = Double.parseDouble(value);
        String convertFrom = editText.getText().toString();

        String ans = String.valueOf(Double.parseDouble(convertFrom) / valueInt);

        textView.setText(ans);


    }

    // get the spinner
    private String getKey(int i) {


        switch (i) {
            case 0:
                return "EUR";

            case 1:

                return "GBP";
            case 2:

                return "RUB";
            case 3:

                return "AUD";
            case 4:

                return "AED";
            case 5:

                return "NGN";

        }

        return "EUR";
    }

    //get the values saved in shared preference and clean
    private void getStoredValues() {
        result = getSharedPreferences("PREF", 0);
        wordString = result.getString("results", "");

        map = new HashMap<String, String>();
        //!!Your string comes here, I escape double quoutes manually !!
        String str = wordString;
        //{"BTC":0.05115,"USD":296.91,"EUR":256.47,"NGN":106412.01} regex to solve this response
        String regex = "\"(\\w+)\":(\\d+\\.\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);

        //write the result into map array
        while (matcher.find()) {
            String key = matcher.group(1);
            String value = matcher.group(2);
            map.put(key, value);
        }


        /**
         listItems = new ArrayList<>();

         for (Map.Entry<String, String> entry : map.entrySet()) {
         String key = entry.getKey();
         String value = entry.getValue();
         ListItem listItem = new ListItem(
         key,
         value
         );

         listItems.add(listItem);

         }
         */

    }
}

