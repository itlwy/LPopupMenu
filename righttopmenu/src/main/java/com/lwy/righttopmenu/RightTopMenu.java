package com.lwy.righttopmenu;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lwy on 2018/2/21.
 */

public class RightTopMenu {

    private static final int DEFAULT_ANIM_STYLE = R.style.RTM_ANIM_STYLE;
    private static final int DEFAULT_HEIGHT = 320;
    private Activity mActivity;
    private RecyclerView mMenuContainer;
    private MenuAdapter mAdapter;
    private PopupWindow mPopupWindow;
    private List<MenuItem> mMenuItems;
    private int mWindowHeight;
    private int mWindowWidth;
    private boolean mNeedAnimationStyle;
    private int mAnimationStyle;
    private boolean mDimBackground;
    private float mAlpha = 0.75f;

    RightTopMenu(Activity activity, int windowHeight, int windowWidth, boolean needAnimationStyle,
                 int animationStyle, boolean dimBackground, List<MenuItem> menuItems, OnMenuItemClickListener onMenuItemClickListener) {
        mActivity = activity;
        mWindowHeight = windowHeight;
        mWindowWidth = windowWidth;
        mNeedAnimationStyle = needAnimationStyle;
        mAnimationStyle = animationStyle;
        mDimBackground = dimBackground;
        mMenuItems = menuItems;
        mOnMenuItemClickListener = onMenuItemClickListener;
        init();
    }

    public List<MenuItem> getMenuItems() {
        return mMenuItems;
    }


    public void addMenuItem(MenuItem item) {
        mMenuItems.add(item);
    }

    public void addMenuList(List<MenuItem> list) {
        mMenuItems.addAll(list);
    }

    private void init() {
        mMenuContainer = (RecyclerView) LayoutInflater.from(mActivity).inflate(R.layout.rt_menu_container, null);
        mMenuContainer.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        if (mMenuItems == null)
            mMenuItems = new ArrayList<>();
        mAdapter = new MenuAdapter(mActivity, this, mMenuItems);
        mAdapter.setOnMenuItemClickListener(mOnMenuItemClickListener);
        mMenuContainer.addItemDecoration(new NormalDividerItemDecoration(mActivity, NormalDividerItemDecoration.VERTICAL_LIST));
    }

    private PopupWindow getPopupWindow() {
        mPopupWindow = new PopupWindow(mActivity);
        mPopupWindow.setContentView(mMenuContainer);
        if (mWindowHeight > 0)
            mPopupWindow.setHeight(mWindowHeight);
        else if (mMenuItems.size() > 3) {
            mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        } else {
            mPopupWindow.setHeight(DEFAULT_HEIGHT);
        }
        mPopupWindow.setWidth(mWindowWidth);
        if (mNeedAnimationStyle) {
            mPopupWindow.setAnimationStyle(mAnimationStyle <= 0 ? DEFAULT_ANIM_STYLE : mAnimationStyle);
        }

        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (mDimBackground) {
                    setBackgroundAlpha(mAlpha, 1f, 300);
                }
            }
        });

        mAdapter.setData(mMenuItems);
        mMenuContainer.setAdapter(mAdapter);
        return mPopupWindow;
    }

    public RightTopMenu showAsDropDown(View anchor) {
        showAsDropDown(anchor, 0, 0);
        return this;
    }

    public void showAsDropDown(View anchor, int xoff, int yoff) {
        if (mPopupWindow == null) {
            getPopupWindow();
        }
        if (!mPopupWindow.isShowing()) {
            mPopupWindow.showAsDropDown(anchor, xoff, yoff);
            mAdapter.notifyDataSetChanged();
            if (mDimBackground) {
                setBackgroundAlpha(1f, mAlpha, 240);
            }
        }
    }


    private void setBackgroundAlpha(float from, float to, int duration) {
        final WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        ValueAnimator animator = ValueAnimator.ofFloat(from, to);
        animator.setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                lp.alpha = (float) animation.getAnimatedValue();
                mActivity.getWindow().setAttributes(lp);
            }
        });
        animator.start();
    }

    public void dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    private OnMenuItemClickListener mOnMenuItemClickListener;

    public interface OnMenuItemClickListener {
        void onMenuItemClick(int position);
    }


    public static class Builder {
        private Activity mActivity;
        private List<MenuItem> mMenuItems;
        private int mWindowHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
        private int mWindowWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        private boolean mNeedAnimationStyle;
        private int mAnimationStyle;
        private boolean mDimBackground;
        private OnMenuItemClickListener mOnMenuItemClickListener;

        public Builder(Activity activity) {
            mActivity = activity;
        }

        public RightTopMenu.Builder menuItems(List<MenuItem> menuItemList) {
            this.mMenuItems = menuItemList;
            return this;
        }

        public RightTopMenu.Builder windowHeight(int windowHeight) {
            if (windowHeight <= 0 && windowHeight != ViewGroup.LayoutParams.MATCH_PARENT
                    && windowHeight != ViewGroup.LayoutParams.WRAP_CONTENT) {
                this.mWindowHeight = DEFAULT_HEIGHT;
            } else {
                this.mWindowHeight = windowHeight;
            }
            return this;
        }

        public RightTopMenu.Builder windowWidth(int windowWidth) {
            if (windowWidth <= 0 && windowWidth != ViewGroup.LayoutParams.MATCH_PARENT) {
                this.mWindowWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
            } else {
                this.mWindowWidth = windowWidth;
            }
            return this;
        }

        public RightTopMenu.Builder needAnimationStyle(boolean needAnimationStyle) {
            mNeedAnimationStyle = needAnimationStyle;
            return this;
        }

        public RightTopMenu.Builder animationStyle(int animationStyle) {
            mAnimationStyle = animationStyle;
            return this;
        }

        public RightTopMenu.Builder dimBackground(boolean dimBackground) {
            this.mDimBackground = dimBackground;
            return this;
        }

        public RightTopMenu.Builder onMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
            mOnMenuItemClickListener = onMenuItemClickListener;
            return this;
        }

        public RightTopMenu build() {
            return new RightTopMenu(mActivity, mWindowHeight, mWindowWidth, mNeedAnimationStyle,
                    mAnimationStyle, mDimBackground, mMenuItems, mOnMenuItemClickListener);
        }
    }
}
