package com.tzw.noah.ui.sns.add;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tzw.noah.R;
import com.tzw.noah.logger.Log;
import com.tzw.noah.models.SnsPerson;
import com.tzw.noah.ui.sns.friendlist.FriendAdapter;
import com.tzw.noah.ui.sns.friendlist.MyCompare;
import com.tzw.noah.utils.Utils;
import com.tzw.noah.widgets.WordNaviView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.tzw.noah.R.id.container;
import static com.tzw.noah.R.id.list_view;

/**
 * Created by yzy on 2017/6/29.
 */
public class AddFriendFragment extends Fragment {
    @BindView(container)
    ViewGroup rootViewGroup;

    @BindView(R.id.wordnavi)
    WordNaviView wordnavi;

    @BindView(R.id.list_view)
    ListView list_view;

    Context mContext;
    List<SnsPerson> items = new ArrayList<>();


    AddActivity mActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        mActivity = (AddActivity) getActivity();
        View view = inflater.inflate(R.layout.sns_friendlist_friend, container, false);
        ButterKnife.bind(this, view);
        wordnavi.setOnWordsChangeListener(new WordNaviView.onWordsChangeListener() {
            @Override
            public void wordsChange(String words) {
                updateListView(words);
            }
        });
        List<String> namelist = new ArrayList<>();
        namelist.add("你111");
        namelist.add("你好在");
        namelist.add("耐111");
        namelist.add("废柴");
        namelist.add("风");
        namelist.add("银");
        namelist.add("辛巴");
        namelist.add("2342辛巴");
        namelist.add("啦啦");
        namelist.add("❤啦啦");
        namelist.add("OMG呵呵");
        namelist.add("ddd呵呵");

        items = new ArrayList<>();
        for (String name : namelist) {
            SnsPerson p = new SnsPerson();
            p.name = name;
            p.namePingyin = Utils.getLetter(name);
            p.shortCut = "好友推荐";
            items.add(p);
        }

        Collections.sort(items, new MyCompare());

        AddAdapter adapter = new AddAdapter(mContext, items);

        list_view.setAdapter(adapter);

        View headSearchView = inflater.inflate(R.layout.sns_search_head, container, false);
        TextView tv = (TextView) headSearchView.findViewById(R.id.tv);
        tv.setText("手机号/微信号");
        list_view.addHeaderView(headSearchView);

        list_view.addHeaderView(getHeadView(inflater, container, R.drawable.sns_create_group, "添加附近的朋友"));
        list_view.addHeaderView(getHeadView(inflater, container, R.drawable.sns_book, "手机通讯录"));
        list_view.addHeaderView(getHeadView(inflater, container, R.drawable.sns_scan, "扫一扫"));

//        View headSpanView = inflater.inflate(R.layout.sns_span, container, false);
//        TextView tag = (TextView) headSpanView.findViewById(R.id.tag);
//        tag.setText("好友推荐");
//        list_view.addHeaderView(headSpanView);

        wordnavi.setVisibility(View.GONE);

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.log("itemclick", "view = " + view + ",position = " + position + ",id = " + id);
                //search
                if (position == 0) {
                }
                //附近的人
                else if (position == 1) {

                }
                //通讯录
                else if (position == 2) {
                    mActivity.startActivity(BookActivity.class);
                }
                //扫一扫
                else if (position == 3) {

                } else if (position >= 4) {

                }
            }
        });

        return view;
    }

    private View getHeadView(LayoutInflater inflater, ViewGroup container, int drawableId, String title) {
        View headView = inflater.inflate(R.layout.sns_next_operation_item, container, false);
        ImageView iv = (ImageView) headView.findViewById(R.id.iv_head);
        TextView tv = (TextView) headView.findViewById(R.id.tv_name);
        iv.setImageResource(drawableId);
        tv.setText(title);
        return headView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void updateListView(String words) {
        if (words == "") {
            list_view.setSelection(0);
            return;
        }
        for (int i = 0; i < items.size(); i++) {
            String ping = items.get(i).shortCut;
            //将手指按下的字母与列表中相同字母开头的项找出来
            if (words.equals(ping)) {
                //将列表选中哪一个
                list_view.setSelection(i + list_view.getHeaderViewsCount());
                //找到开头的一个即可
                return;
            }
        }
    }
}
