package com.didi.dts.utils;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.didi.dts.R;

import java.util.List;

/**
 * Created by dimos on 05/11/2017.
 */

public class HelpDialogArrayAdapter extends ArrayAdapter<HelpDialogActivity.Country> {

    private final List<HelpDialogActivity.Country> list;
    private final Activity context;

    public HelpDialogArrayAdapter(Activity context, List<HelpDialogActivity.Country> list) {
        super(context, R.layout.activity_countrycode_row, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.activity_countrycode_row, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            viewHolder.credit = (TextView) view.findViewById(R.id.credit);
            view.setTag(viewHolder);
        } else {
            view = convertView;
        }

        ViewHolder holder = (ViewHolder) view.getTag();
        holder.name.setText(list.get(position).getName());
        holder.credit.setText(list.get(position).getCredit());
        return view;
    }

    static class ViewHolder {
        protected TextView name;
        protected TextView credit;
    }
}