package com.faraaf.tictacdev.limitrangeedittext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * @author Faramarz Afzali
 * @since Feb 3, 2019
 */

public class LimitRangeEditText extends LinearLayout {

    private View rootView;
    private TypedArray typedArray;
    private EditText mainEditText;
    private TextView minValue;
    private TextView maxValue;
    private TextView slash;
    private String hint;
    private String defaultText;
    private String fontFamily;
    private float textSize;
    private float textValuesSize;
    private int textViewColor;
    private int minMaxValueColor;
    private int inputType;
    private int maxRangeValue;
    private int minRangeValue;
    private int textViewLines;

    public LimitRangeEditText(Context context) {
        super(context);
        init(context);
    }

    public LimitRangeEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
        setHint(attrs);
        setDefaultText(attrs);
        setFont(context, attrs);
        setMainTextSize(attrs);
        setMaxMinTextSize(attrs);
        setDefaultTextColor(attrs);
        setMinMaxValueTextColor(attrs);
        setInputType(attrs);
        setMaxRange(attrs);
        setMinRange(attrs);
        setTextViewLines(attrs);
        lengthController(attrs);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init(Context context) {
        setSaveEnabled(true);
        rootView = inflate(context, R.layout.layout_custom_edittext, this);
        mainEditText = rootView.findViewById(R.id.mainEditText);
        minValue = rootView.findViewById(R.id.minValue);
        maxValue = rootView.findViewById(R.id.maxValue);
        slash = rootView.findViewById(R.id.txtSlash);
    }

    private void setHint(AttributeSet set) {
        checkNullSet(set);
        typedArray = getContext().obtainStyledAttributes(set, R.styleable.LimitRangeEditText);
        hint = typedArray.getString(R.styleable.LimitRangeEditText_hint);
        mainEditText.setHint(hint);
        typedArray.recycle();
    }

    private void setDefaultText(AttributeSet set) {
        checkNullSet(set);
        typedArray = getContext().obtainStyledAttributes(set, R.styleable.LimitRangeEditText);
        defaultText = typedArray.getString(R.styleable.LimitRangeEditText_defaultText);
        mainEditText.setText(defaultText);
        typedArray.recycle();
    }

    private void setFont(Context ctx, AttributeSet attrs) {
        checkNullSet(attrs);
        typedArray = ctx.obtainStyledAttributes(attrs, R.styleable.LimitRangeEditText);
        fontFamily = typedArray.getString(R.styleable.LimitRangeEditText_customFontFamily);
        prepareFont(ctx, fontFamily);
        typedArray.recycle();
    }

    public boolean prepareFont(Context ctx, String asset) {
        Typeface tf;
        try {
            tf = Typeface.createFromAsset(ctx.getAssets(), asset);
        } catch (Exception e) {
            Log.e("TAG", "Could not get typeface: " + e.getMessage());
            return false;
        }
        mainEditText.setTypeface(tf);
        minValue.setTypeface(tf);
        maxValue.setTypeface(tf);
        return true;
    }

    private void setMainTextSize(AttributeSet set) {
        checkNullSet(set);
        typedArray = getContext().obtainStyledAttributes(set, R.styleable.LimitRangeEditText);
        textSize = typedArray.getDimension(R.styleable.LimitRangeEditText_mainTextSize, 18f);
        mainEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        typedArray.recycle();
    }

    private void setMaxMinTextSize(AttributeSet set) {
        checkNullSet(set);
        typedArray = getContext().obtainStyledAttributes(set, R.styleable.LimitRangeEditText);
        textValuesSize = typedArray.getDimension(R.styleable.LimitRangeEditText_textValuesSize, 14f);
        maxValue.setTextSize(TypedValue.COMPLEX_UNIT_SP, textValuesSize);
        minValue.setTextSize(TypedValue.COMPLEX_UNIT_SP, textValuesSize);
        slash.setTextSize(TypedValue.COMPLEX_UNIT_SP, textValuesSize);
        typedArray.recycle();
    }

