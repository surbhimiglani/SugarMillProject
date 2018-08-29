package com.example.surbhimiglani.sugarmillproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Surbhi Miglani on 17-03-2018.
 */

public class KSadapter  extends SQLiteOpenHelper {
    Context c;
    SQLiteDatabase db;
    List<KYStypes> list;


    public KSadapter(Context context){
        super(context, KYSdb.DATABASE_NAME, null, KYSdb.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_TB="CREATE TABLE "+KYSdb.TABLE_NAME+"(" + KYSdb.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+KYSdb.ENTRY_MESSAGE + " VARCHAR);";
        //, "+ KYSdb.COLUMN_ATTEMPT+ " BIT, "+ KYSdb.COLUMN_FRIEND+" BIT, "+ KYSdb.FRIEND_ID+ " VARCHAR, "+ KYSdb.FRIEND_NAME+" VARCHAR
        //   String create_TB2="CREATE TABLE " + KYSdb.TABLE_NAME_A+ "("+ KYSdb.COLUMN_ID_A+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+KYSdb.QuestionAsked+" VARCHAR, "+ KYSdb.FriendName+" VARCHAR, "+KYSdb.timeAsk+" VARCHAR);";
        db.execSQL(create_TB);
        //   db.execSQL(create_TB2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + KYSdb.TABLE_NAME);
        // db.execSQL("DROP TABLE IF EXISTS " + KYSdb.TABLE_NAME_A);
        onCreate(db);
    }

    //Select
    public Cursor retrieve() {
        String[] columns = {KYSdb.COLUMN_ID, KYSdb.ENTRY_MESSAGE};
        //, KYSdb.COLUMN_ATTEMPT, KYSdb.COLUMN_FRIEND, KYSdb.FRIEND_ID, KYSdb.FRIEND_NAME
        Cursor c = db.query(KYSdb.TABLE_NAME, columns, null, null, null, null, null);
        return c;
    }

    public boolean update(int id, String newAnswer) {
    // , boolean attempt, boolean tofriend, String fid, String fname
        db=this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KYSdb.ENTRY_MESSAGE, newAnswer);
    /*   cv.put(KYSdb.COLUMN_ATTEMPT, attempt);
         cv.put(KYSdb.COLUMN_FRIEND, tofriend);
         cv.put(KYSdb.FRIEND_ID, fid);
         cv.put(KYSdb.FRIEND_NAME, fname);
    */

        int result = db.update(KYSdb.TABLE_NAME, cv, KYSdb.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        if (result > 0) {
            return true;
        }
    // Caused by: android.database.sqlite.SQLiteException: no such column: questions (code 1): , while compiling: SELECT id, questions, answers, attempted FROM KYS
        return false;
    }

    public void delete() {
        db=this.getWritableDatabase();
        db.delete(KYSdb.TABLE_NAME,null,null);
        db.close();
    }

     /*
     public boolean add(Questions kyStypes) {
         db=this.getWritableDatabase();
         try {
             for(int i=0; i<kyStypes.getData().getQuestions().size(); i++) {
                 ContentValues cv = new ContentValues();
                 cv.put(KYSdb.COLUMN_QUE, kyStypes.getData().getQuestions().get(i).getQuestionText());
                 cv.put(KYSdb.CHOICE1, kyStypes.getData().getQuestions().get(i).getResponse1());
                 cv.put(KYSdb.CHOICE2, kyStypes.getData().getQuestions().get(i).getResponse2());
                 cv.put(KYSdb.CHOICE3, kyStypes.getData().getQuestions().get(i).getResponse3());
                 cv.put(KYSdb.CHOICE4, kyStypes.getData().getQuestions().get(i).getResponse4());
                 cv.put(KYSdb.COLUMN_ANS, kyStypes.getData().getQuestions().get(i).getSectionName());
            /* cv.put(KYSdb.COLUMN_ATTEMPT, kyStypes.getData().getQuestions().get(i).getScore2());
             cv.put(KYSdb.COLUMN_FRIEND, kyStypes.getData().getQuestions().get(i).getScore3());
             cv.put(KYSdb.FRIEND_ID, kyStypes.getData().getQuestions().get(i).getScore4());
             cv.put(KYSdb.FRIEND_NAME, kyStypes.getData().getQuestions().get(i).getScore4());
     long result = db.insert(KYSdb.TABLE_NAME, KYSdb.COLUMN_ID, cv);
     if (result > 0) {
         return true;
     }
 }
 } catch (SQLException e) {
         e.printStackTrace();
         }
         db.close();
         return false;
         }*/

    public boolean add(KYStypes kyStypes) {
        db=this.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put(KYSdb.ENTRY_MESSAGE,kyStypes.getEntryNo());
            long result = db.insert(KYSdb.TABLE_NAME, KYSdb.COLUMN_ID, cv);
            if (result > 0) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.close();
        return false;
    }

    public boolean CheckDatabase(Context c){
        File dbFile = c.getDatabasePath(KYSdb.DATABASE_NAME);
        return dbFile.exists();
    }

    public List<KYStypes> getAllQuestionsList() {
        // set the table items in a list
        List<KYStypes> questionArrayList = new ArrayList<>();
        db=this.getReadableDatabase();
        Cursor c = retrieve();
        // looping through all records and adding to the list
        if (c.moveToFirst()) {
            do {
                KYStypes question = new KYStypes();
                int id = Integer.parseInt(c.getString(c.getColumnIndex(KYSdb.COLUMN_ID)));
                question.setId(id);

                String idText = c.getString(c.getColumnIndex(KYSdb.ENTRY_MESSAGE));
                question.setEntryNo(idText);
                // adding to Questions list
                questionArrayList.add(question);

            } while (c.moveToNext());
          // Collections.shuffle(questionArrayList);
        }
        return questionArrayList;
    }
}
