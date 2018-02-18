package com.nepal.naxa.smartnaari.calendraevent;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;



class FireworkLayout extends SurfaceView implements SurfaceHolder.Callback {

    class GameThread extends Thread {
        private boolean mRun = false;

        private SurfaceHolder surfaceHolder;
        private AnimateState state;
        private Context context;
        private Handler handler;
        private Paint paint;
        Fireworks fireworks;

        GameThread(SurfaceHolder surfaceHolder, Context context, Handler handler) {
            this.surfaceHolder = surfaceHolder;
            this.context = context;
            this.handler = handler;

            fireworks = new Fireworks(getWidth(), getHeight());

            paint = new Paint();
            paint.setStrokeWidth(2 / getResources().getDisplayMetrics().density);
            paint.setColor(Color.BLACK);
            paint.setAntiAlias(true);
        }

        public void doStart() {
            synchronized (surfaceHolder) {
                setState(AnimateState.asRunning);
            }
        }

        public void pause() {
            synchronized (surfaceHolder) {
                if (state == AnimateState.asRunning)
                    setState(AnimateState.asPause);
            }
        }

        public void unpause() {
            setState(AnimateState.asRunning);
        }

        @Override
        public void run() {
            while (mRun) {
                Canvas c = null;
                try {
                    c = surfaceHolder.lockCanvas(null);

                    synchronized (surfaceHolder) {
                        if (state == AnimateState.asRunning)
                            doDraw(c);
                    }
                } finally {
                    if (c != null) {
                        surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
        }

        public void setRunning(boolean b) {
            mRun = b;
        }

        public void setState(AnimateState state) {
            synchronized (surfaceHolder) {
                this.state = state;
            }
        }

        public void doDraw(Canvas canvas) {
            fireworks.doDraw(canvas, paint);
        }

        public void setSurfaceSize(int width, int height) {
            synchronized (surfaceHolder) {
                fireworks.reshape(width, height);
            }
        }
    }

    private GameThread thread;

    @SuppressLint("HandlerLeak")
    public FireworkLayout(Context context) {
        super(context);

        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        getHolder().addCallback(this);

        thread = new GameThread(holder, context, new Handler() {
            @Override
            public void handleMessage(Message m) {

            }
        });

        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        thread.setSurfaceSize(width, height);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.doStart();
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        thread.setRunning(false);

        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }


}