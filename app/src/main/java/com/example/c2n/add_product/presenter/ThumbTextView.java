package com.example.c2n.add_product.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by vipul.singhal on 08-06-2018.
 */

@SuppressLint("AppCompatCustomView")
public class ThumbTextView extends TextView {

    private LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    private int width = 0;

    public ThumbTextView(Context context) {
        super(context);
    }

    public ThumbTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void attachToSeekBar(SeekBar seekBar) {
        String content = getText().toString();
        Log.d("seek_content:", content + "");

        if (TextUtils.isEmpty(content) || seekBar == null)
            return;
        float contentWidth = this.getPaint().measureText(content);
        Log.d("seek_contentwidth:", contentWidth + "");
        int realWidth = width - seekBar.getPaddingLeft() - seekBar.getPaddingRight();
        int maxLimit = (int) (width - contentWidth - seekBar.getPaddingRight());
        int minLimit = seekBar.getPaddingLeft();
        float percent = (float) (1.0 * seekBar.getProgress() / seekBar.getMax());
        int left = minLimit + (int) (realWidth * percent - contentWidth / 2.0);
        left = left <= minLimit ? minLimit : left >= maxLimit ? maxLimit : left;

        Log.d("seek_realwidth", realWidth + "");
        Log.d("seek_maxlimit", maxLimit + "");
        Log.d("seek_minlimit", minLimit + "");
        Log.d("seek_percent", percent + "");
        Log.d("seek_left", left + "");

        lp.setMargins(left, 0, 0, 0);
        setLayoutParams(lp);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (width == 0)
            width = MeasureSpec.getSize(widthMeasureSpec);
        Log.d("seek_width", width + "");
    }


}
