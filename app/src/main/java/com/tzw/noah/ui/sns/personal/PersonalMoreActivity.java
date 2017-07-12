package com.tzw.noah.ui.sns.personal;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tzw.noah.R;
import com.tzw.noah.models.User;
import com.tzw.noah.net.IMsg;
import com.tzw.noah.net.StringDialogCallback;
import com.tzw.noah.sdk.SnsManager;
import com.tzw.noah.ui.MyBaseActivity;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by yzy on 2017/7/3.
 */

public class PersonalMoreActivity extends MyBaseActivity {

    @BindView(R.id.rl_top)
    RelativeLayout rl_top;
    @BindView(R.id.iv_star)
    ImageView iv_star;
    @BindView(R.id.iv_seeme)
    ImageView iv_seeme;
    @BindView(R.id.iv_seehim)
    ImageView iv_seehim;
    @BindView(R.id.iv_blacklist)
    ImageView iv_blacklist;
    @BindView(R.id.tv_btn1)
    TextView tv_btn1;
    @BindView(R.id.tv_btn2)
    TextView tv_btn2;

    Context mContext = PersonalMoreActivity.this;
    User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sns_layout_personal_more);
        ButterKnife.bind(this);

        initdata();
        findview();
        initview();
    }

    private void initdata() {
        Bundle bu = getIntent().getExtras();
        if (bu != null) {
            user = (User) bu.getSerializable("DATA");
        }
    }

    private void findview() {

    }

    private void initview() {
        setBackground(iv_star, user.ifStar);
        setBackground(iv_seeme, user.ifSeeMe);
        setBackground(iv_seehim, user.ifSeeHim);
        setBackground(iv_blacklist, user.isBlacklist);

        if (user.relative == User.RelativeType.Stranger) {
            tv_btn1.setVisibility(View.GONE);
            tv_btn2.setVisibility(View.GONE);
        } else if (user.relative == User.RelativeType.Fowllow) {
            tv_btn2.setVisibility(View.GONE);

        } else if (user.relative == User.RelativeType.Fans) {
            tv_btn1.setVisibility(View.GONE);
            tv_btn2.setTextColor(getResources().getColor(R.color.white));
            tv_btn2.setBackgroundResource(R.drawable.bg_red_fill_round);
        }
    }

    private void setBackground(ImageView iv, int s) {
        boolean isChecked = false;

        if (s == 0)
            isChecked = true;
        setBackground(iv, isChecked);
    }

    private void setBackground(ImageView iv, boolean isChecked) {

        if (isChecked) {
            iv.setImageResource(R.drawable.btn1_selected);
        } else {
            iv.setImageResource(R.drawable.btn1_unselect);
        }
    }


    public void handle_star(View view) {
        final int status = user.ifStar;
        user.ifStar = status == 0 ? 1 : 0;
        new SnsManager(mContext).snsInfo(user, new StringDialogCallback(mContext) {
            @Override
            public void onFailure(Call call, IOException e) {
                toast(getResources().getString(R.string.internet_fault));
                user.ifStar = status;
            }

            @Override
            public void onResponse(IMsg iMsg) {
                if (iMsg.isSucceed()) {
                    setBackground(iv_star, user.ifStar);
                } else {
                    user.ifStar = status;
                    toast(iMsg.getMsg());
                }
            }
        });
    }

    public void handle_seeme(View view) {
        final int status = user.ifSeeMe;
        user.ifSeeMe = status == 0 ? 1 : 0;
        new SnsManager(mContext).snsInfo(user, new StringDialogCallback(mContext) {
            @Override
            public void onFailure(Call call, IOException e) {
                toast(getResources().getString(R.string.internet_fault));
                user.ifSeeMe = status;
            }

            @Override
            public void onResponse(IMsg iMsg) {
                if (iMsg.isSucceed()) {
                    setBackground(iv_seeme, user.ifSeeMe);
                } else {
                    user.ifSeeMe = status;
                    toast(iMsg.getMsg());
                }
            }
        });
    }

    public void handle_seehim(View view) {
        final int status = user.ifSeeHim;
        user.ifSeeHim = status == 0 ? 1 : 0;
        new SnsManager(mContext).snsInfo(user, new StringDialogCallback(mContext) {
            @Override
            public void onFailure(Call call, IOException e) {
                toast(getResources().getString(R.string.internet_fault));
                user.ifSeeHim = status;
            }

            @Override
            public void onResponse(IMsg iMsg) {
                if (iMsg.isSucceed()) {
                    setBackground(iv_seehim, user.ifSeeHim);
                } else {
                    user.ifSeeHim = status;
                    toast(iMsg.getMsg());
                }
            }
        });
    }

    public void handle_black(View view) {
        if(user.isBlacklist) {
            new SnsManager(mContext).snsRemoveBlacklist(user.memberNo, new StringDialogCallback(mContext) {
                @Override
                public void onFailure(Call call, IOException e) {
                    toast(getResources().getString(R.string.internet_fault));
                }

                @Override
                public void onResponse(IMsg iMsg) {
                    if (iMsg.isSucceed()) {
                        user.isBlacklist=!user.isBlacklist;
                        setBackground(iv_blacklist, user.isBlacklist);
                    } else {
                        toast(iMsg.getMsg());
                    }
                }
            });
        }
        else
        {
            new SnsManager(mContext).snsBlacklist(user.memberNo, new StringDialogCallback(mContext) {
                @Override
                public void onFailure(Call call, IOException e) {
                    toast(getResources().getString(R.string.internet_fault));
                }

                @Override
                public void onResponse(IMsg iMsg) {
                    if (iMsg.isSucceed()) {
                        user.isBlacklist=!user.isBlacklist;
                        setBackground(iv_blacklist, user.isBlacklist);
                    } else {
                        toast(iMsg.getMsg());
                    }
                }
            });
        }
    }

    public void handle_unfollow(View view) {
        new SnsManager(mContext).snsUnfollow(user.memberNo, new StringDialogCallback(mContext) {
            @Override
            public void onFailure(Call call, IOException e) {
                toast(getResources().getString(R.string.internet_fault));
            }

            @Override
            public void onResponse(IMsg iMsg) {
                if (iMsg.isSucceed()) {
                    tv_btn1.setVisibility(View.GONE);
                    tv_btn2.setTextColor(getResources().getColor(R.color.white));
                    tv_btn2.setBackgroundResource(R.drawable.bg_red_fill_round);
                } else {
                    toast(iMsg.getMsg());
                }
            }
        });
    }

    public void handle_unfans(View view) {
        new SnsManager(mContext).snsRemoveFans(user.memberNo, new StringDialogCallback(mContext) {
            @Override
            public void onFailure(Call call, IOException e) {
                toast(getResources().getString(R.string.internet_fault));
            }

            @Override
            public void onResponse(IMsg iMsg) {
                if (iMsg.isSucceed()) {
                    tv_btn2.setVisibility(View.GONE);
                } else {
                    toast(iMsg.getMsg());
                }
            }
        });
    }

    public void handle_edit_remark(View view) {
        Bundle bu = new Bundle();
        bu.putSerializable("DATA", user);
        startActivity(PersonalEditRemarkNameActivity.class, bu);
    }
}
