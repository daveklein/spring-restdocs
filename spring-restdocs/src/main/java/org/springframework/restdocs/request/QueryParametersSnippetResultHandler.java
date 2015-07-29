/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.restdocs.request;

import java.util.Map;
import java.util.Set;

import org.springframework.restdocs.snippet.SnippetGenerationException;
import org.springframework.restdocs.snippet.SnippetWritingResultHandler;
import org.springframework.test.web.servlet.MvcResult;

/**
 * A {@link SnippetWritingResultHandler} that produces a snippet documenting the query
 * parameters supported by a RESTful resource.
 *
 * @author Andy Wilkinson
 */
public class QueryParametersSnippetResultHandler extends
		AbstractParametersSnippetResultHandler {

	protected QueryParametersSnippetResultHandler(String identifier,
			Map<String, Object> attributes, ParameterDescriptor... descriptors) {
		super(identifier, "query-parameters", attributes, descriptors);
	}

	@Override
	protected void verificationFailed(Set<String> undocumentedParameters,
			Set<String> missingParameters) {
		String message = "";
		if (!undocumentedParameters.isEmpty()) {
			message += "Query parameters with the following names were not documented: "
					+ undocumentedParameters;
		}
		if (!missingParameters.isEmpty()) {
			if (message.length() > 0) {
				message += ". ";
			}
			message += "Query parameters with the following names were not found in the request: "
					+ missingParameters;
		}
		throw new SnippetGenerationException(message);
	}

	@Override
	protected Set<String> extractActualParameters(MvcResult result) {
		return result.getRequest().getParameterMap().keySet();
	}

}
