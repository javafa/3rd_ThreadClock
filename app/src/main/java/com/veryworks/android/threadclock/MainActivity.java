package com.veryworks.android.threadclock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    int deviceHeight;
    int deviceWidth;

    int center_x, center_y;

    int LINE = 0;

    double angle = 0;
    double end_x, end_y;

    CustomView stage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics metrics = getResources().getDisplayMetrics();

        // 화면 세로길이
        deviceHeight = metrics.heightPixels;
        // 화면 가로넓이
        deviceWidth = metrics.widthPixels;
        // 중심점 가로
        center_x = deviceWidth / 2;
        // 중심점 세로
        center_y = deviceHeight / 2;
        // 선의길이
        LINE = center_x - 50;

        stage = new CustomView(getBaseContext());
        setContentView(stage);

        // 화면을 그려주는 Thread를 동작시킨다.
        new DrawStage().start();
    }

    // 뷰를 1초에 한번 갱신하는 객체
    class DrawStage extends Thread {
        @Override
        public void run() {
//            super.run();
            while(angle < 360){
                angle = angle + 30;
                stage.postInvalidate();
                try {
                    Thread.sleep(1000); // 1초간 동작을 멈춘다.
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class CustomView extends View {
        Paint paint = new Paint();
        public CustomView(Context context) {
            super(context);
            paint.setColor(Color.BLUE);
            paint.setStrokeWidth(20f);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            // 화면의 중앙부터 12시방향으로 직선을 긋는다
            double angle_temp = angle - 90;
            end_x = Math.cos(angle_temp * Math.PI / 180) * LINE + center_x; // x좌표 구하는 식
            end_y = Math.sin(angle_temp * Math.PI / 180) * LINE + center_y; // y좌표 구하는 식

            canvas.drawLine(center_x,center_y, (float)end_x, (float)end_y, paint);
        }
    }
}
