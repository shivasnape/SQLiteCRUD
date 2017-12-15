package com.example.android.sqlitecrud.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.android.sqlitecrud.R;
import com.example.android.sqlitecrud.adapter.UserAdapter;
import com.example.android.sqlitecrud.helper.DatabaseManager;
import com.example.android.sqlitecrud.model.UserDataList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wesix on 15/12/17.
 */

public class UserActivity extends AppCompatActivity {

    List<UserDataList> employeeList;
    ListView listView;

    //The databasemanager object
    DatabaseManager mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        //Instantiating the database manager object
        mDatabase = new DatabaseManager(this);

        employeeList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listViewuser);

        loadEmployeesFromDatabase();
    }

    private void loadEmployeesFromDatabase() {
        //we are here using the DatabaseManager instance to get all employees
        Cursor cursor = mDatabase.getAllUsers();

        if (cursor.moveToFirst()) {
            do {
                employeeList.add(new UserDataList(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getDouble(4)
                ));
            } while (cursor.moveToNext());

            //passing the databasemanager instance this time to the adapter
            UserAdapter adapter = new UserAdapter(this, R.layout.list_layout_users, employeeList, mDatabase);
            listView.setAdapter(adapter);
        }
    }
}