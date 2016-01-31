package cowberryteam.ru.msa.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import cowberryteam.ru.msa.R;

public class AuthorizationActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        ((Button)findViewById(R.id.buttonSignIn)).setOnClickListener(this);
        ((TextView)findViewById(R.id.buttonSkipAuth)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonSkipAuth:
                openNewsActivity();
                break;
            case R.id.buttonSignIn:
                login();
                break;
        }
    }

    public boolean login(){
        EditText login_input = (EditText)findViewById(R.id.login_input);
        EditText login_password = (EditText)findViewById(R.id.password_input);

        TextInputLayout login_til = (TextInputLayout)login_input.getParent();
        TextInputLayout password_til = (TextInputLayout)login_password.getParent();

        if(TextUtils.isEmpty(login_input.getText())){
            login_til.setErrorEnabled(true);
            login_til.setError(getString(R.string.error_empty_login));
            return  false;
        }else login_til.setErrorEnabled(false);
        if(TextUtils.isEmpty(login_password.getText())){
            password_til.setErrorEnabled(true);
            password_til.setError(getString(R.string.error_empty_password));
            return  false;
        }else  password_til.setErrorEnabled(false);

        LoginAsyncTask loginThread = new LoginAsyncTask();
        loginThread.execute();

        return  true;
    }

    class LoginAsyncTask extends AsyncTask<Void, Void, Boolean>{
        @Override
        protected void onPreExecute() {
            ((LinearLayout)findViewById(R.id.authLoginLayout)).setVisibility(View.INVISIBLE);
            ((LinearLayout)findViewById(R.id.authLoadingLayout)).setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                TimeUnit.SECONDS.sleep(4);
            }catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            ((LinearLayout)findViewById(R.id.authLoginLayout)).setVisibility(View.VISIBLE);
            ((LinearLayout)findViewById(R.id.authLoadingLayout)).setVisibility(View.GONE);
            openNewsActivity();
        }
    }

    public void openNewsActivity(){
        Intent intent = new Intent(getApplicationContext(), NewsActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_in_top);
    }
}
