package tony.loadmoredemo.loadmore;

import android.view.View;

/**
 * 加载更多的UI接口, 一般情况下是footer实现这个接口来设计各个阶段的策略
 */
public interface ILoadMoreFooter {
    /**
     * footer
     */
    View getFooterView();

    /**
     * 等待加载，一般情况下是开始加载之前所做动作，可以根据滑动速度确定是否需要预加载
     */
    void onPreLoad(ILoadMoreLayout loadMoreLayout);

    /**
     * 正在加载
     */
    void onLoading(ILoadMoreLayout loadMoreLayout);

    /**
     * 加载完成
     */
    void onLoadFinish(ILoadMoreLayout loadMoreLayout, boolean hasMore);

    /**
     * 加载错误
     */
    void onLoadError(ILoadMoreLayout loadMoreLayout, int errorCode, String errorMessage);
}
