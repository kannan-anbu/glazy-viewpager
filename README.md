# glazy-viewpager
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Glazy%20ViewPager-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/5856)

ViewPager template with cool animation.

### Preview

![Video](https://github.com/kannan-anbu/glazy-viewpager/blob/master/app/src/main/res/drawable-nodpi/sample_gif.gif)


### Dependencies
```groovy
compile 'com.android.support:palette-v7:25.2.0'
```

### Usage
    Refer the implementation in the sample app.
```xml
    <com.kannan.glazy.pager.GlazyViewPager
        android:layout_width="match_parent"
        android:layout_height="wrap_content">
    </com.kannan.glazy.pager.GlazyViewPager>
```

## GlazyImageView
You can even use the GlazyImageView alone.

```xml
    <com.kannan.glazy.views.GlazyImageView
        android:id="@+id/glazy_image_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        glazy:cutType="wave"
        glazy:cutHeight="50dp"
        glazy:cutCount="3"
        glazy:autoTint="false"
        glazy:tintColor="#ff000000"
        glazy:tintAlpha="125"
        glazy:titleTextSize="25dp"
        glazy:subTitleTextSize="18dp"
        glazy:textMargin="25dp"
        glazy:lineSpacing="10dp"
    />
```

License
--------

    Copyright 2017 Kannan-anbu.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
