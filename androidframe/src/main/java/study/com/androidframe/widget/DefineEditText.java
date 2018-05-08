package study.com.androidframe.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * 自定义EditText，添加去除空格的方法
 */

public class DefineEditText extends EditText {
    public DefineEditText(Context context) {
        super(context);
    }

    public DefineEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DefineEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public String getTextTrim() {
        return super.getText().toString().trim();
    }

    /**
     * 设置文本内容，同时设置其游标位置
     *
     * @param text
     */
    public void setTextAndSelection(String text) {
        setText(text);
        if (!TextUtils.isEmpty(text) && text.length() > 0) {
            setSelection(text.length());
        }
    }

}
