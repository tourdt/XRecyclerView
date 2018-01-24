package com.jcodecraeer.xrecyclerview;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.progressindicator.AVLoadingIndicatorView;

public class LoadingMoreFooter extends LinearLayout {

    private SimpleViewSwitcher progressCon;
    public final static int STATE_LOADING = 0;
    public final static int STATE_COMPLETE = 1;
    public final static int STATE_NOMORE = 2;

    private LinearLayout mContainer;

    private TextView mText;
    private String loadingHint;
    private String noMoreHint;
    private String loadingDoneHint;

    protected AVLoadingIndicatorView progressView;

    public LoadingMoreFooter(Context context) {
        super(context);
        initView();
    }

    /**
     * @param context
     * @param attrs
     */
    public LoadingMoreFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public void destroy() {
        progressCon = null;
        if (progressView != null) {
            progressView.destroy();
            progressView = null;
        }
    }

    public void setLoadingHint(String hint) {
        loadingHint = hint;
    }

    public void setNoMoreHint(String hint) {
        noMoreHint = hint;
    }

    public void setLoadingDoneHint(String hint) {
        loadingDoneHint = hint;
    }

    public void initView() {
        mContainer = (LinearLayout) LayoutInflater.from(getContext()).inflate(
                getLayoutID(), null);

        addView(mContainer, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 0);
        this.setLayoutParams(lp);
        this.setPadding(0, 0, 0, 0);

        progressCon = (SimpleViewSwitcher)findViewById(R.id.listview_footer_progressbar);
        progressView = new AVLoadingIndicatorView(this.getContext());
        progressView.setIndicatorColor(0xffB5B5B5);
        progressView.setIndicatorId(ProgressStyle.BallSpinFadeLoader);
        if(progressCon != null) {
            progressCon.setView(progressView);
        }
        mText = (TextView)findViewById(R.id.listview_foot_more);


        setGravity(Gravity.CENTER);
//        setLayoutParams(new RecyclerView.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        progressCon = new SimpleViewSwitcher(getContext());
//        progressCon.setLayoutParams(new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//
//        progressView = new AVLoadingIndicatorView(this.getContext());
//        progressView.setIndicatorColor(0xffB5B5B5);
//        progressView.setIndicatorId(ProgressStyle.BallSpinFadeLoader);
//        progressCon.setView(progressView);

//        addView(progressCon);
//        mText = new TextView(getContext());
//        mText.setText(getContext().getString(R.string.listview_loading));
//
//        if (loadingHint == null || loadingHint.equals("")) {
//            loadingHint = (String) getContext().getText(R.string.listview_loading);
//        }
//        if (noMoreHint == null || noMoreHint.equals("")) {
//            noMoreHint = (String) getContext().getText(R.string.nomore_loading);
//        }
//        if (loadingDoneHint == null || loadingDoneHint.equals("")) {
//            loadingDoneHint = (String) getContext().getText(R.string.loading_done);
//        }
//
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        layoutParams.setMargins((int) getResources().getDimension(R.dimen.textandiconmargin), 0, 0, 0);
//
//        mText.setLayoutParams(layoutParams);
//        addView(mText);

        mText.setText(getContext().getString(R.string.listview_loading));
        if (loadingHint == null || loadingHint.equals("")) {
            loadingHint = (String) getContext().getText(R.string.listview_loading);
        }
        if (noMoreHint == null || noMoreHint.equals("")) {
            noMoreHint = (String) getContext().getText(R.string.nomore_loading);
        }
        if (loadingDoneHint == null || loadingDoneHint.equals("")) {
            loadingDoneHint = (String) getContext().getText(R.string.loading_done);
        }
    }

    protected int getLayoutID(){
        return R.layout.listview_footer;
    }

    public void setProgressStyle(int style){
        setProgressStyle(style, -1);
    }
    public void setProgressStyle(int style, @DrawableRes int drawableId) {
        if (style == ProgressStyle.CustomAnim) {

            ImageView imageView = new ImageView(getContext());
            if (drawableId <= 0){
                drawableId = R.drawable.progressbar;
            }
            imageView.setImageResource(drawableId);
            progressCon.setView(imageView);
        } else if (style == ProgressStyle.SysProgress) {
            progressCon.setView(new ProgressBar(getContext(), null, android.R.attr.progressBarStyle));
        } else {
            progressView = new AVLoadingIndicatorView(this.getContext());
            progressView.setIndicatorColor(0xffB5B5B5);
            progressView.setIndicatorId(style);
            progressCon.setView(progressView);
        }
    }

    public void setState(int state) {
        switch (state) {
            case STATE_LOADING:
                progressCon.setVisibility(View.VISIBLE);
                progressCon.playAnim(true);
                mText.setText(loadingHint);
                this.setVisibility(View.VISIBLE);
                break;
            case STATE_COMPLETE:
                progressCon.playAnim(false);
                mText.setText(loadingDoneHint);
                this.setVisibility(View.GONE);
                break;
            case STATE_NOMORE:
                mText.setText(noMoreHint);
                progressCon.playAnim(false);
                progressCon.setVisibility(View.GONE);
                this.setVisibility(View.VISIBLE);
                break;
        }
    }
}
