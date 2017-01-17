package site.krason.focusdaily;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import site.krason.focusdaily.fragments.MyFragment;
import site.krason.focusdaily.fragments.TopicFragment;
import site.krason.focusdaily.fragments.VideoFragment;

/**
 * @author Created by KCrason on 2017/1/16.
 * @email 535089696@qq.com
 */

public class MainActivity extends FragmentActivity {

    private ImageView mImgHomePage, mImgVideo, mImgWeiTouTiao, mImgMy;
    private TextView mTxtHomePage, mTxtVideo, mTxtWeiTouTiao, mTxtMy;

    private HomePageFragment mHomePageFragment;

    private VideoFragment mVideoFragment;

    private TopicFragment mTopicFragment;
    private MyFragment mMyFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImgHomePage = (ImageView) findViewById(R.id.img_home_page);
        mImgVideo = (ImageView) findViewById(R.id.img_video);
        mImgWeiTouTiao = (ImageView) findViewById(R.id.img_weitoutiao);
        mImgMy = (ImageView) findViewById(R.id.img_my);

        mTxtHomePage = (TextView) findViewById(R.id.txt_home_page);
        mTxtVideo = (TextView) findViewById(R.id.txt_video);
        mTxtWeiTouTiao = (TextView) findViewById(R.id.txt_weitoutiao);
        mTxtMy = (TextView) findViewById(R.id.txt_my);
        selectIndex(0);
    }


    public void switchSelected(View view) {
        switch (view.getId()) {
            case R.id.llayout_home_page:
                selectIndex(0);
                break;
            case R.id.llayout_video:
                selectIndex(1);
                break;
            case R.id.llayout_weitoutiao:
                selectIndex(2);
                break;
            case R.id.llayout_my:
                selectIndex(3);
                break;
        }
    }

    private void hideFragments(FragmentTransaction fragmentTransaction) {
        if (mHomePageFragment != null) {
            fragmentTransaction.hide(mHomePageFragment);
        }
        if (mVideoFragment != null) {
            fragmentTransaction.hide(mVideoFragment);
        }
        if (mTopicFragment != null) {
            fragmentTransaction.hide(mTopicFragment);
        }
        if (mMyFragment != null) {
            fragmentTransaction.hide(mMyFragment);
        }
    }

//    private int[] picturesNormal = {R.drawable.home_page_normal, R.drawable.video_normal,
//            R.drawable.weijiaodian_normal, R.drawable.my_normal};

    private int[] picturesSelected = {R.drawable.home_page_selected, R.drawable.video_selected,
            R.drawable.weijiaodian_selected, R.drawable.my_selected};

    private void selectIndex(int index) {
        reset();
        getImageView(index).setImageResource(picturesSelected[index]);
        getTextView(index).setTextColor(Color.parseColor("#ed4040"));

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideFragments(fragmentTransaction);
        showIndexFragment(fragmentTransaction, index);
    }

    private void showIndexFragment(FragmentTransaction fragmentTransaction, int index) {
        switch (index) {
            case 0:
                if (mHomePageFragment == null) {
                    mHomePageFragment = new HomePageFragment();
                    fragmentTransaction.add(R.id.flayout_parent, mHomePageFragment);
                } else {
                    fragmentTransaction.show(mHomePageFragment);
                }
                break;
            case 1:
                if (mVideoFragment == null) {
                    mVideoFragment = new VideoFragment();
                    fragmentTransaction.add(R.id.flayout_parent, mVideoFragment);
                } else {
                    fragmentTransaction.show(mVideoFragment);
                }
                break;
            case 2:
                if (mTopicFragment == null) {
                    mTopicFragment = new TopicFragment();
                    fragmentTransaction.add(R.id.flayout_parent, mTopicFragment);
                } else {
                    fragmentTransaction.show(mTopicFragment);
                }
                break;
            case 3:
                if (mMyFragment == null) {
                    mMyFragment = new MyFragment();
                    fragmentTransaction.add(R.id.flayout_parent, mMyFragment);
                } else {
                    fragmentTransaction.show(mMyFragment);
                }
                break;
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    private ImageView getImageView(int index) {
        switch (index) {
            case 0:
                return mImgHomePage;
            case 1:
                return mImgVideo;
            case 2:
                return mImgWeiTouTiao;
            case 3:
                return mImgMy;
            default:
                return mImgHomePage;
        }
    }

    private TextView getTextView(int index) {
        switch (index) {
            case 0:
                return mTxtHomePage;
            case 1:
                return mTxtVideo;
            case 2:
                return mTxtWeiTouTiao;
            case 3:
                return mTxtMy;
            default:
                return mTxtHomePage;
        }
    }

    private void reset() {
        mImgHomePage.setImageResource(R.drawable.home_page_normal);
        mImgVideo.setImageResource(R.drawable.video_normal);
        mImgWeiTouTiao.setImageResource(R.drawable.weijiaodian_normal);
        mImgMy.setImageResource(R.drawable.my_normal);

        mTxtHomePage.setTextColor(Color.parseColor("#707070"));
        mTxtVideo.setTextColor(Color.parseColor("#707070"));
        mTxtWeiTouTiao.setTextColor(Color.parseColor("#707070"));
        mTxtMy.setTextColor(Color.parseColor("#707070"));
    }
}
