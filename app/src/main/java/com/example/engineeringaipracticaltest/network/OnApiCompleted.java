package com.example.engineeringaipracticaltest.network;

public interface OnApiCompleted
{
    public  void onApiSuccessResponse(Object objects);
    public void onApiFailureResponse(Object objects);
}