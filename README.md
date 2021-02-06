<img src="https://raw.githubusercontent.com/st235/Chartio/master/images/preview.gif" width="600" height="286">

# Chartio

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.st235/chartioview/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.st235/chartioview)

A lightweight linear chart library for Android

## Download me

__Important: library was migrated from JCenter to MavenCentral__ 

It means that it may be necessary to add __mavenCentral__ repository to your repositories list

```groovy
allprojects {
    repositories {
        // your repositories

        mavenCentral()
    }
}
```

- Maven

```text
<dependency>
  <groupId>com.github.st235</groupId>
  <artifactId>chartioview</artifactId>
  <version>X.X</version>
  <type>pom</type>
</dependency>
```

- Gradle

```text
implementation 'com.github.st235:chartioview:X.X'
```

- Ivy

```text
<dependency org='com.github.st235' name='chartioview' rev='X.X'>
  <artifact name='chartioview' ext='pom' ></artifact>
</dependency>
```

P.S.: Check out latest version code in badge at the top of this page.

## Usage

To integrate charts into projects you should start from xml

```xml
        <github.com.st235.lib_chartio.ChartioView
            android:id="@+id/chart"
            android:layout_width="match_parent"
            android:layout_height="156dp"
            app:lineWidth="4dp"
            android:paddingBottom="8dp"
            app:lineRoundRadius="4dp"
            app:lineColor="#C847F4"
            app:gridEnabled="false"
            app:chartFillColors="@array/colors2"
            app:chartFillPositions="@array/positions"
            app:animateOnNewData="true" />
```

## Screens

<img src="https://raw.githubusercontent.com/st235/Chartio/master/images/chart_1.png" width="540" height="256"> <img src="https://raw.githubusercontent.com/st235/Chartio/master/images/chart_2.png" width="540" height="256"> <img src="https://raw.githubusercontent.com/st235/Chartio/master/images/chart_3.png" width="540" height="256">

### License

```text
MIT License

Copyright (c) 2019 - present, Alexander Dadukin

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```