package com.flowring.fish.entity;

import java.util.List;

public class FishRuleLogResult {
	
	private List<FishRuleLog> fishRuleLogResult;
	private boolean empty;
	public List<FishRuleLog> getFishRuleLogResult() {
		return fishRuleLogResult;
	}
	public void setFishRuleLogResult(List<FishRuleLog> fishRuleLogResult) {
		this.fishRuleLogResult = fishRuleLogResult;
	}
	public boolean isEmpty() {
		return empty;
	}
	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

}
