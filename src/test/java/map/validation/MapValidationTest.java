package map.validation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import exception.InvalidGrassDistributionException;
import org.junit.jupiter.api.Test;

import map.ClientMap;

import java.util.Collections;
import java.util.List;

class MapValidationTest {
	

	@Test
	void testValidMapWithNoErrorViolation_findsNoError() {
		IRuleValidator mockValidator1 = mock(IRuleValidator.class);
		IRuleValidator mockValidator2 = mock(IRuleValidator.class);
		IRuleValidator mockValidator3 = mock(IRuleValidator.class);
		
		List<IRuleValidator> validators = List.of(mockValidator1, mockValidator2, mockValidator3);
		MapValidation validation = new MapValidation(validators);

		ClientMap mockMap = mock(ClientMap.class);
		when(mockMap.getNodesList()).thenReturn(Collections.emptyList());
		

		validation.isMapValid(mockMap);

		verify(mockValidator1).validate(anyList(), any(Notification.class));
		verify(mockValidator2).validate(anyList(), any(Notification.class));
		verify(mockValidator3).validate(anyList(), any(Notification.class));
		assertFalse(validation.getNotification().hasError());
	}
	@Test
	void testInvalidMapWithOneRuleViolation() {
		IRuleValidator mockValidator1 = mock(IRuleValidator.class);
		IRuleValidator mockValidator2 = mock(IRuleValidator.class);
		IRuleValidator mockValidator3 = mock(IRuleValidator.class);
		
		List<IRuleValidator> validators = List.of(mockValidator1, mockValidator2, mockValidator3);
		MapValidation validation = new MapValidation(validators);

		ClientMap mockMap = mock(ClientMap.class);
		when(mockMap.getNodesList()).thenReturn(Collections.emptyList());

		doAnswer(action -> {
			Notification notification = action.getArgument(1);
			notification.addError( new InvalidGrassDistributionException("Not enough grass"));
			return null;
		}).when(mockValidator3).validate(anyList(),any(Notification.class));

		assertFalse(validation.isMapValid(mockMap));
		verify(mockValidator1).validate(anyList(), any(Notification.class));
		verify(mockValidator2).validate(anyList(), any(Notification.class));
		verify(mockValidator3).validate(anyList(), any(Notification.class));
		assertEquals(1, validation.getNotification().getErrors().size());
	}

}
