package tony.loadmoredemo.loadmore;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import tony.loadmoredemo.R;


/**
 * 一个默认的加载更多视图
 */
public class LoadMoreFooterView extends RelativeLayout implements ILoadMoreFooter {
    private static final String TAG = "LoadMoreFooterView";
    private TextView mTextView;

    public LoadMoreFooterView(Context context) {
        this(context, null);
    }

    public LoadMoreFooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupViews();
    }

    private void setupViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_load_more_footer, this);
        mTextView = (TextView) findViewById(R.id.tv_load_more_footer);
    }

    @Override
    public View getFooterView() {
        return this;
    }

    @Override
    public void onPreLoad(ILoadMoreLayout loadMoreLayout) {
        setVisibility(VISIBLE);
        mTextView.setText("onPreLoad");
        Log.i(TAG, "onPreLoad");
    }

    @Override
    public void onLoading(ILoadMoreLayout loadMoreLayout) {
        setVisibility(VISIBLE);
        mTextView.setText("onLoading");
        Log.i(TAG, "onLoading");
    }

    @Override
    public void onLoadFinish(ILoadMoreLayout loadMoreLayout, boolean hasMore) {
        setVisibility(VISIBLE);
        if (hasMore) {
            mTextView.setText("还有更多");
        } else {
            mTextView.setText("没有了");
        }
        Log.i(TAG, "onLoadFinish");
    }

    @Override
    public void onLoadError(ILoadMoreLayout loadMoreLayout, int errorCode, String errorMessage) {
        mTextView.setText("onLoadError");
        Log.i(TAG, "onLoadError");
    }
}
