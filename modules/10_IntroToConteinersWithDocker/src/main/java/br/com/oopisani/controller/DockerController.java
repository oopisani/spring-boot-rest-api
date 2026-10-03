package br.com.oopisani.controller;

import br.com.oopisani.environment.InstanceInformationService;
import br.com.oopisani.model.HelloDocker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DockerController {

	Logger logger = LoggerFactory.getLogger(DockerController.class);

	@Autowired
	private InstanceInformationService service;

	// http://localhost/
	@GetMapping(path = "/")
	public String imUpAndRunning() { return "{healthy:true}";
	}

	// http://localhost/hello-docker
	@RequestMapping("/hello-docker")
	public HelloDocker greeting() {

		logger.info("Endpoint /hello-docker is called!!!");

		return new HelloDocker(
				"Hello Docker - V1",
				service.retrieveInstanceInfo()
		);
	}
}
