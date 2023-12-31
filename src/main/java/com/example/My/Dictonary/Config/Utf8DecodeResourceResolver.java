package com.example.My.Dictonary.Config;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.Resource;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolver;

public class Utf8DecodeResourceResolver extends PathResourceResolver implements ResourceResolver {

	@Override
	protected Resource getResource(String resourcePath, Resource location) throws IOException {
		resourcePath = URLDecoder.decode(resourcePath, StandardCharsets.UTF_8);
		return super.getResource(resourcePath, location);
	}
}
