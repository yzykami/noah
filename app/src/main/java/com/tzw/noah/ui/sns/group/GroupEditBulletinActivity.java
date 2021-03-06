package com.tzw.noah.ui.sns.group;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tzw.noah.R;
import com.tzw.noah.cache.UserCache;
import com.tzw.noah.logger.Log;
import com.tzw.noah.models.Group;
import com.tzw.noah.net.IMsg;
import com.tzw.noah.net.Param;
import com.tzw.noah.net.StringDialogCallback;
import com.tzw.noah.sdk.SnsManager;
import com.tzw.noah.ui.MyBaseActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by yzy on 2017/7/15.
 */

public class GroupEditBulletinActivity extends MyBaseActivity {

    @BindView(R.id.rl_top)
    RelativeLayout rl_top;
    @BindView(R.id.et_sign)
    EditText et_sign;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_count)
    TextView tv_count;
    int maxCount = 150;

    Context mContext = GroupEditBulletinActivity.this;

    String Tag = "GroupEditBulletinActivity";

    Group group;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sns_layout_group_edit_introduce);
        ButterKnife.bind(this);

        initdata();
        findview();
        initview();
    }

    private void initdata() {
        Bundle bu = getIntent().getExtras();
        if (bu != null) {
            group = (Group) bu.getSerializable("DATA");
        } else
            group = new Group();
    }

    private void findview() {

    }

    private void initview() {
        et_sign.setHint("请输入您的群公告");
        tv_count.setText(maxCount + "");
        et_sign.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;
            private boolean isEdit = true;
            private int selectionStart;
            private int selectionEnd;

            @Override
            public void beforeTextChanged(CharSequence s, int arg1, int arg2,
                                          int arg3) {
                temp = s;
            }

            @Override
            public void onTextChanged(CharSequence s, int arg1, int arg2,
                                      int arg3) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                selectionStart = et_sign.getSelectionStart();
                selectionEnd = et_sign.getSelectionEnd();
                if (temp.length() > maxCount) {


                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionStart;
                    et_sign.setText(s);
                    et_sign.setSelection(tempSelection);
                }
                tv_count.setText((maxCount - temp.length()) + "");
            }
        });
    }

    public void handle_save(View view) {
        if (group.groupAttribute == Group.Type.GROUP) {
            List<Param> body = new ArrayList<>();
//            body.add(new Param("groupName", group.groupName));
            body.add(new Param("groupTypeId", group.groupTypeId));
            body.add(new Param("groupBulletin", et_sign.getText().toString()));
            new SnsManager(mContext).snsUpdateGroupInfo(group.groupId, body, new StringDialogCallback(mContext) {
                @Override
                public void onFailure(Call call, IOException e) {
                    toast(getResources().getString(R.string.internet_fault));
                }

                @Override
                public void onResponse(IMsg iMsg) {
                    try {
                        if (iMsg.isSucceed()) {
                            group.groupBulletin = et_sign.getText().toString();
                            Bundle bu = new Bundle();
                            bu.putSerializable("DATA", group);
                            Intent intent = new Intent();
                            intent.putExtras(bu);
                            setResult(100, intent);
                            finish();
                            toast("群公告修改成功");
                        } else {
                            toast(iMsg.getMsg());
                        }
                    } catch (Exception e) {
                        Log.log(Tag, e);
                    }
                }
            });
        } else {
            List<Param> body = new ArrayList<>();
//            body.add(new Param("groupName", group.groupName));
            body.add(new Param("groupBulletin", et_sign.getText().toString()));
            new SnsManager(mContext).snsDiscussInfo(group.groupId, body, new StringDialogCallback(mContext) {
                @Override
                public void onFailure(Call call, IOException e) {
                    toast(getResources().getString(R.string.internet_fault));
                }

                @Override
                public void onResponse(IMsg iMsg) {
                    try {
                        if (iMsg.isSucceed()) {
                            group.groupBulletin = et_sign.getText().toString();
                            Bundle bu = new Bundle();
                            bu.putSerializable("DATA", group);
                            Intent intent = new Intent();
                            intent.putExtras(bu);
                            setResult(100, intent);
                            finish();
                            toast("群公告修改成功");
                        } else {
                            toast(iMsg.getMsg());
                        }
                    } catch (Exception e) {
                        Log.log(Tag, e);
                    }
                }
            });
        }
    }
}
