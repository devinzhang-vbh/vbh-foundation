package com.foundation.manage.model;

import com.foundation.manage.entity.VBC.NewsCategory;

public class NewsCategoryDto  extends NewsCategory {

    private String newsCategoryName;

    @Override
    public String getNewsCategoryName() {
        return newsCategoryName;
    }

    @Override
    public void setNewsCategoryName(String newsCategoryName) {
        this.newsCategoryName = newsCategoryName;
    }
}
