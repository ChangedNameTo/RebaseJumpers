package com.example.RB.rebasejumpers;

import android.annotation.TargetApi;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import static android.Manifest.permission.READ_CONTACTS;
import static com.example.RB.rebasejumpers.LoginActivity.VALID_EMAIL_ADDRESS_REGEX;

/**
 * The type Registration activity 1.
 */
public class RegistrationActivity1
        extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    // Id to identify READ_CONTACTS permission request
    private static final int REQUEST_READ_CONTACTS = 0;
    private static final String TAG = "RegisterUser";


    // View references
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    /**
     * The M auth.
     */
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration1);

        // Set up views
        mEmailView = (AutoCompleteTextView)
                findViewById(R.id.user_email_string);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password_string);
        mPasswordView.setOnEditorActionListener(
                new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(
                    final TextView textView,
                    final int id,
                    final KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    registerUser();
                    return true;
                }
                return false;
            }
        });

        Button registrationButton = (Button)
                findViewById(R.id.registration_button);
        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                registerUser();
            }
        });
    }

    /**
     * Creates a user in firebase with the given email and password. Since
     * this is a basic app, passwords are not hashed.
     * This is not acceptable in a full size app, but shortcuts are shortcuts.
     *
     * WARNING TO ANYONE REVIEWING THIS CODE. DO NOT USE REAL PASSWORDS.
     * THEY ARE STORED IN PLAIN TEXT. DO NOT USE REAL PASSWORDS.
     */
    private void registerUser() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Grab the values from the form
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        // Local vars
        View focusView = null;
        boolean cancel = false;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (!cancel) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this,
                            new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull final Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "createUserWithEmail:success");
                                startActivity(
                                        new Intent(RegistrationActivity1.this,
                                                LogoutScreen.class));
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegistrationActivity1.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            focusView.requestFocus();
        }
    }

    // Checks email against a valid email regex
    private boolean isEmailValid(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(RegistrationActivity1.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }

    /**
     * The type User.
     */
    public static class User {
        /**
         * The Email.
         */
        String email;
        /**
         * The Username.
         */
        String username;
        /**
         * The Password.
         */
        String password;

        /**
         * Instantiates a new User.
         */
        public User() {}

        /**
         * Instantiates a new User.
         *
         * @param email    the email
         * @param username the username
         * @param password the password
         */
        public User(String email, String username, String password) {
            this.email = email;
            this.username = username;
            this.password = password;
        }
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    private interface ProfileQuery {
        /**
         * The Projection.
         */
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        /**
         * The constant ADDRESS.
         */
        int ADDRESS = 0;
        /**
         * The constant IS_PRIMARY.
         */
        int IS_PRIMARY = 1;
    }
}
