package com.model;

import com.tools.UnitUtils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class TopView extends View {

	private int panelWidth;
	private int panelHeght;

	private int viewWidth;
	private int viewHeight;

	private int rectWidth;
	private int rectHeght;

	private int rectTop;
	private int rectLeft;
	private int rectRight;
	private int rectBottom;

	private int lineLen;
	private int statusHeight;
	private static final int LINE_WIDTH = 8;
	private static final int LEFT_PADDING = 50;
	private static final int RIGHT_PADDING = 50;
	private static final String TIPS = "Please put the number in the box";

	private Paint linePaint;
	private Paint wordPaint;
	private Paint mAreaPaint;
	private Rect rect;
	private int baseline;

	private static int validLeft;
	private static int validRight;
	private static int validTop;
	private static int validBottom;
	private boolean valid;

	public TopView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Activity activity = (Activity) context;
		panelWidth = UnitUtils.getScreenWidth(activity);
		panelHeght = UnitUtils.getScreenHeight(activity);
		statusHeight = UnitUtils.getStatusHeight(activity);

		viewHeight = panelHeght - statusHeight;
		viewWidth = panelWidth;

		rectWidth = panelWidth - UnitUtils.dip2px(activity, LEFT_PADDING + RIGHT_PADDING);
		rectHeght = rectWidth;

		rectTop = (viewHeight - rectHeght) / 2;
		rectLeft = (viewWidth - rectWidth) / 2;
		rectBottom = rectTop + rectHeght;
		rectRight = rectLeft + rectWidth;

		lineLen = panelWidth / 10;

		linePaint = new Paint();
		linePaint.setAntiAlias(true);
		linePaint.setColor(Color.rgb(0xdd, 0x42, 0x2f));
		linePaint.setStyle(Style.STROKE);
		linePaint.setStrokeWidth(LINE_WIDTH);
		linePaint.setAlpha(255);

		wordPaint = new Paint();
		wordPaint.setAntiAlias(true);
		wordPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		wordPaint.setStrokeWidth(3);
		wordPaint.setTextSize(35.0f * panelWidth / 720);

		rect = new Rect(rectLeft, rectTop - 80, rectRight, rectTop - 10);
		FontMetricsInt fontMetrics = wordPaint.getFontMetricsInt();
		baseline = rect.top + (rect.bottom - rect.top - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
		wordPaint.setTextAlign(Paint.Align.CENTER);

		mAreaPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mAreaPaint.setColor(Color.BLACK);
		mAreaPaint.setStyle(Style.FILL);
		mAreaPaint.setAlpha(127);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		wordPaint.setColor(Color.TRANSPARENT);
		canvas.drawRect(rect, wordPaint);
		wordPaint.setColor(Color.WHITE);
		linePaint.setStrokeWidth(LINE_WIDTH);
		canvas.drawLine(rectLeft, rectTop, rectLeft + lineLen, rectTop, linePaint);
		canvas.drawLine(rectRight - lineLen, rectTop, rectRight, rectTop, linePaint);
		canvas.drawLine(rectLeft, rectTop, rectLeft, rectTop + lineLen, linePaint);
		canvas.drawLine(rectRight, rectTop, rectRight, rectTop + lineLen, linePaint);
		canvas.drawLine(rectLeft, rectBottom, rectLeft + lineLen, rectBottom, linePaint);
		canvas.drawLine(rectRight - lineLen, rectBottom, rectRight, rectBottom, linePaint);
		canvas.drawLine(rectLeft, rectBottom - lineLen, rectLeft, rectBottom, linePaint);
		canvas.drawLine(rectRight, rectBottom - lineLen, rectRight, rectBottom, linePaint);

		canvas.drawRect(0, 0, viewWidth, rectTop, mAreaPaint);
		canvas.drawRect(0, rectBottom + 1, viewWidth, viewHeight, mAreaPaint);
		canvas.drawRect(0, rectTop, rectLeft - 1, rectBottom + 1, mAreaPaint);
		canvas.drawRect(rectRight + 1, rectTop, viewWidth, rectBottom + 1, mAreaPaint);

		canvas.drawText(TIPS, rect.centerX(), baseline, wordPaint);

		int center_width = rectLeft + (rectRight - rectLeft) / 2;
		int center_height = rectTop + (rectBottom - rectTop) / 2;
		linePaint.setStrokeWidth(LINE_WIDTH / 4);
		canvas.drawLine(center_width - 25, center_height, center_width + 25, center_height, linePaint);
		canvas.drawLine(center_width, center_height - 25, center_width, center_height + 25, linePaint);

		if (valid) {
			linePaint.setColor(Color.GREEN);
			canvas.drawLine(rectLeft + validLeft, rectTop + validTop, rectLeft + validRight, rectTop + validTop,
					linePaint);
			canvas.drawLine(rectLeft + validLeft, rectTop + validBottom, rectLeft + validRight, rectTop + validBottom,
					linePaint);
			canvas.drawLine(rectLeft + validLeft, rectTop + validTop, rectLeft + validLeft, rectTop + validBottom,
					linePaint);
			canvas.drawLine(rectLeft + validRight, rectTop + validTop, rectLeft + validRight, rectTop + validBottom,
					linePaint);
			linePaint.setColor(Color.rgb(0xdd, 0x42, 0x2f));
		}
	}

	public int getRectLeft() {
		return rectLeft;
	}

	public int getRectTop() {
		return rectTop;
	}

	public int getRectRight() {
		return rectRight;
	}

	public int getRectBottom() {
		return rectBottom;
	}

	public int getViewWidth() {
		return viewWidth;
	}

	public int getViewHeight() {
		return viewHeight;
	}

	public void setValid(boolean newValid) {
		valid = newValid;
	}

	public static void setBound(int left, int right, int top, int bottom) {
		validLeft = left;
		validRight = right;
		validTop = top;
		validBottom = bottom;
	}
}
