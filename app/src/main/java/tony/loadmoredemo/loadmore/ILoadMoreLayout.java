package tony.loadmoredemo.loadmore;

/**
 * 加载更多整体布局的抽象接口
 */
public interface ILoadMoreLayout {
    void setShowLoadingForFirstPage(boolean showLoading);
    void setAutoLoadMore(boolean autoLoadMore);
    void setLoadMoreFooterView(ILoadMoreFooter footer);
    void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener);
    void onLoadFinish(boolean hasMore);
    void onLoadMoreError(int errorCode, String errorMessage);
}
