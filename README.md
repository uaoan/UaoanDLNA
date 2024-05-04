# DLNA投屏

DLNA投屏 支持快进后退 暂停 播放

### 集成
 **1.在 project 的 build.gradle 文件中找到 allprojects{} 代码块添加以下代码：** 

```
allprojects {
    repositories {
        google()
        jcenter()
        maven { url 'https://jitpack.io' }      //增加 jitPack Maven 仓库
    }
}
```
 **在 app 的 build.gradle 文件中找到 dependencies{} 代码块，并在其中加入以下语句：** 

```
implementation 'com.github.uaoan:UaoanDLNA:1.0'
implementation 'com.github.bumptech.glide:glide:4.12.0'
implementation 'com.squareup.okhttp3:okhttp:4.9.0'
implementation 'com.squareup.okio:okio:2.10.0'
implementation 'org.jetbrains.kotlin:kotlin-stdlib:1.5.30'
```

 **添加权限**
`<uses-permission android:name="android.permission.INTERNET"/>` 

 **在AndroidManifest.xml里**
```
<activity android:name=".ScreenActivity"/>

 <activity android:name=".ControlActicy"/>
```




### 投屏
 **投屏使用代码** 


```
new Screen().setStaerActivity(MainActivity.this)
            .setName("斗破苍穹") 
            .setUrl("https://s.xlzys.com/play/9avDmPgd/index.m3u8")
            .setImageUrl("http://i0.hdslb.com/bfs/article/96fa4320db5115711c8c30afaff936910595d336.png")
            .show();
```



![输入图片说明](8001d3d04eb87f032dea3fee27bbd21.jpg) 
![输入图片说明](cc92f911e7a831ff580f40b5e70b39d.jpg) 
