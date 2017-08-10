package com.zity.mshd.bean;

import java.util.List;

/**
 * Created by luochao on 2017/8/10.
 * 通知公告
 */

public class Notice {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * org_name : 乐亭县水利局
         * title : 水利局官网通知公告
         * annId : 146b26c2-3e62-4bb9-8e1c-60220be40961
         * createdate : 2017-06-05
         */

        private String org_name;
        private String title;
        private String annId;
        private String createdate;

        public String getOrg_name() {
            return org_name;
        }

        public void setOrg_name(String org_name) {
            this.org_name = org_name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAnnId() {
            return annId;
        }

        public void setAnnId(String annId) {
            this.annId = annId;
        }

        public String getCreatedate() {
            return createdate;
        }

        public void setCreatedate(String createdate) {
            this.createdate = createdate;
        }
    }
}
