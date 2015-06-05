package com.weibo.libs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;

public class NBView extends FrameLayout implements Runnable {

	private int mViewSize;
	private final Handler handler = new Handler(Looper.getMainLooper());
	private Paint mPaintLine;
	private Paint mPaintInnerLine;

	private float circleR;
	private float start = 0;
	private float[] lastPoint = null;
	private float[] cPoint = null;
	

	public NBView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// setBackgroundColor(Color.TRANSPARENT);
		setBackgroundColor(Color.BLACK);
	}

	public NBView(Context context) {
		this(context, null);
	}

	// @Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
		mViewSize = parentWidth;
		setMeasuredDimension(parentWidth, parentWidth);
		// circleR = mViewSize / 2 - 60;
		circleR = 500;
	}

	@Override
	protected void onDraw(Canvas canvas) {

		
		if (mPaintLine == null) {
			initPaint();
		}
		canvas.drawCircle(mViewSize / 2, mViewSize / 2, circleR, mPaintLine);

		if (null == lastPoint) {
			lastPoint = cPoint = getPoint();
		} else {
			cPoint = getPoint();
		}

		Log.i("POINT", lastPoint[0] + "" + lastPoint[1] + "");
		Log.i("POINT", cPoint[0] + "" + cPoint[1] + "");

		canvas.drawLine(lastPoint[0], lastPoint[1], cPoint[0], cPoint[1],
				mPaintInnerLine);
		lastPoint = cPoint;

		super.onDraw(canvas);

	}

	private float[] getPoint() {
		double hd = start / 180 * Math.PI;
		int N = 3;
		int M = 170;
		float[] aimPoint = new float[2];
		aimPoint[0] = (float) (Math.cos(hd) + Math.cos(N * hd) / N)
				* (circleR - M) + circleR;
		aimPoint[1] = (float) (Math.sin(hd) - Math.sin(N * hd) / N)
				* (circleR - M) + circleR;
		return aimPoint;
	}

	private void initPaint() {
		mPaintLine = new Paint();
		mPaintLine.setStrokeWidth(3);
		mPaintLine.setAntiAlias(true);
		mPaintLine.setStyle(Style.STROKE);
		mPaintLine.setColor(Color.parseColor("#FFC1C1"));

		mPaintInnerLine = new Paint();
		mPaintInnerLine.setStrokeWidth(30);
		mPaintInnerLine.setAntiAlias(true);
		mPaintInnerLine.setStyle(Style.STROKE);
		mPaintInnerLine.setColor(Color.parseColor("#00CCC1"));

		// mPaintSector = new Paint();
		// mPaintSector.setStyle(Style.STROKE);
		// mPaintSector.setStrokeWidth(mViewSize / 10);
		// mPaintSector.setAlpha(100);
		// mPaintSector.setAntiAlias(true);
		//
		// mPaintSector1 = new Paint();
		// mPaintSector1.setStyle(Style.STROKE);
		// mPaintSector1.setStrokeWidth(3);
		// mPaintSector.setAlpha(100);
		// mPaintSector1.setAntiAlias(true);
		//
		// Shader mShader = new SweepGradient(mViewSize / 2, mViewSize / 2,
		// new int[] { Color.WHITE, Color.parseColor("#FFE4E1") },
		// new float[] { 0.5f, 1 });
		// mPaintSector.setShader(mShader);
		//
		// mShader = new SweepGradient(mViewSize / 2, mViewSize / 2, new int[] {
		// Color.WHITE, Color.parseColor("#EE3B3B") }, null);
		// mPaintSector1.setShader(mShader);

	}

	@Override
	public void run() {
		start = start + 1;
		invalidate();
		handler.postDelayed(this, 1000 / 16);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		handler.post(this);
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		handler.removeCallbacksAndMessages(null);
	}
}
