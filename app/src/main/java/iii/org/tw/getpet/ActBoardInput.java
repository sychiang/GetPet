package iii.org.tw.getpet;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import common.CDictionary;

public class ActBoardInput extends AppCompatActivity {
    private String access_token, Email, UserName,UserId, HasRegistered, LoginProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_board_input);
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
            AlertDialog dialog = new AlertDialog.Builder(ActBoardInput.this)
                    .setMessage("是否確定送出資料")
                    .setTitle("送出確認")
                    .setPositiveButton("送出", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String emptyInputField = checkInput();
                            if (emptyInputField.length() > 10) {
                                new AlertDialog.Builder(ActBoardInput.this)
                                        .setMessage(emptyInputField)
                                        .setTitle("欄位未填")
                                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        })
                                        .show();
                            }else {
                                //sendRequestToServer();
                            }
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();
        }
    };

    private void goBoardList() {
        Intent intent = new Intent(ActBoardInput.this, ActBoardList.class);
        startActivity(intent);
    }

    public String checkInput() {
        String emptyInputField = "尚未填寫以下欄位:\n";
        emptyInputField += edTxt_boardContent.getText().toString().isEmpty() ? "留言內容\n" : "";
        return emptyInputField;
    }

    public void initComponent(){
        btnSubmit = (Button)findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(btnSubmit_Click);

        edTxt_boardContent=(EditText)findViewById(R.id.edTxt_boardContent);

    }
    Button btnSubmit;
    EditText edTxt_boardContent;
}
