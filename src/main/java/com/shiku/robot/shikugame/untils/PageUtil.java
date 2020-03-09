package com.shiku.robot.shikugame.untils;

import java.util.List;

/**
 * @Author lijun
 * @Description 分页工具类类
 * @Date 2019-12-22 4:48 下午
 **/

public class PageUtil<T> {
    /**
     * 每页数据默认10条
     */
    private int pageSize = 10;
    /**
     * 当前页码，默认为第一页
     */
    private int pageNo = 1;
    /**
     * 首页，默认为第一页
     */
    private int firstPage = 1;
    /**
     * 尾页，默认为第一页
     */
    private int lastPage = 1;
    /**
     * 上一页，默认为第一页
     */
    private int prevPage = 1;
    /**
     * 下一页，默认为第一页
     */
    private int nextPage = 1;
    /**
     * 总条数，默认为0
     */
    private long totalCount = 0;
    /**
     * 总页数，默认为一页
     */
    private int totalPage = 1;
    /**
     * 数据集合
     */
    private List<T> data;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getFirstPage() {
        return firstPage;
    }

    public void setFirstPage() {
        this.firstPage = 1;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage() {
        this.lastPage = this.getTotalPage() <= 0 ? 1 : this.getTotalPage();
    }

    public int getPrevPage() {
        return prevPage;
    }

    public void setPrevPage() {
        this.prevPage = (this.pageNo > 1) ? this.pageNo - 1 : this.pageNo;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage() {
        this.nextPage = (this.pageNo == this.totalPage) ? this.pageNo : this.pageNo + 1;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage() {
        this.totalPage = (int) ((this.totalCount % this.pageSize > 0) ? (this.totalCount / this.pageSize + 1)
                : this.totalCount / this.pageSize);
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    /**
     * 初始化分页信息
     */
    public PageUtil() {
        this.init();
    }

    /**
     * 设置页码和每页条数
     *
     * @param pageNo   页码
     * @param pageSize 每页条数
     */
    public PageUtil(int pageNo, int pageSize) {
        this.setPageNo(pageNo);
        this.setPageSize(pageSize);
        this.init();
    }

    /**
     * 初始化计算分页
     */
    private void init() {
        //总页数
        this.setTotalPage();
        //上一页
        this.setPrevPage();
        //下一页
        this.setNextPage();
        //首页
        this.setFirstPage();
        //尾页
        this.setLastPage();
    }

    /**
     * 当前页的起始位置
     *
     * @return 当前页的起始位置
     */
    public int getFirstResult() {
        return (this.getPageNo() - 1) * this.getPageSize();
    }
}
