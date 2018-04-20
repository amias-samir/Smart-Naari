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
    protected Result onRunJob(Params params) {
        NotificationUtils.createServicesFeedbackNotification();
        return Result.SUCCESS;
    }

    private int scheduleNotification(int days) {
        int jobId = new JobRequest.Builder(NotificationJob.TAG)
                .setExact(TimeUnit.DAYS.toMillis(days))
                .build()
                .schedule();
        return jobId;
    }

    private int showNotificationImmediately() {
        int jobId = new JobRequest.Builder(NotificationJob.TAG)
                .startNow()
                .build()
                .schedule();

        return jobId;
    }

    private void cancelJob(int jobId) {
        JobManager.instance().cancel(jobId);
    }

}
