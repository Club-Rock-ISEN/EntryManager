package org.clubrockisen.entities;

import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.clubrockisen.entities.Parameter.ParameterColumn;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link Parameter} class.
 * @author Alex
 */
public class ParameterTest {
	
	/** Parameter with no information */
	private Parameter		nullParameter;
	/** Parameter with a name */
	private Parameter		parameterFromName;
	/** Parameter without a component class */
	private Parameter		noComponentParameter;
	/** Parameter with all the informations */
	private Parameter		fullParameter;
	/** List with the parameters to test */
	private List<Parameter>	parameters;
	
	/**
	 * Build the entries which will be used during the tests.
	 */
	@Before
	public void setUp () {
		nullParameter = new Parameter();
		parameterFromName = new Parameter("size");
		noComponentParameter = new Parameter("length", "12", "Integer");
		fullParameter = new Parameter("price", "2.8", "Double", "ComboBox");
		
		parameters = new ArrayList<>(4);
		parameters.add(nullParameter);
		parameters.add(parameterFromName);
		parameters.add(noComponentParameter);
		parameters.add(fullParameter);
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Parameter#getEntityName()}.
	 */
	@Test
	public void testGetEntityName () {
		for (final Parameter parameter : parameters) {
			assertEquals("parameter", parameter.getEntityName());
		}
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Parameter#getEntityColumns()}.
	 */
	@Test
	public void testGetEntityColumns () {
		for (final Parameter parameter : parameters) {
			final Map<? extends Enum<?>, Column> columns = parameter.getEntityColumns();
			assertEquals(4, columns.size());
			
			assertEquals("name", columns.get(ParameterColumn.NAME).getName());
			assertEquals(String.class, columns.get(ParameterColumn.NAME).getType());
			assertEquals(true, columns.get(ParameterColumn.NAME).isID());
			
			assertEquals("value", columns.get(ParameterColumn.VALUE).getName());
			assertEquals(String.class, columns.get(ParameterColumn.VALUE).getType());
			assertEquals(false, columns.get(ParameterColumn.VALUE).isID());
			
			assertEquals("type", columns.get(ParameterColumn.TYPE).getName());
			assertEquals(String.class, columns.get(ParameterColumn.TYPE).getType());
			assertEquals(false, columns.get(ParameterColumn.TYPE).isID());
			
			assertEquals("componentClass", columns.get(ParameterColumn.COMPONENT_CLASS).getName());
			assertEquals(String.class, columns.get(ParameterColumn.COMPONENT_CLASS).getType());
			assertEquals(false, columns.get(ParameterColumn.COMPONENT_CLASS).isID());
		}
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Parameter#getID()}.
	 */
	@Test
	public void testGetID () {
		for (final Parameter parameter : parameters) {
			assertEquals(parameter.getName(), parameter.getID());
		}
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Parameter#getColumns()}.
	 */
	@Test
	public void testGetColumns () {
		for (final Parameter parameter : parameters) {
			assertEquals(parameter.getEntityColumns(), Parameter.getColumns());
		}
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Parameter#getName()}.
	 */
	@Test
	public void testGetName () {
		assertEquals("", nullParameter.getName());
		assertEquals("size", parameterFromName.getName());
		assertEquals("length", noComponentParameter.getName());
		assertEquals("price", fullParameter.getName());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Parameter#getValue()}.
	 */
	@Test
	public void testGetValue () {
		assertEquals("", nullParameter.getValue());
		assertEquals("", parameterFromName.getValue());
		assertEquals("12", noComponentParameter.getValue());
		assertEquals("2.8", fullParameter.getValue());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Parameter#setValue(java.lang.String)}.
	 */
	@Test
	public void testSetValue () {
		nullParameter.setValue("null");
		parameterFromName.setValue("800x640");
		noComponentParameter.setValue("82");
		fullParameter.setValue("3.2");
		
		assertEquals("null", nullParameter.getValue());
		assertEquals("800x640", parameterFromName.getValue());
		assertEquals("82", noComponentParameter.getValue());
		assertEquals("3.2", fullParameter.getValue());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Parameter#getType()}.
	 */
	@Test
	public void testGetType () {
		assertEquals("", nullParameter.getType());
		assertEquals("", parameterFromName.getType());
		assertEquals("Integer", noComponentParameter.getType());
		assertEquals("Double", fullParameter.getType());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Parameter#setType(java.lang.String)}.
	 */
	@Test
	public void testSetType () {
		nullParameter.setType("Object");
		parameterFromName.setType("String");
		noComponentParameter.setType("Class");
		fullParameter.setType("Integer");
		
		assertEquals("Object", nullParameter.getType());
		assertEquals("String", parameterFromName.getType());
		assertEquals("Class", noComponentParameter.getType());
		assertEquals("Integer", fullParameter.getType());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Parameter#getComponentClass()}.
	 */
	@Test
	public void testGetComponentClass () {
		assertEquals("", nullParameter.getComponentClass());
		assertEquals("", parameterFromName.getComponentClass());
		assertEquals("", noComponentParameter.getComponentClass());
		assertEquals("ComboBox", fullParameter.getComponentClass());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Parameter#setComponentClass(java.lang.String)}.
	 */
	@Test
	public void testSetComponentClass () {
		nullParameter.setComponentClass("ComboBox");
		parameterFromName.setComponentClass("IntSpinner");
		noComponentParameter.setComponentClass("DoubleSpinner");
		fullParameter.setComponentClass("TimeSpinner");
		
		assertEquals("ComboBox", nullParameter.getComponentClass());
		assertEquals("IntSpinner", parameterFromName.getComponentClass());
		assertEquals("DoubleSpinner", noComponentParameter.getComponentClass());
		assertEquals("TimeSpinner", fullParameter.getComponentClass());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Entity#getIDColumn()}.
	 */
	@Test
	public void testGetIDColumn () {
		try {
			for (final Parameter parameter : parameters) {
				final Column column = parameter.getIDColumn();
				assertEquals("name", column.getName());
				assertEquals(String.class, column.getType());
				assertEquals(true, column.isID());
			}
		} catch (final NoIdException e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Entity#generateInsertQuerySQL()}.
	 */
	@Test
	public void testGenerateInsertQuerySQL () {
		for (final Parameter parameter : parameters) {
			assertEquals("INSERT INTO parameter(`name`,`value`,`type`,`componentClass`) VALUES ", parameter.generateInsertQuerySQL());
		}
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Entity#generateInsertQuerySQL(boolean)}.
	 */
	@Test
	public void testGenerateInsertQuerySQLBoolean () {
		for (final Parameter parameter : parameters) {
			assertEquals("INSERT INTO parameter(`name`,`value`,`type`,`componentClass`) VALUES ", parameter.generateInsertQuerySQL(true));
			assertEquals("INSERT INTO parameter(`value`,`type`,`componentClass`) VALUES ", parameter.generateInsertQuerySQL(false));
		}
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Entity#generateDeleteQuerySQL()}.
	 */
	@Test
	public void testGenerateDeleteQuerySQL () {
		try {
			assertEquals("DELETE FROM parameter WHERE name = ''", nullParameter.generateDeleteQuerySQL());
			assertEquals("DELETE FROM parameter WHERE name = 'size'", parameterFromName.generateDeleteQuerySQL());
			assertEquals("DELETE FROM parameter WHERE name = 'length'", noComponentParameter.generateDeleteQuerySQL());
			assertEquals("DELETE FROM parameter WHERE name = 'price'", fullParameter.generateDeleteQuerySQL());
		} catch (final NoIdException e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Entity#generateUpdateQuerySQL()}.
	 */
	@Test
	public void testGenerateUpdateQuerySQL () {
		for (final Parameter parameter : parameters) {
			assertEquals("UPDATE parameter SET ", parameter.generateUpdateQuerySQL());
		}
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Entity#generateWhereIDQuerySQL()}.
	 */
	@Test
	public void testGenerateWhereIDQuerySQL () {
		try {
			assertEquals(" WHERE name = ''", nullParameter.generateWhereIDQuerySQL());
			assertEquals(" WHERE name = 'size'", parameterFromName.generateWhereIDQuerySQL());
			assertEquals(" WHERE name = 'length'", noComponentParameter.generateWhereIDQuerySQL());
			assertEquals(" WHERE name = 'price'", fullParameter.generateWhereIDQuerySQL());
		} catch (final NoIdException e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test method for
	 * {@link org.clubrockisen.entities.Entity#generateWhereIDQuerySQL(java.lang.Object)}.
	 */
	@Test
	public void testGenerateWhereIDQuerySQLObject () {
		try {
			assertEquals(" WHERE name = 'nbPlaces'", nullParameter.generateWhereIDQuerySQL("nbPlaces"));
			assertEquals(" WHERE name = 'timeLimit'", parameterFromName.generateWhereIDQuerySQL("timeLimit"));
			assertEquals(" WHERE name = 'max'", noComponentParameter.generateWhereIDQuerySQL("max"));
			assertEquals(" WHERE name = 'maxPeople'", fullParameter.generateWhereIDQuerySQL("maxPeople"));
		} catch (final NoIdException e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Entity#generateSearchAllQuerySQL()}.
	 */
	@Test
	public void testGenerateSearchAllQuerySQL () {
		for (final Parameter parameter : parameters) {
			assertEquals("SELECT * FROM parameter", parameter.generateSearchAllQuerySQL());
		}
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Entity#hashCode()}.
	 */
	@Test
	public void testHashCode () {
		for (final Parameter parameter : parameters) {
			// Checking that all hash codes are different
			for (final Parameter other : parameters) {
				if (parameter != other) {
					assertThat(parameter.hashCode(), not(other.hashCode()));
				}
			}
			try {
				final Parameter clone = parameter.clone();
				assertEquals(parameter.hashCode(), clone.hashCode());
				clone.setType(parameter.getType() + "/");
				assertThat(parameter.hashCode(), not(clone.hashCode()));
				clone.setType(parameter.getType());
				clone.setValue(parameter.getValue() + "./");
				assertThat(parameter.hashCode(), not(clone.hashCode()));
				clone.setValue(parameter.getValue());
				clone.setComponentClass(parameter.getComponentClass() + ".field");
				assertThat(parameter.hashCode(), not(clone.hashCode()));
			} catch (final CloneNotSupportedException e) {
				fail(e.getMessage());
			}
		}
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Entity#equals(Object)}.
	 */
	@Test
	public void testEquals () {
		for (final Parameter parameter : parameters) {
			// Checking that all hash codes are different
			for (final Parameter other : parameters) {
				if (parameter != other) {
					assertThat(parameter, not(other));
				} else {
					assertEquals(parameter, other);
				}
			}
			// Checking clones
			try {
				final Parameter clone = parameter.clone();
				assertEquals(parameter, clone);
				clone.setType(parameter.getType() + "/");
				assertThat(parameter, not(clone));
				clone.setType(parameter.getType());
				clone.setValue(parameter.getValue() + "./");
				assertThat(parameter, not(clone));
				clone.setValue(parameter.getValue());
				clone.setComponentClass(parameter.getComponentClass() + ".field");
				assertThat(parameter.hashCode(), not(clone.hashCode()));
			} catch (final CloneNotSupportedException e) {
				fail(e.getMessage());
			}
			
			// Other cases
			assertThat(null, not(parameter));
			assertFalse(parameter.equals(parameters));
		}
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Parameter#clone()}.
	 */
	@Test
	public void testClone () {
		try {
			for (final Parameter parameter : parameters) {
				final Parameter other = parameter.clone();
				assertEquals(parameter.getName(), other.getName());
				assertEquals(parameter.getType(), other.getType());
				assertEquals(parameter.getValue(), other.getValue());
				assertEquals(parameter.getComponentClass(), other.getComponentClass());
			}
		} catch (final CloneNotSupportedException e) {
			fail(e.getMessage());
		}
	}
}
