package com.example.stopwatch;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.os.Bundle;
import java.util.Locale;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends Activity {


    // Số giây được hiển thị trên đồng hồ bấm giờ
    private int seconds = 0;

    // Xem đồng hồ bấm giờ có đang chạy không ?
    private boolean running;

    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        if (savedInstanceState != null) {

            // Nhận trạng thái trước đó của đồng hồ nếu activity bị destroy và tạo lại
            seconds
                    = savedInstanceState
                    .getInt("seconds");
            running
                    = savedInstanceState
                    .getBoolean("running");
            wasRunning
                    = savedInstanceState
                    .getBoolean("wasRunning");
        }
        runTimer();
    }

    // Lưu trạng thái của đồng hồ nếu nó sắp bị destroy
    @Override
    public void onSaveInstanceState(
            Bundle savedInstanceState)
    {
        savedInstanceState
                .putInt("seconds", seconds);
        savedInstanceState
                .putBoolean("running", running);
        savedInstanceState
                .putBoolean("wasRunning", wasRunning);
    }

    // Nếu activity bị pause thì dừng đồng hồ bấm giờ
    @Override
    protected void onPause()
    {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    // Nếu activity resume thì bắt đầu đồng hồ, 1 lần nữa nếu nó đã chạy trước đó
    @Override
    protected void onResume()
    {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    // Bắt đầu đồng hồ bấm giờ khi click vào button Start
    public void onClickStart(View view)
    {
        running = true;
    }

    // Dừng đồng hồ bấm giờ khi click vào button Stop
    public void onClickStop(View view)
    {
        running = false;
    }

    // Đặt lại đồng hồ bấm giờ khi click vào button Reset
    public void onClickReset(View view)
    {
        running = false;
        seconds = 0;
    }

    // Phương thức runTimer để tăng số giây và cập nhật vào Text View
    private void runTimer()
    {

        // Nhận Text View
        final TextView timeView
                = (TextView)findViewById(
                R.id.time_view);

        // Tạo new Handler
        final Handler handler
                = new Handler();

        // Gọi phương thức post() truyền vào new Runnable
        handler.post(new Runnable() {
            @Override

            public void run()
            {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                // Chuyển giây thành giờ:phút:giây
                String time
                        = String
                        .format(Locale.getDefault(),
                                "%d:%02d:%02d", hours,
                                minutes, secs);

                // Đặt text dạng xem văn bản
                timeView.setText(time);

                // Nếu đang chạy sẽ tăng biến second
                if (running) {
                    seconds++;
                }

                // Xử lý độ trễ với handler post
                handler.postDelayed(this, 1000);
            }
        });
    }
}