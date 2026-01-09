package ru.ryatronth.sd.property.reader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

@Slf4j
public class YamlPropertySourceFactory implements PropertySourceFactory {

    private static final List<PropertySourceLoader> LOADERS =
        SpringFactoriesLoader.loadFactories(PropertySourceLoader.class, YamlPropertySourceFactory.class.getClassLoader());

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource encodedResource) throws IOException {
        Resource resource = encodedResource.getResource();

        if (!resource.exists()) {
            log.warn("Config resource does not exist: {}", resource.getDescription());
            return new CompositePropertySource(fallbackName(name, resource));
        }

        PropertySourceLoader loader = findLoader(extension(resource));
        if (loader == null) {
            throw new IllegalStateException("No PropertySourceLoader for resource: " + resource.getDescription());
        }

        String psName = fallbackName(name, resource);
        List<PropertySource<?>> loaded = loader.load(psName, resource);

        if (loaded == null || loaded.isEmpty()) {
            return new CompositePropertySource(psName);
        }

        if (loaded.size() == 1) {
            log.info("Loaded config: {}", resource.getDescription());
            return loaded.getFirst();
        }

        CompositePropertySource composite = new CompositePropertySource(psName);
        loaded.forEach(composite::addPropertySource);

        log.info("Loaded config (multi-source): {} -> parts: {}", resource.getDescription(), loaded.size());
        return composite;
    }

    private static PropertySourceLoader findLoader(String ext) {
        for (PropertySourceLoader loader : LOADERS) {
            for (String e : loader.getFileExtensions()) {
                if (e.equalsIgnoreCase(ext)) {
                    return loader;
                }
            }
        }
        return null;
    }

    private static String fallbackName(String name, Resource resource) {
        return (name != null && !name.isBlank()) ? name : "extra-config [" + resource.getDescription() + "]";
    }

    private static String extension(Resource resource) {
        String fn = resource.getFilename();
        if (fn == null) {
            return "";
        }
        int i = fn.lastIndexOf('.');
        return (i < 0) ? "" : fn.substring(i + 1).toLowerCase(Locale.ROOT);
    }

}
