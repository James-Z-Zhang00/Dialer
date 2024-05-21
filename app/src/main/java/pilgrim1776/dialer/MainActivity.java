

package pilgrim1776.dialer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btn0, btn1, btn2, btn3, btn4,btn5,btn6, btn7, btn8, btn9, btnDel, btnCall;
    Button btnAsterisk, btnPound;
    int CALL_PERMISSION_REQUEST_CODE = 1776;
    TextView dialNumber, copyNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // hide the top actionBar to provide a larger area for number pad displaying
        // if the device is in landscape layout
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_main);

        btn0 = findViewById(R.id.btn0); btn1 = findViewById(R.id.btn1); btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3); btn4 = findViewById(R.id.btn4); btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6); btn7 = findViewById(R.id.btn7); btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);

        btnDel = findViewById(R.id.btnDel);
        btnCall = findViewById(R.id.btnCall);
        btnAsterisk = findViewById(R.id.btnAsterisk);
        btnPound = findViewById(R.id.btnPound);
        dialNumber = findViewById(R.id.daileNumber);
        copyNumber = findViewById(R.id.copyNumber);

        //TODO: make the button label display with larger number and smaller alphabet
        formatBtn(btn0);formatBtn(btn1);formatBtn(btn2);formatBtn(btn3);
        formatBtn(btn4);formatBtn(btn5);formatBtn(btn6);formatBtn(btn7);
        formatBtn(btn8);formatBtn(btn9);formatBtn(btnAsterisk);formatBtn(btnPound);
        formatBottomBtn(btnCall); formatBottomBtn(btnDel);

        btn0.setOnClickListener(l -> addNumber("0")); btn1.setOnClickListener(l -> addNumber("1"));
        btn2.setOnClickListener(l -> addNumber("2")); btn3.setOnClickListener(l -> addNumber("3"));
        btn4.setOnClickListener(l -> addNumber("4")); btn5.setOnClickListener(l -> addNumber("5"));
        btn6.setOnClickListener(l -> addNumber("6")); btn7.setOnClickListener(l -> addNumber("7"));
        btn8.setOnClickListener(l -> addNumber("8")); btn9.setOnClickListener(l -> addNumber("9"));
        btnCall.setOnClickListener(l -> makeCall());

        btn0.setOnLongClickListener(l           ->          {addNumber("+");return true;});
        // enter '+' if the user hold button 0
        btn1.setOnLongClickListener(l           ->          {callVoiceMail();return true;});
        btnAsterisk.setOnClickListener(l        ->          addNumber("*"));
        btnPound.setOnClickListener(l           ->          addNumber("#"));
        btnDel.setOnClickListener(l             ->          deleteNumber());
        copyNumber.setOnClickListener(l         ->          copyToClipboard());

        dialNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (dialNumber.getText().toString().length() == 0) {btnDel.setVisibility(View.INVISIBLE);copyNumber.setVisibility(View.INVISIBLE);}
                else {btnDel.setVisibility(View.VISIBLE);copyNumber.setVisibility(View.VISIBLE);}
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void deleteNumber() {
        String currentNumber = dialNumber.getText().toString();
        currentNumber = currentNumber.substring(0,currentNumber.length()-1);
        dialNumber.setText(currentNumber);
    }

    private void addNumber(String num) {
        String currentNumber = dialNumber.getText().toString();
        currentNumber += num;
        dialNumber.setText(currentNumber);
    }

    private void copyToClipboard() {
        ClipboardManager cb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("number",dialNumber.getText().toString());
        cb.setPrimaryClip(clip);
        makeToast("Number copied to clipboard!");
    }

    private void makeCall() {
        // if condition to prevent user call an empty number
        String currentNumber = dialNumber.getText().toString();
        if(currentNumber.length() != 0) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+currentNumber));
            if(ActivityCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                askForCallPermission();
            } else {
                startActivity(callIntent);
            }
        } else {
            makeToast("Please enter a valid number");
        }
    }

    private void callVoiceMail() {
        makeToast("No SIM card, voice mail service not found.");
    }

    private void askForCallPermission() {
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[] {Manifest.permission.CALL_PHONE},
                CALL_PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            makeCall();
        } else {
            makeToast("Allow the permission to make call!");
        }
    }

    private void formatBtn(Button btn) {
        String btnLabel = btn.getText().toString();
        //StyleSpan bss = new StyleSpan(Typeface.BOLD);
        SpannableString ss = new SpannableString(btnLabel);
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(20,true);
        AbsoluteSizeSpan assAfter = new AbsoluteSizeSpan(10,true);
        ss.setSpan(ass,0,1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        //ss.setSpan(bss,0,1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ss.setSpan(assAfter,1,btnLabel.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        btn.setText(ss);
    }

    private void formatBottomBtn(Button btn) {
        String newLabel = "\n";
        String btnLabel = btn.getText().toString();
        newLabel += btnLabel;
        newLabel += "\n";
        btn.setText(newLabel);
    }

    private void makeToast(String msg) {
        Toast toast = Toast.makeText(getApplicationContext(),
                msg,
                Toast.LENGTH_SHORT);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            toast.setGravity(Gravity.LEFT,100,-400);
        } else {
            toast.setGravity(Gravity.TOP,0,100);
        }
        toast.show();
    }

}
