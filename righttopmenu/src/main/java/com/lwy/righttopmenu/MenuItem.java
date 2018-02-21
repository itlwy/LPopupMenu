package com.lwy.righttopmenu;

/**
 * Created by lwy on 2018/2/21.
 */

public class MenuItem {
    private int icon;
    private String text;
    private int badgeCount;

    public MenuItem(int icon, String text) {
        this.icon = icon;
        this.text = text;
    }

    public MenuItem(int icon, String text, int badgeCount) {
        this.icon = icon;
        this.text = text;
        this.badgeCount = badgeCount;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getBadgeCount() {
        return badgeCount;
    }

    public void setBadgeCount(int badgeCount) {
        this.badgeCount = badgeCount;
    }
}
