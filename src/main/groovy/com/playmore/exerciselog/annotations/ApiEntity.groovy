package com.playmore.exerciselog.annotations

import groovy.transform.AnnotationCollector
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includeFields = true, includePackage = false, ignoreNulls = true)
@EqualsAndHashCode
@AnnotationCollector
@interface ApiEntity {}