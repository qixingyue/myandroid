package com.weibo.libs;

import java.util.Timer;
import java.util.TimerTask;

import com.weibo.config.Const;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class DemoSurfaceView extends SurfaceView {

	private Timer mTimer;
	private Paint paint;
	private int pointPos;
	private SurfaceHolder holder = null; // 控制对象
	private int centerX = 300, centerY = 300;
	private int[] circleRs;

	public DemoSurfaceView(Context context) {
		this(context, null);
	}

	public DemoSurfaceView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public DemoSurfaceView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		holder = getHolder();
		holder.addCallback(new MyCallBack());
		paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(Color.GREEN);
		paint.setStrokeWidth(10);
		circleRs = new int[32];
		for (int i = 0; i < 32; i++) {
			circleRs[i] = 200 + i * 100;
		}
		pointPos = 0;
	}

	class MyTask implements Runnable {

		@Override
		public void run() {

			for (int i = 0; i < 32; i++) {

				Canvas canvas = holder.lockCanvas();// 关键:获取画布
				canvas.drawLine(500, 0, 0, circleRs[i], paint);
				holder.unlockCanvasAndPost(canvas);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				pointPos++;
			}
		}

	}

	class MyCallBack implements Callback {

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			new Thread(new MyTask()).start();
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {

		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {

		}

	}

}
