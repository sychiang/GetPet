package iii.org.tw.getpet;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import common.CDictionary;
import model.object_petDataForSelfDB;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static iii.org.tw.getpet.R.id.tv_msgTo_userName;

public class ActUpdateStatus extends AppCompatActivity {
    private String access_token, Email, UserName,UserId, HasRegistered, LoginProvider;
    object_petDataForSelfDB iv_object_petDataForSelfDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_update_status);
        setTitle(Html.fromHtml("<font color='#2d4b44'>送養確認</font>"));
        initComponent();

        UserName = getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_username,"");
        Log.d(CDictionary.Debug_TAG,"GET USER NAME："+UserName);
        UserId = getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_userid,"");
        Log.d(CDictionary.Debug_TAG,"GET USER ID："+UserId);
        access_token = getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_token,"");
        Log.d(CDictionary.Debug_TAG,"GET USER ID："+access_token);


    }

    View.OnClickListener btnSubmit_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            AlertDialog dialog = new AlertDialog.Builder(ActUpdateStatus.this)
                    .setMessage(Html.fromHtml("<font color='#2d4b44'>是否確定送出資料</font>"))
                    .setTitle(Html.fromHtml("<font color='#2d4b44'>送出確認</font>"))
                    .setPositiveButton("送出", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String emptyInputField = checkInput();
                            if (emptyInputField.length() > 10) {
                                new AlertDialog.Builder(ActUpdateStatus.this)
                                        .setMessage(Html.fromHtml("<font color='#2d4b44'>"+emptyInputField+"</font>"))
                                        .setTitle(Html.fromHtml("<font color='#2d4b44'>欄位未填</font>"))
                                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        })
                                        .show();
                            }else {
                                sendRequestToServer();
                            }
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();
        }
    };
    public void sendRequestToServer(){
        Intent l_intent = getIntent();
        //object_petDataForSelfDB l_object_petDataForSelfDB = (object_petDataForSelfDB) l_intent.getSerializableExtra("object_ConditionOfAdoptPet_objA");
        iv_object_petDataForSelfDB = (object_petDataForSelfDB) l_intent.getSerializableExtra("object_ConditionOfAdoptPet_objA");

        String strURL = "http://twpetanimal.ddns.net:9487/api/v1/animalDatas";
        strURL += "/"+String.format("%d",iv_object_petDataForSelfDB.getAnimalID());
        Log.d(CDictionary.Debug_TAG,"GET URL: "+strURL);
        //傳送PUT修改為已被認養
        OkHttpClient okhttpclient = new OkHttpClient();
        final MediaType mediatype_json = MediaType.parse("application/json; charset=utf-8");

        JSONObject jsonObject = new JSONObject();
        Log.d(CDictionary.Debug_TAG,"CREATE JSON OBJ: "+jsonObject.toString());
        try {
            jsonObject.put("animalID", iv_object_petDataForSelfDB.getAnimalID());
            jsonObject.put("animalKind", iv_object_petDataForSelfDB.getAnimalKind());
            jsonObject.put("animalType", iv_object_petDataForSelfDB.getAnimalType());
            jsonObject.put("animalName", iv_object_petDataForSelfDB.getAnimalName());
            jsonObject.put("animalAddress",iv_object_petDataForSelfDB.getAnimalAddress());
            jsonObject.put("animalDate", iv_object_petDataForSelfDB.getAnimalDate());
            jsonObject.put("animalGender", iv_object_petDataForSelfDB.getAnimalGender());
            jsonObject.put("animalAge",iv_object_petDataForSelfDB.getAnimalAge());
            jsonObject.put("animalColor",iv_object_petDataForSelfDB.getAnimalColor());
            jsonObject.put("animalBirth",iv_object_petDataForSelfDB.getAnimalBirth());
            jsonObject.put("animalChip",iv_object_petDataForSelfDB.getAnimalChip());
            jsonObject.put("animalHealthy",iv_object_petDataForSelfDB.getAnimalHealthy());
            jsonObject.put("animalDisease_Other",iv_object_petDataForSelfDB.getAnimalDisease_Other());
            jsonObject.put("animalOwner_userID",iv_object_petDataForSelfDB.getAnimalOwner_userID());
            jsonObject.put("animalReason",iv_object_petDataForSelfDB.getAnimalReason());
            jsonObject.put("animalGetter_userID",edTxt_getterAccount.getText().toString());
            jsonObject.put("animalAdopted","已被領養");
            jsonObject.put("animalAdoptedDate","");
            jsonObject.put("animalNote",iv_object_petDataForSelfDB.getAnimalNote());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody =  RequestBody.create(mediatype_json,jsonObject.toString());
        Request postRequest = new Request.Builder()
                .url(strURL)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization","Bearer "+access_token)
                .put(requestBody)
                .build();

        Call call = okhttpclient.newCall(postRequest);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String json = response.body().string();
                Log.d(CDictionary.Debug_TAG,"RESPONSE BODY: "+json);
                Log.d(CDictionary.Debug_TAG,"RESPONSE CODE: "+response.code());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(ActUpdateStatus.this);
                        dialog.setTitle(Html.fromHtml("<font color='#2d4b44'>資料傳送完畢</font>"));
                        dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                goUploadList();
                            }
                        });
                        dialog.create().show();
                    }
                });
            }
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(CDictionary.Debug_TAG,"PUT FAIL......");
                AlertDialog.Builder dialog = new AlertDialog.Builder(ActUpdateStatus.this);
                dialog.setTitle(Html.fromHtml("<font color='#2d4b44'>連線錯誤, 請稍後再試</font>"));
                dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.create().show();
            }
        });
    }

    private void goUploadList() {
        Intent intent = new Intent(ActUpdateStatus.this, ActAdoptUploadList.class);
        startActivity(intent);
        startActivity(intent);
        ActAdoptUploadList.iv_ActAdoptUploadList.finish();
        ActAdoptEdit.iv_ActAdoptEdit.finish();
        finish();
    }

    public String checkInput() {
        String emptyInputField = "尚未填寫以下欄位:\n";
        emptyInputField += edTxt_getterAccount.getText().toString().isEmpty() ? "認養人帳號\n" : "";
        return emptyInputField;
    }

    @Override
    public void finish() {
        super.finish();
    }

    public void initComponent(){
        btnSubmit = (Button)findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(btnSubmit_Click);
        edTxt_getterAccount=(EditText)findViewById(R.id.edTxt_getterAccount);
    }
    Button btnSubmit;
    EditText edTxt_getterAccount;
}
