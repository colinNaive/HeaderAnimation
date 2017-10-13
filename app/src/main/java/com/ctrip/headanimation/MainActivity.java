package com.ctrip.headanimation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initHeadAnimation();
    }

    private LinearLayout tagView;
    private TextView title;

    private void initHeadAnimation() {
        final float search_layout_min_margin = CommonUtil.dp2px(this, 4.5f);
        final float search_layout_max_margin = CommonUtil.dp2px(this, 49f);
        final float search_layout_max_width = CommonUtil.getScreenWidth(this) - CommonUtil.dp2px(this, 30f);
        final float search_layout_min_width = CommonUtil.getScreenWidth(this) - CommonUtil.dp2px(this, 56f);
        final float header_title_max_margin = CommonUtil.dp2px(this, 13.5f);
        tagView = (LinearLayout) findViewById(R.id.tagView);
        title = (TextView) findViewById(R.id.header_name_title);
        AnimationScrollView scrollView = (AnimationScrollView) this.findViewById(R.id.scrollview);
        final ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) tagView.getLayoutParams();
        final ViewGroup.MarginLayoutParams textViewParams = (ViewGroup.MarginLayoutParams) title.getLayoutParams();
        scrollView.setOnScrollListener(new AnimationScrollView.OnScrollChangeListener() {
            @Override
            public synchronized void onScrollChanged(int action, final float dy) {

                float newTopMargin = search_layout_max_margin - dy;
                //int newWidth = tagView.getWidth() - (int) (dy * 42 / 54.2);
                float newWidth = search_layout_max_width - dy;

                float headerTitleNewTopMargin = (float) (header_title_max_margin - dy * 0.5);

                newWidth = newWidth < search_layout_min_width ? search_layout_min_width : newWidth;

                if (newTopMargin < search_layout_min_margin) {
                    newTopMargin = search_layout_min_margin;
                }

                if (newWidth < search_layout_min_width) {
                    newWidth = search_layout_min_width;
                }

                float alpha = 255 * headerTitleNewTopMargin / header_title_max_margin;
                if (alpha < 0) {
                    alpha = 0;
                }
                System.out.println("alpha=" + alpha);
                title.setTextColor(title.getTextColors().withAlpha((int) alpha));
                System.out.println("tv_margin_top=" + headerTitleNewTopMargin);
                textViewParams.topMargin = (int) headerTitleNewTopMargin;
                System.out.println("tv_margin_top2=" + textViewParams.topMargin);
                title.setLayoutParams(textViewParams);
                params.topMargin = (int) newTopMargin;
                params.width = (int) newWidth;
                tagView.setLayoutParams(params);
            }

        });
    }
}
