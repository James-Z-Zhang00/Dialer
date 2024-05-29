# Dialer

An Android app for making phone calls with customized UI, device optimization and multilingual support.

## General Structure

The project was made by 3 parts:
- .xml UI design
- Back end Java logic
- Manifest file & other meta data

## .xml UI Design

The portrait and  view, button labels are beautified by `spannableString`

<img width="618" alt="Screen Shot 2024-05-29 at 12 07 18 PM" src="https://github.com/James-Z-Zhang00/Dialer/assets/144994336/a902eaac-ca70-4197-9f0f-3b92e414f822">

The landscape view

<img width="504" alt="Screen Shot 2024-05-29 at 12 07 09 PM" src="https://github.com/James-Z-Zhang00/Dialer/assets/144994336/3a115987-56e7-475f-a533-c8e3d5a8a8d8">

The UI was built by nested `LinearLayout`, scales are vary for different screen sizes

<img width="730" alt="Screen Shot 2024-05-29 at 12 09 40 PM" src="https://github.com/James-Z-Zhang00/Dialer/assets/144994336/eebe0f60-52c0-450a-a83e-c6930ccf5f06">

## Back End Java Logic

Check if the app has _CALL ACTION_ promission, if not ask the user to give promission and the following operations

```java
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
```

Clipboard feature to allow user copy the entered number for future process

```java
private void copyToClipboard() {
        ClipboardManager cb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("number",dialNumber.getText().toString());
        cb.setPrimaryClip(clip);
        makeToast("Number copied to clipboard!");
    }
```

Button components initialization

```java
btn0.setOnClickListener(l -> addNumber("0"));
btn1.setOnClickListener(l -> addNumber("1"));
```

Button beautification, where button object will be passed as parameter

```java
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
```

Screen modification to hide the top action bar for more spaces

```java
if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getSupportActionBar().hide();
        }
```

## Manifest File & Other Meta Data

Some basic information about the app and icon setting

```xml
<application
        android:allowBackup="true"
        android:dataExtractionRules="@xml/data_extraction_rules"
        android:fullBackupContent="@xml/backup_rules"
        android:icon="@mipmap/ic_icon"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_icon_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.Dialer"
        tools:targetApi="31">
        <activity
            android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
```

Multilingual support

Use variables for each string

```xml
<string name="app_name">Dialer</string>
<string name="call">Call</string>
<string name="del">Del</string>
<string name="copy">Copy Number</string>
```

The supported languages: English, German, Russian and Spanish

<img width="797" alt="Screen Shot 2024-05-29 at 1 25 18 PM" src="https://github.com/James-Z-Zhang00/Dialer/assets/144994336/9de1c1dd-8848-47db-9362-2e3f0b7ee075">

The language of the app will change as the system settings change

<img width="505" alt="Screen Shot 2024-05-29 at 1 27 25 PM" src="https://github.com/James-Z-Zhang00/Dialer/assets/144994336/8bce06e5-3374-4039-a562-4093753c1006">  \  

<img width="498" alt="Screen Shot 2024-05-29 at 1 27 47 PM" src="https://github.com/James-Z-Zhang00/Dialer/assets/144994336/987adba4-f97a-4153-97c7-868427386e51">
