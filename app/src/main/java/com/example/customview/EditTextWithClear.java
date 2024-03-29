package com.example.customview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.res.ResourcesCompat;

public class EditTextWithClear extends AppCompatEditText {

    Drawable mClearButtonImage;

    private void init() {
        mClearButtonImage = ResourcesCompat.getDrawable(
                getResources(), R.drawable.ic_clear_opaque_24dp, null);

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//          saat teks terisi maka button muncul
                showClearButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(getCompoundDrawables()[2] != null){
                    float clearButtonStartPosition = (getWidth()-getPaddingEnd()-
                            mClearButtonImage.getIntrinsicWidth());
                    boolean isClearButtonClicked = false;

//                  if (motionEvent.getX() > clearButtonStart){
//                      isClearButtonClicked = true;
//                  }

//                  TUGAS Custom View RTL
                    if (getLayoutDirection() == LAYOUT_DIRECTION_RTL) {
                        if (motionEvent.getX() < clearButtonStartPosition) {
                            isClearButtonClicked = true;
                        }
                    }
                    else {
                        if (motionEvent.getX() > clearButtonStartPosition) {
                            isClearButtonClicked = true;
                        }
                    }

                    if (isClearButtonClicked){
                        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                            mClearButtonImage = ResourcesCompat.getDrawable(getResources(),
                                    R.drawable.ic_clear_black_24dp, null);
                            showClearButton();
                        }

                        if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                            mClearButtonImage = ResourcesCompat.getDrawable(getResources(),
                                    R.drawable.ic_clear_opaque_24dp, null);
                            showClearButton();
                            getText().clear();
                            hideClearButton();
                            return true;
                        }
                    }
                }
                return false;
            }
        });
    }

    public EditTextWithClear(@NonNull Context context) {
        super(context);
        init();
    }

    public EditTextWithClear(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextWithClear(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void showClearButton(){
        setCompoundDrawablesWithIntrinsicBounds(null, null, mClearButtonImage, null);
    }

    private void hideClearButton(){
        setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
    }
}
