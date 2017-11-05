package com.didi.dts.utils;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.didi.dts.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dimos on 05/11/2017.
 */

public class HelpDialogActivity extends ListActivity {

    public static String RESULT_CONTRYCODE = "countrycode";
    public String[] countrynames, countrycredit, countrycodes;
    private List<Country> countryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        populateCountryList();
        ArrayAdapter<Country> adapter = new HelpDialogArrayAdapter(this, countryList);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Country c = countryList.get(position);
                Intent returnIntent = new Intent();
                returnIntent.putExtra(RESULT_CONTRYCODE, c.getCode());
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }

    private void populateCountryList() {
        countryList = new ArrayList<Country>();
        countrynames = getResources().getStringArray(R.array.help_names);
        countrycredit = getResources().getStringArray(R.array.help_credit);
        countrycodes = getResources().getStringArray(R.array.help_codes);
        for (int i = 0; i < countrycodes.length; i++) {
            countryList.add(new Country(countrynames[i], countrycredit[i], countrycodes[i]));
        }
    }

    public class Country {
        private String name;
        private String credit;
        private String code;

        public Country(String name, String credit, String code) {
            this.name = name;
            this.credit = credit;
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public String getCredit() {
            return credit;
        }

        public String getCode() {
            return code;
        }
    }
}
