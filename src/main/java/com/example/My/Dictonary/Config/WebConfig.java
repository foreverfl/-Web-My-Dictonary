package com.example.My.Dictonary.Config;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	private List<String> getAllFolder() {
		List<String> dateArr = new ArrayList<>();

		LocalDate tmpDate = LocalDate.parse("2022-09-08");
		dateArr.add(tmpDate.toString());
		LocalDate lastDate = LocalDate.now().plusDays(1);

		while (true) {

			tmpDate = tmpDate.plusDays(1);

			if (tmpDate.equals(lastDate)) {
				break;
			}

			dateArr.add(tmpDate.toString());
		}

		return dateArr;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		List<String> dates = getAllFolder();
		for (int i = 0; i < dates.size(); i++) {

			registry.addResourceHandler("/photo/**") //
					.addResourceLocations("file:///C:/upload/" + dates.get(i) + "/") //
					.resourceChain(true) //
					.addResolver(new Utf8DecodeResourceResolver());

		}
	}

}
