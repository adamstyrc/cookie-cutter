# cookie-cutter
Android library to crop images by moving &amp; scaling image.

![alt tag](https://github.com/adamstyrc/cookie-cutter/blob/master/logo.jpeg)

Demo:
--------

![alt tag](https://github.com/adamstyrc/cookie-cutter/blob/master/video.gif)


Usage:
--------

Add **CookieCutterImageView** to your layout:

```
    <com.adamstyrc.cookiecutter.CookieCutterImageView
        android:id="@+id/ivCrop"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:src="@drawable/your_image"/>
```

To get bitmap from currently selected region use below method:
```
CookieCutterImageView.getCroppedBitmap();
```

There are few adjustable parameters that you can access with:
```
CookieCutterImageView.getParams()
```

For example it's possible to change the shape of the cookie-cutter overlay with method:
```
CookieCutterImageView.getParams().setShape();
```

Download:
--------

Download latest AAR with Gradle:
```
compile 'com.adamstyrc.cookiecutter:cookie-cutter:1.0.2'
```

Required Android >=4.0 (API 15)
