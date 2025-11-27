package com.saytongg.mock;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.saytongg.shared.controller.BaseController;
import com.saytongg.shared.controller.ControllerResponse;

@Controller
@RequestMapping("mock")
public class MockController extends BaseController{
	
	private final MockService mockService;

	public MockController(@Qualifier("mockService") MockService mockService) {
		super();
		
		this.mockService = mockService;
	}
	
	@ResponseBody
	@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<ControllerResponse<MockDTO>> readMock(){
		MockDTO data = new MockDTO();
		data.setId(1);
		data.setName("Braullo");
		data.setAge(23);
		
		return ResponseEntity.ok(new ControllerResponse<MockDTO>(data, "Success"));
	}
	
	@ResponseBody
	@PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, 
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<ControllerResponse<MockDTO>> saveMock(@RequestBody MockDTO data){
		logger.info("Received data is {}", data.toString());
		
		// Create mock entity to be persisted
		MockEntity mockEntity = new MockEntity();
		mockEntity.setName(data.getName());
		mockEntity.setAge(data.getAge());
		
		try {
			mockService.create(mockEntity);
			
			MockDTO newData = new MockDTO();
			newData.setId(mockEntity.getId());
			newData.setName(mockEntity.getName());
			newData.setAge(mockEntity.getAge());
			
			return ResponseEntity.ok(new ControllerResponse<MockDTO>(newData, "Success"));
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			return ResponseEntity.ok(new ControllerResponse<MockDTO>(e));
		}
	}
}
