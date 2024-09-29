package de.homelabs.api.core.config;

import javax.sql.DataSource;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.db.jdbc.ColumnConfig;
import org.apache.logging.log4j.core.appender.db.jdbc.JdbcAppender;
import org.apache.logging.log4j.core.filter.ThresholdFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

/*
 * refs: 
 * https://stackoverflow.com/questions/73573710/log4j2-programmatic-jdbcappender-columnconfig-lookup-not-working
 * https://smasue.github.io/log4j2-spring-database-appender
 */
@Service
public class LoggerConfiguration {

	@Autowired
	DataSource dataSource;
	
	@PostConstruct
	public void onStartUp() {
				
		ColumnConfig[] columnConfigs = new ColumnConfig[6];
		columnConfigs[0] = ColumnConfig.newBuilder().setName("event_id").setLiteral("nextval('logs_event_id_seq')").setUnicode(false).build();
		columnConfigs[1] = ColumnConfig.newBuilder().setName("event_date").setEventTimestamp(true).build();
		columnConfigs[2] = ColumnConfig.newBuilder().setName("level").setPattern("%level").setUnicode(false).build();
		columnConfigs[3] = ColumnConfig.newBuilder().setName("logger").setPattern("%logger").setUnicode(false).build();
		columnConfigs[4] = ColumnConfig.newBuilder().setName("message").setPattern("%message").setUnicode(false).build();
		columnConfigs[5] = ColumnConfig.newBuilder().setName("throwable").setPattern("%ex{full").setUnicode(false).build();
	    
	    ThresholdFilter filter = ThresholdFilter.createFilter(Level.WARN, null, null);
	    PooledConnectionSource pooledConnectionSource = new PooledConnectionSource(dataSource);
	    
	    JdbcAppender appender = JdbcAppender.newBuilder()
	    		.setName("db logging")
	    		.setColumnConfigs(columnConfigs)
	    		.setConnectionSource(pooledConnectionSource)
	    		.setFilter(filter)
	    		.setTableName("logs")
	    		.build();
	    
	    // start the appender and add it to the right context (crazy hack)
	    appender.start();
	    
	    LoggerContext lc = (LoggerContext) LogManager.getContext(false);
	    lc.getRootLogger().addAppender(appender);
	}
	
	
}
