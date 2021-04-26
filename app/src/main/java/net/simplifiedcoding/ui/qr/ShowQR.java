package net.simplifiedcoding.ui.qr;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import net.simplifiedcoding.R;
import net.simplifiedcoding.exchange.interfaces.ExchangeAPI;
import net.simplifiedcoding.exchange.models.Exchange;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowQR extends AppCompatActivity {
    TextView textView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r);

        textView = (TextView)findViewById(R.id.edittext);
        imageView = (ImageView)findViewById(R.id.imageview);
        getExchage();
    }

    private void getExchage(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ExchangeAPI exchangeAPI = retrofit.create(ExchangeAPI.class);

        Call<List<Exchange>> call = exchangeAPI.getExchange();
        call.enqueue(new Callback<List<Exchange>>() {
            @Override
            public void onResponse(Call<List<Exchange>> call, Response<List<Exchange>> response) {
                if(!response.isSuccessful()){
                    textView.setText("Codigo: " + response.code());
                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

                    try{
                        BitMatrix bitMatrix = multiFormatWriter.encode(textView.getText().toString(), BarcodeFormat.QR_CODE,500,500);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                        imageView.setImageBitmap(bitmap);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    return;
                }

                List<Exchange> echangeList = response.body();

                for(Exchange exchange: echangeList ){
                    String content = "";
                    content += "BS:" + exchange.getBS() + "\n";
                    content += "BTC" + exchange.getBTC() + "\n";
                    content += "ETH:" + exchange.getETH() + "\n";
                    content += "PTR:" + exchange.getPTR() + "\n";
                    content += "USD:" + exchange.getUSD() + "\n";
                    content += "EUR:" + exchange.getEUR() + "\n";
                    textView.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Exchange>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }
}