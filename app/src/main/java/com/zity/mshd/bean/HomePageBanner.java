package com.zity.mshd.bean;

import java.util.List;

/**
 * Created by luochao on 2017/7/21.
 * 首页轮播图
 */

public class HomePageBanner {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * imgId : d86e6648-6153-4f2e-9de2-88043258a1e3
         * url : /upload/file/home/2017/06/01/abaa3fe5eed043128809d5b5544c855f.jpg
         */

        private String imgId;
        private String url;

        public String getImgId() {
            return imgId;
        }

        public void setImgId(String imgId) {
            this.imgId = imgId;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
