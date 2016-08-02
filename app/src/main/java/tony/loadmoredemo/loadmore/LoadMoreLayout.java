package tony.loadmoredemo.loadmore;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;

/**
 * 加载更多的容器
 */
public abstract class LoadMoreLayout extends LinearLayout implements ILoadMoreLayout {
    //滚动视图
    private View mScrollView;

    //加载视图
    private ILoadMoreFooter mFooter;

    private OnLoadMoreListener mOnLoadMoreListener;


    //正在加载。。。
    private boolean mIsLoading;
    private boolean mLoadError = false;
    private boolean mHasMore = false;
    private boolean mAutoLoadMore = true;

    private boolean mPreLoadMore = false;
    private boolean mShowLoadingForFirstPage = false;


    public LoadMoreLayout(Context context) {
        this(context, null);
    }

    public LoadMoreLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // TODO: 7/30/16 需要进行安全验证
        mScrollView = retrieveScrollView();
        initViews();
    }

    private void initViews() {
        //将footer添加到滚动视图
        if (mFooter != null) {
            addFooterView(mFooter.getFooterView());
        }

        if (mScrollView instanceof AbsListView) {
            ((AbsListView) mScrollView).setOnScrollListener(new AbsListView.OnScrollListener() {
                private boolean mIsEnd = false;

                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    Log.e("mScrollView", scrollState + "");
                    //确定视图已经停止
                    if (scrollState == SCROLL_STATE_IDLE) {
                        //是否已经滚动到底部
                        if (mIsEnd) {
                            onBottom();
                        }
                    }
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    if (firstVisibleItem + visibleItemCount >= totalItemCount - 1) {
                        mIsEnd = true;
                    } else {
                        mIsEnd = false;
                    }
                }
            });
        } else if (mScrollView instanceof RecyclerView) {
            ((RecyclerView) mScrollView).setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (recyclerView.canScrollVertically(1)) {
                        onBottom();
                    }
                }
            });
        } else {
            throw new AssertionError("The view can't scroll");
        }
    }

    /**
     * 执行加载更多
     */
    private void performLoadMore() {
        if (mIsLoading) {
            return;
        }
        if (!mHasMore && !(mShowLoadingForFirstPage)) {
            return;
        }

        mIsLoading = true;

        mFooter.onLoading(this);

        if (mOnLoadMoreListener != null) {
            mOnLoadMoreListener.onLoadMore(this);
        }
    }

    /**
     * 到达底部
     */
    private void onBottom() {
        //如果上次显示为error则直接返回不做处理
        if (mLoadError) {
            return;
        }
        //自动加载
        if (mAutoLoadMore) {
            performLoadMore();
        } else {
            if (mHasMore) {
                mFooter.onPreLoad(this);
            }
        }
    }

    @Override
    public void setShowLoadingForFirstPage(boolean showLoadingForFirstPage) {
        mShowLoadingForFirstPage = showLoadingForFirstPage;
    }

    @Override
    public void setAutoLoadMore(boolean autoLoadMore) {
        mAutoLoadMore = autoLoadMore;
    }

    @Override
    public void setLoadMoreFooterView(ILoadMoreFooter iLoadMoreFooter) {
        if (iLoadMoreFooter == null) {
            iLoadMoreFooter = new LoadMoreFooterView(getContext());
        }
        if (mFooter != null) {
            removeFooterView(mFooter.getFooterView());
        }
        // add current
        mFooter = iLoadMoreFooter;
        mFooter.getFooterView().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                performLoadMore();
            }
        });
        addFooterView(mFooter.getFooterView());
    }

    @Override
    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        mOnLoadMoreListener = onLoadMoreListener;
    }

    @Override
    public void onLoadFinish(boolean hasMore) {
        mIsLoading = false;
        mLoadError = false;
        mHasMore = hasMore;

        if (mFooter != null) {
            mFooter.onLoadFinish(this, hasMore);
        }
    }

    public void setPreLoadMore(boolean preLoadMore) {
        mPreLoadMore = preLoadMore;
    }

    @Override
    public void onLoadMoreError(int errorCode, String errorMessage) {
        mIsLoading = false;
        mLoadError = true;

        if (mFooter != null) {
            mFooter.onLoadError(this, errorCode, errorMessage);
        }
    }

    protected abstract void addFooterView(View view);

    protected abstract void removeFooterView(View view);

    protected abstract View retrieveScrollView();
}
