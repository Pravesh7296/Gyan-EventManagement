package com.Event.Database;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.Event.Utils.Event;
@Repository
public interface DatabaseRepo extends JpaRepository<Event, Integer> {

}
