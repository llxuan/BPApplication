package com.tools;

import com.model.TopView;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;

public class ImageProcessing {
	public static final int splitNum = 28;
	public static int threshold = 100;
	public static double denoising = 0.01;
	public static int color = -16777216;

	public static Bitmap binaryzation(Bitmap bitmap) {
		Matrix matrix = new Matrix();
		matrix.postScale(0.7f, 0.7f);
		Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();
		}

		int black = 0;
		int width = bmp.getWidth();
		int height = bmp.getHeight();
		int[] pixels = new int[width * height];
		bmp.getPixels(pixels, 0, width, 0, 0, width, height);
		int alpha = 0xFF << 24;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int grey = pixels[width * i + j];
				alpha = ((grey & 0xFF000000) >> 24);
				int red = ((grey & 0x00FF0000) >> 16);
				int green = ((grey & 0x0000FF00) >> 8);
				int blue = (grey & 0x000000FF);
				if (red > threshold) {
					red = 255;
				} else {
					red = 0;
				}
				if (blue > threshold) {
					blue = 255;
				} else {
					blue = 0;
				}
				if (green > threshold) {
					green = 255;
				} else {
					green = 0;
				}
				pixels[width * i + j] = alpha << 24 | red << 16 | green << 8 | blue;
				if (pixels[width * i + j] == -1) {
					pixels[width * i + j] = -1;
				} else {
					pixels[width * i + j] = -16777216;
					++black;
				}
			}
		}
		if (bmp != null && !bmp.isRecycled()) {
			bmp.recycle();
		}
		Bitmap newBmp = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		newBmp.setPixels(pixels, 0, width, 0, 0, width, height);

		if (((double) black) / (width * height) < 0.5) {
			color = -16777216;
		} else {
			color = -1;
		}
		return newBmp;
	}

	public static Bitmap testAndCrop(Bitmap bmp) {
		int width = bmp.getWidth();
		int height = bmp.getHeight();
		int black_up = 0, black_down = 0, black_left = 0, black_right = 0;
		for (int i = 0; i < width; ++i) {
			if (bmp.getPixel(i, 0) == color)
				++black_up;
			if (bmp.getPixel(i, height - 1) == color)
				++black_down;
		}
		for (int i = 0; i < height; ++i) {
			if (bmp.getPixel(0, i) == color)
				++black_left;
			if (bmp.getPixel(width - 1, i) == color)
				++black_right;
		}
		if (((((double) black_left) / height > 0.2) || (((double) black_right) / height > 0.2))
				&& ((((double) black_up) / width > 0.2) || (((double) black_down) / width > 0.2)))
			return null;
		// 一次拓展
		int up = 0, down = height - 1;
		for (int i = height / 2; i >= 0; --i) {
			int black = 0;
			for (int j = 0; j < width; ++j) {
				if (bmp.getPixel(j, i) == color)
					++black;
			}
			if ((((double) black) / width) < (denoising / 2)) {
				up = i + 1;
				break;
			}
		}
		if (up == height / 2 + 1)
			return null;
		for (int i = height / 2; i < height; ++i) {
			int black = 0;
			for (int j = 0; j < width; ++j) {
				if (bmp.getPixel(j, i) == color)
					++black;
			}
			if ((((double) black) / width) < (denoising / 2)) {
				down = i - 1;
				break;
			}
		}
		if ((down == height / 2 - 1) || (down - up + 1 < splitNum))
			return null;
		// 二次拓展
		int left = 0, right = width - 1;
		for (int i = width / 2; i >= 0; --i) {
			int black = 0;
			for (int j = up; j <= down; ++j) {
				if (bmp.getPixel(i, j) == color)
					++black;
			}
			if ((((double) black) / (down - up + 1)) < (denoising / 2)) {
				left = i + 1;
				break;
			}
		}
		if (left == width / 2 + 1)
			return null;
		for (int i = width / 2; i < width; ++i) {
			int black = 0;
			for (int j = up; j <= down; ++j) {
				if (bmp.getPixel(i, j) == color)
					++black;
			}
			if ((((double) black) / (down - up + 1)) < (denoising / 2)) {
				right = i - 1;
				break;
			}
		}
		if (right == width / 2 - 1)
			return null;
		// 三次拓展
		for (int i = height / 2; i >= up; --i) {
			int black = 0;
			for (int j = left; j <= right; ++j) {
				if (bmp.getPixel(j, i) == color)
					++black;
			}
			if ((((double) black) / (right - left + 1)) < (denoising / 2)) {
				up = i + 1;
				break;
			}
		}
		if (up == height / 2 + 1)
			return null;
		for (int i = height / 2; i <= down; ++i) {
			int black = 0;
			for (int j = left; j <= right; ++j) {
				if (bmp.getPixel(j, i) == color)
					++black;
			}
			if ((((double) black) / (right - left + 1)) < (denoising / 2)) {
				down = i - 1;
				break;
			}
		}
		if ((down == height / 2 - 1) || (down - up + 1 < splitNum))
			return null;

		TopView.setBound((int) (left / 0.7), (int) (right / 0.7), (int) (up / 0.7), (int) (down / 0.7));

		if (color == -1) {
			return resizeBitmap(bmp, left, up, right, down, Color.BLACK);
		} else {
			return resizeBitmap(bmp, left, up, right, down, Color.WHITE);
		}
	}

	public static Bitmap crop(Bitmap bmp) {
		int width = bmp.getWidth();
		int height = bmp.getHeight();

		int up = -1, down = -1;
		for (int i = 0; i < height; ++i) {
			boolean black = false;
			for (int j = 0; j < width && !black; ++j) {
				if (bmp.getPixel(j, i) == color)
					black = true;
			}
			if (black) {
				up = i;
				break;
			}
		}
		if (up == -1)
			return null;
		for (int i = height - 1; i >= up; --i) {
			boolean black = false;
			for (int j = 0; j < width && !black; ++j) {
				if (bmp.getPixel(j, i) == color)
					black = true;
			}
			if (black) {
				down = i;
				break;
			}
		}
		if (down - up + 1 < splitNum)
			return null;

		int left = -1, right = -1;
		for (int i = 0; i < width; ++i) {
			boolean black = false;
			for (int j = up; j <= down && !black; ++j) {
				if (bmp.getPixel(i, j) == color)
					black = true;
			}
			if (black) {
				left = i;
				break;
			}
		}
		for (int i = width - 1; i >= left; --i) {
			boolean black = false;
			for (int j = up; j <= down && !black; ++j) {
				if (bmp.getPixel(i, j) == color)
					black = true;
			}
			if (black) {
				right = i;
				break;
			}
		}

		return resizeBitmap(bmp, left, up, right, down, Color.WHITE);
	}

	private static Bitmap resizeBitmap(Bitmap bmp, int left, int up, int right, int down, int background) {
		Rect srcRect = new Rect(left, up, right, down);
		Rect dstRect;
		Bitmap resizeBmp;
		Canvas canvas;
		if ((down - up) > (right - left)) {
			resizeBmp = Bitmap.createBitmap(down - up + 1, down - up + 1, Config.ARGB_8888);
			canvas = new Canvas(resizeBmp);
			canvas.drawColor(background);
			int delta = (down - up - right + left) / 2;
			dstRect = new Rect(delta, 0, delta + right - left, down - up);
		} else {
			resizeBmp = Bitmap.createBitmap(right - left + 1, right - left + 1, Config.ARGB_8888);
			canvas = new Canvas(resizeBmp);
			canvas.drawColor(background);
			int delta = (right - left - down + up) / 2;
			dstRect = new Rect(0, delta, right - left, delta + down - up);
		}
		canvas.drawBitmap(bmp, srcRect, dstRect, null);
		if (bmp != null && !bmp.isRecycled()) {
			bmp.recycle();
		}

		return resizeBmp;
	}

	public static int[] bitmap2Array(Bitmap bmp) {
		int[] result = new int[splitNum * splitNum];
		int width = bmp.getWidth();
		int height = bmp.getHeight();
		double length = (double) width / (double) splitNum;
		double extent = (double) height / (double) splitNum;
		for (int i = 0; i < splitNum; ++i) {
			for (int j = 0; j < splitNum; ++j) {
				int u = (int) (i * extent);
				int l = (int) (j * length);
				int d, r;
				if ((int) ((i + 1) * extent) < height) {
					d = (int) ((i + 1) * extent);
				} else {
					d = height;
				}
				if ((int) ((j + 1) * length) < width) {
					r = (int) ((j + 1) * length);
				} else {
					r = width;
				}
				int black = 0;
				for (int x = l; x < r; ++x) {
					for (int y = u; y < d; ++y) {
						if (bmp.getPixel(x, y) == color) {
							++black;
						}
					}
				}
				if (((double) black / (double) ((r - l) * (d - u))) > denoising) {
					result[i * splitNum + j] = 1;
				} else {
					result[i * splitNum + j] = 0;
				}
			}
		}

		return result;
	}
}
