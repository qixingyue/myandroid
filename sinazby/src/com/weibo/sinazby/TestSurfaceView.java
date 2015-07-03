package com.weibo.sinazby;
import java.util.Timer;
import java.util.TimerTask;

import com.weibo.config.Const;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
 
public class TestSurfaceView extends Activity {

    SurfaceView sfv;
    SurfaceHolder sfh;
 
    private Timer mTimer;
    private MyTimerTask mTimerTask;
    int Y_axis[],//保存正弦波的Y轴上的点
    centerY,//中心线
    oldX,oldY,//上一个XY点 
    currentX;//当前绘制到的X轴上的点
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
//        sfv = (SurfaceView) this.findViewById(R.id.SurfaceView01);
//        sfh = sfv.getHolder();
//        //动态绘制正弦波的定时器
//        mTimer = new Timer();
//        mTimerTask = new MyTimerTask();
// 
//        // 初始化y轴数据
//        centerY = (getWindowManager().getDefaultDisplay().getHeight() - sfv
//                .getTop()) / 2;
//        Y_axis = new int[getWindowManager().getDefaultDisplay().getWidth()];
//        
//        for (int i = 1; i < Y_axis.length; i++) {// 计算正弦波
//            Y_axis[i - 1] = centerY
//                    - (int) (500 * Math.sin(i * 2 * Math.PI / 180));
//        }
//        
//        sfh.addCallback(new MyCallBack());
    }
 
    class MyCallBack implements Callback{

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			mTimer.schedule(mTimerTask, 0,1);
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			
		}
    	
    }
    
 
    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
        	
            SimpleDraw(currentX);
            currentX += 1;//往前进
            if (currentX >= Y_axis.length - 1) {//如果到了终点，则清屏重来
            	Log.i(Const.TAG, "CLEAN");
                ClearDraw();
                currentX = 0;
                oldY = centerY;
            }
        }
 
    }
     
    /**
     * 绘制指定区域
     */
    void SimpleDraw(int length) {
        if (length == 0)
            oldX = 0;
        Canvas canvas = sfh.lockCanvas(new Rect(oldX, 0, oldX + length,
                getWindowManager().getDefaultDisplay().getHeight()));// 关键:获取画布
 
        Paint mPaint = new Paint();
        mPaint.setColor(Color.GREEN);// 画笔为绿色
        mPaint.setStrokeWidth(2);// 设置画笔粗细
 
        int y;
        for (int i = oldX + 1; i < length; i++) {// 绘画正弦波
            y = Y_axis[i - 1];
            canvas.drawLine(oldX, oldY, i, y, mPaint);
            oldX = i;
            oldY = y;
        }
        sfh.unlockCanvasAndPost(canvas);// 解锁画布，提交画好的图像
    }
 
    void ClearDraw() {
        Canvas canvas = sfh.lockCanvas(null);
        canvas.drawColor(Color.BLACK);// 清除画布
        sfh.unlockCanvasAndPost(canvas);
    }
}