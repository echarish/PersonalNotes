package com.example.hksystems.personalnotes;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    private static final String TAG = "com.example.hksystems";

    EditText noteInput;
    ListView noteListView;
    NoteDBManager noteDBManager;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        noteInput = (EditText) findViewById(R.id.noteInput);
        noteListView = (ListView) findViewById(R.id.noteListView);
        noteDBManager = new NoteDBManager(this, null, null, 1);
        makeNoteList();
    }

    private void makeNoteList() {
        List<PersonalNote> personalNoteArrayList = noteDBManager.getAllNote();
        ListAdapter listAdapter = new NoteListAdapter(this, personalNoteArrayList);
        noteListView.setAdapter(listAdapter);
    }

    private void makeNoteList(List<PersonalNote> personalNoteArrayList) {
        ListAdapter listAdapter = new NoteListAdapter(this, personalNoteArrayList);
        noteListView.setAdapter(listAdapter);
    }


    public void addNoteClicked(View view) {
        PersonalNote personalNote = new PersonalNote(noteInput.getText().toString().trim());
        noteDBManager.addNote(personalNote);
        noteInput.setText("");
        makeNoteList();
    }

    public void searchNoteClicked(View view) {
        List<PersonalNote> personalNoteArrayList = new ArrayList<PersonalNote>();
        if (noteInput.getText().toString().equals("")) {
            personalNoteArrayList = noteDBManager.getAllNote();
        } else {
            personalNoteArrayList = noteDBManager.searchAllNote(noteInput.getText().toString().trim());
            Toast.makeText(this, "To Reset Search, Click \"Search\" again", Toast.LENGTH_LONG).show();
        }
        makeNoteList(personalNoteArrayList);
        noteInput.setText("");
    }


    public void deleteNoteClicked(View view) {

        if (noteInput.getText().toString().equals("")) {
            NoteListAdapter noteListAdapter = (NoteListAdapter) noteListView.getAdapter();
            int selectedNoteIdForDelete = noteListAdapter.getSelectedPosition();
            //Log.d(TAG," In DELETE for selected id "+selectedNoteIdForDelete);
            //ListView selectedListItemView=(ListView) noteListView.getItemAtPosition(selectedNoteIdForDelete);
            noteDBManager.deleteNoteWithId(selectedNoteIdForDelete);

        } else {
            PersonalNote personalNote = new PersonalNote(noteInput.getText().toString().trim());
            noteDBManager.deleteNoteWithValue(personalNote);
        }
        noteInput.setText("");
        makeNoteList();
    }
}
