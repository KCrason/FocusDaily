package site.krason.focusdaily.activities;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import site.krason.focusdaily.R;
import site.krason.focusdaily.adapters.ProjectAdapter;
import site.krason.focusdaily.adapters.ProjectFooterAblumAdapter;
import site.krason.focusdaily.bean.ProjectBean;
import site.krason.focusdaily.fragments.RecommendedFragment;
import site.krason.focusdaily.utils.GlideImageLoader;

/**
 * @author Created by KCrason on 2016/12/26.
 * @email 535089696@qq.com
 */

public class ProjectActivity extends BaseActivity {

    private ListView mListView;

    @Override
    protected boolean isExistToolbar() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_project;
    }

    private void getData(String url) {
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                JSONObject jsonObject = JSON.parseObject(response);
                if (jsonObject.containsKey("body")) {
                    JSONObject jsonObjectBody = jsonObject.getJSONObject("body");
                    if (jsonObjectBody.containsKey("content")) {
                        List<ProjectBean> projectBeen = JSON.parseArray(jsonObjectBody.getString("subjects"), ProjectBean.class);
                        setHeaderMultiTitle(projectBeen);
                        setHeaderText(projectBeen);
                        setHeaderSlides(projectBeen);
                        setFooterAblum(projectBeen);

                        ProjectAdapter projectAdapter = new ProjectAdapter(ProjectActivity.this);

                        projectAdapter.setData(getPodItemsBeanList(projectBeen), getPodItemsBeanType(projectBeen));
                        mListView.setAdapter(projectAdapter);
                    }
                }
            }
        });
    }

    private List<String> getPodItemsBeanType(List<ProjectBean> projectBeen) {
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < projectBeen.size(); i++) {
            if (projectBeen.get(i).getView().equals("list")) {
                ProjectBean projectBean = projectBeen.get(i);
                stringList.add(projectBean.getTitle() + "$" + projectBean.getPodItems().size());
            }
        }
        return stringList;
    }


    private List<ProjectBean.PodItemsBean> getPodItemsBeanList(List<ProjectBean> projectBeen) {
        List<ProjectBean.PodItemsBean> podItemsBeanList = new ArrayList<>();
        for (int i = 0; i < projectBeen.size(); i++) {
            if (projectBeen.get(i).getView().equals("list")) {
                ProjectBean projectBean = projectBeen.get(i);
                for (int j = 0; j < projectBean.getPodItems().size(); j++) {
                    podItemsBeanList.add(projectBean.getPodItems().get(j));
                }
            }
        }
        return podItemsBeanList;
    }

    private void setHeaderMultiTitle(List<ProjectBean> projectBeen) {
        ProjectBean projectBean = getProjectBean(projectBeen, "multiTitle");
        if (projectBean != null) {
            View headerMultiTitleView = LayoutInflater.from(this).inflate(R.layout.header_list_project_multi_title, null);
            ImageView imageView = (ImageView) headerMultiTitleView.findViewById(R.id.img_pic);
            if (projectBean.getContent() != null) {
                Glide.with(this).load(projectBean.getContent().getBgImage()).into(imageView);
            }
            mListView.addHeaderView(headerMultiTitleView);
        }
    }

    private void setHeaderText(List<ProjectBean> projectBeen) {
        ProjectBean projectBean = getProjectBean(projectBeen, "text");
        if (projectBean != null) {
            View headerTextView = LayoutInflater.from(this).inflate(R.layout.header_list_project_text, null);
            TextView textView = (TextView) headerTextView.findViewById(R.id.txt_text);
            if (projectBean.getContent() != null) {
                textView.setText(projectBean.getContent().getIntro());
            }
            mListView.addHeaderView(headerTextView);
        }
    }

    private void setHeaderSlides(List<ProjectBean> projectBeen) {
        ProjectBean projectBean = getProjectBean(projectBeen, "slider");
        if (projectBean != null) {
            View headerSlideView = LayoutInflater.from(this).inflate(R.layout.header_list_project_slide, null);
            Banner banner = (Banner) headerSlideView.findViewById(R.id.banner);
            if (projectBean.getPodItems() != null) {
                banner.setImages(projectBean.getPodItems())
                        .setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
                        .setDelayTime(4000)
                        .setBannerTitles(getTitles(projectBean.getPodItems()))
                        .setImageLoader(new GlideImageLoader())
                        .start();
            }
            mListView.addHeaderView(headerSlideView);
        }
    }

    private List<String> getTitles(List<ProjectBean.PodItemsBean> podItemsBeen) {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < podItemsBeen.size(); i++) {
            strings.add(podItemsBeen.get(i).getTitle());
        }
        return strings;
    }

    private void setFooterAblum(List<ProjectBean> projectBeen) {
        ProjectBean projectBean = getProjectBean(projectBeen, "album");
        if (projectBean != null) {
            View footerAblumView = LayoutInflater.from(this).inflate(R.layout.footer_list_project_album, null);
            GridView gridView = (GridView) footerAblumView.findViewById(R.id.grid_view);
            ProjectFooterAblumAdapter projectFooterAblumAdapter = new ProjectFooterAblumAdapter(this);
            projectFooterAblumAdapter.setData(projectBean.getPodItems());
            gridView.setAdapter(projectFooterAblumAdapter);
            mListView.addFooterView(footerAblumView);
        }
    }


    private ProjectBean getProjectBean(List<ProjectBean> projectBeen, String type) {
        for (int i = 0; i < projectBeen.size(); i++) {
            if (projectBeen.get(i).getView().equals(type)) {
                ProjectBean projectBean = projectBeen.get(i);
                projectBeen.remove(i);
                return projectBean;
            }
        }
        return null;
    }

    @Override
    public void initViews() {
        String url = getIntent().getStringExtra(RecommendedFragment.KEY_NEWS);
        mListView = (ListView) findViewById(R.id.list_view);
        getData(url);
    }
}
