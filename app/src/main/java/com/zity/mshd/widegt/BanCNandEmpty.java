package com.zity.mshd.widegt;

import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;

/**
 * Created by luochao on 2017/8/4.
 */

public class BanCNandEmpty {

    private static class SingletonHolder{
        public static BanCNandEmpty instance = new BanCNandEmpty();
    }

    private BanCNandEmpty(){}

    public static BanCNandEmpty newInstance(){
        return SingletonHolder.instance;
    }


    public void banCNandEmpty(final EditText editText) {
        editText.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(20),
                new InputFilter() {

                    @Override
                    public CharSequence filter(CharSequence source, int start,
                                               int end, Spanned dest, int dstart, int dend) {

                        if (isCN(source.toString())) {
                            if (dstart >= 6) {
                                editText.setSelection(dstart);
                                return "";
                            } else {
                                if (source.length() + dest.length() > 0) {
                                    return source.subSequence(start, start + 0 - dest.length());
                                } else {
                                    return source;
                                }
                            }
                        } else {
                            if (source.equals(" ") || source.toString().contentEquals("\n")) {
                                return "";
                            } else {
                                return source;
                            }

                        }

                    }

                }
        });

    }

    public boolean isCN(String str) {
        try {
            byte[] bytes = str.getBytes("UTF-8");
            if (bytes.length == str.length()) {
                return false;
            } else {
                return true;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }
}
