package com.example.alarmclock;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ClockFragment extends Fragment {
    private View view;
    FloatingActionButton btnAddClock;
    ListView lvClock;
    ArrayList<ClockData> timeZoneList;
    ClockAdapter clockArrayAdapter;
    ClockDbHelper db;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ClockFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_clock, container, false);
        btnAddClock = view.findViewById(R.id.btnAddClock);
        lvClock = view.findViewById(R.id.lvClock);
        db = new ClockDbHelper(getContext());
        timeZoneList = db.getTimeZone();
        clockArrayAdapter = new ClockAdapter(getContext(), R.layout.activity_clock_show, timeZoneList);
        lvClock.setAdapter(clockArrayAdapter);

        lvClock.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                confirmDialog(position);
                return false;
            }
        });

        btnAddClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddClockActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void confirmDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Xóa " + timeZoneList.get(position).getName() + "?");
        builder.setMessage("Bạn có muốn xóa không?");

        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                db = new ClockDbHelper(getContext());
                db.deleteTimeZone(timeZoneList.get(position).getId());

//                Reload list view
                clockArrayAdapter.clear();
                db = new ClockDbHelper(getContext());
                timeZoneList = db.getTimeZone();
                clockArrayAdapter = new ClockAdapter(getContext(), R.layout.activity_clock_show, timeZoneList);
                lvClock.setAdapter(clockArrayAdapter);

                Toast.makeText(getContext(), "Xóa thành công!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        builder.create().show();
    }
}