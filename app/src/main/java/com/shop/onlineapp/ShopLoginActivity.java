package com.shop.onlineapp;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class ShopLoginActivity extends AppCompatActivity {
    EditText emailField;
    EditText passwordField;
    TextInputLayout emailErrorLabel;
    TextInputLayout passwordErrorLabel;
    Button loginButton;
    SqliteDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        dbHelper = new SqliteDBHelper(this);
        initView();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent shoppingIntent=new Intent(ShopLoginActivity.this,ShoppingActivity.class);
                startActivity(shoppingIntent);
                finish();*/
                if (validateAll()) {
                    String emailId = emailField.getText().toString();
                    String password = passwordField.getText().toString();
                    UserObject loggedInUser = dbHelper.authenticateUser(new UserObject(null, null, emailId, password));
                    if (loggedInUser != null) {
                        Snackbar.make(loginButton, "Logged in successfully!!!", Snackbar.LENGTH_LONG).show();

                        Intent shoppingIntent=new Intent(ShopLoginActivity.this,ShoppingActivity.class);
                        startActivity(shoppingIntent);
                        finish();
                    } else {
                        Snackbar.make(loginButton, "Invalid login credentials, try again!!!", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void initView() {
        TextView accountCreateLabel = (TextView) findViewById(R.id.accountCreateLabel);
        accountCreateLabel.setText(getHtmlSpanObject("<font color='#ffffff'>Not registered yet. </font><font color='#0c0089'>Register Now</font>"));
        accountCreateLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(ShopLoginActivity.this, ShopRegisterActivity.class);
                startActivity(registerIntent);
            }
        });
        emailField = (EditText) findViewById(R.id.emailField);
        passwordField = (EditText) findViewById(R.id.passwordField);
        emailErrorLabel = (TextInputLayout) findViewById(R.id.emailErrorLabel);
        passwordErrorLabel = (TextInputLayout) findViewById(R.id.passwordErrorLabel);
        loginButton = (Button) findViewById(R.id.shopLoginButton);
    }

    public boolean validateAll() {
        boolean isValid = false;
        String emailId = emailField.getText().toString();
        String password = passwordField.getText().toString();

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailId).matches()) {
            isValid = false;
            emailErrorLabel.setError("Invalid email Id (e.g name@gmail.com) !!!");
        } else {
            isValid = true;
            emailErrorLabel.setError(null);
        }
        if (password.isEmpty()) {
            isValid = false;
            passwordErrorLabel.setError("Password cant be empty!");
        } else {
            if (password.length() > 5) {
                isValid = true;
                passwordErrorLabel.setError(null);
            } else {
                isValid = false;
                passwordErrorLabel.setError("Too short password!!!");
            }
        }
        return isValid;
    }

    public static Spanned getHtmlSpanObject(String html) {
        Spanned spanResult;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            spanResult = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            spanResult = Html.fromHtml(html);
        }
        return spanResult;
    }
}
