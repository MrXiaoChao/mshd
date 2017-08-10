package com.zity.mshd.bean;

import java.util.List;

/**
 * Created by luochao on 2017/8/10.
 * 办事指南
 */

public class Guide {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * org_name : 公安局
         * title : 身份证补办指南
         * guideId : 34fa917c-9209-4960-b826-1fa66d8af0f5
         * createdate : 2017-04-27
         */

        private String org_name;
        private String title;
        private String guideId;
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

        public String getGuideId() {
            return guideId;
        }

        public void setGuideId(String guideId) {
            this.guideId = guideId;
        }

        public String getCreatedate() {
            return createdate;
        }

        public void setCreatedate(String createdate) {
            this.createdate = createdate;
        }
    }
}
