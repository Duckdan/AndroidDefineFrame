package study.com.androidframe.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 自定义TextView，重写getText()方法，添加去除空格的方式
 */

public class DefineTextView extends TextView {
    public DefineTextView(Context context) {
        super(context);
    }

    public DefineTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DefineTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public String getTextTrim() {
        return super.getText().toString().trim();
    }
}
