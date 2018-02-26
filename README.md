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
	        compile 'com.github.itlwy:LPopupMenu:v1.0'
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

　　目前合适就拿去用吧~
# By the way
　　关于数量的提示角标，是用shape画的圆。但是当大于99时，直接撑大显示不好看，改成和qq那边的效果类似（两边半圆，中间矩形）；由于本人才疏学浅，暂时没找到代码的简单实现，直接用图片代替了，这方面有待改进（查了下网上的实现，大部分是用.9图代替的，但是要动态改变圈的颜色效果什么的就不好弄了）