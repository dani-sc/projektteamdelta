package at.itb13.oculus.technicalServices.util;

import java.time.LocalDateTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * TODO: Insert description here.
 * 
 * @author Daniel Scheffknecht
 * @date 13.04.2015
 */
@Converter
public class LocalDateTimePersistenceConverter implements AttributeConverter<LocalDateTime, java.sql.Timestamp> {
	 @Override
	  public java.sql.Timestamp convertToDatabaseColumn(LocalDateTime entityValue) {
	    if (entityValue != null) {
	      return java.sql.Timestamp.valueOf(entityValue);
	    }
	    return null;
	  }

	  @Override
	  public LocalDateTime convertToEntityAttribute(java.sql.Timestamp databaseValue) {
	    if (databaseValue != null) {
	      return databaseValue.toLocalDateTime();
	    }
	    return null;
	  }
}