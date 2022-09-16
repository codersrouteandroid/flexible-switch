# flexible-switch


It is a responsive switch in android, it can resize itself according to its size. It's recommended to use it with [ConstraintLayout](https://developer.android.com/develop/ui/views/layout/constraint-layout) to get better result.


https://user-images.githubusercontent.com/111636054/187066512-20eb4e64-c134-463d-b039-d3c6cac4bd6f.mp4


USAGE
-----

Make sure pluginManagement & dependencyResolutionManagement is properly configured in settings.gradle file like this below:

```pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url "https://jitpack.io" }
    }
}
```
XML
-----

Just add FlexibleSwitch in your layout XML and flexible-switch library in your project via Gradle:

```gradle
allprojects {
    repositories {
    ...
    maven { url 'https://jitpack.io' }
    ...
  }
}
dependencies {
  implementation 'com.github.codersrouteandroid:flexible-switch:1.0'
}
```
XML
-----

```xml
<com.codersroute.flexiblewidgets.FlexibleSwitch
            android:layout_width="200dp"
            android:layout_height="100dp"
            android:layout_marginTop="20dp"
            android:layout_marginBottom="20dp"
            android:layout_marginLeft="10dp"
            android:layout_marginRight="10dp"
            android:layout_gravity="center"
            android:textSize="15sp"
            app:backgroundColorOnSwitchOn="#827717"
            app:backgroundColorOnSwitchOff="#4A148C"
            app:thumbColorOnSwitchOff="#FFFFFF"
            app:showText="true"
            android:checked="false"
            app:thumbColorOnSwitchOn="#4A148C"
            app:strokeColorOnSwitchOn="#827717"
            app:strokeColorOnSwitchOff="#827717"
            app:textColorOnSwitchOff="@color/white"
            app:textColorOnSwitchOn="#4A148C"
            />
```

An example activity is in this project to show all the functionalities of this switch, for more information check out this video below:



https://user-images.githubusercontent.com/111636054/187068136-7761525d-4f95-4d3b-8297-9b94c19eff50.mp4


Use these following properties in your XML to change your FlexibleSwitch.


##### Properties:

* `android:checked`                  To change (on/off) status
* `app:backgroundColorOnSwitchOn`    To change background color of switch when switch is on
* `app:backgroundColorOnSwitchOff`   To change background color of switch when switch is off
* `app:thumbColorOnSwitchOn`         To change thumb color of switch when switch is on
* `app:thumbColorOnSwitchOff`        To change thumb color of switch when switch is off
* `app:strokeColorOnSwitchOn`        To change stroke border color of switch when switch is on
* `app:strokeColorOnSwitchOff`       To change stroke border of switch when switch is off
* `app:textColorOnSwitchOn`          To change text color of switch when switch is on
* `app:textColorOnSwitchOff`         To change text color of switch when switch is off
* `app:speed`                        To change speed of animation
* `app:strokeWidth`                  To change size of stroke border
* `app:showText`                     To turn on or off text visibility
* `android:fontFamily`               To change typeface of status text of switch
* `app:textOn`                       To change status text when switch is on
* `app:textOff`                      To change status text when switch is off

Java
-----

```java
 flexibleSwitch.addOnStatusChangedListener(new FlexibleSwitch.OnStatusChangedListener() {
            @Override
            public void onStatusChanged(boolean b) {
                
            }
        });
```
