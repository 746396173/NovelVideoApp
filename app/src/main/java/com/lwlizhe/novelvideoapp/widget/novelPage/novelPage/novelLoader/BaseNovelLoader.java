package com.lwlizhe.novelvideoapp.widget.novelPage.novelPage.novelLoader;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.lwlizhe.novelvideoapp.widget.novelPage.novelPage.NovelPage;
import com.lwlizhe.novelvideoapp.widget.novelPage.novelPage.entity.NovelContentCatalogueEntity;
import com.lwlizhe.novelvideoapp.widget.novelPage.novelPage.manager.NovelContentManager;
import com.lwlizhe.novelvideoapp.widget.novelPage.novelPage.manager.NovelPageBitmapManager;
import com.lwlizhe.novelvideoapp.widget.novelPage.novelPage.pageAnimationBitmapLoader.BaseAnimationBitmapLoader;
import com.lwlizhe.novelvideoapp.widget.novelPage.novelPage.pageAnimationBitmapLoader.PageAnimationBitmapLoaderFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.lwlizhe.basemodule.utils.StringUtils.getTwoSpaces;

/**
 * 这部分负责的是整体页面逻辑，所有的数据都在这里进行交互处理
 * Created by Administrator on 2018/5/15 0015.
 */

public abstract class BaseNovelLoader implements IPageLoader {

    protected Context mContext;

    protected int mPageHeight, mPageWidth;

    protected NovelPage mTargetPageView;

    // 分页相关
//    private String mContent;

    // 目录
    protected NovelContentCatalogueEntity mCatalogueEntity;

    protected BaseAnimationBitmapLoader mPageAnimationBitmapLoader;

    protected NovelPageBitmapManager mBitmapManager;
    protected NovelContentManager mContentManager;

    protected PageAnimationBitmapLoaderFactory mAnimationFactory;

    public BaseNovelLoader(NovelPage targetPageView) {
        this.mTargetPageView = targetPageView;
        mContext = targetPageView.getContext();

        mBitmapManager = NovelPageBitmapManager.instance(mContext);
        mContentManager = NovelContentManager.instance(mContext);

        mAnimationFactory = new PageAnimationBitmapLoaderFactory(targetPageView);

        initPageAnimation();

    }

    private void initPageAnimation() {
        mPageAnimationBitmapLoader = mAnimationFactory.createNovelLoader(0);
    }


    @Override
    public void loadNovel(long bookId,long volumeId,long chapterId,String content) {

        Pattern p_space = Pattern.compile("&nbsp;|<br/>|<br />", Pattern.CASE_INSENSITIVE);
        Matcher m_space = p_space.matcher(content);
        content = m_space.replaceAll("\u3000");
        Pattern p_enter = Pattern.compile("\\r\\n\\r\\n", Pattern.CASE_INSENSITIVE);
        Matcher m_enter = p_enter.matcher(content);
        content = m_enter.replaceAll("\r\n\r\n" + getTwoSpaces());

        mContentManager.setContent(bookId, volumeId, chapterId, content);

        mBitmapManager.drawCurrent();

        mTargetPageView.postInvalidate();
    }


    public void loadNextChapter(String content) {

        Pattern p_space = Pattern.compile("&nbsp;|<br/>|<br />", Pattern.CASE_INSENSITIVE);
        Matcher m_space = p_space.matcher(content);
        content = m_space.replaceAll("\u3000");
        Pattern p_enter = Pattern.compile("\\r\\n\\r\\n", Pattern.CASE_INSENSITIVE);
        Matcher m_enter = p_enter.matcher(content);
        content = m_enter.replaceAll("\r\n\r\n" + getTwoSpaces());

        mContentManager.setNextContent(1, 1, 3, content);
    }

    public void loadPreChapter(String content) {
        Pattern p_space = Pattern.compile("&nbsp;|<br/>|<br />", Pattern.CASE_INSENSITIVE);
        Matcher m_space = p_space.matcher(content);
        content = m_space.replaceAll("\u3000");
        Pattern p_enter = Pattern.compile("\\r\\n\\r\\n", Pattern.CASE_INSENSITIVE);
        Matcher m_enter = p_enter.matcher(content);
        content = m_enter.replaceAll("\r\n\r\n" + getTwoSpaces());

        mContentManager.setPreContent(1, 1, 1, content);
    }

    @Override
    public void loadChapter(NovelContentCatalogueEntity catalogueEntity) {
        mCatalogueEntity = catalogueEntity;
        mContentManager.setCatalogue(catalogueEntity);
    }

    @Override
    public void onPageSizeChanged(int w, int h) {
        mPageWidth = w;
        mPageHeight = h;

        notifyPageSizeChanged(w, h);

    }

    private void notifyPageSizeChanged(int w, int h) {

        mBitmapManager.setPageSize(w, h);

//        if(mContentManager.getCurChapterList().size()!=0){
        mBitmapManager.drawCurrent();
//        }

//        mPageAnimationBitmapLoader.setBitmap(mBitmapManager.getCurrentPageBitmap(), mBitmapManager.getNextPageBitmap());


    }

    @Override
    public void onDraw(Canvas mTargetPageCanvas) {

        mPageAnimationBitmapLoader.onDraw(mTargetPageCanvas);

//        mTargetPageCanvas.drawBitmap(mBitmapManager.getCurrentPageBitmap(), 0, 0, null);

    }

    @Override
    public void onTouch(MotionEvent event) {

        mBitmapManager.onTouch(event);
        mPageAnimationBitmapLoader.onTouch(event);

    }

    @Override
    public void computeScroll() {
        mPageAnimationBitmapLoader.computeScroll();
    }

    @Override
    public void onDetachedFromWindow() {
        mContentManager.onDestroy();
        mBitmapManager.onDestroy();
    }
}