# 仿QQ右上角的弹出菜单框

# Screenshots
#![Alt text](https://github.com/itlwy/LPopupMenu/blob/master/pic/rtPopupMenu.gif)

# How to Use

## step 1 
Add the JitPack repository to your build file

```
	allprojects {
			repositories {
				...
				maven { url 'https://jitpack.io' }
			}
		}
```

## Step 2
Add the dependency

```
dependencies {
	        compile 'com.github.itlwy:LPopupMenu:v1.1'
	}

```

## step 3
　　使用方法见如下：

```
  List<MenuItem> menuItems = new ArrayList<>();
                menuItems.add(new MenuItem(R.mipmap.multichat, "发起多人聊天", 100));
                menuItems.add(new MenuItem(R.mipmap.addmember, "加好友", 3));
                menuItems.add(new MenuItem(R.mipmap.qr_scan, "扫一扫"));
                menuItems.add(new MenuItem(R.mipmap.facetoface, "面对面快传"));
                menuItems.add(new MenuItem(R.mipmap.pay, "付款"));
                if (mRightTopMenu == null) {
                    mRightTopMenu = new RightTopMenu.Builder(MainActivity.this)
//                            .windowHeight(480)     //当菜单数量大于3个时，为wrap_content,反之取默认高度320
//                        .windowWidth(320)      //默认宽度wrap_content
                            .dimBackground(true)           //背景变暗，默认为true
                            .needAnimationStyle(true)   //显示动画，默认为true
                            .animationStyle(R.style.RTM_ANIM_STYLE)  //默认为R.style.RTM_ANIM_STYLE
                            .menuItems(menuItems)
                            .onMenuItemClickListener(new RightTopMenu.OnMenuItemClickListener() {
                                @Override
                                public void onMenuItemClick(int position) {
                                    Toast.makeText(MainActivity.this, "点击菜单:" + position, Toast.LENGTH_SHORT).show();
                                }
                            }).build();
                }
                mRightTopMenu.showAsDropDown(mMenuIV, 0, 0);
```

　　觉得合适就拿去用吧~
# Otherthing
　　项目依赖了

```
com.android.support:recyclerview-v7:26.1.0
com.android.support:appcompat-v7:26.1.0
```
  当你的项目里已有这些依赖时，按照gradle的编译特性，会取最新版本的。所以，可能会报错咯，解决办法可参考[gradle提供的依赖冲突解决方案](http://blog.csdn.net/Weiye__Lee/article/details/79472501).下面是给出示例解决方案(build.gradle文件)：
  
```
apply plugin: 'com.android.application'

android {
    compileSdkVersion 23
    buildToolsVersion "26.0.2"

  ......
  
//configurations.all {
//    resolutionStrategy {
//        force 'com.android.support:appcompat-v7:23.2.1'  // 这里可以采用设置强制版本
//    }
//}
dependencies {
    compile fileTree(include: ['*.jar'], dir: 'libs')
    androidTestCompile('com.android.support.test.espresso:espresso-core:2.2.2', {
        exclude group: 'com.android.support', module: 'support-annotations'
    })
    compile 'com.android.support:appcompat-v7:23.2.1'
    compile "com.android.support:recyclerview-v7:23.4.0"
    compile 'com.android.support:design:23.2.1'
    compile ("com.github.itlwy:LPopupMenu:v1.0"){
        exclude group: 'com.android.support',module: 'appcompat-v7'
        exclude group: 'com.android.support',module: 'recyclerview-v7'
    }
    testCompile 'junit:junit:4.12'
}

```

# By the way
　　关于数量的提示角标，是用shape画的圆(要改颜色的话方便~)。但是当大于99时，直接撑大显示不好看，改成和qq那边的效果类似（两边半圆，中间矩形）；由于水平有限，暂时没找到代码的简单实现，直接用图片代替了，这方面有待改进（查了下网上的实现，大部分是用.9图代替的，但是要动态改变圈的颜色效果什么的就不好弄了）