package com.example.laba1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.laba1.Models.Notes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteActivity extends AppCompatActivity {

    EditText editTextTitle;
    EditText editTextNotes;
    FloatingActionButton fabSave;
    ImageView imageViewDelete;
    Notes notes;
    boolean isOldNote = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        editTextTitle = findViewById(R.id.editText_title);
        editTextNotes = findViewById(R.id.editText_notes);
        fabSave = findViewById(R.id.fab_save);
        imageViewDelete = findViewById(R.id.imageView_delete);

        notes = new Notes();
        try {
            notes = (Notes) getIntent().getSerializableExtra("old_note");
            editTextTitle.setText(notes.getTitle());
            editTextNotes.setText(notes.getNotes());
            isOldNote = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTextTitle.getText().toString();
                String text = editTextNotes.getText().toString();

                if (title.isEmpty()) {
                    Toast.makeText(NoteActivity.this, "Please enter title", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (text.isEmpty()) {
                    Toast.makeText(NoteActivity.this, "Please enter text", Toast.LENGTH_SHORT).show();
                    return;
                }

                SimpleDateFormat formatter = new SimpleDateFormat("MMM d, HH:mm");
                Date date = new Date();

                if (!isOldNote) {
                    notes = new Notes();
                }
                notes.setTitle(title);
                notes.setNotes(text);
                notes.setDate(formatter.format(date));

                Intent intent = new Intent();
                intent.putExtra("note", notes);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOldNote) {
                    Intent intent = new Intent();
                    intent.putExtra("note", notes);
                    setResult(-2, intent);
                    finish();
                }
            }
        });
    }
}