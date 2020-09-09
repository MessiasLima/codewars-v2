# Codewars

[![License](https://img.shields.io/github/license/MessiasLima/codewars-v2)](https://img.shields.io/github/license/MessiasLima/codewars-v2)
[![Maintainability](https://api.codeclimate.com/v1/badges/68615d7d59d443b4d898/maintainability)](https://codeclimate.com/github/MessiasLima/codewars-v2/maintainability)

This application consumes the [Codewars public API](https://dev.codewars.com/#rest­api) and show the data caching it on a local database on device. This app is made using [Kotlin](https://kotlinlang.org/) and follows the [guide to app architecture](https://developer.android.com/jetpack/guide) from google.

## Libraries

The following libraries was used on this app:

- [Constraint Layout](https://developer.android.com/training/constraint-layout?hl=en)
- [Navigation Component](https://developer.android.com/guide/navigation?hl=en)
- [Dagger 2](https://dagger.dev/)
- [LiveData coroutines extension](https://developer.android.com/kotlin/ktx#livedata)
- [Retrofit](https://square.github.io/retrofit/)
- [Room persistence library](https://developer.android.com/training/data-storage/room)
- [Paging library](https://developer.android.com/topic/libraries/architecture/paging)
- [Markwon](https://noties.io/Markwon/)

testing libraries

- [AndroidX testing libraries](https://developer.android.com/training/testing?hl=en)(Espresso, JUnit, Hamcrest)
- [Mockk](https://mockk.io/)

## Development tools

- [Ktlint](https://ktlint.github.io/) was added to help to maintain the code style and quality
- [Netris commit lint](https://plugins.gradle.org/plugin/ru.netris.commitlint) was added to assure that the commits are matching the [conventional commit specification](https://www.conventionalcommits.org/en/v1.0.0/)
- [Gradle Git Hook plugin](https://github.com/STAR-ZERO/gradle-githook) was added to integrate the lint plugins above on git-hooks lifecycle

## License

    MIT License

    Copyright (c) 2020 Messias Junior

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
