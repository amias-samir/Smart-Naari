package com.nepal.naxa.smartnaari.application;

import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;

import java.util.concurrent.TimeUnit;

public class NotificationJob extends Job {
    public static final String TAG = "notification_job";

    @Override
    @NonNull
    protected Result onRunJob(@NonNull Params params) {
        NotificationUtils.createServicesFeedbackNotification();
        return Result.SUCCESS;
    }

    public void showNotificationInFewDays(int days) {
        int jobId = new JobRequest.Builder(NotificationJob.TAG)
                .setExact(TimeUnit.DAYS.toMillis(days))
                .build()
                .schedule();

    }

    public void showNotificationImmediately() {
        int jobId = new JobRequest.Builder(NotificationJob.TAG)
                .startNow()
                .build()
                .schedule();


    }

    private void cancelJob(int jobId) {
        JobManager.instance().cancel(jobId);
    }

}
