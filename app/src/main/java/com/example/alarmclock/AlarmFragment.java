package com.example.alarmclock;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.app.Dialog;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.content.res.AssetManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlarmFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlarmFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button btn_them;
    Dialog myDialog;
    Button btn_Huy;
    Button btn_Ok;
    TimePicker timePicker;
    Calendar calendar;
    ListView listView_alarm;
    PendingIntent pendingIntent;

    public AlarmFragment() {
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
    public static AlarmFragment newInstance(String param1, String param2) {
        AlarmFragment fragment = new AlarmFragment();
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_alarm, container, false);

        listView_alarm =(ListView)v.findViewById(R.id.list_alarm);

        myDialog = new Dialog(this.getContext());
        btn_them = v.findViewById(R.id.Add_btn);


        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.setContentView(R.layout.new_alarm_popup);
                btn_Huy =(Button) myDialog.findViewById(R.id.close_btn);
                timePicker = myDialog.findViewById(R.id.TimePikerAlarm);
                timePicker.setIs24HourView(true);
                btn_Huy.setOnClickListener(new View.OnClickListener() { //Event click Huy
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
                btn_Ok = (Button) myDialog.findViewById(R.id.confirm_btn);
                btn_Ok.setOnClickListener(new View.OnClickListener() {
                     // Event click tao moi
                    @Override
                    public void onClick(View view) {
                        calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                        calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
                        UpdateTimeText(calendar, v.getContext());
                        Alarm(calendar, 1);
                        myDialog.dismiss();
                    }
                });
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
            }
        });
        return v;
    }
    public void Alarm(Calendar calendar, int i)
    {
        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(myDialog.getContext(), i, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        if (calendar.before(Calendar.getInstance()))
        {
            calendar.add(Calendar.DATE, 1);
        }
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pendingIntent);
        intent.putExtra("toggle","on");
    }
    public void UpdateTimeText(Calendar calendar, Context context)
    {
        String timeText = "Chuông báo vào:";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());
        Toast.makeText(context, timeText ,Toast.LENGTH_SHORT).show();
    }
    public void CancelAlarm( int i){
        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(myDialog.getContext(), i, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        intent.putExtra("toggle","off");
    }


    class docJSON extends AsyncTask<String, Integer, String>{
        @Override
        protected String doInBackground(String... strings) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
    public void getJson ()
    {

    }
    public  void writeJson(View v)
    {

    }
}
