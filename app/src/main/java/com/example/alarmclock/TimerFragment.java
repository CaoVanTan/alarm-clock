package com.example.alarmclock;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.sql.Time;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private EditText edtTimeCountDown;
    private TextView tvTimeCountDown;
    private Button btnStartPauseCountDown;
    private Button btnSetCountDown;
    private Button btnResetCountDown;
    private boolean TimerRunning;
    private long StartTimeInMillis ;
    private long EndTimeCountDown;
    private long TimeLeftInMillis;
    private CountDownTimer Cdtimer;

    public TimerFragment() {
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
    public static TimerFragment newInstance(String param1, String param2) {
        TimerFragment fragment = new TimerFragment();
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
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_timer, container, false);


         edtTimeCountDown = (EditText)v.findViewById(R.id.edtTime);
         tvTimeCountDown = (TextView)v.findViewById(R.id.tvTime);
         btnStartPauseCountDown = (Button)v.findViewById(R.id.btnStartPause);
         btnResetCountDown = (Button)v.findViewById(R.id.btnReset);
         btnSetCountDown =(Button)v.findViewById(R.id.btnSet);


        btnSetCountDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = edtTimeCountDown.getText().toString();
                if(input.length() == 0){
                    Toast.makeText(getContext(), "Khong duoc de trong", Toast.LENGTH_SHORT).show();
                    return;
                }
                long millisInput = Long.parseLong(input) * 60000;
                if (millisInput == 0){
                    Toast.makeText(getContext(), "Hay nhap vao mot so khac 0", Toast.LENGTH_SHORT).show();
                    return;
                }
                setTime(millisInput);
                edtTimeCountDown.setText("");

            }
        });


        btnStartPauseCountDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TimerRunning){
                    pauseTimer();

                }
                else{
                    startTimer();
                }
            }

        });

        btnResetCountDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();

            }
        });


        return v;
    }


    private void closeKeyboard() {
        View view = this.getActivity().getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }


    public void startTimer(){

        EndTimeCountDown = System.currentTimeMillis() + TimeLeftInMillis;

        Cdtimer = new CountDownTimer(TimeLeftInMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                TimeLeftInMillis = millisUntilFinished;
                updateCountDownText();

            }

            @Override
            public void onFinish() {
                TimerRunning=false;
                updateWhatchInterface();

            }

        }.start();
        TimerRunning=true;
        updateWhatchInterface();
    }

    private void setTime(long milliseconds){
        StartTimeInMillis = milliseconds;
        resetTimer();
        closeKeyboard();
    }


    private void pauseTimer(){
        Cdtimer.cancel();
        TimerRunning=false;
        updateWhatchInterface();
    }


    private void resetTimer(){
        TimeLeftInMillis = StartTimeInMillis;
        updateCountDownText();
        updateWhatchInterface();

    }




    private void updateCountDownText(){
        int hours =(int) (TimeLeftInMillis / 1000)  /3600;
        int minutes = (int) ((TimeLeftInMillis / 1000)  % 3600)/ 60;
        int seconds = (int) (TimeLeftInMillis /1000) %60;
        String timeLeftFormatted;
        if( hours > 0){
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d",  hours, minutes, seconds);
        }
        else{
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }
        tvTimeCountDown.setText(timeLeftFormatted);
    }


    private void updateWhatchInterface(){
        if(TimerRunning){
            edtTimeCountDown.setVisibility(View.INVISIBLE);
            btnSetCountDown.setVisibility(View.INVISIBLE);
            btnResetCountDown.setVisibility(View.INVISIBLE);
            btnStartPauseCountDown.setText("Pause");
        }
        else{
            edtTimeCountDown.setVisibility(View.VISIBLE);
            btnSetCountDown.setVisibility(View.VISIBLE);
            btnStartPauseCountDown.setText("Start");
            if(TimeLeftInMillis < 1000){
                btnStartPauseCountDown.setVisibility(View.INVISIBLE);
            }
            else{
                btnStartPauseCountDown.setVisibility(View.VISIBLE);
            }
            if(TimeLeftInMillis < StartTimeInMillis){
                btnResetCountDown.setVisibility(View.VISIBLE);
            }
            else{
                btnResetCountDown.setVisibility(View.INVISIBLE);
            }
        }
    }


        public void onStop(){
        super.onStop();
        SharedPreferences prefs = getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("startTimeInMillis", StartTimeInMillis);
        editor.putLong("millisLeft", TimeLeftInMillis);
        editor.putBoolean("timerRunning", TimerRunning);
        editor.putLong("endTime", EndTimeCountDown);

        editor.apply();
        if(Cdtimer != null){
            Cdtimer.cancel();
        }
    }
    public void onStart(){
        super.onStart();
        SharedPreferences prefs = getActivity().getSharedPreferences("prefs",Context.MODE_PRIVATE);

        StartTimeInMillis = prefs.getLong("startTimeInMillis",600000);
        TimeLeftInMillis = prefs.getLong("millisLeft",StartTimeInMillis);
        TimerRunning = prefs.getBoolean("timerRunning",false);
        updateCountDownText();
        updateWhatchInterface();
        if(TimerRunning){
            EndTimeCountDown = prefs.getLong("endTime",0);
            TimeLeftInMillis = EndTimeCountDown-System.currentTimeMillis();
            if(TimeLeftInMillis < 0){
                TimeLeftInMillis = 0;
                TimerRunning = false;
                updateCountDownText();
                updateWhatchInterface();
            }
            else{
                startTimer();
            }
        }
    }
}