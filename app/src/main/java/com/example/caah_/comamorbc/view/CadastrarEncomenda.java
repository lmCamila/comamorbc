package com.example.caah_.comamorbc.view;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.caah_.comamorbc.R;

import java.util.Calendar;

public class CadastrarEncomenda extends AppCompatActivity {
    Calendar calendar ;
    EditText editTextData ;
    DatePickerDialog dpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_encomenda);
        setTitle(R.string.cadastrar_encomenda);
        editTextData = findViewById(R.id.editTextDataEntregaEncomendas);

        escolherData();

    }

    public void escolherData(){
        editTextData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                dpd = new DatePickerDialog(CadastrarEncomenda.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editTextData.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                    }
                },day,month,year);
                dpd.show();
            }
        });
    }

}
