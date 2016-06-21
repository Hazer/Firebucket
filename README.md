# Firebucket (Work in progress...)
![Firebase](https://raw.githubusercontent.com/remychantenay/Firebucket/master/blob/header.jpg)

Glimpse into the astonishing future of BaaS- [Firebase](https://www.firebase.com/) with a very simple bucket list app.

## Presentation
Since it's interesting evolution from the [Google I/O 2016](https://developers.googleblog.com/2016/05/firebase-expands-to-become-unified-app.html)
, I really wanted to play with Firebase to witness the potential as well as the limitations.

This project is nothing more than a way to put into practice what I've learn.

## Firebase modules used
* Database
* Crash reporting
* Remote Config
* Analytics

## Tools
* Firebase
* Dependency Injection (Dagger 2)
* ButterKnife
* RxJava, RxAndroid and RxLifecycle
* Jackson

## Pattern
* **MVP**: Model-View-Presenter
* **MVVM**: Model-View-ViewModel (using Android Data Binding)

## Structure
* **mobile**: contain all the code specific to the mobile application only
* **wear**: code specific to the wearable devices (Android Wear 2.0)
* **tv**: Android TV app
* **Shared**: this module host the code shared by the three module above
* **SharedTest**: contain very specific testing classes used by all the modules

## Testing
* **Espresso** for instrumented testing
* **Mockito** to mock some classes such as the *DataManager*
* **Harmcrest** to create custom assertions for the tests
* **JUnit**, because JUnit

## Android Wear 2.0
__Work in progress...__

## Android TV
__Work in progress...__

## Testing
__Work in progress...__