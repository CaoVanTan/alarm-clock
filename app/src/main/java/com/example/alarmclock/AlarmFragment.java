package com.example.alarmclock;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.app.Dialog;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.content.res.AssetManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.util.ArrayList;
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
    SwitchCompat switchCompat;
    AlarmDbHelper alarmDb;
    ArrayList<Alarm_class> alarm_list;
    ListAdapter_Alarm alarm_Adapter;


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
        myDialog = new Dialog(this.getContext());
        btn_them = v.findViewById(R.id.Add_alarm);
        createNotificationChannnel();
        // Do du lieu vao listView


        listView_alarm =(ListView)v.findViewById(R.id.list_alarm);
        alarmDb = new AlarmDbHelper(getContext());

        ListAdapter_Alarm alarm_Adapter;
        alarm_list = alarmDb.getAlarm();
        alarm_Adapter = new ListAdapter_Alarm(getContext(), R.layout.list_view_alarrm, alarm_list);
        listView_alarm.setAdapter(alarm_Adapter);
        listView_alarm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switchCompat = (SwitchCompat) view.findViewById(R.id.SwitchAlarm);
                Integer id = alarm_list.get(position).getId();
                String date = alarm_list.get(position).getThoigian();
                Integer min = alarm_list.get(position).getPhut();
                Integer hour = alarm_list.get(position).getGio();
                Log.e("Id", id.toString());
                switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        Log.e("Id", id.toString());
                        if(isChecked == true)
                        {
                            SetAlarm(hour, min, date,id);
                            alarmDb.updateAlarm_isChecked(id,isChecked);
                        }
                        else {
                            CancelAlarm(id);
                            alarmDb.updateAlarm_isChecked(id,isChecked);
                        }
                    }
                });
            }
        });

        listView_alarm.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                confirmDialog(i);
                return false;
            }
        });

        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.setContentView(R.layout.new_alarm_popup);
                btn_Huy =(Button) myDialog.findViewById(R.id.close_btn);
                timePicker = myDialog.findViewById(R.id.TimePikerAlarm);
                timePicker.setIs24HourView(true);
                calendar = Calendar.getInstance();
                //Event click Huy
                btn_Huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });

                // Event click tao moi
                btn_Ok = (Button) myDialog.findViewById(R.id.confirm_btn);
                btn_Ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                        calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
                        calendar.set(Calendar.SECOND, 0);
                        String Time = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());
                        Boolean On = true;
                        Alarm_class new_alarm = new Alarm_class(Time,timePicker.getCurrentHour(),timePicker.getCurrentMinute(), On);
                        alarmDb.insertAlarm(new_alarm);
                        Integer Id = alarmDb.getID(Time, timePicker.getCurrentHour(),timePicker.getCurrentMinute());
                        UpdateTimeText(Time, v.getContext());
                        Alarm(calendar, Id);
                        myDialog.dismiss();
                        LoadListView(listView_alarm);
                    }
                });
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
            }
        });
        return v;
    }

    private void confirmDialog(int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Xóa " + alarm_list.get(pos).getThoigian() + "?");
        builder.setMessage("Bạn có muốn xóa không?");

        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alarmDb.deleteAlarm(alarm_list.get(pos).getId());
//                alarm_Adapter.clear();
                alarm_list = alarmDb.getAlarm();
                alarm_Adapter = new ListAdapter_Alarm(getContext(), R.layout.list_view_alarrm, alarm_list);
                listView_alarm.setAdapter(alarm_Adapter);
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


    public void Alarm(Calendar calendar, int i)
    {
        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
        Log.e("Set New Alarm:", String.valueOf(i));
        intent.putExtra("text", calendar.getTime().toString());
        intent.putExtra("toggle","on");
        pendingIntent = PendingIntent.getBroadcast(myDialog.getContext(), i, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        if (calendar.before(Calendar.getInstance()))
        {
            calendar.add(Calendar.DATE, 1);
        }
        Log.e("Thoi gian :", calendar.getTime().toString());
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pendingIntent);

    }
    public void SetAlarm(int gio, int phut , String text ,int i)
    {
        Calendar setCalendar = Calendar.getInstance();
        setCalendar.set(Calendar.HOUR, gio);
        setCalendar.set(Calendar.MINUTE, phut);
        setCalendar.set(Calendar.SECOND,0);
        Log.e("Thoi gian :", setCalendar.getTime().toString());
        Log.e("SetAlarm", String.valueOf(i));
        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
        intent.putExtra("text", text);
        intent.putExtra("toggle","on");
        pendingIntent = PendingIntent.getBroadcast(myDialog.getContext(), i, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        if (setCalendar.before(Calendar.getInstance()))
        {
            setCalendar.add(Calendar.DATE, 1);
            Log.e("Thoi gian :", setCalendar.getTime().toString());
        }
        alarmManager.set(AlarmManager.RTC_WAKEUP,setCalendar.getTimeInMillis(),pendingIntent);
        
    }
    public void UpdateTimeText(String time, Context context)
    {
        String timeText = "Chuông báo vào:";
        timeText += time;
        Toast.makeText(context, timeText ,Toast.LENGTH_SHORT).show();
    }
    public void CancelAlarm( int i){
        Log.e("CancelAlarm: ", String.valueOf(i));
        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(myDialog.getContext(), i, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        intent.putExtra("toggle","off");
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
    }
    public void LoadListView(ListView listView_alarm){
        alarm_list = alarmDb.getAlarm();
        alarm_Adapter = new ListAdapter_Alarm(getContext(), R.layout.list_view_alarrm, alarm_list);
        listView_alarm.setAdapter(alarm_Adapter);
    }
    public void createNotificationChannnel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Alarm";
            String des = "Alarm Manager";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel serviceChannel = new NotificationChannel( "Alarm", name,importance);
            NotificationManager manager = getActivity().getSystemService(NotificationManager.class);
            serviceChannel.setDescription(des);
            manager.createNotificationChannel(serviceChannel);
        }
    }

}
