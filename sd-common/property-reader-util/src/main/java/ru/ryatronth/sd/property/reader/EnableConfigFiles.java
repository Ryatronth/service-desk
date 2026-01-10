package ru.ryatronth.sd.property.reader;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.AliasFor;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@PropertySource(value = {}, factory = YamlPropertySourceFactory.class)
public @interface EnableConfigFiles {

  @AliasFor(annotation = PropertySource.class, attribute = "value")
  String[] value();

}
