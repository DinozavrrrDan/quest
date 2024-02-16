package com.nedorezov.model;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class Content{
    public String code;
    public String value;
    public boolean isGameOver;
    public boolean isVictory;
    public ArrayList<String> nextValues;
}
