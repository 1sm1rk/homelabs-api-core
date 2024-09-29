package de.homelabs.api.core.config;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.logging.log4j.core.appender.db.jdbc.ConnectionSource;
import org.springframework.beans.factory.annotation.Autowired;

/*
 * Wrapper - log4j needs a Connection Source 
 * gets its data source from the underlaying spring/hikari implementation
 */
public class PooledConnectionSource implements ConnectionSource {

	@Autowired
	private DataSource dataSource;
	
	public PooledConnectionSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	public State getState() {
		return State.STARTED;
	}

	@Override
	public void initialize() {
		//do nothing
	}

	@Override
	public void start() {
		//do nothing
	}

	@Override
	public void stop() {
		//do nothing
	}

	@Override
	public boolean isStarted() {
		return true;
	}

	@Override
	public boolean isStopped() {
		return true;
	}

	@Override
	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();

	}

}
