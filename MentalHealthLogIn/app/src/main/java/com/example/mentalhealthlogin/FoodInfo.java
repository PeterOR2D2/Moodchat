package com.example.mentalhealthlogin;

public class FoodInfo {
    private String ate_at;
    private String food_name;

    public FoodInfo(String ate_at, String food_name) {
        this.ate_at = ate_at;
        this.food_name = food_name;
    }

    public String getAte_at() {
        return ate_at;
    }

    public void setAte_at(String ate_at) {
        this.ate_at = ate_at;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }
}
