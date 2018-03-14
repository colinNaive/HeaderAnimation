一、为什么需要做这样的动画？

产品提出这样的需求：希望标题栏能够随着scrollView的滑动而进行缩放。

这个动画到底是什么样？现在把gif图传一下。当然，实际上线的页面会比这个好看很多，这只是demo而已，不过动画功能已经全部实现。



二、这个动画的具体实现思路

1、步骤：

（1）找到scrollView的scrollListener方法

（2）在回调的方法里可以得到滑动距离

（3）根据滑动距离，实时改变标题栏的大小。

2、具体实现：

（1）ScrollView没有提供setOnScrollListener方法，因此需要自己写一个借口回调

public void setOnScrollListener(OnScrollChangeListener listener) {
        this.changeListener = listener;
    }

    public interface OnScrollChangeListener {
        void onScrollChanged(float dy);
    }
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (changeListener != null) {
            changeListener.onScrollChanged(getScrollY() * 0.65f);
        }
    }
此处需要注意，这里不要在onScrollChanged方法去进行接口回调！！！因为这个方法不是均匀回调的，当滑动很快时，动画就非常快，看上去就是直接挪过去的，用户体验非常差！！！这个问题坑了我好久。
（2）回调的方法里可以得到滑动距离

        scrollView.setOnScrollListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public synchronized void onScrollChanged(final float dy) {

            }

        });
这个dy就是滑动距离
（3）根据滑动距离，实时改变标题栏的大小
这部分的思想是：首先拿到初始textView的topMargin，然后用topMargin减去dy，width减去dy就可以了，只不过要对dy进行一下加权。

		float newTopMargin = search_layout_max_margin - dy;
                float newWidth = search_layout_max_width - dy;

                newWidth = newWidth < search_layout_min_width ? search_layout_min_width : newWidth;

                if (newTopMargin < search_layout_min_margin) {
                    newTopMargin = search_layout_min_margin;
                }

                if (newWidth < search_layout_min_width) {
                    newWidth = search_layout_min_width;
                }

                params.topMargin = (int) newTopMargin;
                params.width = (int) newWidth;
                tagView.setLayoutParams(params);

以上就是重要代码了，需要demo可以发邮件联系我zhshan@ctrip.com
~~~~~~~~~~~~~~~~~~~

附上demo（点击即可下载），欢迎批评指正
