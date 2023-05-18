package com.example.enchanteur;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class BorrowerAdapter extends ArrayAdapter<BorrowerStudent> {

    private LayoutInflater inflater;

    public BorrowerAdapter(Context context, List<BorrowerStudent> borrowerList) {
        super(context, 0, borrowerList);
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_borrower, parent, false);
        }

        // Get the borrower at the specified position
        BorrowerStudent borrower = getItem(position);

        // Set the student name
        TextView nameTextView = convertView.findViewById(R.id.tvStudentName);
        nameTextView.setText(borrower.getName());

        // Set the student number
        TextView numberTextView = convertView.findViewById(R.id.tvStudentNo);
        numberTextView.setText(borrower.getStudentNumber());

        // Set the contact number
        TextView contactTextView = convertView.findViewById(R.id.tvContactNo);
        contactTextView.setText(borrower.getContactNumber());

        return convertView;
    }
}

