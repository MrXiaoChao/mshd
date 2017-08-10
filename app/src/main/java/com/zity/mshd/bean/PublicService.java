package com.zity.mshd.bean;

import java.util.List;

/**
 * Created by luochao on 2017/7/28.
 * 公共服务
 */

public class PublicService {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * title : 公共服务-医疗4
         * publicId : 699763fe-3ead-478e-a740-916b6781afa2
         * picurl : /upload/file/public/2017/07/28/8eb1d29a8ef94192aae4cba3875f42af.jpg
         * url : /upload/file/public/2017/07/28/8eb1d29a8ef94192aae4cba3875f42af.mov
         */

        private String title;
        private String publicId;
        private String picurl;
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPublicId() {
            return publicId;
        }

        public void setPublicId(String publicId) {
            this.publicId = publicId;
        }

        public String getPicurl() {
            return picurl;
        }

        public void setPicurl(String picurl) {
            this.picurl = picurl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
