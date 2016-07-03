# Firebucket
![Firebase](https://raw.githubusercontent.com/remychantenay/Firebucket/master/blob/header.jpg)

A glimpse into the astonishing future of BaaS- [Firebase](https://www.firebase.com/) with a very simple bucket list app.

## Presentation
Since its interesting evolution from the [Google I/O 2016](https://developers.googleblog.com/2016/05/firebase-expands-to-become-unified-app.html), my goal being to play with Firebase to witness the potential as well as the limitations.

This project is nothing more than a way to put into practice what I've learn.

![Screenshots](https://raw.githubusercontent.com/remychantenay/Firebucket/master/blob/screenshots.jpg)

## Read More
* [Read more about Firebucket on Medium](https://medium.com/@remy.chantenay/f1r3b4s3-13cf28def122)

## Download
* [Google Play Store](https://play.google.com/store/apps/details?id=com.cremy.firebucket)

## Firebase modules used
* Database
* Crash reporting
* Remote Config
* Analytics

## Tools
* Firebase
* Dependency Injection (Dagger 2)
* ButterKnife
* RxJava wrapping, RxAndroid and RxLifecycle
* Jackson

## Pattern
* **MVP**: Model-View-Presenter
* **MVVM**: Model-View-ViewModel (using Android Data Binding)

## Structure
* **mobile**: contain all the code specific to the mobile application only
* **wear**: code specific to the wearable devices (Android Wear 2.0)
* **Shared**: this module host the code shared by the two module above
* **SharedTest**: contain very specific testing classes used by all the modules

## Android Wear 2.0
__Coming soon...__