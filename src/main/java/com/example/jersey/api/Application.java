package com.example.jersey.api;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import javax.validation.ParameterNameProvider;
import javax.validation.Validation;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.ContextResolver;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.validation.ValidationConfig;
import org.glassfish.jersey.server.validation.ValidationFeature;
import org.glassfish.jersey.server.validation.internal.InjectingConstraintValidatorFactory;

import com.example.jersey.api.versioning.resources.ContactCardResource;

public class Application extends ResourceConfig {

	public Application() {
		// Resources.
		packages(ContactCardResource.class.getPackage().getName());
		
		// Now you can expect validation errors to be sent to the client.
	    property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
	    property(ServerProperties.BV_DISABLE_VALIDATE_ON_EXECUTABLE_OVERRIDE_CHECK, true);

		// Validation.
		register(ValidationConfigurationContextResolver.class);

		// Providers - JSON.
		register(ValidationFeature.class);
	}

	/**
	 * Custom configuration of validation. This configuration defines custom:
	 * <ul>
	 * <li>ConstraintValidationFactory - so that validators are able to inject
	 * Jersey providers/resources.</li>
	 * <li>ParameterNameProvider - if method input parameters are invalid, this
	 * class returns actual parameter names instead of the default ones
	 * ({@code arg0, arg1, ..})</li>
	 * </ul>
	 */
	public static class ValidationConfigurationContextResolver implements ContextResolver<ValidationConfig> {

		@Context
		private ResourceContext resourceContext;

		@Override
		public ValidationConfig getContext(final Class<?> type) {
			return new ValidationConfig()
					.constraintValidatorFactory(resourceContext.getResource(InjectingConstraintValidatorFactory.class))
					.parameterNameProvider(new CustomParameterNameProvider());
		}

		/**
		 * See ContactCardTest#testAddInvalidContact.
		 */
		private class CustomParameterNameProvider implements ParameterNameProvider {

			private final ParameterNameProvider nameProvider;

			public CustomParameterNameProvider() {
				nameProvider = Validation.byDefaultProvider().configure().getDefaultParameterNameProvider();
			}

			@Override
			public List<String> getParameterNames(final Constructor<?> constructor) {
				return nameProvider.getParameterNames(constructor);
			}

			@Override
			public List<String> getParameterNames(final Method method) {
				// See ContactCardTest#testAddInvalidContact.
				if ("addContact".equals(method.getName())) {
					return Arrays.asList("contact");
				}
				return nameProvider.getParameterNames(method);
			}
		}
	}
}
