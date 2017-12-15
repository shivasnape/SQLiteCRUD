package com.example.android.sqlitecrud.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.sqlitecrud.R;
import com.example.android.sqlitecrud.helper.DatabaseManager;
import com.example.android.sqlitecrud.model.UserDataList;

import java.util.List;

/**
 * Created by wesix on 15/12/17.
 */

public class UserAdapter extends ArrayAdapter<UserDataList> {

    Context mCtx;
    int layoutRes;
    List<UserDataList> dataList;

    //the databasemanager object
    DatabaseManager mDatabase;

    //modified the constructor and we are taking the DatabaseManager instance here
    public UserAdapter(Context mCtx, int layoutRes, List<UserDataList> dataList, DatabaseManager mDatabase) {
        super(mCtx, layoutRes, dataList);

        this.mCtx = mCtx;
        this.layoutRes = layoutRes;
        this.dataList = dataList;
        this.mDatabase = mDatabase;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View view = inflater.inflate(layoutRes, null);

        TextView textViewName = view.findViewById(R.id.textViewName);
        TextView textViewDept = view.findViewById(R.id.textViewDepartment);
        TextView textViewSalary = view.findViewById(R.id.textViewSalary);
        TextView textViewJoinDate = view.findViewById(R.id.textViewJoiningDate);

        final UserDataList user = dataList.get(position);

        textViewName.setText(user.getName());
        textViewDept.setText(user.getDept());
        textViewSalary.setText(String.valueOf(user.getSalary()));
        textViewJoinDate.setText(user.getJoiningdate());

        view.findViewById(R.id.buttonDeleteEmployee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteEmployee(user);
            }
        });

        view.findViewById(R.id.buttonEditEmployee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateEmployee(user);
            }
        });

        return view;
    }

    private void updateEmployee(final UserDataList userDataList) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.dialog_update_user, null);
        builder.setView(view);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        final EditText editTextName = view.findViewById(R.id.editTextName);
        final EditText editTextSalary = view.findViewById(R.id.editTextSalary);
        final Spinner spinner = view.findViewById(R.id.spinnerDepartment);

        editTextName.setText(userDataList.getName());
        editTextSalary.setText(String.valueOf(userDataList.getSalary()));


        view.findViewById(R.id.buttonUpdateEmployee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = editTextName.getText().toString().trim();
                String salary = editTextSalary.getText().toString().trim();
                String dept = spinner.getSelectedItem().toString().trim();

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


                //calling the update method from database manager instance
                if (mDatabase.updateUser(userDataList.getId(), name, dept, Double.valueOf(salary))) {
                    Toast.makeText(mCtx, "User Details Updated", Toast.LENGTH_SHORT).show();
                    loadEmployeesFromDatabaseAgain();
                }
                alertDialog.dismiss();
            }
        });
    }

    private void deleteEmployee(final UserDataList userDataList) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
        builder.setTitle("Are you sure?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //calling the delete method from the database manager instance
                if (mDatabase.deleteUser(userDataList.getId()))
                    loadEmployeesFromDatabaseAgain();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void loadEmployeesFromDatabaseAgain() {
        //calling the read method from database instance
        Cursor cursor = mDatabase.getAllUsers();

        dataList.clear();
        if (cursor.moveToFirst()) {
            do {
                dataList.add(new UserDataList(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getDouble(4)
                ));
            } while (cursor.moveToNext());
        }
        notifyDataSetChanged();
    }
}