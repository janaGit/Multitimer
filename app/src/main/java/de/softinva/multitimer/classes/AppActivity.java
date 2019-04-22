package de.softinva.multitimer.classes;

import androidx.appcompat.app.AppCompatActivity;

public class AppActivity extends AppCompatActivity {
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
