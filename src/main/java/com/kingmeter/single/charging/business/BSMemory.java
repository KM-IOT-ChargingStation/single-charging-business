package com.kingmeter.single.charging.business;

import lombok.ToString;

@ToString
public class BSMemory {

    public static volatile int acm;

    public static volatile int expense;

    public static volatile int ret;

    public static volatile int rtm;


    public static volatile int ast;

    public static volatile int minbsoc = 10;

    public static volatile String userId = "9527";



}
