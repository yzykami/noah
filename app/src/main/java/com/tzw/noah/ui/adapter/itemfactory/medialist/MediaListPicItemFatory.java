package com.tzw.noah.ui.adapter.itemfactory.medialist;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tzw.noah.R;
import com.tzw.noah.models.MediaArticle;
import com.tzw.noah.ui.adapter.MediaListItemAssemblyRecyclerItem;
import com.tzw.noah.utils.Utils;

import butterknife.BindView;
import me.xiaopan.assemblyadapter.AssemblyRecyclerItemFactory;
import me.xiaopan.sketchsample.widget.SampleImageView;

public class MediaListPicItemFatory extends AssemblyRecyclerItemFactory<MediaListPicItemFatory.GalleryItem> {

    private MediaListListener mMediaListListener;
    private int width = 0, height = 0, bjs = 0, btnsize = 0;

    public MediaListPicItemFatory(MediaListListener mMediaListListener) {
        this.mMediaListListener = mMediaListListener;
    }

    @Override
    public boolean isTarget(Object o) {
        if (o instanceof MediaArticle)
            return ((MediaArticle) o).isListPicRL();
        return false;
    }

    @Override
    public GalleryItem createAssemblyItem(ViewGroup viewGroup) {

        int screenWidth = Utils.getScreenWidth();
        btnsize = Utils.dp2px(viewGroup.getContext(),34);
        bjs = (int) viewGroup.getContext().getResources().getDimension(R.dimen.bjs);
        width = (int) (screenWidth - bjs * 3) / 3;
        height = width * 2 / 3;

        return new GalleryItem(R.layout.media_list_article_item_pic, viewGroup);
    }

    public class GalleryItem extends MediaListItemAssemblyRecyclerItem<MediaArticle> {
        @BindView(R.id.container)
        RelativeLayout container;
        @BindView(R.id.iv_cover)
        SampleImageView iv_cover;
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.tv_comment_count)
        TextView tv_comment_count;
        @BindView(R.id.tv_pic_count)
        TextView tvPicCount;
        @BindView(R.id.iv_play_icon)
        ImageView ivPlayIcon;
        @BindView(R.id.tv_tag)
        TextView tvTag;

        Context mContext;

        ImageView ivSelect;

        public GalleryItem(int itemLayoutId, ViewGroup parent) {
            super(itemLayoutId, parent);

        }

        @Override
        protected void onConfigViews(Context context) {
            mContext = context;
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mMediaListListener != null) {
                        mMediaListListener.onItemClick(getAdapterPosition(), getData());
                    }
                }
            });
            iv_cover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mMediaListListener != null) {
                        mMediaListListener.onItemClick(getAdapterPosition(), getData());
                    }
                }
            });
            if (width == 0) {
                int screenWidth = Utils.getScreenWidth();
                width = (int) (screenWidth - mContext.getResources().getDimension(R.dimen.bjs) * 3) / 3;
                height = width * 15 / 23;
                ViewGroup.LayoutParams lp = iv_cover.getLayoutParams();
                lp.width = width;
                lp.height = height;
                iv_cover.setLayoutParams(lp);
            } else {
                ViewGroup.LayoutParams lp = iv_cover.getLayoutParams();
                lp.width = width;
                lp.height = height;
                iv_cover.setLayoutParams(lp);
            }
        }

        @Override
        protected void onSetData(int i, final MediaArticle mediaArticle) {
            String ss[] = mediaArticle.articleImage.split(",");
            if (mediaArticle.articleImage.contains(","))
                ss = mediaArticle.articleImage.split(",");
            else if (mediaArticle.articleImage.contains(";"))
                ss = mediaArticle.articleImage.split(";");

            if (mediaArticle.articleImage.isEmpty()) {
                iv_cover.setVisibility(View.GONE);
            } else {
                iv_cover.setVisibility(View.VISIBLE);
                iv_cover.displayRoundImageSmallThumb(ss[0]);
            }
            tv_title.setText(mediaArticle.articleTitle);
            tv_time.setText(Utils.getStandardDate(mediaArticle.createTime));
            if (mediaArticle.articleCommentSum == -1) {
                tv_comment_count.setVisibility(View.GONE);
            } else {
                tv_comment_count.setVisibility(View.VISIBLE);
            }
            tv_comment_count.setText(mediaArticle.articleCommentSum + "评");

            if (mediaArticle.isArticleTypPicGallery()) {
                tvPicCount.setText(mediaArticle.articleContentImageNum + "图");
                tvPicCount.setVisibility(View.VISIBLE);
                tvTag.setVisibility(View.VISIBLE);
                tvTag.setText("图集");
                tvTag.setBackgroundResource(R.drawable.bg_red_border_round_1px);
                tvTag.setTextColor(mContext.getResources().getColor(R.color.myRed));
            } else
                tvPicCount.setVisibility(View.GONE);
            if (mediaArticle.isArticleTypeVideo()) {
                ivPlayIcon.setVisibility(View.VISIBLE);
                tvTag.setVisibility(View.VISIBLE);
                tvTag.setText("视频");
                tvTag.setBackgroundResource(R.drawable.bg_red_border_round_1px);
                tvTag.setTextColor(mContext.getResources().getColor(R.color.myRed));
            } else
                ivPlayIcon.setVisibility(View.GONE);

            initEditMode(container, mediaArticle, mMediaListListener);
        }
    }
}
