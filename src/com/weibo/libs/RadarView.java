package com.weibo.libs;

import java.util.Timer;
import java.util.TimerTask;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class RadarView extends FrameLayout {

	private int radarSize = 400;
	private int viewSize = 400;
	private Paint mPaintLine;
	private Paint mPaintSector;
	private Paint mPaintSector1;
	private Paint mPaintText;

	public boolean isstart = false;
	private Timer mTimer;
	private int start = 0;

	private static final int WIDTH = 720;
	private static final int HEIGHT = 1200;
	private static final int FONT_SIZE = 35;
	private float radio = 1f;
	private float radioHeight = 1f;
	private float radioWidth = 1f;
	private int fontSize = 35;

	public RadarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setBackgroundColor(Color.TRANSPARENT);
		onResume();
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
		mPaintSector.setStrokeWidth(radarSize / 10);
		mPaintSector.setAlpha(100);
		mPaintSector.setAntiAlias(true);

		mPaintSector1 = new Paint();
		mPaintSector1.setStyle(Style.STROKE);
		mPaintSector1.setStrokeWidth(3);
		mPaintSector.setAlpha(100);
		mPaintSector1.setAntiAlias(true);

		mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);
		mPaintText.setTextSize(fontSize);

		Shader mShader = new SweepGradient(viewSize / 2, viewSize / 2,
				new int[] { Color.WHITE, Color.parseColor("#FFE4E1") },
				new float[] { 0.5f, 1 });
		mPaintSector.setShader(mShader);

		mShader = new SweepGradient(viewSize / 2, viewSize / 2, new int[] {
				Color.WHITE, Color.parseColor("#EE3B3B") }, null);
		mPaintSector1.setShader(mShader);
	}

	public void setradarSize(int width, int height) {
		this.viewSize = width - width / 8;
		this.radarSize = width - width / 8;

		this.radioWidth = (float) width / WIDTH;
		this.radioHeight = (float) height / HEIGHT;
		this.radio = Math.min(this.radioWidth, this.radioHeight);
		this.fontSize = Math.round(FONT_SIZE * radio);

		setMeasuredDimension(viewSize, viewSize);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(viewSize, viewSize);
	}

	public void onResume() {
		if (mTimer != null) {
			mTimer.cancel();
		}
		mTimer = new Timer(true);
		mTimer.schedule(new UpdateTask(this), 0, 15);// 启动定时器
	}

	public void onPause() {
		if (mTimer != null) {
			mTimer.cancel();
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (mPaintLine == null) {
			initPaint();
		}

		float baseLeft = viewSize / 2 - 80 * radioWidth;
		float baseTop = viewSize / 2 - 80 * radioHeight;

		/* draw text */
		mPaintText.setColor(Color.GREEN);
		// canvas.drawText("买入: " + this.showPriceModel.getInnerPrice(),
		// baseLeft , baseTop, mPaintText);
		//
		// mPaintText.setColor(Color.RED);
		// canvas.drawText("卖出: " + this.showPriceModel.getOutPrice(), baseLeft,
		// baseTop + 45 * radioHeight, mPaintText);
		//
		// mPaintText.setColor(Color.BLACK);
		// canvas.drawText("最高: " + this.showPriceModel.getTodayHigh(),
		// baseLeft, baseTop + 95 * radioHeight, mPaintText);
		// canvas.drawText("最低: " + this.showPriceModel.getTodayLow(), baseLeft,
		// baseTop + 135 * radioHeight, mPaintText);
		//
		// String dirStr = this.showPriceModel.getDirection();
		//
		canvas.drawText("买入: 000", baseLeft, baseTop, mPaintText);
		canvas.drawText("卖出: 000", baseLeft, baseTop + 45 * radioHeight,
				mPaintText);

		mPaintText.setColor(Color.BLACK);
		canvas.drawText("最高: " + "000", baseLeft, baseTop + 95 * radioHeight,
				mPaintText);
		canvas.drawText("最低: " + "000", baseLeft, baseTop + 135 * radioHeight,
				mPaintText);

		String dirStr = "U";

		String direction = (dirStr.equals("D")) ? "下跌" : "上涨";
		if (direction.equals("上涨")) {
			mPaintText.setColor(Color.RED);
		} else {
			mPaintText.setColor(Color.GREEN);
		}
		canvas.drawText("趋势: " + direction, baseLeft, baseTop + 175
				* radioHeight, mPaintText);

		canvas.drawCircle(viewSize / 2, viewSize / 2, radarSize / 4, mPaintLine);
		// canvas.drawCircle(radarSize / 2, radarSize / 2, 110, mPaintLine);
		canvas.drawCircle(viewSize / 2, viewSize / 2, radarSize * 9 / 20,
				mPaintLine);

		canvas.drawLine(viewSize / 2, viewSize / 2 - (radarSize * 9 / 20),
				viewSize / 2, viewSize / 2 + (radarSize * 9 / 20), mPaintLine);
		canvas.drawLine(viewSize / 2 - (radarSize * 9 / 20), viewSize / 2,
				viewSize / 2 + (radarSize * 9 / 20), viewSize / 2, mPaintLine);

		if (matrix == null) {
			matrix = new Matrix();
		}

		canvas.concat(matrix);
		canvas.drawCircle(viewSize / 2, viewSize / 2, radarSize * 8 / 20,
				mPaintSector);
		canvas.drawCircle(viewSize / 2, viewSize / 2, (radarSize * 7 / 20) + 1,
				mPaintSector1);

		super.onDraw(canvas);
	}

	private Matrix matrix;

	private final class UpdateTask extends TimerTask {

		private RadarView view;

		public UpdateTask(RadarView view) {
			this.view = view;
		}

		@Override
		public void run() {
			view.post(new Runnable() {
				public void run() {
					start = start + 1;
					matrix = new Matrix();
					matrix.postRotate(start, viewSize / 2, viewSize / 2);
					view.invalidate();
				}
			});
		}
	}
}
