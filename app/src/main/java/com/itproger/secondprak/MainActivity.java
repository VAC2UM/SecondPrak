package com.itproger.secondprak;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Camera;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView mTextView;
    Camera mCamera;

    static final String STATE_SCORE = "playerScore";
    static final String STATE_LEVEL = "playerLevel";

    public int mCurrentScore = 5;
    public int mCurrentLevel = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Всегда сначала вызывайте метод базового класса

        // Проверяем, существует ли предыдущее состояние
        if (savedInstanceState != null) {
            // Восстанавливаем данные предыдущего состояния
            mCurrentScore = savedInstanceState.getInt(STATE_SCORE);
            mCurrentLevel = savedInstanceState.getInt(STATE_LEVEL);
        } else {
            // Инициализируем значениями по умолчанию
        }
        setContentView(R.layout.activity_main);
    }

    private void setUpActionBar(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            ActionBar actionBar = getActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        android.os.Debug.stopMethodTracing();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mCamera != null) {
            // mCamera.release(); // не работает
            mCamera = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mCamera == null){
            initializeCamera();
        }
    }
    private void initializeCamera() {
    }

    @Override
    protected void onStop() {
        super.onStop();  // Всегда вызывайте сначала метод базового класса

        // Сохраняем текущие данные, поскольку явление останавливается
        ContentValues values = new ContentValues();
        // нет NotePad
//        values.put(NotePad.Notes.COLUMN_NAME_NOTE, getCurrentNoteText());
//        values.put(NotePad.Notes.COLUMN_NAME_TITLE, getCurrentNoteTitle());
//
//        getContentResolver().update(
//                mUri,    // URI для обновления записи
//                values,  // Карта колонок и значений
//                null,    // Не использовать критерии отбора.
//                null     // Не использовать условия отбора.
//        );
    }

    @Override
    protected void onStart() {
        super.onStart();  // Всегда сначала вызывайте метод базового класса

        // Явление было запущено или перезапущено, поэтому здесь мы убеждаемся, что GPS включен
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!gpsEnabled) {
            //Если GPS отключен, создать в этом месте диалог и использовать
            //Intent с действием android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
            //для открытия окна настроек GPS
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();  // Всегда вызывайте сначала метод базового класса

        //А этот код выполнится только если явление было перезапущено
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Сохраняем текущее состояние пользователя в игре
        savedInstanceState.putInt(STATE_SCORE, mCurrentScore);
        savedInstanceState.putInt(STATE_LEVEL, mCurrentLevel);

        // Всегда вызываем метод базового класса, чтобы сохранить информацию об элементах разметки
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        //Всегда вызывайте метод базового класса, чтобы восстановить состояние элементов разметки
        super.onRestoreInstanceState(savedInstanceState);

        // Восстановление сохраненных пользовательских данных
        mCurrentScore = savedInstanceState.getInt(STATE_SCORE);
        mCurrentLevel = savedInstanceState.getInt(STATE_LEVEL);
    }
}