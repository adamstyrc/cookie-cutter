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

License:
--------

```
Copyright 2016 Adam Styrc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
