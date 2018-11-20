package com.sparrow.support.protocol.pager;

/**
 * simpler pager without html
 */
public class SimplePager {

    /**
     * page size
     */
    protected Integer pageSize;
    /**
     * current page index
     */
    protected Integer currentPageIndex;

    public SimplePager(Integer pageSize, Integer currentPageIndex) {
        this.pageSize = pageSize;
        if (currentPageIndex == null) {
            currentPageIndex = 1;
        }
        this.currentPageIndex = currentPageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getCurrentPageIndex() {
        return currentPageIndex;
    }
}
