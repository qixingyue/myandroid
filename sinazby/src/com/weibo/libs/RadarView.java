package com.weibo.libs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class RadarView extends FrameLayout implements Runnable {

	private int mViewSize;
	private Paint mPaintLine;
	private Paint mPaintSector;
	private Paint mPaintSector1;
	private final Handler handler = new Handler(Looper.getMainLooper());

	public boolean isstart = false;
	private int start = 0;

	public RadarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setBackgroundColor(Color.TRANSPARENT);
	}

	public RadarView(Context context) {
		this(context, null);
	}

	private void initPaint() {
		mPaintLine = new Paint();
		mPaintLine.setStrokeWidth(1);
		mPaintLine.setAntiAlias(true);
		mPaintLine.setStyle(Style.STROKE);
		mPaintLine.setColor(Color.parseColor("#FFC1C1"));

		mPaintSector = new Paint();
		mPaintSector.setStyle(Style.STROKE);
		mPaintSector.setStrokeWidth(mViewSize / 10);
		mPaintSector.setAlpha(100);
		mPaintSector.setAntiAlias(true);

		mPaintSector1 = new Paint();
		mPaintSector1.setStyle(Style.STROKE);
		mPaintSector1.setStrokeWidth(3);
		mPaintSector.setAlpha(100);
		mPaintSector1.setAntiAlias(true);

		Shader mShader = new SweepGradient(mViewSize / 2, mViewSize / 2,
				new int[] { Color.WHITE, Color.parseColor("#FFE4E1") },
				new float[] { 0.5f, 1 });
		mPaintSector.setShader(mShader);

		mShader = new SweepGradient(mViewSize / 2, mViewSize / 2, new int[] {
				Color.WHITE, Color.parseColor("#EE3B3B") }, null);
		mPaintSector1.setShader(mShader);

	}


	// @Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
		mViewSize = parentWidth ;
		setMeasuredDimension(parentWidth, parentWidth);
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

	@Override
	protected void onDraw(Canvas canvas) {
		if (mPaintLine == null) {
			initPaint();
		}

		canvas.drawCircle(mViewSize / 2, mViewSize / 2, mViewSize / 4, mPaintLine);
		canvas.drawCircle(mViewSize / 2, mViewSize / 2, mViewSize * 9 / 20,
				mPaintLine);

		canvas.drawLine(mViewSize / 2, mViewSize / 2 - (mViewSize * 9 / 20),
				mViewSize / 2, mViewSize / 2 + (mViewSize * 9 / 20), mPaintLine);
		canvas.drawLine(mViewSize / 2 - (mViewSize * 9 / 20), mViewSize / 2,
				mViewSize / 2 + (mViewSize * 9 / 20), mViewSize / 2, mPaintLine);

		if (matrix == null) {
			matrix = new Matrix();
		}

		canvas.concat(matrix);
		canvas.drawCircle(mViewSize / 2, mViewSize / 2, mViewSize * 8 / 20,
				mPaintSector);
		canvas.drawCircle(mViewSize / 2, mViewSize / 2, (mViewSize * 7 / 20) + 1,
				mPaintSector1);

		super.onDraw(canvas);
	}

	private Matrix matrix;


	@Override
	public void run() {
		start = start + 1;
		matrix = new Matrix();
		matrix.postRotate(start, mViewSize / 2, mViewSize / 2);
		invalidate();
		handler.postDelayed(this, 1000 / 60);
	}
}
