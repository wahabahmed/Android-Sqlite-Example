package sqlite.automaticamusements.com.sqlite;

import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.media.MediaMetadata;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    SqliteHelper dbHelper;
    private EditText id;
    private EditText name;
    private TextView outputView;
    private Button btn_insert;
    private Button btn_update;
    private Button btn_delete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new SqliteHelper(MainActivity.this);

        init();
    }

    private void init() {
        //All Fields
        id = (EditText) findViewById(R.id.f_id);
        name = (EditText) findViewById(R.id.f_name);
        outputView = (TextView) findViewById(R.id.f_output);

        //All Buttons
        btn_insert = (Button) findViewById(R.id.btn_insert);
        btn_update = (Button) findViewById(R.id.btn_update);
        btn_delete = (Button) findViewById(R.id.btn_delete);

        btn_insert.setOnClickListener(this);
        btn_update.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
    }

    private void showAllDataIntoView() {
        Cursor cursor = dbHelper.getAllRecords();
        StringBuffer finalData = new StringBuffer();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            finalData.append(" ");
            finalData.append(cursor.getInt(cursor.getColumnIndex(SqliteHelper.COLUMN_ID)));
            finalData.append(" - ");
            finalData.append(cursor.getString(cursor.getColumnIndex(SqliteHelper.COLUMN_NAME)));
            finalData.append("\n");
        }

        outputView.setText(finalData);
    }

    public String getValue(EditText component) {
        String value = component.getText().toString();
        if (!value.isEmpty()) {
            return value.trim();
        }

        return null;
    }

    @Override
    protected void onStart() {
        dbHelper.openDB();
        super.onStart();

        showAllDataIntoView();
    }

    @Override
    protected void onStop() {
        super.onStop();
        dbHelper.closeDB();
    }


    @Override
    public void onClick(View v) {
        long result;
        switch (v.getId()) {
            case R.id.btn_insert:
                result = dbHelper.insert(-1, getValue(name));
                if (result == -1) {
                    Toast.makeText(MainActivity.this, "Error Inserting Data", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Data Insertion Successful!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_update:
                if (getValue(id) != null && getValue(name) != null) {
                    result = dbHelper.update(Integer.parseInt(getValue(id)), getValue(name));
                    if (result == -1) {
                        Toast.makeText(MainActivity.this, "Error Updating Data", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Data Update Successful!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Error Fill all Fields.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_delete:
                if (getValue(id) != null) {
                    result = dbHelper.delete(Integer.parseInt(getValue(id)));
                    if (result == -1) {
                        Toast.makeText(MainActivity.this, "Error Deleting Data", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Data Deleted Successful!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Error Enter valid Id.", Toast.LENGTH_SHORT).show();
                }
                break;
        }

        showAllDataIntoView();
    }
}
