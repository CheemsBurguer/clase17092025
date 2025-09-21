package com.pougang.clase17092025;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Spinner spnrCategory;
    private TextView tvDate, tvTime;
    private ImageButton ibPickDate, ibPickTime, ibAdd, ibClear;
    private EditText etDescription;
    private ListView lvItems;
    private final ArrayList<String> items = new ArrayList<>();
    private ArrayAdapter<String> listAdapter;
    private Calendar selected;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        spnrCategory = findViewById(R.id.spinnerCategory);
        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);
        ibPickDate = findViewById(R.id.ibPickDate);
        ibPickTime = findViewById(R.id.ibPickTime);
        ibAdd = findViewById(R.id.ibAdd);
        ibClear = findViewById(R.id.ibClear);
        lvItems = findViewById(R.id.lvItems);
        etDescription = findViewById(R.id.etDescription);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                this, R.array.categorias, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrCategory.setAdapter(spinnerAdapter);

        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(listAdapter);

        selected = Calendar.getInstance();


        ibPickDate.setOnClickListener(v -> {
            int year = selected.get(Calendar.YEAR);
            int month = selected.get(Calendar.MONTH);
            int day = selected.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    MainActivity.this,
                    (view, year1, month1, dayOfMonth) -> {
                        String date = String.format("%04d-%02d-%02d",
                                year1, (month1 + 1), dayOfMonth);
                        tvDate.setText(date);
                    },
                    year, month, day
            );

            datePickerDialog.show();

        });

        ibPickTime.setOnClickListener(v -> {
            int hour = selected.get(Calendar.HOUR_OF_DAY);
            int minute = selected.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    MainActivity.this,
                    (view, selectedHour, selectedMinute) -> {
                        String time = String.format("%02d:%02d", selectedHour, selectedMinute);
                        tvTime.setText(time);
                    },
                    hour, minute, true
            );
            timePickerDialog.show();
        });

        ibAdd.setOnClickListener(v -> {
            String categoria = spnrCategory.getSelectedItem().toString();
            String fecha = tvDate.getText().toString();
            String hora = tvTime.getText().toString();
            String desc = etDescription.getText().toString().trim();

            if (desc.isEmpty()){
                Toast.makeText(this, "Por favor, escribe una descripción", Toast.LENGTH_SHORT).show();
                return;
            }

            String item = categoria + "-" + fecha + " " + hora + "-" + desc;
            items.add(item);
            listAdapter.notifyDataSetChanged();
            etDescription.getText().clear();

        });

        lvItems.setOnItemLongClickListener((parent, view, position, id) -> {
            items.remove(position);
            listAdapter.notifyDataSetChanged();
            return true;
        });

        ibClear.setOnClickListener(v -> {
            if (items.isEmpty()) {
                Toast.makeText(this, "La lista ya está vacía", Toast.LENGTH_SHORT).show();
                return;
            } else {
                etDescription.getText().clear();
                items.clear();
                listAdapter.notifyDataSetChanged();
            }

        });

    }
}