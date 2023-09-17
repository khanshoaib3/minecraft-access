package com.github.khanshoaib3.minecraft_access.test_utils.annotations;

import com.github.khanshoaib3.minecraft_access.test_utils.MockMinecraftClientWrapper;
import com.github.khanshoaib3.minecraft_access.test_utils.extensions.MockMinecraftClientExtension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Tag for generating {@link MockMinecraftClientWrapper} instances, see {@link MockMinecraftClientExtension}.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MockMinecraftClient {
}
