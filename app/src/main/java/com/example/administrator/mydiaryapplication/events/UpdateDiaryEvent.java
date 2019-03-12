package com.example.administrator.mydiaryapplication.events;

public class UpdateDiaryEvent {
    private int position;

    public UpdateDiaryEvent(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


}
