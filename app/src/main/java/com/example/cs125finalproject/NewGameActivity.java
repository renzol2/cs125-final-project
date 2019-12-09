package com.example.cs125finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Possible settings to include:
 * - set time limit
 * - enable custom names
 */
public class NewGameActivity extends AppCompatActivity {
    RadioGroup durationButtons;

    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        durationButtons = findViewById(R.id.durationButtons);

        Button startGame = findViewById(R.id.startGame);
        startGame.setOnClickListener(unused -> startActivity());
    }

    private void startActivity() {
        if (durationButtons.getCheckedRadioButtonId() != -1) {
            // Set new intent.
            Intent intent = new Intent(this, GameActivity.class);

            // Send whether or not to use Geoff Challen's name in fake tweets.
            Switch customNameSwitch = findViewById(R.id.customNameSwitch);
            intent.putExtra("useGeoffChallen", customNameSwitch.isChecked());

            // Send the selected game duration.
            int selectedDurationButton = durationButtons.getCheckedRadioButtonId();
            int gameDuration;
            switch (selectedDurationButton) {
                case R.id.lengthTimeButton30s:
                    gameDuration = 30000;
                    break;
                case R.id.lengthTimeButton60s:
                    gameDuration = 60000;
                    break;
                case R.id.lengthTimeButton90s:
                    gameDuration = 90000;
                    break;
                default:
                    gameDuration = 45000; // this should never happen though?
                    break;
            }
            intent.putExtra("gameDuration", gameDuration);

            // Start GameActivity and finish this one.
            startActivity(intent);
            finish();
        }
    }
}
