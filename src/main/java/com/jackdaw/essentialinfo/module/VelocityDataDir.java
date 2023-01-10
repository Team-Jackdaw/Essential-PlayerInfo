package com.jackdaw.essentialinfo.module;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The annotation to indicate the Path instance is a Velocity Data Directory.
 * i.e. the Path annotated by the following in Main Class.
 * com.velocitypowered.api.plugin.annotation.DataDirectory
 *
 * @see com.velocitypowered.api.plugin.annotation.DataDirectory
 */
@Qualifier
@Target({FIELD, PARAMETER, METHOD})
@Retention(RUNTIME)
public @interface VelocityDataDir {}
