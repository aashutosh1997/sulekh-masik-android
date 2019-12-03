package com.sulekhmasik.aashutosh.sulekhmasikpatrika;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import java.io.File;
import java.io.FileOutputStream;
public class PhotoLoader implements Target {
    private final String name;
    private ImageView imageView;
    public String t;
    public PhotoLoader(articlePage o, String name, ImageView imageView) {
        this.name = name;
        this.imageView = imageView;
        t = o.getFilesDir().getPath();
    }
    public PhotoLoader(appHome o, String name, ImageView imageView) {
        this.name = name;
        this.imageView = imageView;
        t = o.getFilesDir().getPath();
    }
    public PhotoLoader(articleDisplay o, String name, ImageView imageView) {
        this.name = name;
        this.imageView = imageView;
        t = o.getFilesDir().getPath();
    }

    public PhotoLoader(String temp, String img_name, ImageView imgView) {
        this.name = img_name;
        this.imageView = imgView;
        t = temp;
    }

    @Override
    public void onPrepareLoad(Drawable arg0) {
    }
    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom arg1) {
        File file = new File(t + "/media/" + name);
        try {
            file.createNewFile();
            FileOutputStream ostream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
            ostream.close();
            imageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

    }

}