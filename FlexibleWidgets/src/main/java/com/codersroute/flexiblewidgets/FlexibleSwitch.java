package com.codersroute.flexiblewidgets;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

public class FlexibleSwitch extends View {

    protected int width;
    protected int height;
    protected int speed = 20;
    protected int textSize = 0;
    protected int fontFamilyId = 0;
    protected String textOn;
    protected String textOff;
    protected boolean checked = false;
    private float border = 0f;
    private float strokeBorder;
    private float innerStrokeBorder;
    private float circleDiameter;
    private float innercircleDiameter;
    private Path path, bgPath;
    private Rect textBounds = new Rect();
    private float circleX = 0f, circleY = 0f;
    private float textX = 0f, textY = 0f;

    private RectF outerRect;
    private Paint mPaint;
    private Paint mPaintCircle;
    private Paint bgPaint;
    private Paint textPaint;

    private ValueAnimator animator;
    private boolean isTurningOn = false, isTurningOff = false;
    private boolean isOn = false;
    private boolean isFirstTime = true;
    private boolean showText = false;

    private int strokeColorOnSwitchOn = Color.parseColor("#FF6200EE");
    private int strokeColorOnSwitchOff = Color.parseColor("#FF6200EE");
    private int textColorOnSwitchOn = Color.parseColor("#FFFFFF");
    private int textColorOnSwitchOff = Color.parseColor("#FF6200EE");
    private int bgColorOnSwitchOn = Color.parseColor("#FF6200EE");
    private int bgColorOnSwitchOff = Color.parseColor("#FFFFFF");
    private int thumbColorOnSwitchOn = Color.parseColor("#FFFFFF");
    private int thumbColorOnSwitchOff = Color.parseColor("#FF6200EE");
    private OnStatusChangedListener onStatusChangedListener;

    public FlexibleSwitch(Context context) {
        super(context);
        TextView v = new TextView(context);
        outerRect = new RectF();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(strokeColorOnSwitchOn);
        mPaint.setAntiAlias(true);

        mPaintCircle = new Paint();
        mPaintCircle.setStyle(Paint.Style.FILL);
        mPaintCircle.setColor(thumbColorOnSwitchOff);
        mPaintCircle.setAntiAlias(true);

        bgPaint = new Paint();
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(bgColorOnSwitchOn);
        bgPaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(bgColorOnSwitchOn);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);

        textOn = "ON";
        textOff = "OFF";

