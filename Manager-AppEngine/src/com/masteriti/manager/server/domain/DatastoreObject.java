package com.masteriti.manager.server.domain;

import javax.persistence.Id;
import javax.persistence.PrePersist;

public class DatastoreObject {
	
	@Id
	private Long id;
	private Integer version = 0;
	private Boolean active = true;
	
	/**
	 * Auto-increment version # whenever persisted
	 */
	@PrePersist
	void onPersist()
	{
		this.version++;
	}

	public Long getId() {
		return id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
	public void setActive() {
		this.active = true;
	}
	
	public void setInactive() {
		this.active = false;
	}
	
	public Boolean getActiveStatus() {
		return active;
	}

}
