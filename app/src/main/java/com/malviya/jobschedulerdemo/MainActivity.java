package com.malviya.jobschedulerdemo;

import android.app.Activity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Prafulla on 12/6/2016.
 */
public class MainActivity extends Activity {

    public static final int JOB_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_schedule_job).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                scheduleJob();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void scheduleJob() {
        ComponentName serviceName = new ComponentName(this, MyJobService.class);
        JobInfo jobInfo = new JobInfo.Builder(JOB_ID, serviceName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setRequiresDeviceIdle(true)
                .setRequiresCharging(true)
//                .setOverrideDeadline(400) // Remove comment for faster testing.
                .build();

        JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        int result = scheduler.schedule(jobInfo);
        if (result == JobScheduler.RESULT_SUCCESS) {
            Toast.makeText(this, R.string.job_scheduled_successfully, Toast.LENGTH_LONG).show();
        }
    }
}