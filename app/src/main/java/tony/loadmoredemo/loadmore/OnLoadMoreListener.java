package tony.loadmoredemo.loadmore;

/**
 * 一个加载回调， 在这个回调中开始加载请求
 */
public interface OnLoadMoreListener {
    void onLoadMore(ILoadMoreLayout loadMoreLayout);
}
