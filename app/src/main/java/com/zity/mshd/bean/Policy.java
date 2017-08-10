package com.zity.mshd.bean;

import java.util.List;

/**
 * Created by luochao on 2017/8/10.
 * 政策法规
 */

public class Policy {

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
         * title : 政策法规
         * policyId : b197eda3-01ba-4c5e-9a79-7edb53340edc
         * createdate : 2017-05-09
         */

        private String org_name;
        private String title;
        private String policyId;
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

        public String getPolicyId() {
            return policyId;
        }

        public void setPolicyId(String policyId) {
            this.policyId = policyId;
        }

        public String getCreatedate() {
            return createdate;
        }

        public void setCreatedate(String createdate) {
            this.createdate = createdate;
        }
    }
}
