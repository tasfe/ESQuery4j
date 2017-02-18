/*
 * Copyright 2014 the original author or authors.
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
package lemon.elastic.query4j.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.google.common.base.Preconditions;

import lemon.elastic.query4j.util.ReflectionUtils.MethodCallback;

/**
 * {@link MethodCallback} to find annotations of a given type.
 * 
 * @author Oliver Gierke
 */
public class AnnotationDetectionMethodCallback<A extends Annotation> implements MethodCallback {

    private static final String MULTIPLE_FOUND = "Found annotation %s both on %s and %s! Make sure only one of them is annotated with it!";

    private final boolean enforceUniqueness;
    private final Class<A> annotationType;

    private Method foundMethod;
    private A annotation;

    /**
     * Creates a new {@link AnnotationDetectionMethodCallback} for the given annotation type.
     * 
     * @param annotationType must not be {@literal null}.
     */
    public AnnotationDetectionMethodCallback(Class<A> annotationType) {
        this(annotationType, false);
    }

    /**
     * Creates a new {@link AnnotationDetectionMethodCallback} for the given annotation type.
     * 
     * @param annotationType must not be {@literal null}.
     * @param enforceUniqueness whether to fail if multiple methods with the annotation are found.
     */
    public AnnotationDetectionMethodCallback(Class<A> annotationType, boolean enforceUniqueness) {

        Preconditions.checkNotNull(annotationType, "Annotation type must not be null!");

        this.annotationType = annotationType;
        this.enforceUniqueness = enforceUniqueness;
    }

    /**
     * @return the method
     */
    public Method getMethod() {
        return foundMethod;
    }

    /**
     * @return the annotation
     */
    public A getAnnotation() {
        return annotation;
    }

    /**
     * Returns whether an annotation was found.
     * 
     * @return
     */
    public boolean hasFoundAnnotation() {
        return annotation != null;
    }

    /* 
     * (non-Javadoc)
     * @see org.springframework.util.ReflectionUtils.MethodCallback#doWith(java.lang.reflect.Method)
     */
    @Override
    public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {

        if (foundMethod != null && !enforceUniqueness) {
            return;
        }

        A foundAnnotation = AnnotationUtils.findAnnotation(method, annotationType);

        if (foundAnnotation != null) {

            if (foundMethod != null && enforceUniqueness) {
                throw new IllegalStateException(String.format(MULTIPLE_FOUND, foundAnnotation.getClass().getName(), foundMethod, method));
            }

            this.annotation = foundAnnotation;
            this.foundMethod = method;
        }
    }
}