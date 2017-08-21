package com.tzw.noah.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tzw.noah.R;
import com.tzw.noah.logger.Log;
import com.tzw.noah.models.Group;
import com.tzw.noah.net.IMsg;
import com.tzw.noah.net.NetHelper;
import com.tzw.noah.net.Param;
import com.tzw.noah.net.StringDialogCallback;
import com.tzw.noah.ui.MyBaseActivity;
import com.tzw.noah.ui.sns.group.GroupCreateActivity4;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by yzy on 2017/8/21.
 */

public class FindPwdActivity2 extends MyBaseActivity {

    @BindView(R.id.rl_top)
    RelativeLayout rl_top;
    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_pwd)
    EditText et_pwd;
    Context mContext = FindPwdActivity2.this;

    String Tag = "FindPwdActivity2";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_findpwd2);
        ButterKnife.bind(this);

        initdata();
        findview();
        initview();
    }

    private void initdata() {
        Bundle bu = getIntent().getExtras();
    }

    private void findview() {

    }

    private void initview() {
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 100) {
            setResult(100);
            finish();
        }
    }

    public void handle_submit(View view) {
        if (et_username.length() < 6) {
            toast("密码必须多于6位");
            return;
        }
        if (!et_username.getText().toString().equals(et_pwd.getText().toString())) {
            toast("两次密码输入不一致");
            return;
        }
        List<Param> body=new ArrayList<>();
        body.add(new Param("memberMobile",FindPwdActivity.mobile));
        body.add(new Param("memberPasswd",et_pwd.getText().toString()));
        body.add(new Param("vcode",FindPwdActivity.vcode));
        NetHelper.getInstance().memberFindPwd(body,new StringDialogCallback(this) {
            @Override
            public void onFailure(Call call, IOException e) {
                toast(getString(R.string.internet_fault));

            }

            @Override
            public void onResponse(IMsg iMsg) {
                if(iMsg.isSucceed())
                {
                    toast("密码修改成功,请登录");
                    setResult(100);
//                    startActivity2(LoginActivity.class);
                    finish();

                }
                else
                {
                    Log.log(Tag,iMsg.getMsg());
                    toast(iMsg.getMsg());
                }
            }
        });
    }
}