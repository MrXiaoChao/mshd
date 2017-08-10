package com.zity.mshd.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by luochao on 2017/8/1.
 * 进度详情
 */

public class ProgressXQ {


    /**
     * createtime : 2017-07-27 10:58:18
     * content : 内容
     * open : 1
     * phone : 15010610103
     * title : 主题公园
     * status : 2
     * appealId : 78a89084-bec1-4d13-b85c-0f0d2077adc8
     * zt : [{"org_name":"","tascreatetime":"2017-07-27 10:58:18","grade":"","tasstatus":"1","tascontent":"您已成功提交诉求，请耐心等待受理。"},{"org_name":"","tascreatetime":"2017-07-27 11:38:56","grade":"","tasstatus":"2","tascontent":"您的诉求已受理，正在处理中。"},{"org_name":"受理中心","tascreatetime":"2017-07-27 11:39:19","grade":"","tasstatus":"3","tascontent":"感谢"},{"org_name":"受理中心","tascreatetime":"2017-07-27 11:39:23","grade":"","tasstatus":"4","tascontent":"感谢"},{"org_name":"","tascreatetime":"2017-07-27 11:40:02","tasstatus":"8","grade":"3","tascontent":"15010610103\t"}]
     * name : 淡淡的忧伤
     * type : 3
     */

    private String createtime;
    private String content;
    private int open;
    private String phone;
    private String title;
    private int status;
    private String appealId;
    private String name;
    private int type;
    private List<ZtBean> zt;

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getOpen() {
        return open;
    }

    public void setOpen(int open) {
        this.open = open;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAppealId() {
        return appealId;
    }

    public void setAppealId(String appealId) {
        this.appealId = appealId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<ZtBean> getZt() {
        return zt;
    }

    public void setZt(List<ZtBean> zt) {
        this.zt = zt;
    }

    public static class ZtBean {
        /**
         * org_name :
         * tascreatetime : 2017-07-27 10:58:18
         * grade :
         * tasstatus : 1
         * tascontent : 您已成功提交诉求，请耐心等待受理。
         */

        private String org_name;
        private String tascreatetime;
        private String grade;
        private String tasstatus;
        private String tascontent;

        public String getOrg_name() {
            return org_name;
        }

        public void setOrg_name(String org_name) {
            this.org_name = org_name;
        }

        public String getTascreatetime() {
            return tascreatetime;
        }

        public void setTascreatetime(String tascreatetime) {
            this.tascreatetime = tascreatetime;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getTasstatus() {
            return tasstatus;
        }

        public void setTasstatus(String tasstatus) {
            this.tasstatus = tasstatus;
        }

        public String getTascontent() {
            return tascontent;
        }

        public void setTascontent(String tascontent) {
            this.tascontent = tascontent;
        }
    }
}
