package com.shop.onlineapp;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;
import android.widget.Button;
import android.widget.EditText;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputLayout;

public class ShopRegisterActivity extends AppCompatActivity {
    Button registerButton;
    EditText registerUserNameField;
    EditText registerEmailField;
    EditText registerPasswordField;
    TextInputLayout regUserErrorLabel;
    TextInputLayout regEmailErrorLabel;
    TextInputLayout regPasswordErrorLabel;
    SqliteDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        dbHelper = new SqliteDBHelper(this);
        initView();
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateAll()) {
                    String name = registerUserNameField.getText().toString();
                    String emailId = registerEmailField.getText().toString();
                    String password = registerPasswordField.getText().toString();
                    if (!dbHelper.isAccountExists(emailId)) {
                        dbHelper.createNewUser(new UserObject(null, name, emailId, password));
                        Snackbar.make(registerButton, "Registered successfully! Login Now", Snackbar.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run()
                            {   // call finish for refreshing the view
                                finish();
                            }
                        }, Snackbar.LENGTH_LONG);
                    }else {
                        Snackbar.make(registerButton, "Sorry!!! Account already exists!!! ", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public boolean validateAll() {
        boolean isValid = false;
        String userName = registerUserNameField.getText().toString();
        String emailId = registerEmailField.getText().toString();
        String password = registerPasswordField.getText().toString();
        if (userName.isEmpty()) {
            isValid = false;
            regUserErrorLabel.setError("Username can't be empty!");
        } else {
            if (userName.length() > 5) {
                isValid = true;
                regUserErrorLabel.setError(null);
            } else {
                isValid = false;
                regUserErrorLabel.setError("Too short username(>5)!");
            }
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailId).matches()) {
            isValid = false;
            regEmailErrorLabel.setError("Enter valid email id (e.g name@gmail.com)!");
        } else {
            isValid = true;
            regEmailErrorLabel.setError(null);
        }
        if (password.isEmpty()) {
            isValid = false;
            regPasswordErrorLabel.setError("Password can't be empty!");
        } else {
            if (password.length() > 5) {
                isValid = true;
                regPasswordErrorLabel.setError(null);
            } else {
                isValid = false;
                regPasswordErrorLabel.setError("Too short password(>5)!");
            }
        }
        return isValid;
    }

    private void initView() {
        TextView textViewLogin = (TextView) findViewById(R.id.textViewLogin);
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        registerEmailField = (EditText) findViewById(R.id.emailField);
        registerPasswordField = (EditText) findViewById(R.id.passwordField);
        registerUserNameField = (EditText) findViewById(R.id.userNameField);
        regEmailErrorLabel = (TextInputLayout) findViewById(R.id.emailErrorLabel);
        regPasswordErrorLabel = (TextInputLayout) findViewById(R.id.passwordErrorLabel);
        regUserErrorLabel = (TextInputLayout) findViewById(R.id.userErrorLabel);
        registerButton = (Button) findViewById(R.id.registerButton);
    }

}
