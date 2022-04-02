package com.example.alarmclock;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClockFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClockFragment extends Fragment {
    private View view;
    ImageButton btnAddClock;
    ListView lvClock;
    ArrayList<TimeZoneData> timeZoneList;
    CustomClockArrayAdapter clockArrayAdapter;
    ClockDbHelper db;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ClockFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AlarmFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClockFragment newInstance(String param1, String param2) {
        ClockFragment fragment = new ClockFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_clock, container, false);
        btnAddClock = view.findViewById(R.id.btnAddClock);
        lvClock = view.findViewById(R.id.lvClock);

        db = new ClockDbHelper(getContext());
        timeZoneList = db.getTimeZone();

        clockArrayAdapter = new CustomClockArrayAdapter(getContext(), R.layout.activity_clock_show, timeZoneList);
        lvClock.setAdapter(clockArrayAdapter);

        lvClock.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                TimeZoneData timeZoneData;
                timeZoneData = db.getTimeZoneById(position);
                if(timeZoneData.getId() != position) {
                    timeZoneData = new TimeZoneData(position, timeZoneList.get(position).getName(), timeZoneList.get(position).getTime());
                    db.insertClock(timeZoneData);
                }

                Intent intent = new Intent(getActivity(), AddClockActivity.class);
                startActivity(intent);
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
}