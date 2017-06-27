package com.example.RB.rebasejumpers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


/**
 * The type Banned unbanned.
 */
@SuppressWarnings("ALL")
public class BannedUnbanned extends AppCompatActivity {

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banned__unbanned);

        Button unbannedButton = (Button) findViewById(R.id.unBanButton);
        unbannedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                startActivity(new Intent(BannedUnbanned.this,
                        LoginActivity.class));
            }
        });

    }

}
