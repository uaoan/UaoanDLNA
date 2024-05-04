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
```

 **添加权限**
`<uses-permission android:name="android.permission.INTERNET"/>` 

 **在AndroidManifest.xml里**
`<activity android:name=".ScreenActivity"/>\n
 <activity android:name=".ControlActicy"/>` 



### 投屏
 **投屏使用代码** 


```
new Screen().setStaerActivity(MainActivity.this)
            .setName("斗破苍穹") 
            .setUrl("https://s.xlzys.com/play/9avDmPgd/index.m3u8")
            .setImageUrl("http://i0.hdslb.com/bfs/article/96fa4320db5115711c8c30afaff936910595d336.png")
            .show();
```
