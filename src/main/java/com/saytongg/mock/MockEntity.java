package com.saytongg.mock;

import com.saytongg.shared.database.entity.BaseEntity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity(name = MockEntity.TABLE_NAME)
@Table(name = MockEntity.TABLE_NAME)
public class MockEntity extends BaseEntity {
	
	private static final long serialVersionUID = 696186724307L;
	protected static final String TABLE_NAME = "mock";
	
	private String name;
	private int age;
	
	@NotEmpty
	@Basic
    @Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic
    @Column(name = "age")
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