    private void setDefaultTextColor(AttributeSet set) {
        checkNullSet(set);
        typedArray = getContext().obtainStyledAttributes(set, R.styleable.LimitRangeEditText);
        textViewColor = typedArray.getInt(R.styleable.LimitRangeEditText_editTextColor, Color.BLACK);
        mainEditText.setTextColor(textViewColor);
        typedArray.recycle();
    }

    private void setMinMaxValueTextColor(AttributeSet set) {
        checkNullSet(set);
        typedArray = getContext().obtainStyledAttributes(set, R.styleable.LimitRangeEditText);
        minMaxValueColor = typedArray.getInt(R.styleable.LimitRangeEditText_minMaxValueColor, Color.BLACK);
        maxValue.setTextColor(minMaxValueColor);
        minValue.setTextColor(minMaxValueColor);
        slash.setTextColor(minMaxValueColor);
        typedArray.recycle();
    }

    private void setInputType(AttributeSet set) {
        checkNullSet(set);
        typedArray = getContext().obtainStyledAttributes(set, R.styleable.LimitRangeEditText);
        inputType = typedArray.getInt(R.styleable.LimitRangeEditText_inputType, 0);
        switch (inputType) {
            case 0:
                mainEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                break;

            case 1:
                mainEditText.setInputType(InputType.TYPE_CLASS_PHONE);
                break;

            case 2:
                mainEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                break;

            case 3:
                mainEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                break;

            case 4:
                mainEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                break;
        }
        typedArray.recycle();
    }

    private void setMaxRange(AttributeSet set) {
        checkNullSet(set);
        typedArray = getContext().obtainStyledAttributes(set, R.styleable.LimitRangeEditText);
        maxRangeValue = typedArray.getInt(R.styleable.LimitRangeEditText_maxRangeValue, 20);
        maxValue.setText(String.valueOf(maxRangeValue));
        typedArray.recycle();
    }

    private void setMinRange(AttributeSet set) {
        checkNullSet(set);
        typedArray = getContext().obtainStyledAttributes(set, R.styleable.LimitRangeEditText);
        minRangeValue = typedArray.getInt(R.styleable.LimitRangeEditText_minRangeValue, 0);
        minValue.setText(String.valueOf(minRangeValue));
        typedArray.recycle();
    }


    private void setTextViewLines(AttributeSet set) {
        checkNullSet(set);
        // TODO you should add this <<app:inputType="multi_line">> to your EditText element to perform this function properly
        typedArray = getContext().obtainStyledAttributes(set, R.styleable.LimitRangeEditText);
        textViewLines = typedArray.getInt(R.styleable.LimitRangeEditText_editTextLines, 1);
        mainEditText.setLines(textViewLines);
        mainEditText.setMaxLines(textViewLines);
        typedArray.recycle();
    }

    private void lengthController(AttributeSet set) {
        checkNullSet(set);
        mainEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int lent = mainEditText.length();
                minValue.setText(String.valueOf(lent));
                if (lent >= maxRangeValue) {
                    blockTextView();
                } else {
                    unFadeUi();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void blockTextView() {
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(maxRangeValue);
        mainEditText.setFilters(filterArray);
        fadeUi();
    }

    private void fadeUi() {
        mainEditText.setAlpha(0.5f);
        minValue.setAlpha(0.5f);
        maxValue.setAlpha(0.5f);
        slash.setAlpha(0.5f);
    }

    private void unFadeUi() {
        mainEditText.setAlpha(1f);
        minValue.setAlpha(1f);
        maxValue.setAlpha(1f);
        slash.setAlpha(1f);
    }

    private void checkNullSet(AttributeSet set) {
        if (set == null) {
            return;
        }
    }

    public String getText() {
        return mainEditText.getText().toString();
    }

    public int getLength() {
        return mainEditText.length();
    }

    public void setText(String text){
        mainEditText.setText(text);
    }

}