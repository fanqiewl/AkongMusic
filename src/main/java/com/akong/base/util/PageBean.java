package com.akong.base.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 分页对象
 *
 * @author Akong
 * @create 2021/12/20 21:46
 */
public class PageBean {
    private int total;
    private int page = 1;
    private int row = 5;
    private boolean pagination = true;
    private String url;
    private Map<String, String[]> ms;

    public void setMs(Map<String, String[]> ms) {
        this.ms = ms;
    }

    public int calcStartIndex() {
        return (page - 1) * row;
    }

    public int calcMaxPage() {
        return total % row == 0 ? total / row : total / row + 1;
    }

    public int nextPage() {
        return page + 1 > calcMaxPage() ? calcMaxPage() : page + 1;
    }

    public int prevPage() {
        return page - 1 > 1 ? page - 1 : 1;
    }

    public PageBean(int total, int page, int row, boolean pagination, String url) {
        this.total = total;
        this.page = page;
        this.row = row;
        this.pagination = pagination;
        this.url = url;
    }

    @Override
    public String toString() {
        return "PageBean [total=" + total + ", page=" + page + ", row=" + row + ", pagination=" + pagination + ", url="
                + url + "]";
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public boolean isPagination() {
        return pagination;
    }

    public void setPagination(boolean pagination) {
        this.pagination = pagination;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public PageBean() {
        // TODO Auto-generated constructor stub
    }

    public Map<String, String[]> getMs() {
        return ms;
    }

    public void setRequest(HttpServletRequest req) {
        // 1.保存当前的访问地址 便于下次访问
        setUrl(req.getRequestURL().toString());
        // 2.保存当前请求中携带的参数
        setMs(req.getParameterMap());
        // 3.保存当前的页数
        String page = req.getParameter("page");
        if (page == null) {
            setPage(1);
        } else {
            setPage(Integer.parseInt(page));
        }
        // 4.保存当前的显示条数
        String row = req.getParameter("rows");
        if (row == null) {
            setRow(5);
        } else {
            setRow(Integer.parseInt(row));
        }
        // 5.判断是否需要分页
        String pagination = req.getParameter("pagination");
        if (pagination != null && pagination.equals("false")) {
            setPagination(false);
        }
    }
}