        path = new Path();
        bgPath = new Path();
    }

    public FlexibleSwitch(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Resources r = context.getResources();

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.FlexibleSwitch,
                0, 0);

        try {
            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(androidx.appcompat.R.attr.colorPrimary, typedValue, true);
            int color = typedValue.data;
            speed = a.getInteger(R.styleable.FlexibleSwitch_speed, 20);
            textOn = a.getString(R.styleable.FlexibleSwitch_textOn);
            textOff = a.getString(R.styleable.FlexibleSwitch_textOff);
            showText = a.getBoolean(R.styleable.FlexibleSwitch_showText, false);
            checked = a.getBoolean(R.styleable.FlexibleSwitch_android_checked, false);
            fontFamilyId = a.getResourceId(R.styleable.FlexibleSwitch_android_fontFamily, 0);
            border = a.getDimensionPixelSize(R.styleable.FlexibleSwitch_strokeWidth, 0);
            textSize = a.getDimensionPixelSize(R.styleable.FlexibleSwitch_android_textSize, 0);


            strokeColorOnSwitchOn = a.getColor(
                    R.styleable.FlexibleSwitch_strokeColorOnSwitchOn,
                    color);
            strokeColorOnSwitchOff = a.getColor(
                    R.styleable.FlexibleSwitch_strokeColorOnSwitchOff,
                    color);
            bgColorOnSwitchOn = a.getColor(
                    R.styleable.FlexibleSwitch_backgroundColorOnSwitchOn,
                    color);
            bgColorOnSwitchOff = a.getColor(
                    R.styleable.FlexibleSwitch_backgroundColorOnSwitchOff,
                    Color.parseColor("#FFFFFF"));

            textColorOnSwitchOn = a.getColor(
                    R.styleable.FlexibleSwitch_textColorOnSwitchOn,
                    Color.parseColor("#FFFFFF"));

            textColorOnSwitchOff = a.getColor(
                    R.styleable.FlexibleSwitch_textColorOnSwitchOff,
                    color);

            thumbColorOnSwitchOn = a.getColor(
                    R.styleable.FlexibleSwitch_thumbColorOnSwitchOn,
                    Color.parseColor("#FFFFFF"));
            thumbColorOnSwitchOff = a.getColor(
                    R.styleable.FlexibleSwitch_thumbColorOnSwitchOff,
                    color);

        } finally {
            a.recycle();
        }

        outerRect = new RectF();

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        if (checked)
            mPaint.setColor(strokeColorOnSwitchOn);
        else
            mPaint.setColor(strokeColorOnSwitchOff);
        mPaint.setAntiAlias(true);

        mPaintCircle = new Paint();
        mPaintCircle.setStyle(Paint.Style.FILL);
        if (checked)
            mPaintCircle.setColor(thumbColorOnSwitchOn);
        else
            mPaintCircle.setColor(thumbColorOnSwitchOff);
        mPaintCircle.setAntiAlias(true);

        bgPaint = new Paint();
        bgPaint.setStyle(Paint.Style.FILL);
        if (checked)
            bgPaint.setColor(bgColorOnSwitchOn);
        else
            bgPaint.setColor(bgColorOnSwitchOff);
        bgPaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setStyle(Paint.Style.FILL);
        if (checked)
            textPaint.setColor(bgColorOnSwitchOn);
        else
            textPaint.setColor(bgColorOnSwitchOff);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);

        if (textSize > 0) {
            float px = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_PX,
                    textSize,
                    r.getDisplayMetrics()
            );
            textPaint.setTextSize(px);
        }else{
            float px = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_PX,
                    50,
                    r.getDisplayMetrics()
            );
            textPaint.setTextSize(px);
        }

        if (border > 0) {
            border = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_PX,
                    border,
                    r.getDisplayMetrics()
            );
        }

        if (fontFamilyId > 0) {
            textPaint.setTypeface(ResourcesCompat.getFont(getContext(), fontFamilyId));
        }

        if (null == textOn)
            textOn = "ON";
        if (null == textOff)
            textOff = "OFF";

        path = new Path();
        bgPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = getWidth();
        height = getHeight();

        if (0 == width && 0 == height) {
            return;
        }
        init();
        float tempSpeed = innerStrokeBorder * (speed / 100f);
        if (isTurningOn) {
            mPaint.setColor(strokeColorOnSwitchOn);
            mPaintCircle.setColor(thumbColorOnSwitchOn);
            bgPaint.setColor(bgColorOnSwitchOn);
            textPaint.setColor(textColorOnSwitchOn);
            if (((circleX + tempSpeed) + innercircleDiameter / 2f) < (width - innerStrokeBorder / 2f)) {
                circleX += tempSpeed;
            } else {
                turnOffAnimation();
                isOn = true;
                isTurningOn = false;
                isTurningOff = false;
                circleX = (width - (innerStrokeBorder / 2f + innercircleDiameter / 2f));
                if (null != onStatusChangedListener)
                    onStatusChangedListener.onStatusChanged(isOn);
            }

            textX = (strokeBorder / 2f + circleDiameter / 2f) + ((width - (strokeBorder + (circleDiameter + circleDiameter / 2f)))) / 2f;
            textY = (height / 2f);
        } else if (isTurningOff) {
            mPaint.setColor(strokeColorOnSwitchOff);
            mPaintCircle.setColor(thumbColorOnSwitchOff);
            bgPaint.setColor(bgColorOnSwitchOff);
            textPaint.setColor(textColorOnSwitchOff);
            if ((circleX - tempSpeed) - innercircleDiameter / 2f > (innerStrokeBorder / 2f)) {
                circleX -= tempSpeed;
            } else {
                turnOffAnimation();
                isOn = false;
                isTurningOn = false;
                isTurningOff = false;
                circleX = (innerStrokeBorder / 2f + innercircleDiameter / 2f);
                if (null != onStatusChangedListener)
                    onStatusChangedListener.onStatusChanged(isOn);
            }
            textX = (strokeBorder / 2f + circleDiameter) + ((width - (strokeBorder + (circleDiameter + circleDiameter / 2f)))) / 2f;
            textY = (height / 2f);
        }

        canvas.drawPath(bgPath, bgPaint);
        if (showText && !isTurningOn && !isTurningOff) {
            if (isOn) {
                textPaint.getTextBounds(textOn, 0, textOn.length(), textBounds);
                canvas.drawText(textOn, textX, textY + (textBounds.height() - textBounds.bottom) / 2f, textPaint);
            } else {
                textPaint.getTextBounds(textOff, 0, textOff.length(), textBounds);
                canvas.drawText(textOff, textX, textY + (textBounds.height() - textBounds.bottom) / 2f, textPaint);
            }
        }
        canvas.drawCircle(circleX, circleY, innercircleDiameter / 2f, mPaintCircle);
        canvas.drawPath(path, mPaint);
    }

    private void init() {
        if (isFirstTime) {
            if (border == 0f) {
                if (width <= height) {
                    border = width / 6;
                } else {
                    border = height / 6;
                }
            }
            strokeBorder = border / 4;
            innerStrokeBorder = strokeBorder * 8;
            mPaint.setStrokeWidth(strokeBorder);
            circleDiameter = height - strokeBorder;
            innercircleDiameter = height - innerStrokeBorder;

            path.reset();
            outerRect.set(strokeBorder / 2f, strokeBorder / 2f, (strokeBorder / 2f + circleDiameter), height - (strokeBorder / 2f));
            path.addArc(outerRect, -180f, 90f);
            outerRect.set((width - (strokeBorder / 2f)) - (circleDiameter), strokeBorder / 2f, width - (strokeBorder / 2f), height - (strokeBorder / 2f));
            path.arcTo(outerRect, -90f, 180f);
            outerRect.set(strokeBorder / 2f, strokeBorder / 2f, (strokeBorder / 2f + circleDiameter), height - (strokeBorder / 2f));
            path.arcTo(outerRect, 90f, 90f);
            path.close();
            isFirstTime = false;

            bgPath.reset();
            bgPath.addPath(path);

            if (!checked) {
                isOn = false;
                mPaint.setColor(strokeColorOnSwitchOff);
                mPaintCircle.setColor(thumbColorOnSwitchOff);
                bgPaint.setColor(bgColorOnSwitchOff);
                textPaint.setColor(textColorOnSwitchOff);
                circleX = innerStrokeBorder / 2f + innercircleDiameter / 2f;
                textX = (strokeBorder / 2f + circleDiameter) + ((width - (strokeBorder + (circleDiameter + circleDiameter / 2f)))) / 2f;
                textY = (height / 2f);
            } else {
                isOn = true;
                mPaint.setColor(strokeColorOnSwitchOn);
                mPaintCircle.setColor(thumbColorOnSwitchOn);
                bgPaint.setColor(bgColorOnSwitchOn);
                textPaint.setColor(textColorOnSwitchOn);
                circleX = width - (innerStrokeBorder / 2f + innercircleDiameter / 2f);
                textX = (strokeBorder / 2f + circleDiameter / 2f) + ((width - (strokeBorder + (circleDiameter + circleDiameter / 2f)))) / 2f;
                textY = (height / 2f);
            }
            circleY = height / 2f;
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN: {
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                break;
            }

            case MotionEvent.ACTION_UP: {
                if (!isOn) {
                    if (!isTurningOff && !isTurningOn) {
                        isTurningOn = true;
                        isTurningOff = false;
                        turnOnAnimation();
                    } else {
                        isTurningOn = true;
                        isTurningOff = false;
                    }
                } else {
                    if (!isTurningOff && !isTurningOn) {
                        isTurningOff = true;
                        isTurningOn = false;
                        turnOnAnimation();
                    } else {
                        isTurningOff = true;
                        isTurningOn = false;
                    }
                }

                break;
            }
        }

        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(72, widthSize);
        } else {
            width = 72;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(32, heightSize);
        } else {
            height = 32;
        }

        if(!isFirstTime){
            isFirstTime = true;
            border = 0f;
            Resources r = getContext().getResources();
            if(null != r){
                if(height<=width){
                    float px = TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_PX,
                            height/3,
                            r.getDisplayMetrics()
                    );
                    textPaint.setTextSize(px);
                }else{
                    float px = TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_PX,
                            width/3,
                            r.getDisplayMetrics()
                    );
                    textPaint.setTextSize(px);
                }
            }
        }
        setMeasuredDimension(width, height);
    }


    private void turnOnAnimation() {
        if (null == animator) {
            animator = ValueAnimator.ofInt(0, 1);

            animator.addUpdateListener(animation -> {
                if (isTurningOn || isTurningOff) {
                    invalidate();
                }
            });


            animator.setDuration(1000);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.start();
        } else {
            animator.start();
        }
    }

    private void turnOffAnimation() {
        if (null != animator) {
            animator.cancel();
            animator.removeAllUpdateListeners();
            animator = null;
        }
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
        invalidate();
    }

    public void setTextSize(int textSize, int size) {
        this.textSize = size;
        Context c = getContext();
        if (null != c) {
            Resources r = c.getResources();
            float px = TypedValue.applyDimension(
                    size,
                    textSize,
                    r.getDisplayMetrics()
            );
            textPaint.setTextSize(px);
        }
        invalidate();

    }

    public void setStrokeWidth(float strokeWidth) {
        this.border = strokeWidth;
        isFirstTime = true;
        invalidate();
    }

    public void setStrokeWidth(float strokeWidth, int size) {
        Context c = getContext();
        if (null != c) {
            isFirstTime = true;
            Resources r = c.getResources();
            float px = TypedValue.applyDimension(
                    size,
                    strokeWidth,
                    r.getDisplayMetrics()
            );
            this.border = px;
        }
        invalidate();
    }

    public void setFontFamilyId(int fontFamilyId) {
        this.fontFamilyId = fontFamilyId;
        Context c = getContext();
        if (null != c) {
            if (fontFamilyId > 0) {
                textPaint.setTypeface(ResourcesCompat.getFont(c, fontFamilyId));
            }
        }
        invalidate();
    }

    public void setTextOn(String textOn) {
        this.textOn = textOn;
        invalidate();
    }

    public void setTextOff(String textOff) {
        this.textOff = textOff;
        invalidate();
    }

    public void setShowText(boolean showText) {
        this.showText = showText;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        if (isOn != checked) {
            if (!isOn) {
                if (!isTurningOff && !isTurningOn) {
                    isTurningOn = true;
                    isTurningOff = false;
                    turnOnAnimation();
                } else {
                    isTurningOn = true;
                    isTurningOff = false;
                }
            } else {
                if (!isTurningOff && !isTurningOn) {
                    isTurningOff = true;
                    isTurningOn = false;
                    turnOnAnimation();
                } else {
                    isTurningOff = true;
                    isTurningOn = false;
                }
            }
        }
    }

    public boolean isChecked() {
        return checked;
    }

    public void setStrokeColorOnSwitchOn(int strokeColorOnSwitchOn) {
        this.strokeColorOnSwitchOn = strokeColorOnSwitchOn;
        isFirstTime = true;
        invalidate();
    }

    public void setStrokeColorOnSwitchOff(int strokeColorOnSwitchOff) {
        this.strokeColorOnSwitchOff = strokeColorOnSwitchOff;
        isFirstTime = true;
        invalidate();
    }

    public void setTextColorOnSwitchOn(int textColorOnSwitchOn) {
        this.textColorOnSwitchOn = textColorOnSwitchOn;
        isFirstTime = true;
        invalidate();
    }

    public void setTextColorOnSwitchOff(int textColorOnSwitchOff) {
        this.textColorOnSwitchOff = textColorOnSwitchOff;
        isFirstTime = true;
        invalidate();
    }

    public void setBackgroundColorOnSwitchOn(int bgColorOnSwitchOn) {
        this.bgColorOnSwitchOn = bgColorOnSwitchOn;
        isFirstTime = true;
        invalidate();
    }

    public void setBackgroundColorOnSwitchOff(int bgColorOnSwitchOff) {
        this.bgColorOnSwitchOff = bgColorOnSwitchOff;
        isFirstTime = true;
        invalidate();
    }

    public void setThumbColorOnSwitchOn(int thumbColorOnSwitchOn) {
        this.thumbColorOnSwitchOn = thumbColorOnSwitchOn;
        isFirstTime = true;
        invalidate();
    }

    public void setThumbColorOnSwitchOff(int thumbColorOnSwitchOff) {
        this.thumbColorOnSwitchOff = thumbColorOnSwitchOff;
        isFirstTime = true;
        invalidate();
    }

    public interface OnStatusChangedListener {
        void onStatusChanged(boolean check);
    }

    public void addOnStatusChangedListener(OnStatusChangedListener onStatusChangedListener) {
        this.onStatusChangedListener = onStatusChangedListener;
    }
}
