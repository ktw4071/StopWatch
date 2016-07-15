package com.example.ccei.stopwatch;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;


public class MainActivity extends AppCompatActivity {
    private EditText timer;

    int progressDialogIncrement_second_milli;
    int progressDialogIncrement_second;
    int progressDialogIncrement_min;
    int progressDialogIncrement_hour;
    AsyncStopWatch myTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        timer = (EditText)findViewById(R.id.timer);

        Button btn_clear = (Button)findViewById(R.id.btn_clear);
        final ToggleButton btn_toggle = (ToggleButton)findViewById(R.id.btn_toggle);

        btn_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn_toggle.isChecked()){
                    myTimer = new AsyncStopWatch();
                    myTimer.execute(progressDialogIncrement_second, progressDialogIncrement_min, progressDialogIncrement_hour, progressDialogIncrement_second_milli);
                    Toast.makeText(MainActivity.this, "사용시작", Toast.LENGTH_SHORT).show();
                }
                else{
                    myTimer.cancel(true);
                    Toast.makeText(MainActivity.this, "사용종료", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!myTimer.isCancelled()){
                    myTimer.cancel(true);
                    btn_toggle.setChecked(false);
                }

                timer.setText("0 : 0 : 0초");
                progressDialogIncrement_second = 0;
                progressDialogIncrement_min = 0;
                progressDialogIncrement_hour = 0;
                progressDialogIncrement_second_milli = 0;

            }
        });



//        btn_start.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final AsyncStopWatch myTimer;
//                if(!newTimer) {
//                    myTimer.execute(progressDialogIncrement_second, progressDialogIncrement_min, progressDialogIncrement_hour, progressDialogIncrement_second_milli);
//
//                }
//
//                else {
//                    myTimer = new AsyncStopWatch();
//                    myTimer.execute(progressDialogIncrement_second, progressDialogIncrement_min, progressDialogIncrement_hour, progressDialogIncrement_second_milli);
//                    newTimer = !newTimer;
//                    //new AsyncStopWatch().execute(0, 0, 0, 0);
//                }
//            }
//        });
////
//        btn_stop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                myTimer.cancel(true);
//                newTimer = !newTimer;
//            }
//        });
    }


    private class AsyncStopWatch extends AsyncTask<Integer, Integer, String>{
        private boolean threadFlag;
        private boolean threadStop = false;
        @Override
        protected String doInBackground(Integer... startTaskObj) {

            progressDialogIncrement_second_milli = startTaskObj[3];

            progressDialogIncrement_second = startTaskObj[0];
            //startTaskObj[0].intValue() = progressDialogIncrement_second;
            progressDialogIncrement_min = startTaskObj[1];
            //startTaskObj[1].intValue() = progressDialogIncrement_min;
            progressDialogIncrement_hour = startTaskObj[2];
            while(!isCancelled()){
                try{
                    /*if(threadStop == true){
                        wait();
                    }
                    */
                    //isCancelled();
                    //wait();

                    Thread.sleep(100);

                    if(progressDialogIncrement_min >= 60){
                        startTaskObj[2] =  ++progressDialogIncrement_hour;
                        //publishProgress(startTaskObj);
                        //threadFlag = true;
                    }

                    if(progressDialogIncrement_second  >= 60){
                        startTaskObj[1] = ++progressDialogIncrement_min;
                        progressDialogIncrement_second = 0;
                        //publishProgress(startTaskObj);
                        //threadFlag = true;
                    }

                    if(progressDialogIncrement_second_milli  >= 9){
                        startTaskObj[0] = ++progressDialogIncrement_second;
                        progressDialogIncrement_second_milli = 0;
                        //publishProgress(startTaskObj);
                        //threadFlag = true;
                    }

                    else{
                        startTaskObj[3] = ++progressDialogIncrement_second_milli;
                        //publishProgress(startTaskObj);
                        //publishProgress(progressDialogIncrement_min);
                        //publishProgress(++progressDialogIncrement_second);
                        //publishProgress();
                    }

                    publishProgress(startTaskObj);
                }catch (InterruptedException e){
                    Log.e("BACK_THREAD_TAG", "THREAD IS KILLED!");
                }
            }
            return "BACKGROUND THREAD IS FINISHED!";
        }


        @Override
        protected void onProgressUpdate(Integer... progressValues) {
            timer.setText(progressValues[2].toString() + " : " + progressValues[1].toString() + " : " + progressValues[0].toString() + "." +  progressValues[3].toString() + "초");
        }

        //        @Override
//        protected void onProgressUpdate(ArrayList<Integer>... progressValues) {
//            timer.setText("0분" + " : " + progressValues[0].toString() + "초");
//        }
    }
}
