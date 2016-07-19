package com.wf2311.commons.persist.lang;

import com.wf2311.commons.domain.Pager;

import java.util.List;

@Deprecated
public class PagerResult {
	private List<?> results;
	
	private int totalCount;
	
	private Pager pager;
	
	public PagerResult(List<?> results, int totalCount){
		this.results = results;
		this.totalCount = totalCount;
	}
	public PagerResult(List<?> results, int totalCount, Pager pager){
		this.results = results;
		this.totalCount = totalCount;
		this.pager = pager;
	}
	public List<?> getResults() {
		return results;
	}

	public void setResults(List<?> results) {
		this.results = results;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}
	
}
