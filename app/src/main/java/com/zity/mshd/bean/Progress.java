package com.zity.mshd.bean;

import java.util.List;

/**
 * Created by luochao on 2017/7/21.
 * 进度
 */

public class Progress {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * title : 123
         * appealId : 5071603a-ea30-455b-8b56-c84dc26c9618
         * state : 5
         * createdate : 2017-04-14 16:16:34
         */

        private String title;
        private String appealId;
        private int state;
        private String createdate;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAppealId() {
            return appealId;
        }

        public void setAppealId(String appealId) {
            this.appealId = appealId;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getCreatedate() {
            return createdate;
        }

        public void setCreatedate(String createdate) {
            this.createdate = createdate;
        }
    }
}
