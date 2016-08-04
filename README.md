### 加载更多
#### 加载更多的几种布局   
根据Android中可以滚动的布局来添加加载更多功能：

```
ListView->AbsListView
ScrollView->FrameLayout
GridView-> AbsListView
RecyclerView->ViewGroup implements ScrollingView, NestedScrollingChild
```

常用的有这么几种可以滚动的布局，其中可以通过判断是否滚动到最后一个视图来触发一次事件。   
不过这其中ScrollView不适合用来做加载更多的容器，所以不使用。

#### 滚动到最后的判断   
剩下的这几个控件都可以通过setOnScrollListener方法来添加滚动监听：
```
@Override
public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
      Log.e(TAG, view.canScrollVertically(1)+"");
}
```
#### 加载更多的几种状态
