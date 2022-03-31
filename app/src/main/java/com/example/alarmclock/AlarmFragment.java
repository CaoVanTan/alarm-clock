package com.example.alarmclock;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.app.Dialog;
import android.widget.TimePicker;
import android.widget.Toast;
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_alarm, container, false);
        myDialog = new Dialog(this.getContext());
        btn_them = v.findViewById(R.id.Add_btn);
        calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        long current_time = calendar.getTimeInMillis();
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
                        SimpleDateFormat simpleHour = new SimpleDateFormat("dd:hh-mm-ss");
                        SimpleDateFormat simpleMin = new SimpleDateFormat("mm");
                        long set_time = calendar.getTimeInMillis();
                        Alarm(set_time);
                        myDialog.dismiss();
                        if ((current_time-set_time)<0) // Hom nay
                        {
                            long Time_tday = set_time-current_time;
                            String hour_tday = simpleHour.format(Time_tday);
                            String min_tday  = simpleMin.format(Time_tday);
                            Toast.makeText(v.getContext(),
                                    "Chuông báo sẽ đổ sau "+hour_tday+"giờ, " + min_tday+ " phút nữa!" ,Toast.LENGTH_SHORT).show();
                        }
                        else if((current_time-set_time) >0) { // Ngay mai
                        Toast.makeText(v.getContext(),
                                "Chuông báo sẽ đổ vào "+"giờ" + "phút hôm nay! " ,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
            }
        });
        return v;
    }
    public void Alarm(long calendar)
    {
        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(myDialog.getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar,pendingIntent);
    }
}
