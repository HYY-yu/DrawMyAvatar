package com.app.feng.drawmyavatar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;

import com.app.feng.drawmyavatar.R;

/**
 * 头像
 * <p>
 * create by feng
 */
public class AvatarView extends View {

    private int avatarR; // 控制图像大小的基本单位
    private int avatarColor;

    private Paint mPaint;

    private final float GEN2 = 1.41421356237f;

    public AvatarView(Context context) {
        super(context);
        init(null,0);
    }

    public AvatarView(Context context,AttributeSet attrs) {
        super(context,attrs);
        init(attrs,0);
    }

    public AvatarView(Context context,AttributeSet attrs,int defStyle) {
        super(context,attrs,defStyle);
        init(attrs,defStyle);
    }

    private void init(AttributeSet attrs,int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(attrs,R.styleable.AvatarView,
                                                                 defStyle,0);

        avatarR = a.getDimensionPixelSize(R.styleable.AvatarView_avatarR,30);
        avatarColor = a.getColor(R.styleable.AvatarView_avatarColor,Color.DKGRAY);
        a.recycle();

        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(avatarColor);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);

        setMinimumWidth(6 * avatarR); // 6R 最小

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        widthSize = Math.min(widthSize,heightSize); // 取正方形

        int width;

        if (widthSize > getMinimumWidth()) {
            // 能够放下
            width = getMinimumWidth();
        } else {
            // 放不下
            width = widthSize;
            //重新计算R
            avatarR = width / 6;
        }

        setMeasuredDimension(width,width);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画圆
        canvas.drawCircle(2 * avatarR,2 * avatarR,avatarR / 2,mPaint);

        //画折线
        Path path = new Path();
        path.moveTo(4 * avatarR,2 * avatarR);
        path.lineTo(4.5f * avatarR,1.5f * avatarR);
        path.lineTo(4 * avatarR,1.5f * avatarR);
        path.lineTo(3.5f * avatarR,2 * avatarR);
        path.lineTo(4 * avatarR,2.5f * avatarR);
        path.lineTo(4.5f * avatarR,2.5f * avatarR);
        path.close();

        canvas.drawPath(path,mPaint);

        //画弧
        RectF rectf = new RectF();
        rectf.left = avatarR * 1.5f;
        rectf.top = avatarR;
        rectf.right = 4.5f * avatarR;
        rectf.bottom = 4f * avatarR;

        Paint arcPaint = new Paint(mPaint);
        arcPaint.setStrokeWidth(avatarR / 4);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeCap(Paint.Cap.ROUND);

        //        Paint test = new Paint();
        //        test.setStyle(Paint.Style.STROKE);
        //        test.setStrokeWidth(2);
        //        canvas.drawRect(rectf,test);

        canvas.drawArc(rectf,45,90,false,arcPaint);

    }

}
