package com.omnicrola.testing.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Component;
import java.awt.Container;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javassist.Modifier;
import junit.framework.AssertionFailedError;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.powermock.api.easymock.PowerMock;

import com.omnicrola.util.ConstructorParameter;

public abstract class EnhancedTestCase {

	private static Random randomizer = new Random();

	@SuppressWarnings("unchecked")
	public static <T> T assertAnnotationIsOnClass(Class<?> ourClass, Class<T> annotationClass) {
		final Annotation[] annotations = ourClass.getAnnotations();
		for (final Annotation annotation : annotations) {
			if (annotation.annotationType().equals(annotationClass)) {
				return (T) annotation;
			}
		}
		throw new AssertionFailedError("An annotation of type " + annotationClass.getName()
				+ " was not found on class " + ourClass.getName());
	}

	public static void assertConstructionParameterEquals(Object productionObject, Object expectedReturn, String name) {

		final Object retrievedObject = getConstructionParam(productionObject, name);
		if (expectedReturn.equals(retrievedObject)) {
			return;
		} else {
			throwEqualsException(expectedReturn, name, retrievedObject);
		}
	}

	public static void assertConstructionParameterNotSame(Object productionObject, Object expectedReturn, String name) {

		final Object retrievedObject = getConstructionParam(productionObject, name);
		if (expectedReturn != retrievedObject) {
			return;
		} else {
			throw new AssertionFailedError("\nMethod with @ConstructionParameter name '" + name
					+ "' matched, but was supposed to be different.");

		}
	}

	public static void assertConstructionParamSame(String name, Object expectedReturn, Object productionObject) {

		final Object retrievedObject = getConstructionParam(productionObject, name);
		if (expectedReturn == retrievedObject) {
			return;
		} else {
			throw new AssertionFailedError("\nMethod with @ConstructionParameter name '" + name
					+ "' did not match expected object.\n Expected " + expectedReturn + "\n but was \n"
					+ retrievedObject + ".");
		}
	}

	public static void assertConstructorIsPackageProtected(Class<?> class1) {
		final Constructor<?>[] declaredConstructors = class1.getDeclaredConstructors();
		for (int i = 0; i < declaredConstructors.length; i++) {
			final int modifiers = declaredConstructors[i].getModifiers();
			assertTrue(Modifier.isPackage(modifiers));

		}
	}

	public static void assertEqualArrays(byte[] expectedBytes, byte[] actualBytes) {
		if (expectedBytes == null || actualBytes == null) {
			throw new AssertionError("Byte arrays are not equal. One was null");
		}
		if (expectedBytes.length != actualBytes.length) {
			throw new AssertionError("Byte arrays where not of equal length.");
		}

		for (int i = 0; i < expectedBytes.length; i++) {
			if (expectedBytes[i] != actualBytes[i]) {
				throw new AssertionError("Byte arrays are not equal. Unequal values occur at index [" + i
						+ "]. Expected " + expectedBytes[i] + " but was " + actualBytes[i]);
			}
		}

	}

	public static void assertEqualWithTollerance(double expected, double actual, double tollerance) {
		final double difference = Math.abs(expected - actual);
		final boolean success = difference < tollerance;
		if (!success) {
			final AssertionFailedError assertionFailedError = new AssertionFailedError(actual + " was not equal to "
					+ expected + " within a tollerance of " + tollerance);
			throw (assertionFailedError);
		}
	}

	@SuppressWarnings("unchecked")
	protected static <T> T assertIsOfTypeAndGet(Class<T> class1, Object target) {
		assertNotNull(target, "Could not check type, target was null");
		assertEquals(class1, target.getClass());
		return (T) target;
	}

	public void assertCollectionsEqual(Collection<?> expected, Collection<?> actual) {
		if (expected.size() != actual.size()) {
			throw new AssertionFailedError("Collections where not of equal size");
		}
		for (Object expectedObject : expected) {
			if (!actual.contains(expectedObject)) {
				throw new AssertionFailedError("Object " + expectedObject + " was not found in the actual Collection");
			}
		}
	}

	public static void assertNotSame(Object expected, Object actual) {
		assertNotSame(expected, actual, expected + "\n was same as \n" + actual);
	}

	public static void assertNotSame(Object expected, Object actual, String errorMessage) {
		if (expected == actual) {
			throw new AssertionFailedError(errorMessage);
		}
	}

	public static void assertNotNull(Object target) {
		assertNotNull(target, "Object was null");
	}

	public static void assertNotNull(Object target, String message) {
		if (target == null) {
			throw new AssertionFailedError(message);
		}
	}

