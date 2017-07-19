package com.tzw.noah.ui.sns.friendlist;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.tzw.noah.R;
import com.tzw.noah.logger.Log;
import com.tzw.noah.models.Group;
import com.tzw.noah.models.User;
import com.tzw.noah.net.IMsg;
import com.tzw.noah.net.StringDialogCallback;
import com.tzw.noah.sdk.SnsManager;
import com.tzw.noah.ui.MyBaseActivity;
import com.tzw.noah.ui.sns.add.AddAdapter;
import com.tzw.noah.ui.sns.add.AddGroupAdapter;
import com.tzw.noah.ui.sns.personal.PersonalActivity;
import com.tzw.noah.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by yzy on 2017/7/18.
 */

public class RecommendGroupListActivity extends MyBaseActivity implements AddGroupAdapter.OnAddClickListener {
    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.list_view)
    ListView list_view;

    List<Group> items = new ArrayList<>();

    AddGroupAdapter adapter;

    Context mContext = RecommendGroupListActivity.this;
    RecommendGroupListActivity instance;
    String Tag = "RecommendGroupListActivity";
//    private AssemblyRecyclerAdapter adapter;

//    int selectPage;
//    Fragment[] fragmentList = null;

    String title = "群组推荐";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sns_layout_list);
        ButterKnife.bind(this);
        instance = this;
        initdata();
        findview();
        initview();

        refreshListView();
    }

    private void initdata() {
//        selectPage = 0;
//        fragmentList = new Fragment[4];
        Bundle bu = getIntent().getExtras();
        if (bu != null) {
            title = bu.getString("title");
        }
    }

    private void findview() {

    }

    private void initview() {
        tv_title.setText(title);

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bu = new Bundle();
                bu.putSerializable("DATA", items.get(position - list_view.getHeaderViewsCount()));
                startActivity(PersonalActivity.class, bu);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void refreshListView() {
        new SnsManager(mContext).snsRecommendGroup(new StringDialogCallback(mContext) {
            @Override
            public void onFailure(Call call, IOException e) {
                toast(getResources().getString(R.string.internet_fault));
            }

            @Override
            public void onResponse(IMsg iMsg) {
                try {
                    if (iMsg.isSucceed()) {
                        if (iMsg.Data != null)
                            items = (List<Group>) iMsg.Data;
                        else
                            items = Group.loadRecommendList(iMsg);
//                        items = Utils.processUser(items);
//                        Collections.sort(items, new MyCompare());
                        if (items == null)
                            items = new ArrayList<Group>();
                        adapter = new AddGroupAdapter(mContext, items);
                        adapter.setOnAddClickListener(instance);
                        list_view.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    } else {
                        toast(iMsg.getMsg());
                    }
                } catch (Exception e) {
                    Log.log(Tag, e);
                }
            }
        });
    }

    @Override
    public void onAddClick(View v, int position) {
        toast("sdda" + position);
    }
}