package com.increff.pos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.hibernate.internal.util.io.StreamCopier.BUFFER_SIZE;

@Controller
public class SampleController {

	//Spring ignores . (dot) in the path. So we need fileName:.+

	@GetMapping(value = "/sample/{fileName:.+}")
	public StreamingResponseBody getFile(@PathVariable("fileName") String fileName, HttpServletResponse response) {

		response.setContentType("text/csv");
		response.addHeader("Content-disposition:", "attachment; filename=" + fileName);
		String fileClasspath = "C:\\Users\\KIIT\\Desktop\\Projects\\Pos_project\\project\\pos\\src\\main\\resources\\" +
				"com\\increff\\pos\\" + fileName;

		return outputStream -> {
			int bytesRead;
			byte[] buffer = new byte[BUFFER_SIZE];
			InputStream inputStream = new FileInputStream(fileClasspath);
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
		};
	}

}