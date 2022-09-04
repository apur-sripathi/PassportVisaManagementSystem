package com.pvms.model.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class State {
		@Id
		@Column(length=6)
		private String stateId;
		@Column(length=20)
		private String stateName;
		public String getStateId() {
			return stateId;
		}
		public void setStateId(String stateId) {
			this.stateId = stateId;
		}
		public String getStateName() {
			return stateName;
		}
		public void setStateName(String stateName) {
			this.stateName = stateName;
		}
		public State(String stateId, String stateName) {
			super();
			this.stateId = stateId;
			this.stateName = stateName;
		}
		public State() {
			super();
		}
		@Override
		public String toString() {
			return "State [stateId=" + stateId + ", stateName=" + stateName + "]";
		}
		
		
}
