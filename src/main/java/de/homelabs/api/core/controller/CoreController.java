package de.homelabs.api.core.controller;

import java.time.ZoneId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/core/v1")
public class CoreController {
	Logger logger = LoggerFactory.getLogger(CoreController.class);
	
	@Autowired
	BuildProperties buildProperties;
	
	@GetMapping("/version")
	public ResponseEntity<BuildProperties> getVersion() {
		
		/*return String.format("%s %s (%s)" 
				,buildProperties.getArtifact()
				,buildProperties.getVersion()
				,buildProperties.getTime().atZone(ZoneId.of("Europe/Berlin")));*/
		logger.warn("sending build properties");
		return new ResponseEntity<BuildProperties>(buildProperties, HttpStatus.OK);
	}
}
