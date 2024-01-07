package study.jpa.entity1;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.log4j.Log4j2;

@Converter
@Log4j2
public class BooleanToYNConverter implements AttributeConverter<Boolean, String>{
	@Override
	public String convertToDatabaseColumn(Boolean att) {
		log.debug("convert " + att + " to " + ((att != null && att) ? "Y" : "N"));
		return (att != null && att) ? "Y" : "N";
	}
	@Override
	public Boolean convertToEntityAttribute(String dbData) {
		log.debug("convert " + dbData + " to " + ("Y".equals(dbData)));
		return "Y".equals(dbData);
	}
}
