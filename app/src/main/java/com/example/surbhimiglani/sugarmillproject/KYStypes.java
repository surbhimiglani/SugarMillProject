package com.example.surbhimiglani.sugarmillproject;

/**
 * Created by Surbhi Miglani on 17-03-2018.
 */

public class KYStypes {

    int id;
    String entryNo;

    public KYStypes() {
    }

    public KYStypes(String entryNo) {
        this.entryNo = entryNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEntryNo() {
        return entryNo;
    }

    public void setEntryNo(String entryNo) {
        this.entryNo = entryNo;
    }
}
