package br.com.extractor.ygops.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import com.amulyakhare.textdrawable.TextDrawable;

/**
 * Created by Muryllo Tiraza on 10/02/2016.
 */
public class ImageUtils {

    private static ImageUtils INSTANCE = new ImageUtils();

    public ImageUtils() {
    }

    public static ImageUtils getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ImageUtils();
        }
        return INSTANCE;
    }

    public Bitmap getRoundedCornerBitmap(byte[] bytes) {
        Bitmap source = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (squaredBitmap != source) {
            source.recycle();
        }

        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);

        squaredBitmap.recycle();
        return bitmap;
    }

    public Drawable getDrawableRealm(int text, int color, Context context) {
        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .bold()
                .endConfig()
                .buildRound(context.getResources().getString(text), new ColorGenerator().getColor(color));
        return drawable;
    }

    public Drawable getDrawable(String text, int color) {
        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .bold()
                .endConfig()
                .buildRound(text, color);
        return drawable;
    }

}
