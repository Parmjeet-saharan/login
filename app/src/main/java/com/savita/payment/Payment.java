package com.savita.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.app.ProgressDialog;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.paytm.pgsdk.TransactionManager;
import com.paytm.pgsdk.PaytmOrder;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Locale;


import com.savita.login.R;
import com.savita.simplefunction.ConstantVar;

import io.github.parthav46.httprequest.HttpRequest;
import io.github.parthav46.httprequest.HttpResponseCallback;

public class Payment extends AppCompatActivity {
    private TextView textView,textView1,textView2;
    private Button button;
    double catFees,ourFees,totalFees;
    String uid = "QBua2xNPO5QGXRb1Ic9zDsc6u6Y2";
    final int requestCode = 2;
    String ORDER_ID;
    LoaderManager loaderManager;
    ConstantVar constantVar = new ConstantVar();
    String bodyData = "";
    float value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ORDER_ID = "ID" + System.currentTimeMillis();
        loaderManager = LoaderManager.getInstance(this);
        textView = (TextView) findViewById(R.id.catfees);
        textView1 = (TextView) findViewById(R.id.ourFees);
        textView2 = (TextView) findViewById(R.id.totalFees);
        button = (Button) findViewById(R.id.button);
        catFees =100;
        textView.setHint("Your FORM FEES According to your catagery is "+ catFees);
        textView.setVisibility(View.VISIBLE);
        ourFees = 60;
        textView1.setHint("OUR Charges is "+ ourFees);
        textView1.setVisibility(View.VISIBLE);
        totalFees = 160;
        textView2.setHint("SO Total FEES is  "+ totalFees);
        textView2.setVisibility(View.VISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment();
            }
        });
      }
    void startPayment() {
        final ProgressDialog progressDialog = new ProgressDialog(Payment.this);
        progressDialog.setMessage("Making Payment....");
        progressDialog.show();
        bodyData = getPaytmParams();

        new HttpRequest(Payment.this, constantVar.CHECKSUM, HttpRequest.Request.POST, bodyData, new HttpResponseCallback() {
            @Override
            public void onResponse(String response) {
                if(response != null) {
                    try {
                        JSONObject paytmParams = new JSONObject();

                        JSONObject head = new JSONObject();

                        String checksum = new JSONObject(response).getString("checksum");
                        Log.e("checksum @@@@@@@", checksum);
                        head.put("signature", checksum);

                        paytmParams.put("head", head);
                        paytmParams.put("body", new JSONObject(bodyData));

                        String url = "https://securegw-stage.paytm.in/theia/api/v1/initiateTransaction?mid=" + constantVar.MERCHANT_ID + "&orderId=" + ORDER_ID;

                        new HttpRequest(Payment.this, url, HttpRequest.Request.POST, paytmParams.toString(), new HttpResponseCallback() {
                            @Override
                            public void onResponse(String response) {
                                if (response != null) {
                                    try {
                                        processPaytmTransaction(new JSONObject(response));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } finally {
                                        ORDER_ID = "ID" + System.currentTimeMillis();
                                    }
                                }
                                if (progressDialog.isShowing()) progressDialog.dismiss();
                            }
                        }).execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (progressDialog.isShowing()) progressDialog.dismiss();
                    }
                } else {
                    if (progressDialog.isShowing()) progressDialog.dismiss();
                }
            }
        }).execute();
    }

    String getPaytmParams () {
        JSONObject paytmParams;
        try {
            JSONObject body = new JSONObject();
            body.put("requestType", "Payment");
            body.put("mid", constantVar.MERCHANT_ID);
            body.put("websiteName", constantVar.WEBSITE);
            body.put("orderId", ORDER_ID);
            body.put("callbackUrl", constantVar.CALLBACK);

            JSONObject txnAmount = new JSONObject();
            try{
                value = (float) totalFees;
            } catch (Exception e) {
                value = 0f;
            }
            txnAmount.put("value", String.format(Locale.getDefault(), "%.2f", value));
            txnAmount.put("currency", "INR");

            JSONObject userInfo = new JSONObject();
            userInfo.put("custId", uid);

            body.put("txnAmount", txnAmount);
            body.put("exam","ssc");
            body.put("cat","gen_fees");
            body.put("userInfo", userInfo);

            /*
             * Generate checksum by parameters we have in body
             * You can get Checksum JAR from https://developer.paytm.com/docs/checksum/
             * Find your Merchant Key in your Paytm Dashboard at https://dashboard.paytm.com/next/apikeys
             */

            paytmParams = body;

        } catch (Exception e) {
            e.printStackTrace();
            paytmParams = new JSONObject();
        }
        return paytmParams.toString();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == this.requestCode && data != null) {
            String nsdk = data.getStringExtra("nativeSdkForMerchantMessage");
            String response = data.getStringExtra("response");
            Toast.makeText(this, nsdk + response, Toast.LENGTH_SHORT).show();
        }
    }

    void processPaytmTransaction(JSONObject data) {
        try {
            Log.i("CHECKSUM", data.getJSONObject("body").toString());
            Log.i("CHECKSUM", data.getJSONObject("head").getString("signature"));
            Log.e("TXN_TOKEN", data.getJSONObject("body").getString("txnToken"));



            PaytmOrder paytmOrder = new PaytmOrder(ORDER_ID, constantVar.MERCHANT_ID, data.getJSONObject("body").getString("txnToken"),
                    String.format(Locale.getDefault(), "%.2f", value), constantVar.CALLBACK);
            TransactionManager transactionManager = new TransactionManager(paytmOrder, new PaytmPaymentTransactionCallback() {
                @Override
                public void onTransactionResponse(Bundle bundle) {
                    Toast.makeText(getApplicationContext(), "Payment Transaction response " + bundle.toString(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void networkNotAvailable() {
                    Log.e("RESPONSE", "network not available");
                }

                @Override
                public void onErrorProceed(String s) {
                    Log.e("RESPONSE", "error proceed: " + s);

                }

                @Override
                public void clientAuthenticationFailed(String s) {
                    Log.e("RESPONSE", "client auth failed: " + s);

                }

                @Override
                public void someUIErrorOccurred(String s) {
                    Log.e("RESPONSE", "UI error occured: " + s);

                }

                @Override
                public void onErrorLoadingWebPage(int i, String s, String s1) {
                    Log.e("RESPONSE", "error loading webpage: " + s + "--" + s1);

                }

                @Override
                public void onBackPressedCancelTransaction() {
                    Log.e("RESPONSE", "back pressed");

                }

                @Override
                public void onTransactionCancel(String s, Bundle bundle) {
                    Log.e("RESPONSE", "transaction cancel: " + s);

                }
            });
            transactionManager.startTransaction(Payment.this, requestCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     }