	public static void assertLessThan(double left, double right, double delta) {
		final boolean success = (left - delta <= right);
		if (!success) {
			final AssertionFailedError assertionFailedError = new AssertionFailedError("Assertion failed. " + left
					+ " was not less than  " + right);
			throw (assertionFailedError);

		}
	}

	public static <T> void assertListContains(Object item, List<T> targetList) {
		final Iterator<T> iterator = targetList.iterator();
		T nextItem;
		while (iterator.hasNext()) {
			nextItem = iterator.next();
			if (nextItem == item) {
				return;
			}
		}
		throw new AssertionFailedError("Target list does not contain " + item);
	}

	public static <T> void assertListContainsEqual(T item, List<T> targetList) {
		final Iterator<T> iterator = targetList.iterator();
		T nextItem;
		while (iterator.hasNext()) {
			nextItem = iterator.next();
			if (nextItem.equals(item)) {
				return;
			}
		}
		throw new AssertionFailedError("Target list does not contain equal " + item);
	}

	protected static void assertMethodIsProtected(Method method) {
		final int modifiers = method.getModifiers();
		if (!Modifier.isProtected(modifiers)) {
			throw new AssertionFailedError("Method " + method.getName() + " was not protected.");
		}
	}

	protected static void assertPrivateConstructor(Class<?> targetClass) {
		final int constructors = targetClass.getConstructors().length;
		if (constructors != 0) {
			throw new AssertionError("Target class did not have zero constructors. Had " + constructors);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T assertConstructorParamAndGet(Object targetObject, String name, Class<T> expectedClass) {
		Object constructionParam = getConstructionParam(targetObject, name);
		if (expectedClass.equals(constructionParam.getClass())) {
			return (T) constructionParam;
		}
		throw new AssertionFailedError("Construction parameter '" + name + "' was not of type " + expectedClass
				+ ", it was " + constructionParam.getClass());
	}

	private static Object getConstructionParam(Object targetObject, String name) {
		try {
			final Field[] declaredFields = targetObject.getClass().getDeclaredFields();
			for (final Field field : declaredFields) {
				if (field.isAnnotationPresent(ConstructorParameter.class)) {
					final String annotatedName = field.getAnnotation(ConstructorParameter.class).value();
					if (annotatedName.equals(name)) {
						field.setAccessible(true);
						return field.get(targetObject);
					}
				}
			}
			throwNotFoundException(name);
		} catch (final IllegalAccessException e) {
			throwReflectionErrorException(e);
		} catch (final IllegalArgumentException e) {
			throwReflectionErrorException(e);
		}
		return null;
	}

	protected static byte[] joinArrays(byte[]... arrayOfArrays) {
		int totalLength = 0;
		for (int i = 0; i < arrayOfArrays.length; i++) {
			totalLength += arrayOfArrays[i].length;
		}
		final byte[] bigArray = new byte[totalLength];
		int x = 0;
		for (int i = 0; i < arrayOfArrays.length; i++) {
			final byte[] singleArray = arrayOfArrays[i];
			for (int j = 0; j < singleArray.length; j++) {
				bigArray[x] = singleArray[j];
				x++;
			}
		}
		return bigArray;
	}

	private static void throwEqualsException(Object expected, String name, Object getterReturn)
			throws AssertionFailedError {
		throw new AssertionFailedError("\nMethod with @ConstructorArgument name '" + name
				+ "' did not match expected object.\n Expected " + expected + "\n but was \n" + getterReturn + ".");
	}

	private static void throwNotFoundException(String name) throws AssertionFailedError {
		throw new AssertionFailedError("\nNo @ConstructionParameter could be found with name : '" + name + "'");
	}

	private static void throwReflectionErrorException(Exception e) throws AssertionFailedError {
		throw new AssertionFailedError("\nException while evaluating @ConstructionParameter : \n" + e.getMessage());
	}

	public static void assertImplementsInterface(Class<?> expectedInterface, Class<?> concreteClass) {
		final Class<?>[] interfaces = concreteClass.getInterfaces();
		for (final Class<?> face : interfaces) {
			if (face == expectedInterface) {
				return;
			}

		}
		throw new AssertionError("Class " + concreteClass.getSimpleName() + " does not implement "
				+ expectedInterface.getSimpleName());
	}

	public static void assertIsSuperclass(Class<?> childClass, Class<?> expectedSuperclass) {
		final Class<?> superclass = childClass.getSuperclass();
		if (superclass != expectedSuperclass) {
			throw new AssertionFailedError("Class " + childClass.getSimpleName() + " does not inherit from superclass "
					+ expectedSuperclass.getSimpleName());
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T getComponentByName(Container container, String name, Class<T> class1) {
		final Component[] components = container.getComponents();
		for (final Component component : components) {
			if (name.equals(component.getName())) {
				return (T) component;
			}
		}
		throw new AssertionFailedError("Component '" + name + "' not found");
	}

	public static void checkHashCodeAndEquality(Object primaryObject, Object equalObject, Object... notEqualObjects) {
		if (!primaryObject.equals(equalObject)) {
			throw new AssertionFailedError("Equality failure. Primary object was not equal to declared equal");
		}
		if (primaryObject.hashCode() != equalObject.hashCode()) {
			throw new AssertionFailedError(
					"Hash  failure. Primary object's hash was not equal to declared equal object");
		}
		for (final Object notEqualObject : notEqualObjects) {
			if (primaryObject.equals(notEqualObject)) {
				throw new AssertionFailedError("Unexpected equality! Primary was equal to : " + notEqualObject);
			}
			if (primaryObject.hashCode() == notEqualObject.hashCode()) {
				throw new AssertionFailedError("Unexpected hash equality! Primary has was equal to hash of :"
						+ notEqualObject);
			}
		}
	}

	private ArrayList<Object> mocks = new ArrayList<Object>();
	private final ArrayList<Class<?>> staticMocks = new ArrayList<Class<?>>();
	private boolean stopReplayWasCalled;

	@Before
	public void setUp() throws Exception {
		this.stopReplayWasCalled = false;
	}

	@After
	public void tearDown() throws Exception {
		if (!this.stopReplayWasCalled) {
			stopReplay();
		}
	}

	protected void startReplay() {
		for (final Object singleMock : this.mocks) {
			EasyMock.replay(singleMock);
		}
		for (final Class<?> singleClass : this.staticMocks) {
			PowerMock.replay(singleClass);
		}
		this.stopReplayWasCalled = false;
	}

	protected void stopReplay() {
		for (final Object singleMock : this.mocks) {
			EasyMock.verify(singleMock);
		}
		for (final Class<?> singleClass : this.staticMocks) {
			PowerMock.verify(singleClass);
		}
		this.stopReplayWasCalled = true;
	}

	protected void nullAllFieldsInObject(Object childObject) throws IllegalArgumentException, IllegalAccessException {
		final Field[] fields = childObject.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			wipeField(childObject, fields[i]);
		}
	}

	private void wipeField(Object childObject, Field targetField) throws IllegalArgumentException,
			IllegalAccessException {
		if (!targetField.getType().isPrimitive()) {
			targetField.setAccessible(true);
			targetField.set(childObject, null);
		}
	}

	protected void nullOutAllFields() {
		for (@SuppressWarnings("unused")
		Object singleMock : this.mocks) {
			singleMock = null;
		}
		this.mocks = null;

	}

	public static int randI() {
		return randomizer.nextInt();
	}

	public static float randF() {
		return randomizer.nextFloat();
	}

	private static String stringValues = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

	public static String randString(int length) {
		final StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < length; i++) {
			final int charValue = randomizer.nextInt(stringValues.length());
			stringBuilder.append(stringValues.charAt(charValue));

		}
		return stringBuilder.toString();
	}

	public static GregorianCalendar randDate() {
		final int year = randomizer.nextInt(100) + 1990;
		final int month = randomizer.nextInt(12);
		final int day = randomizer.nextInt(28);
		return new GregorianCalendar(year, month, day);
	}

	protected <T> T useMock(Class<T> classToMock) {
		final T newMockObject = EasyMock.createMock(classToMock);
		this.mocks.add(newMockObject);
		return newMockObject;
	}

	protected <T> T useNiceMock(Class<T> classToMock) {
		final T newMockObject = EasyMock.createNiceMock(classToMock);
		this.mocks.add(newMockObject);
		return newMockObject;
	}

	protected void useStaticMock(Class<?> classToMock) {
		PowerMock.mockStatic(classToMock);
		this.staticMocks.add(classToMock);
	}

	protected void useStaticMockNice(Class<?> classToMock) {
		PowerMock.mockStaticNice(classToMock);
		this.staticMocks.add(classToMock);
	}

	protected void useStaticMockStrict(Class<?> classToMock) {
		PowerMock.mockStaticStrict(classToMock);
		this.staticMocks.add(classToMock);
	}

	protected <T> T useStrictMock(Class<T> classToMock) {
		final T newMockObject = EasyMock.createStrictMock(classToMock);
		this.mocks.add(newMockObject);
		return newMockObject;
	}

}
