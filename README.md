Auto App Distribution
===================

![picture](data/preview-0.png)

### Target

* Configure [gradle](https://firebase.google.com/docs/app-distribution/android/distribute-gradle?apptype=apk) service [Firebase App Distribution](https://firebase.google.com/docs/app-distribution)
* Add the ability to centrally change testers.
* Automatic provisioning of **releaseNotes** based on git commits.

### Architecture

* MVVM - [Guide to app architecture](https://developer.android.com/jetpack/guide)
* Gradle - [Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html)
* Toolkit - [Jetpack Compose](https://developer.android.com/jetpack/compose)
* Distribution - [Firebase App Distribution](https://firebase.google.com/docs/app-distribution)
* Animation - [Lottie for Android](http://airbnb.io/lottie/#/android-compose)

### Preview
<p>
<img src="data/preview-1.png" width="38%"/>
<img src="data/preview.gif" width="38%"/>
</p>

### Result
App Distribution gradle have problem for AAB (See screenshot, taken on 08/03/2021).

![picture](data/aab.png)

APK work ok.

![picture](data/apk.png)

# License

```
Copyright 2021 Vitaliy Zarubin

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