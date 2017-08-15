package com.tzw.noah.ui.sns.friendlist;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tzw.noah.R;
import com.tzw.noah.ui.MyBaseActivity;
import com.tzw.noah.ui.circle.FragmentViewPagerAdapter;
import com.tzw.noah.ui.sns.add.AddActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import me.xiaopan.sketchsample.adapter.itemfactory.LoadMoreItemFactory;

/**
 * Created by yzy on 2017/6/26.
 */

public class FriendListActivity extends MyBaseActivity implements ViewPager.OnPageChangeListener {


    //    @BindView(R.id.framelayout)
//    FrameLayout frameLayout;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.iv_detail)
    ImageView iv_add;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    Context mContext = FriendListActivity.this;
//    private AssemblyRecyclerAdapter adapter;

    int selectPage;
    List<Fragment> fragmentList = null;
    private boolean firstLoad = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sns_layout_friendlist);
        ButterKnife.bind(this);

        initdata();
        findview();
        initview();
        doWorking();
    }

    private void initdata() {
        selectPage = 0;
        fragmentList = new ArrayList<>();
        Bundle bu = getIntent().getExtras();
        if (bu != null) {
            selectPage = bu.getInt("DATA");
        }
    }

    private void findview() {

    }

    private void initview() {
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bu = new Bundle();
                if (selectPage == 3)
                    bu.putInt("DATA", 1);
                else
                    bu.putInt("DATA", 0);
                startActivity(AddActivity.class, bu);
            }
        });
        fragmentList.add(new FriendFragment());
        fragmentList.add(new FollowFragment());
        fragmentList.add(new FansFragment());
        fragmentList.add(new GroupFragment());
        FragmentViewPagerAdapter adapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(this);
        viewPager.setOffscreenPageLimit(4);

        showFragment(selectPage);
        setTag(selectPage);
    }

    private void showFragment(int selected) {
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction transaction = fm.beginTransaction();
//
//        if (fragmentList[selected] == null) {
//            if (selected == 0)
//                fragmentList[selected] = new FriendFragment();
//            if (selected == 1)
//                fragmentList[selected] = new FollowFragment();
//            if (selected == 2)
//                fragmentList[selected] = new FansFragment();
//            if (selected == 3)
//                fragmentList[selected] = new GroupFragment();
//
//        }
//
//        if (selected == 0)
//            tv_title.setText("好友");
//        if (selected == 1)
//            tv_title.setText("关注");
//        if (selected == 2)
//            tv_title.setText("粉丝");
//        if (selected == 3)
//            tv_title.setText("群组");
//
////        fragment.setArguments(getArguments(selected));
//        transaction.replace(R.id.framelayout, fragmentList[selected]);
//        transaction.commit();
        viewPager.setCurrentItem(selected, true);
    }

    protected void setupViews(final PtrClassicFrameLayout ptrFrame) {

    }

    LoadMoreItemFactory loadMoreItem;


    private void doWorking() {
    }


    public void handle_select(View v) {
        int clickindex = 0;
        for (int i = 0; i < ll.getChildCount(); i++) {
            TextView child = (TextView) ll.getChildAt(i);
            if (child.equals(v)) {
                clickindex = i;
            }
        }
        if (clickindex == selectPage) {
            return;
        } else {
            selectPage = clickindex;
            showFragment(selectPage);
            setTag(selectPage);
        }
    }


    public void setTag(int index) {
        for (int i = 0; i < ll.getChildCount(); i++) {
            TextView child = (TextView) ll.getChildAt(i);
            if (i == index) {
                child.setTextColor(getResources().getColor(R.color.white));
                child.setBackgroundColor(getResources().getColor(R.color.transParent));
            } else {
                if (i == 0) {
                    child.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_red_border_left_round));
                } else if (i == ll.getChildCount() - 1) {
                    child.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_red_boder_right_round));
                } else {
                    child.setBackgroundColor(getResources().getColor(R.color.white));
                }
                child.setTextColor(getResources().getColor(R.color.myRed));
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        fragmentList[selectPage].onResume();
    }

    public boolean firstLoad() {
        if (firstLoad) {
            firstLoad = false;
            return true;
        }
        return false;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        selectPage = position;
        setTag(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
