package com.gs.thread.splitwise.expense;

public class ExpenseMetaData {

    private String comment;
    private String imageUrl;

    public ExpenseMetaData(String comment, String imageUrl) {
        this.comment = comment;
        this.imageUrl = imageUrl;
    }

    public String getComment() {
        return comment;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
