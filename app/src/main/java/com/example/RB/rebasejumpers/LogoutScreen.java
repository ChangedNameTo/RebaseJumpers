package com.example.RB.rebasejumpers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * The type Logout screen.
 */
@SuppressWarnings("ALL")
public class LogoutScreen extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout__screen);

        Button logoutButton = (Button) findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                startActivity(
                        new Intent(LogoutScreen.this, LoginActivity.class));
            }
        });

        Button itemListButton = (Button) findViewById(R.id.item_list_button);
        itemListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                startActivity(
                        new Intent(LogoutScreen.this, ItemView.class));
            }
        });
    }

}
