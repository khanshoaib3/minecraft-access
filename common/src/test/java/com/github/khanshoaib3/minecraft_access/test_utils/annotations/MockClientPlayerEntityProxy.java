package com.github.khanshoaib3.minecraft_access.test_utils.annotations;

import com.github.khanshoaib3.minecraft_access.test_utils.extensions.MockClientPlayerEntityProxyExtension;
import com.github.khanshoaib3.minecraft_access.utils.ClientPlayerEntityProxy;
import org.mockito.MockedStatic;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Tag for generating {@link MockedStatic} {@link ClientPlayerEntityProxy} instances, see {@link MockClientPlayerEntityProxyExtension}.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MockClientPlayerEntityProxy {
}
