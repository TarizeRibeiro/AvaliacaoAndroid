package com.example.fabricio.myapplication.Util;

import android.content.Context;
import android.widget.EditText;

import com.example.fabricio.myapplication.R;

/**
 * Created by Fabricio on 22/07/2015.
 */
public class FormHelper {

    private FormHelper(){
        super();
    }

    public static boolean requiredValidate(Context context, EditText... editTexts){
        boolean valid = true;

        for ( EditText editText : editTexts){
            String value = editText.getText() == null? null : editText.getText().toString().trim();
            if(value == null || value.isEmpty()){
                String errorMessage = context.getString(R.string.requiredField);
                editText.setError(errorMessage);
                valid = false;
            }
        }

        return  valid;
    }

}
