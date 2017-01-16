package site.krason.focusdaily;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Created by KCrason on 2017/1/16.
 * @email 535089696@qq.com
 */

public class MainActivity extends AppCompatActivity {

    private ImageView mImgHomePage, mImgVideo, mImgWeiTouTiao, mImgMy;
    private TextView mTxtHomePage, mTxtVideo, mTxtWeiTouTiao, mTxtMy;

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

    private int[] picturesNormal = {R.drawable.home_page_normal, R.drawable.video_normal,
            R.drawable.weijiaodian_normal, R.drawable.my_normal};

    private int[] picturesSelected = {R.drawable.home_page_selected, R.drawable.video_selected,
            R.drawable.weijiaodian_selected, R.drawable.my_selected};

    private void selectIndex(int index) {
        reset();
        getImageView(index).setImageResource(picturesSelected[index]);
        getTextView(index).setTextColor(Color.parseColor("#ed4040"));
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
