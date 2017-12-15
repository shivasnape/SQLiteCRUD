package com.example.android.sqlitecrud.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.sqlitecrud.R;
import com.example.android.sqlitecrud.helper.DatabaseManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by wesix on 15/12/17.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextName, editTextSalary;
    Spinner spinnerDept;

    //We creating our DatabaseManager object
    DatabaseManager mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing the database manager object
        mDatabase = new DatabaseManager(this);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextSalary = (EditText) findViewById(R.id.editTextSalary);
        spinnerDept = (Spinner) findViewById(R.id.spinnerDepartment);

        findViewById(R.id.buttonAddEmployee).setOnClickListener(this);
        findViewById(R.id.textViewViewEmployees).setOnClickListener(this);
    }

    private void addEmployee() {
        String name = editTextName.getText().toString().trim();
        String salary = editTextSalary.getText().toString().trim();
        String dept = spinnerDept.getSelectedItem().toString();


        if (name.isEmpty()) {
            editTextName.setError("Name can't be empty");
            editTextName.requestFocus();
            return;
        }

        if (salary.isEmpty()) {
            editTextSalary.setError("Salary can't be empty");
            editTextSalary.requestFocus();
            return;
        }


        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String joiningDate = sdf.format(cal.getTime());

        //adding the employee with the DatabaseManager instance
        if (mDatabase.addUser(name, dept, joiningDate, Double.parseDouble(salary)))
            Toast.makeText(this, "User Added", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Could not add User", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonAddEmployee:
                addEmployee();
                break;
            case R.id.textViewViewEmployees:
                startActivity(new Intent(this, UserActivity.class));
                break;
        }
    }
}

