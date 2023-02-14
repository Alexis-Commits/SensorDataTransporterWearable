
# SensorDataTransporterWearable

This library pairs with [SensorDataTransporterHandheld](https://github.com/Alexis-Commits/SensorDataTransporterHandheld) to achieve a connection between an `Android Mobile Device` with the `SmartWatch Device`

#### Important: ***This library is for the smartwatch  app***




## How to use it
Initialize the helper

```Kotlin
val sensorDataTransporter = SensorDataTransporterWearable(applicationContext)
```

***

### Then Initiliaze which sensors the app is going to consume


#### StepCounter


```
sensorDataTransporter.initStepCounterSensor()
```
***



#### HeartRate


```
sensorDataTransporter.initHeartRateSensor()
```

***


#### Gyroscope


```
sensorDataTransporter.initGyroscopeSensor()
```

***


#### Accelerometer


```
sensorDataTransporter.initAccelerometerSensor()
```
***

#### Then to make the library able to receive the sensor data use

```
    sensorDataTransporter.registerBroadcastReceiver()
```

#### And to disable it

```
    sensorDataTransporter.unRegisterBroadcastReceiver()
```




## Important things

 - The `smartwatch` app and the `handheld` app must have the same `package` name and must be signed with the same `keystore`

- You have to add this service inside <application> .. </application> tag

```
      <service
            android:name="com.damnluck.sensordatatransporterwearable.core.MessageReceiverWear"
            android:enabled="true"
            android:exported="true">
            <intent-filter>
                <action android:name="com.google.android.gms.wearable.DATA_CHANGED" />
                <action android:name="com.google.android.gms.wearable.MESSAGE_RECEIVED" />
                <data android:scheme="wear" android:host="*" android:pathPrefix="/startHeartRate" />
                <data android:scheme="wear" android:host="*" android:pathPrefix="/stopHeartRate"/>
                <data android:scheme="wear" android:host="*" android:pathPrefix="/startAccelerometer" />
                <data android:scheme="wear" android:host="*" android:pathPrefix="/stopAccelerometer"/>
                <data android:scheme="wear" android:host="*" android:pathPrefix="/startGyroscope" />
                <data android:scheme="wear" android:host="*" android:pathPrefix="/stopGyroscope"/>
                <data android:scheme="wear" android:host="*" android:pathPrefix="/getStepCount"/>
            </intent-filter>
        </service>
```

- Also, you have add important dependencies in your gradle file

```
    implementation 'com.google.android.gms:play-services-wearable:17.1.0'
    implementation 'androidx.localbroadcastmanager:localbroadcastmanager:1.0.0'

```
## The end


Now the app is ready to receive messages from the handheld to start sending sensor data.
