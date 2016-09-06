package com.example.hksystems.personalnotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by harishkumar on 2/20/16.
 */
public class NoteDBManager extends SQLiteOpenHelper {

    private static final String TAG = "com.example.hksystems";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "personal_note.db";
    private static final String TABLE_PERSONAL_NOTE = "personal_note";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NOTE_VALUE = "note_value";

    public NoteDBManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public NoteDBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override public void onCreate(SQLiteDatabase db) {
        String noteCreateQuery = " CREATE TABLE " + TABLE_PERSONAL_NOTE + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                COLUMN_NOTE_VALUE + " TEXT" +
                ");";
        //Log.d(TAG, noteCreateQuery);
        db.execSQL(noteCreateQuery);
    }

    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_PERSONAL_NOTE);
        onCreate(db);
    }

    //add new note to db
    public void addNote(PersonalNote personalNote) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NOTE_VALUE, personalNote.getNotevalue());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_PERSONAL_NOTE, null, contentValues);
        db.close();
    }

    //delete note from db
    public void deleteNoteWithValue(PersonalNote personalNote) {
        SQLiteDatabase db = getWritableDatabase();
        String deleteNoteQuery = "DELETE FROM " + TABLE_PERSONAL_NOTE + " WHERE " + COLUMN_NOTE_VALUE + "=\"" + personalNote.getNotevalue() + "\";";
        //Log.d(TAG, deleteNoteQuery);
        db.execSQL(deleteNoteQuery);
        db.close();
    }

    //delete note from db
    public void deleteNoteWithId(int idForDelete) {
        SQLiteDatabase db = getWritableDatabase();
        String deleteNoteQuery="DELETE FROM " + TABLE_PERSONAL_NOTE + " WHERE " + COLUMN_ID + "=" + idForDelete + ";";
        //Log.d(TAG,deleteNoteQuery);
        db.execSQL(deleteNoteQuery);
        db.close();
    }

    //get all notes from DB
    public List<PersonalNote> getAllNote() {
        List<PersonalNote> allNotes = new ArrayList<PersonalNote>();
        SQLiteDatabase db = getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_PERSONAL_NOTE + " WHERE 1 ;";
        //Log.d(TAG,selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);
        //Log.d(TAG,cursor.toString());
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if (cursor.getString(cursor.getColumnIndex(COLUMN_NOTE_VALUE)) != null) {
                //Log.d(TAG,"iterating over DB");
                PersonalNote personalNote = new PersonalNote(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)), cursor.getString(cursor.getColumnIndex(COLUMN_NOTE_VALUE)));
                //Log.d(TAG,personalNote.toString());
                allNotes.add(personalNote);
            }
            cursor.moveToNext();
        }
        db.close();
        return allNotes;
    }

    //get all notes from DB
    public List<PersonalNote> searchAllNote(String noteValueInput) {
        List<PersonalNote> allNotes = new ArrayList<PersonalNote>();
        SQLiteDatabase db = getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_PERSONAL_NOTE + " WHERE " + COLUMN_NOTE_VALUE + " LIKE \"%" + noteValueInput + "%\";";
       // Log.d(TAG,selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if (cursor.getString(cursor.getColumnIndex(COLUMN_NOTE_VALUE)) != null) {
                PersonalNote personalNote = new PersonalNote(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)), cursor.getString(cursor.getColumnIndex(COLUMN_NOTE_VALUE)));
                allNotes.add(personalNote);
            }
            cursor.moveToNext();
        }
        db.close();
        return allNotes;
    }
}
