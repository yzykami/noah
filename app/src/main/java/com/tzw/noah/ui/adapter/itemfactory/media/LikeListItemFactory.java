package com.tzw.noah.ui.adapter.itemfactory.media;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tzw.noah.R;
import com.tzw.noah.models.GroupMember;
import com.tzw.noah.models.MediaLike;

import butterknife.BindView;
import me.xiaopan.assemblyadapter.AssemblyRecyclerItemFactory;
import me.xiaopan.sketch.util.SketchUtils;
import me.xiaopan.sketchsample.adapter.BindAssemblyRecyclerItem;
import me.xiaopan.sketchsample.widget.SampleImageViewHead;

public class LikeListItemFactory extends AssemblyRecyclerItemFactory<LikeListItemFactory.Item> {

    private OnImageClickListener onImageClickListener;
    private int itemSize;

    public LikeListItemFactory(OnImageClickListener onImageClickListener) {
        this.onImageClickListener = onImageClickListener;
    }

    @Override
    public boolean isTarget(Object o) {
        return o instanceof MediaLike;
    }

    @Override
    public Item createAssemblyItem(ViewGroup viewGroup) {
        if (itemSize == 0) {
            itemSize = -1;
            if (viewGroup instanceof RecyclerView) {
                int spanCount = 1;
                RecyclerView recyclerView = (RecyclerView) viewGroup;
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof GridLayoutManager) {
                    spanCount = ((GridLayoutManager) recyclerView.getLayoutManager()).getSpanCount();
                } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                    spanCount = ((StaggeredGridLayoutManager) recyclerView.getLayoutManager()).getSpanCount();
                }
                if (spanCount > 1) {
                    int screenWidth = viewGroup.getResources().getDisplayMetrics().widthPixels;
                    itemSize = (screenWidth - (SketchUtils.dp2px(viewGroup.getContext(), 0) * (spanCount + 1))) / spanCount;
                }
            }
        }
        return new Item(R.layout.sns_group_member_item, viewGroup);
    }

    public interface OnImageClickListener {
        void onClickImage(int position, MediaLike optionsKey);
    }

    public class Item extends BindAssemblyRecyclerItem<MediaLike> {
        @BindView(R.id.iv_head)
        SampleImageViewHead imageView;
        @BindView(R.id.ll_user)
        RelativeLayout ll_user;
        @BindView(R.id.tv_name)
        TextView tv_name;
//        @BindView(R.id.view)
//        MyGroupCoverView view;

        public Item(int itemLayoutId, ViewGroup parent) {
            super(itemLayoutId, parent);
        }

        @Override
        protected void onConfigViews(Context context) {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onImageClickListener != null) {
                        onImageClickListener.onClickImage(getAdapterPosition(), getData());
                    }
                }
            });

            if (itemSize > 0) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
                int nn = SketchUtils.dp2px(context, 10);
                layoutParams.width = itemSize - nn * 2;
                layoutParams.height = itemSize - nn * 2;
                layoutParams.setMargins(nn, nn * 2, nn, nn);
                imageView.setLayoutParams(layoutParams);
            }
        }

        @Override
        protected void onSetData(int i, MediaLike mediaLike) {
//            imageView.setNum(i);
            imageView.displayImage(mediaLike.memberHeadPic);
            tv_name.setText(mediaLike.memberNickName);
//            view.setMask(imageView.getDrawable());

        }
    }
}
