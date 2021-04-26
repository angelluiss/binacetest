package net.simplifiedcoding.ui.qr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.zxing.Result;

import net.simplifiedcoding.R;


import java.util.logging.Logger;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import okhttp3.logging.HttpLoggingInterceptor;

public class QrCodeScanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_scanner);

        // Programmatically initialize the scanner view
        mScannerView = new ZXingScannerView(this);
        // Set the scanner view as the content view
        setContentView(mScannerView);
    }

    @Override
    protected void onResume() {
        super.onResume();

        super.onResume();
        // Register ourselves as a handler for scan results.
        mScannerView.setResultHandler(this);
        // Start camera on resume
        mScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Stop camera on pause
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        // Do something with the result here
        // Prints scan results
        Log.i("result", result.getText());
        // Prints the scan format (qrcode, pdf417 etc.)
        Log.i("result", result.getBarcodeFormat().toString());
        //If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);
        Intent intent = new Intent();
        intent.putExtra(AppConstants.KEY_QR_CODE, result.getText());
        setResult(RESULT_OK, intent);
        finish();
    }
}