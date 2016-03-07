package com.example.dante.liveitup;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dante on 3/2/16.
 */

public class ListAdapter extends ArrayAdapter<ListElement>
{
    int resource;
    Context context;
    public List<ListElement> List = new ArrayList<ListElement>();


    @Override
    public void add(ListElement object) {
        List.add(object);
        super.add(object);
    }

    public void Clear()
    {
        List.clear();
    }
    public ListAdapter(Context _context, int _resource, List<ListElement> items) {
        super(_context, _resource, items);
        resource = _resource;
        context = _context;
    }




        @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout newView;

        ListElement w = getItem(position);

        // Inflate a new view if necessary.
        if (convertView == null) {
            newView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(inflater);
            vi.inflate(resource,  newView, true);
        } else {
            newView = (LinearLayout) convertView;
        }

        // Fills in the view.
        TextView tv = (TextView) newView.findViewById(R.id.itemText);
             tv.setText(w.textLabel);

        // Set a listener for the whole list item.
        newView.setTag("hello");

        return newView;
    }

}

