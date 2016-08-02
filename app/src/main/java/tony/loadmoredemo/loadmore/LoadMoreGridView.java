package tony.loadmoredemo.loadmore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by tony on 8/1/16.
 */
public class LoadMoreGridView extends LoadMoreLayout{
    public LoadMoreGridView(Context context) {
        super(context);
    }

    public LoadMoreGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadMoreGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void addFooterView(View view) {

    }

    @Override
    protected void removeFooterView(View view) {

    }

    @Override
    protected View retrieveScrollView() {
        return null;
    }
}
