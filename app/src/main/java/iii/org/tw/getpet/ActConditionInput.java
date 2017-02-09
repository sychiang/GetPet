package iii.org.tw.getpet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import common.CDictionary;
import model.object_ConditionOfAdoptPet;

public class ActConditionInput extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_condition_input);
        init();
    }

    @Override
    public void finish() {
        object_ConditionOfAdoptPet l_object_ConditionOfAdoptPet_objA = new object_ConditionOfAdoptPet();
        l_object_ConditionOfAdoptPet_objA.setConditionAge(edTxt_conditionAge.getText().toString());
        l_object_ConditionOfAdoptPet_objA.setConditionEconomy(edTxt_conditionEconomy.getText().toString());
        l_object_ConditionOfAdoptPet_objA.setConditionHome(edTxt_conditionHome.getText().toString());
        l_object_ConditionOfAdoptPet_objA.setConditionFamily(edTxt_conditionFamily.getText().toString());
        l_object_ConditionOfAdoptPet_objA.setConditionReply(edTxt_conditionReply.getText().toString());
        l_object_ConditionOfAdoptPet_objA.setConditionPaper(edTxt_conditionPaper.getText().toString());
        l_object_ConditionOfAdoptPet_objA.setConditionFee(edTxt_conditionFee.getText().toString());
        l_object_ConditionOfAdoptPet_objA.setConditionOther(edTxt_conditionOther.getText().toString());
        Intent intent = getIntent();
        intent.putExtra("l_object_ConditionOfAdoptPet_objA",l_object_ConditionOfAdoptPet_objA);
        setResult(CDictionary.IntentRqCodeOfPetAdoptCondition,intent);
        super.finish();
    }

    private void init() {

        edTxt_conditionAge=(EditText)findViewById(R.id.edTxt_conditionAge);
        edTxt_conditionEconomy=(EditText)findViewById(R.id.edTxt_conditionEconomy);
        edTxt_conditionHome=(EditText)findViewById(R.id.edTxt_conditionHome);
        edTxt_conditionFamily=(EditText)findViewById(R.id.edTxt_conditionFamily);
        edTxt_conditionReply=(EditText)findViewById(R.id.edTxt_conditionReply);
        edTxt_conditionPaper=(EditText)findViewById(R.id.edTxt_conditionPaper);
        edTxt_conditionFee=(EditText)findViewById(R.id.edTxt_conditionFee);
        edTxt_conditionOther=(EditText)findViewById(R.id.edTxt_conditionOther);
        //*****
        Intent l_intent = getIntent();
        object_ConditionOfAdoptPet l_object_ConditionOfAdoptPet = (object_ConditionOfAdoptPet)l_intent.getSerializableExtra("l_object_ConditionOfAdoptPet_objA");
        if(l_object_ConditionOfAdoptPet != null){
            edTxt_conditionAge.setText(l_object_ConditionOfAdoptPet.getConditionAge());
            edTxt_conditionEconomy.setText(l_object_ConditionOfAdoptPet.getConditionEconomy());
            edTxt_conditionHome.setText(l_object_ConditionOfAdoptPet.getConditionHome());
            edTxt_conditionFamily.setText(l_object_ConditionOfAdoptPet.getConditionFamily());
            edTxt_conditionReply.setText(l_object_ConditionOfAdoptPet.getConditionReply());
            edTxt_conditionPaper.setText(l_object_ConditionOfAdoptPet.getConditionPaper());
            edTxt_conditionFee.setText(l_object_ConditionOfAdoptPet.getConditionFee());
            edTxt_conditionOther.setText(l_object_ConditionOfAdoptPet.getConditionOther());
        }
        //********
        Button btnSubmit =(Button)findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //*********
    }
    EditText edTxt_conditionAge;
    EditText edTxt_conditionEconomy;
    EditText edTxt_conditionHome;
    EditText edTxt_conditionFamily;
    EditText edTxt_conditionReply;
    EditText edTxt_conditionPaper;
    EditText edTxt_conditionFee;
    EditText edTxt_conditionOther;
}
