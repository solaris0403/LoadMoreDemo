package tony.loadmoredemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tony.loadmoredemo.loadmore.ILoadMoreLayout;
import tony.loadmoredemo.loadmore.LoadMoreFooterView;
import tony.loadmoredemo.loadmore.LoadMoreListView;
import tony.loadmoredemo.loadmore.OnLoadMoreListener;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RecyclerView mRecyclerView;
    private ListView mListView;
    private List<String> mList = new ArrayList<>();
    private DataAdapter mAdapter;
    private Button mBtnClick;
    private TextView mTvItem;
    private LoadMoreListView mLoadMoreListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mListView = (ListView) findViewById(R.id.listView);
        mBtnClick = (Button) findViewById(R.id.btn_click);
        mTvItem = (TextView) findViewById(R.id.textView);
        mLoadMoreListView = (LoadMoreListView) findViewById(R.id.loadMoreListView);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        GridLayoutManager girdLayoutManager = new GridLayoutManager(this, 4);
//        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL);
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        mRecyclerView.setLayoutManager(linearLayoutManager);
//        mRecyclerView.addItemDecoration(new AdvanceDecoration(this, OrientationHelper.VERTICAL));
//        mAdapter = new DataAdapter(this, mList);
//        mAdapter.setOnItemClickListener(onItemClickListener);
        setData(true);
//        mRecyclerView.setAdapter(mAdapter);
//        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                Log.e(TAG, canChildScrollDown(recyclerView)+"");
//            }
//        });

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mList);
        mListView.setAdapter(adapter);
        mLoadMoreListView.setShowLoadingForFirstPage(true);
        mLoadMoreListView.setAutoLoadMore(true);
        mLoadMoreListView.setLoadMoreFooterView(new LoadMoreFooterView(this));
        mLoadMoreListView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(ILoadMoreLayout loadMoreLayout) {
                mLoadMoreListView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setData(false);
                        adapter.notifyDataSetChanged();
                        mLoadMoreListView.onLoadFinish(true);
                    }
                }, 2000);
            }
        });
    }

    private void setData(boolean isClear) {
        if (isClear){
            mList.clear();
        }
        for (int i = 0; i < 10; i++) {
            mList.add(String.valueOf(i));
        }
    }

    private DataAdapter.OnItemClickListener onItemClickListener = new DataAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, String position) {
            mTvItem.setText(String.valueOf(position));
        }
    };

    /**
     * 如果 Content 不是 ViewGroup，返回 true,表示可以下拉</br>
     * 例如：TextView，ImageView
     */
    public static boolean canChildScrollDown(View view) {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (view instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) view;
                return absListView.getChildCount() > 0 && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0).getTop() < absListView.getPaddingTop());
            } else {
                return view.getScrollY() > 0;
            }
        } else {
            return view.canScrollVertically(1);
        }
    }
}
