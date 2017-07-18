package com.example.RB.rebasejumpers;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.DialogInterface;
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
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
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
import java.util.regex.Pattern;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class LoginActivity
        extends AppCompatActivity
        implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;
    /**
     * The constant VALID_EMAIL_ADDRESS_REGEX.
     */
// Email regex
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                    Pattern.CASE_INSENSITIVE);

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    // Database references
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Authentication
        mAuth = FirebaseAuth.getInstance();

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(
                new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(
                    final TextView textView,
                    final int id, final KeyEvent keyEvent) {
                if ((id == R.id.login) || (id == EditorInfo.IME_NULL)) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        final Button mEmailSignInButton =
                (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {
                attemptLogin();
            }
        });


        Button registerButton = (Button) findViewById(R.id.register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            /**
             * The onClick method
             * @param view takes in the view
             */
            @Override
            public void onClick(final View view) {
                startActivity(new Intent(LoginActivity.this,
                        RegistrationActivity1.class));
            }
        });

        Button forgotPasswordButton =
                (Button) findViewById(R.id.forgot_password);

        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog alertDialog =
                        new AlertDialog.Builder(LoginActivity.this).create();
                alertDialog.setTitle("Forgotten Password");

                mAuth.sendPasswordResetEmail(mEmailView.getText().toString())
                                .addOnCompleteListener(
                                        new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(
                                            @NonNull final Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(
                                                    LoginActivity.this,
                                                    "Email sent.",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                        });

                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(
                                    final DialogInterface dialog,
                                    final int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });


        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean mayRequestContacts() {
        if (checkSelfPermission(READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView,
                    R.string.permission_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(final View v) {
                            requestPermissions(
                                    new String[]{READ_CONTACTS},
                                    REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS},
                    REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * The onRequestPermissionsResult is a callback received when permissions request has been
     * completed
     * @param requestCode the integer request code
     * @param permissions the String array of permissions
     * @param grantResults the integer array of the results
     */
    @Override
    public void onRequestPermissionsResult(final int requestCode,
                                           @NonNull final String[] permissions,
                                           @NonNull final int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if ((grantResults.length == 1)
                    && (grantResults[0]
                    == PackageManager.PERMISSION_GRANTED)) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        // Local variables
        boolean cancel = false;
        View focusView = null;

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

        // Cancellation parts
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this,
                            new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(
                                @NonNull final Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success,
                                // update UI with the
                                // signed-in user's information
                                startActivity(new Intent(LoginActivity.this,
                                        LogoutScreen.class));
                            } else {
                                // If sign in fails,
                                // display a message to the user.
                                Toast.makeText(LoginActivity.this,
                                        "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                cancelLogin();
                            }
                        }
                    });
        }
    }

    // Checks email against a valid email regex
    private boolean isEmailValid(final CharSequence email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    private boolean isPasswordValid(final CharSequence password) {
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress() {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        int shortAnimTime = getResources().
                getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(View.GONE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(final Animator animation) {
                mLoginFormView.setVisibility(View.GONE);
            }
        });

        mProgressView.setVisibility(View.VISIBLE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(View.VISIBLE);
            }
        });

        Button cancelButton = (Button) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {
                cancelLogin();
            }
        });

    }

    private void cancelLogin() {
        startActivity(new Intent(LoginActivity.this, LoginActivity.class));
    }

    /**
     * The onCreateLoader to get the profile of the user
     * @param i an integer i
     * @param bundle the final Bundle
     * @return the Loader coming from the Cursor
     */
    @Override
    @TargetApi(Build.VERSION_CODES.M)
    public Loader<Cursor> onCreateLoader(final int i, final Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY),
                ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE
                        + " = ?",
                new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    /**
     * The onLoadFinished loads the data after the user logs in
     * @param cursorLoader loads all the data
     * @param cursor the cursor to load the data
     */
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

    /**
     * The onLoaderReset method
     * @param cursorLoader loads all the data
     */
    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(final List<String>
                                                 emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView
        // what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line,
                        emailAddressCollection);

        mEmailView.setAdapter(adapter);
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

