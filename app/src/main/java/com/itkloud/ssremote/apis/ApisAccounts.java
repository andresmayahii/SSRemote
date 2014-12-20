package com.itkloud.ssremote.apis;

import com.itkloud.ssremote.dto.Account;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andressh on 20/12/14.
 */
public class ApisAccounts {

    private final static List<Account> googleList;
    private final static List<Account> pusherList;
    private final static List<Account> slideShareList;

    static {
        pusherList = new ArrayList<>(3);
        pusherList.add(new Account("31611","a8b1399d0321e0f79b12","228932d348fce78e039e"));
        pusherList.add(new Account("31611","a8b1399d0321e0f79b12","228932d348fce78e039e"));
        slideShareList = new ArrayList<>(3);
        slideShareList.add(new Account("w8TMLcxx","CiFLUnuo"));
        slideShareList.add(new Account("w8TMLcxx","CiFLUnuo"));
        googleList = new ArrayList<>(3);
    }


    public Account getPusherAccount() {
        int index = (int) (Math.random() * pusherList.size());
        return pusherList.get(index);
    }

    public Account getSlideShareAccount() {
        int index = (int) (Math.random() * slideShareList.size());
        return slideShareList.get(index);
    }
}
