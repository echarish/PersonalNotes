package com.example.hksystems.personalnotes;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by harishkumar on 2/20/16.
 */
public class NoteListAdapter extends ArrayAdapter {

    public static final String TAG = "com.example.hksystems";

    int selectedPosition = 0;

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public NoteListAdapter(Context context, List<PersonalNote> personalNoteList) {
        super(context, R.layout.note_list_row, personalNoteList);
        //Log.d(TAG, "In Adapter personalList Size " + personalNoteList.size());
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        //Log.d(TAG, " position " + position + " , selectedPosition " + selectedPosition);
        RadioButton noteRowRadio;
        View customView = convertView;
        TextView noteRowText;
        if (customView == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            customView = vi.inflate(R.layout.note_list_row, parent, false);
        }

        PersonalNote personalNote = (PersonalNote) getItem(position);

        String noteItem = personalNote.toString();
        //Log.d(TAG,"NoteItem in adapter "+noteItem);
        noteRowText = (TextView) customView.findViewById(R.id.noteRowText);

        noteRowRadio = (RadioButton) customView.findViewById(R.id.noteRowRadio);
        //noteRowRadio.setChecked(position==selectedPosition);
        noteRowRadio.setTag(personalNote.get_id());
        noteRowRadio.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                selectedPosition = (Integer) view.getTag();
                notifyDataSetChanged();
            }
        });
        noteRowText.setText(noteItem);
        return customView;
    }
}
