package com.example.hksystems.personalnotes;

/**
 * Created by harishkumar on 2/20/16.
 */
public class PersonalNote {

    private int _id;
    private String notevalue;

    public PersonalNote() {
    }

    public PersonalNote(String notevalue) {
        this.notevalue = notevalue;
    }

    public PersonalNote(int _id, String notevalue) {
        this._id = _id;
        this.notevalue = notevalue;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getNotevalue() {
        return notevalue;
    }

    public void setNotevalue(String notevalue) {
        this.notevalue = notevalue;
    }

    @Override public String toString() {
        return this.notevalue;
    }
}
